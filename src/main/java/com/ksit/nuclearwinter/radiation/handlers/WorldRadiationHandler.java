package com.ksit.nuclearwinter.radiation.handlers;

import com.ksit.nuclearwinter.effect.ModEffects;
import com.ksit.nuclearwinter.effect.illness.StageOne;
import com.ksit.nuclearwinter.effect.illness.StageThree;
import com.ksit.nuclearwinter.effect.illness.StageTwo;
import com.ksit.nuclearwinter.network.PacketChunkRadiation;
import com.ksit.nuclearwinter.network.PacketHandler;
import com.ksit.nuclearwinter.radiation.ActiveSource;
import com.ksit.nuclearwinter.radiation.RadiationCapability;
import com.ksit.nuclearwinter.radiation.RadiationConfig;
import com.ksit.nuclearwinter.radiation.RadiationSourceManager;
import com.ksit.nuclearwinter.radiation.ResistanceRegistry;
import com.ksit.nuclearwinter.radiation.illness.IllnessStage;
import it.unimi.dsi.fastutil.longs.Long2FloatMap;
import it.unimi.dsi.fastutil.longs.Long2FloatOpenHashMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.*;

public class WorldRadiationHandler {

    // главный кэш/менеджер всех активных источников радиации мира
    private final RadiationSourceManager sourceManager = new RadiationSourceManager();

    // мапа, в которой будут хранится все вклады источников в определённый чанк
    private final Long2FloatOpenHashMap chunkActivities = new Long2FloatOpenHashMap();

    // == TICK =============================================
    @SubscribeEvent
    public void onLevelTick(TickEvent.LevelTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (event.level.isClientSide()) return;
        if (event.level.getGameTime() % 20 != 0) return; // раз в секунду

        ServerLevel level = (ServerLevel) event.level;

        sourceManager.rebuildFromStorage(level);
        updateChunkRadiation(level);
        applyIllnessEffects(level);
        sendRadiationPackets(level);
    }

    // == ЧАНКИ, ЗАГРУЖЕННЫЕ ВОКРУГ ИГРОКА =============================================
    public static Set<ChunkPos> getLoadedChunksForPlayer(ServerPlayer player) {
        // получаем мир игрока
        ServerLevel level = (ServerLevel) player.level();

        //дальность загрузки чанков
        int viewDistance = 4;

        //позиция игрока в чанках
        ChunkPos playerChunk = player.chunkPosition();
        // сюда мы будем складывать чанки без дубликатов
        Set<ChunkPos> loadedChunks = new HashSet<>();

        // перебор области вокруг игрока
        for (int dx = -viewDistance; dx <= viewDistance; dx++) {
            for (int dz = -viewDistance; dz <= viewDistance; dz++) {
                //смещаемся от игрока и получаем каждый чанк вокруг него
                ChunkPos chunkPos = new ChunkPos(playerChunk.x + dx, playerChunk.z + dz);

                LevelChunk chunk = level.getChunkSource().getChunkNow(chunkPos.x, chunkPos.z);

                if (chunk != null) {
                    loadedChunks.add(chunkPos);
                }
            }
        }

        return loadedChunks;
    }

    // =========================================================================================

    //    private List<LevelChunk> getLoadedChunks(ServerLevel level) {
    //
    //        List<LevelChunk> chunks = new ArrayList<>();
    //
    //        for (ServerPlayer player : level.players()) {
    //
    //            LevelChunk center =
    //                    level.getChunkAt(player.blockPosition());
    //
    //            int radius = 8;
    //
    //            for (int dx = -radius; dx <= radius; dx++) {
    //                for (int dz = -radius; dz <= radius; dz++) {
    //
    //                    int cx = center.getPos().x + dx;
    //                    int cz = center.getPos().z + dz;
    //
    //                    if (level.hasChunk(cx, cz)) {
    //
    //                        LevelChunk chunk =
    //                                level.getChunk(cx, cz);
    //
    //                        if (!chunks.contains(chunk)) {
    //                            chunks.add(chunk);
    //                        }
    //                    }
    //                }
    //            }
    //        }
    //
    //        return chunks;
    //    }

    // СБОР ИСТОЧНИКОВ
    //    private List<ActiveSource> collectSourcesRaw(ServerLevel level) {
    //
    //        List<ActiveSource> sources = new ArrayList<>();
    //
    //       for (ServerPlayer player : level.players()) {
    //            collectPlayerItemSources(player, sources);
    //       }
    //
    //        BlockRadiationStorage storage = BlockRadiationStorage.get(level);
    //
    //        for (Map.Entry<BlockPos, float[]> entry : storage.getAll().entrySet()) {
    //
    //            BlockPos pos = entry.getKey();
    //            float[] values = entry.getValue();
    //
    //            ChunkPos cpos = new ChunkPos(pos);
    //
    //            RadiationImpl rad = new RadiationImpl(values[0], values[1], values[2]);
    //
    //            sources.add(new ActiveSource(cpos.x, cpos.z, rad));
    //        }
    //
    //        return sources;
    //    }
    //
    //    private void rebuildSpatialIndex(ServerLevel level) {
    //
    //        this.spatialSources.clear();
    //
    //        for (ActiveSource source : collectSourcesRaw(level)) {
    //
    //            ChunkPos pos = new ChunkPos(source.chunkX(), source.chunkZ());
    //            List<ActiveSource> list = this.spatialSources.computeIfAbsent(pos, k -> new ArrayList<>());
    //            list.add(source);
    //        }
    //    }
    //    private void collectPlayerItemSources(Player player, List<ActiveSource> sources) {
    //
    //        int cx = level(player).getChunkAt(player.blockPosition()).getPos().x;
    //        int cz = level(player).getChunkAt(player.blockPosition()).getPos().z;
    //
    //        checkItem(player.getMainHandItem(), cx, cz, sources);
    //        checkItem(player.getOffhandItem(), cx, cz, sources);
    //
    //        for (ItemStack armor : player.getArmorSlots())
    //            checkItem(armor, cx, cz, sources);
    //
    //        for (int i = 0; i < player.getInventory().getContainerSize(); i++)
    //            checkItem(player.getInventory().getItem(i), cx, cz, sources);
    //    }
    //
    //    private void checkItem(ItemStack stack, int cx, int cz, List<ActiveSource> sources) {
    //
    //        if (stack.isEmpty()) return;
    //
    //        stack.getCapability(RadiationCapability.RADIATION_SOURCE).ifPresent(rad -> {
    //            if (rad.getInitialPower() > 0) {
    //                sources.add(new ActiveSource(cx, cz, rad));
    //            }
    //        });
    //    }

    // == ОБНОВЛЕНИЕ РАДИАЦИИ ЧАНКОВ =============================================
    private void updateChunkRadiation(ServerLevel level) {

        // Ключ = ChunkPos.asLong(x, z)
        // Значение = суммарная активность чанка за эту секунду
        final Long2FloatOpenHashMap chunkActivities = this.chunkActivities;
        chunkActivities.clear();
        chunkActivities.defaultReturnValue(0f);
        /*
         * ==========================================
         * ПЕРВЫЙ ПРОХОД
         * Собираем сумму вкладов всех источников
         * ==========================================
         */

        //пробег по всем источникам
        for (ActiveSource source : sourceManager.getAllSources()) {

            //получаем координаты источника
            int sourceChunkX = source.chunkX();
            int sourceChunkZ = source.chunkZ();

            //получаем радиус источника
            float radius = source.radiation().getRadius();
            int chunkRadius = (int) Math.ceil(radius);

            // добавление чанков с воздействием источников
            for (int dx = -chunkRadius; dx <= chunkRadius; dx++) {
                for (int dz = -chunkRadius; dz <= chunkRadius; dz++) {
                    int targetChunkX = sourceChunkX + dx;
                    int targetChunkZ = sourceChunkZ + dz;

                    //получаем чанк и проверяем находится он в прогрузке или нет
                    LevelChunk chunk = level.getChunkSource().getChunkNow(targetChunkX, targetChunkZ);
                    if (chunk == null) continue;


                    //ищем дистанцию от чанка до источника
                    float distanceX = targetChunkX - sourceChunkX;
                    float distanceZ = targetChunkZ - sourceChunkZ;
                    float distance = (float) Math.sqrt(distanceX * distanceX + distanceZ * distanceZ);

                    //высчитываем вклад источника в этот чанк
                    float contribution = source.radiation().getInitialPower() - source.radiation().getDecayPerChunk() * distance;

                    if (contribution <= 0) continue;

                    //добавляем это в мапу с вкладами источников
                    long key = ChunkPos.asLong(targetChunkX, targetChunkZ);
                    chunkActivities.addTo(key, contribution);
                }
            }
        }

        /*
         * ==========================================
         * ВТОРОЙ ПРОХОД
         * Записываем activity и pollution в capability
         * перенос из таблицы в капабилити
         * ==========================================
         */

        for (Long2FloatMap.Entry entry : chunkActivities.long2FloatEntrySet()) {

            //получаем чанк
            long key = entry.getLongKey();
            int chunkX = ChunkPos.getX(key);
            int chunkZ = ChunkPos.getZ(key);
            LevelChunk chunk = level.getChunkSource().getChunkNow(chunkX, chunkZ);

            if (chunk == null) continue;

            //получаем вклад всех источников за последнюю секунду
            float activity = entry.getFloatValue();


            chunk.getCapability(
                            RadiationCapability.CHUNK_RADIATION)
                    .ifPresent(cap -> {

                        /*
                         * ACTIVITY
                         * Всегда хранит сумму вкладов
                         * за текущий тик.
                         */

                        //записываем activity в capability
                        cap.setRadiationActivityLevel(activity);

                        /*
                         * POLLUTION
                         * Накопленная радиация.
                         */

                        //добавляем значение activity (вкладу всех источников за последнюю секунду) к pollution (радиационному загрязнению)
                        //и применяем decay
                        float pollution = cap.getRadiationPollutionLevel();

                        pollution *= (1f - RadiationConfig.DECAY_RATE);

                        pollution += activity;

                        cap.setRadiationPollutionLevel(pollution);
                    });
        }
    }

    // == ЛУЧЕВАЯ БОЛЕЗНЬ ================================================
    // с доки для формулы (IncomingRad * GlobalDoseMultiplier * playerMultiplier)
    // – (GlobalIllnessDecay + PlayerIllnessDecay)
    private static final float GLOBAL_DOSE_MULTIPLIER = 0.10f;
    private static final float GLOBAL_ILLNESS_DECAY = 0.00002f;

    private void applyIllnessEffects(ServerLevel level) {
        /*
         * Нужен для защиты от повторной обработки.
         *
         * Один и тот же чанк может попасть в радиус
         * нескольких игроков.
         *
         * Без этой проверки сущности внутри чанка
         * будут получать радиацию несколько раз за тик.
         */
        Set<Long> processedChunks = new HashSet<>();


        /*
         * ==================================================
         * ОБХОД ИГРОКОВ
         * ==================================================
         */
        for (ServerPlayer player : level.players()) {

            /*
             * Получаем все загруженные чанки,
             * которые находятся в зоне прогрузки игрока.
             */
            for (ChunkPos pos : getLoadedChunksForPlayer(player)) {

                long chunkKey = pos.toLong();

                /*
                 * Если этот чанк уже был обработан
                 * через другого игрока — пропускаем.
                 */
                if (!processedChunks.add(chunkKey))
                    continue;


                /*
                 * Получаем сам чанк.
                 */
                LevelChunk chunk = level.getChunk(pos.x, pos.z);

                /*
                 * ==================================================
                 * ПОЛУЧАЕМ РАДИАЦИЮ ЧАНКА
                 * ==================================================
                 */
                chunk.getCapability(RadiationCapability.CHUNK_RADIATION).ifPresent(chunkRad -> {

                    /*
                     * Activity = текущая активность
                     * за последнюю секунду.
                     */
                    float incomingRad = chunkRad.getRadiationActivityLevel();

                    /*
                     * ==================================================
                     * ПОЛУЧАЕМ СУЩНОСТИ ТОЛЬКО ИЗ ЭТОГО ЧАНКА
                     * ==================================================
                     */

                    //получаем ААВВ чанка
                    AABB chunkBox = new AABB(
                            pos.getMinBlockX(),
                            level.getMinBuildHeight(),
                            pos.getMinBlockZ(),

                            pos.getMaxBlockX() + 1,
                            level.getMaxBuildHeight(),
                            pos.getMaxBlockZ() + 1
                    );

                    //получаем сущности только из этого чанка
                    List<Entity> entities = level.getEntities(null, chunkBox);

                    /*
                     * ===================
                     * ОБРАБОТКА СУЩНОСТЕЙ
                     * ===================
                     */
                    for (Entity entity : entities) {

                        /*
                         * Нас интересуют только живые сущности.
                         */
                        if (!(entity instanceof LivingEntity living))
                            continue;

                        /*
                         * Получаем защиту сущности.
                         *
                         * doseMultiplier:
                         * множитель входящей радиации.
                         *
                         * illnessDecayBonus:
                         * бонус к естественному снижению
                         * лучевой болезни.
                         */
                        ResistanceRegistry.ProtectionData protection = ResistanceRegistry.getProtectionData(living);

                        float playerMultiplier = protection.doseMultiplier();
                        float playerIllnessDecay = protection.illnessDecayBonus();


                        /*
                         * ==================================================
                         * ПОЛУЧАЕМ КАПАБИЛИТИ ЛУЧЕВОЙ БОЛЕЗНИ
                         * ==================================================
                         */
                        living.getCapability(RadiationCapability.RADIATION_ILLNESS).ifPresent(illness -> {

                            /*
                             * Формула начисления лучевой болезни.
                             *
                             * Чем больше радиация чанка —
                             * тем быстрее растёт болезнь.
                             *
                             * Чем лучше защита —
                             * тем меньше прирост.
                             */
                            float radiationToAdd =
                                    (incomingRad * GLOBAL_DOSE_MULTIPLIER * playerMultiplier)
                                            - (GLOBAL_ILLNESS_DECAY + playerIllnessDecay);


                            /*
                             * Добавляем поинты болезни.
                             */
                            illness.addRadiationPoints(radiationToAdd);

                            /*
                             * Накладываем эффекты стадии.
                             */
                            applyStagePoisons(living, illness.getStage());
                        });
                    }
                });
            }
        }
    }


    // == ЭФФЕКТЫ ===========================================================
    private void applyStagePoisons(LivingEntity entity, IllnessStage stage) {

        final int DURATION = -1;

        switch (stage) {
            case STAGE_1 -> {
                entity.addEffect(new MobEffectInstance(
                        ModEffects.STAGE_ONE.get(),
                        DURATION,
                        0,
                        false,
                        false
                ));
            }
            case STAGE_2 -> {
                if (entity.hasEffect(ModEffects.STAGE_ONE.get())) {
                    StageOne.clear(entity);
                }
                entity.addEffect(new MobEffectInstance(
                        ModEffects.STAGE_TWO.get(),
                        DURATION,
                        0,
                        false,
                        false
                ));
            }
            case STAGE_3 -> {
                if (entity.hasEffect(ModEffects.STAGE_TWO.get())) {
                    StageTwo.clear(entity);
                }
                entity.addEffect(new MobEffectInstance(
                        ModEffects.STAGE_THREE.get(),
                        DURATION,
                        0,
                        false,
                        false
                ));
            }
            case STAGE_4 -> {
                if (entity.hasEffect(ModEffects.STAGE_THREE.get())) {
                    StageThree.clear(entity);
                }
                entity.addEffect(new MobEffectInstance(
                        ModEffects.STAGE_FOUR.get(),
                        DURATION,
                        0,
                        false,
                        false
                ));
            }
        }
    }

    // == ПАКЕТЫ ==========================================================================
    private void sendRadiationPackets(ServerLevel level) {

        for (ServerPlayer player : level.players()) {

            LevelChunk chunk = level.getChunkAt(player.blockPosition());

            chunk.getCapability(RadiationCapability.CHUNK_RADIATION).ifPresent(cap ->

                    PacketHandler.INSTANCE.send(net.minecraftforge.network.PacketDistributor.PLAYER.with(() -> player),

                            new PacketChunkRadiation(cap.getRadiationActivityLevel())));
        }
    }

    // == МЕНЕДЖЕР ИСТОЧНИКОВ ==========================================================================
    public RadiationSourceManager getSourceManager() {
        return sourceManager;
    }
}