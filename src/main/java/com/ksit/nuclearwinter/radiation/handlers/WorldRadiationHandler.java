package com.ksit.nuclearwinter.radiation.handlers;

import com.ksit.nuclearwinter.network.PacketChunkRadiation;
import com.ksit.nuclearwinter.network.PacketHandler;
import com.ksit.nuclearwinter.radiation.*;
import com.ksit.nuclearwinter.radiation.illness.IllnessStage;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import com.ksit.nuclearwinter.radiation.ActiveSource;

import java.util.*;


public class WorldRadiationHandler {
    private final Map<ChunkPos, List<ActiveSource>> spatialSources = new HashMap<>();
    public static Set<ChunkPos> getLoadedChunksForPlayer(ServerPlayer player){
        ServerLevel level = (ServerLevel) player.level();
        ChunkMap chunkMap = level.getChunkSource().chunkMap;

        int viewDistance = level.getServer().getPlayerList().getViewDistance();

        ChunkPos playerChunk = player.chunkPosition();
        Set<ChunkPos> loadedChunks = new HashSet<>();

        for (int dx = -viewDistance; dx <= viewDistance; dx++) {
            for (int dz = -viewDistance; dz <= viewDistance; dz++) {
                ChunkPos chunkPos = new ChunkPos(playerChunk.x + dx, playerChunk.z + dz);

                LevelChunk chunk = level.getChunkSource()
                        .getChunkNow(chunkPos.x, chunkPos.z);

                if (chunk != null) {
                    loadedChunks.add(chunkPos);
                }
            }
        }

        return loadedChunks;
    }

    @SubscribeEvent
    public void onLevelTick(TickEvent.LevelTickEvent event) {

        if (event.phase != TickEvent.Phase.END) return;
        if (event.level.isClientSide()) return;
        if (event.level.getGameTime() % 20 != 0) return;

        ServerLevel level = (ServerLevel) event.level;

        rebuildSpatialIndex(level);
        updateChunkRadiation(level);
        applyIllnessEffects(level);
        sendRadiationPackets(level);
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
    private List<ActiveSource> collectSourcesRaw(ServerLevel level) {

        List<ActiveSource> sources = new ArrayList<>();

        for (ServerPlayer player : level.players()) {
            collectPlayerItemSources(player, sources);
        }

        BlockRadiationStorage storage = BlockRadiationStorage.get(level);

        for (Map.Entry<BlockPos, float[]> entry : storage.getAll().entrySet()) {

            BlockPos pos = entry.getKey();
            float[] values = entry.getValue();

            ChunkPos cpos = new ChunkPos(pos);

            RadiationImpl rad = new RadiationImpl();
            rad.setInitialPower(values[0]);
            rad.setRadius(values[1]);
            rad.setDecayPerChunk(values[2]);

            sources.add(new ActiveSource(cpos.x, cpos.z, rad));
        }

        return sources;
    }

    private void rebuildSpatialIndex(ServerLevel level) {

        spatialSources.clear();

        List<ActiveSource> sources = collectSourcesRaw(level);

        for (ActiveSource s : sources) {

            ChunkPos pos = new ChunkPos(s.chunkX(), s.chunkZ());
            List<ActiveSource> list = spatialSources.computeIfAbsent(pos, k -> new ArrayList<>());

            if(list.isEmpty()){
                list.add(s);
                continue;
            }
            float newRadius = s.radiation().getRadius();
            float firstRadius = list.get(0).radiation().getRadius();

            if (newRadius > firstRadius){
                list.add(0,s);
            }
            else{
                list.add(s);
            }

        }
    }



    private void collectPlayerItemSources(Player player,
                                          List<ActiveSource> sources) {

        int cx = level(player).getChunkAt(player.blockPosition()).getPos().x;
        int cz = level(player).getChunkAt(player.blockPosition()).getPos().z;

        checkItem(player.getMainHandItem(), cx, cz, sources);
        checkItem(player.getOffhandItem(), cx, cz, sources);

        for (ItemStack armor : player.getArmorSlots())
            checkItem(armor, cx, cz, sources);

        for (int i = 0; i < player.getInventory().getContainerSize(); i++)
            checkItem(player.getInventory().getItem(i), cx, cz, sources);
    }

    private void checkItem(ItemStack stack,
                           int cx,
                           int cz,
                           List<ActiveSource> sources) {

        if (stack.isEmpty()) return;

        stack.getCapability(RadiationCapability.RADIATION_SOURCE)
                .ifPresent(rad -> {
                    if (rad.getInitialPower() > 0) {
                        sources.add(new ActiveSource(cx, cz, rad));
                    }
                });
    }

    private ServerLevel level(Entity entity) {
        return (ServerLevel) entity.level();
    }

    // == ОБНОВЛЕНИЕ РАДИАЦИИ ЧАНКОВ =============================================

    private void updateChunkRadiation(ServerLevel level) {

        Set<ChunkPos> affectedChunks = new HashSet<>();


        // берём только чанки с источниками
        for (ChunkPos sourcePos : spatialSources.keySet()) {

            List<ActiveSource> sources = spatialSources.get(sourcePos);
            if (sources == null) continue;

            for (ActiveSource s : sources) {

                int radius = (int) Math.ceil(s.radiation().getRadius());

                // добавление чанков с воздействием источников
                for (int dx = -radius; dx <= radius; dx++) {
                    for (int dz = -radius; dz <= radius; dz++) {

                        affectedChunks.add(new ChunkPos(
                                s.chunkX() + dx,
                                s.chunkZ() + dz
                        ));
                    }
                }
            }
        }

        for (ChunkPos pos : affectedChunks) {

            LevelChunk chunk = level.getChunk(pos.x, pos.z);

            chunk.getCapability(RadiationCapability.CHUNK_RADIATION)
                    .ifPresent(cap -> {

                        cap.setRadiationLevel(
                                cap.getRadiationLevel() *
                                        (1f - RadiationConfig.DECAY_RATE)
                        );

                        List<ActiveSource> nearby = new ArrayList<>();

                        for (int dx = -8; dx <= 8; dx++) {
                            for (int dz = -8; dz <= 8; dz++) {

                                ChunkPos check = new ChunkPos(pos.x + dx, pos.z + dz);

                                List<ActiveSource> list = spatialSources.get(check);

                                if (list != null) {
                                    for (ActiveSource src : list) {

                                        nearby.add(new ActiveSource(
                                                src.chunkX(),
                                                src.chunkZ(),
                                                src.radiation()
                                        ));
                                    }
                                }
                            }
                        }

                        if (!nearby.isEmpty()) {

                            float newRad = RadiationCalculator.calcChunkRadiation(
                                    pos.x,
                                    pos.z,
                                    nearby
                            );

                            cap.addRadiation(newRad);
                        }
                    });
        }
    }


    // == ЛУЧЕВАЯ БОЛЕЗНЬ ================================================

    //с доки для формулы (IncomingRad * GlobalDoseMultiplier * playerMultiplier)
    // – (GlobalIllnessDecay + PlayerIllnessDecay)
    private static final float GLOBAL_DOSE_MULTIPLIER = 0.10f;
    private static final float GLOBAL_ILLNESS_DECAY = 0.00002f;

    private void applyIllnessEffects(ServerLevel level) {

        List<LivingEntity> stageDamage1 = new ArrayList<>();
        List<LivingEntity> stageDamage3 = new ArrayList<>();

        for (ServerPlayer player : level.players()) {
            for (ChunkPos pos : getLoadedChunksForPlayer(player)) {

                LevelChunk chunk = level.getChunk(pos.x, pos.z);

                chunk.getCapability(RadiationCapability.CHUNK_RADIATION)
                        .ifPresent(chunkRad -> {

                            //временно (Артём должен переписать)
                            float incomingRad = chunkRad.getRadiationLevel();
                            if (incomingRad <= 0f) return;

                            float chunkLevel = chunkRad.getRadiationLevel();

                            level.getEntities().getAll().forEach(entity -> {

                                if (!(entity instanceof LivingEntity living)) return;

                                if (!level.getChunkAt(entity.blockPosition())
                                        .getPos().equals(chunk.getPos()))
                                    return;

                                //переменные для нового просчёта поинтов радиации по доке
                                ResistanceRegistry.ProtectionData protection =
                                        ResistanceRegistry.getProtectionData(entity);

                                float playerMultiplier = protection.doseMultiplier();
                                float playerIllnessDecay = protection.illnessDecayBonus();

                                if (playerMultiplier <= 0f) {
                                    return;
                                }

                                entity.getCapability(RadiationCapability.RADIATION_ILLNESS)
                                        .ifPresent(illness -> {

                                            //новая формула для добавления поинтов радиации по доке
                                            float radiationToAdd = (incomingRad * GLOBAL_DOSE_MULTIPLIER * playerMultiplier)
                                                    - (GLOBAL_ILLNESS_DECAY + playerIllnessDecay);
                                            if (radiationToAdd < 0f) {
                                                radiationToAdd = 0f;
                                            }

                                            illness.addRadiationPoints(radiationToAdd);

                                            applyStagePoisons(living, illness.getStage());

                                            if (illness.getStage() == IllnessStage.STAGE_3)
                                                stageDamage1.add(living);

                                            if (illness.getStage() == IllnessStage.STAGE_4)
                                                stageDamage3.add(living);
                                        });
                            });
                        });
            }
        }

        stageDamage1.forEach(e -> e.hurt(e.level().damageSources().magic(), 1.0f));
        stageDamage3.forEach(e -> e.hurt(e.level().damageSources().magic(), 3.0f));
    }

    // == ЭФФЕКТЫ ===========================================================

    private void applyStagePoisons(LivingEntity entity,
                                   IllnessStage stage) {

        final int D = 600;

        switch (stage) {

            case NONE -> {}

            case STAGE_1 -> {

                entity.addEffect(new MobEffectInstance(
                        MobEffects.MOVEMENT_SLOWDOWN,
                        D, 0, false, false));

                entity.addEffect(new MobEffectInstance(
                        MobEffects.WEAKNESS,
                        D, 0, false, false));

                entity.addEffect(new MobEffectInstance(
                        MobEffects.CONFUSION,
                        D, 0, false, false));
            }

            case STAGE_2 -> {

                entity.addEffect(new MobEffectInstance(
                        MobEffects.MOVEMENT_SLOWDOWN,
                        D, 1, false, false));

                entity.addEffect(new MobEffectInstance(
                        MobEffects.WEAKNESS,
                        D, 1, false, false));

                entity.addEffect(new MobEffectInstance(
                        MobEffects.CONFUSION,
                        D, 0, false, false));

                entity.addEffect(new MobEffectInstance(
                        MobEffects.HUNGER,
                        D, 1, false, false));

                entity.addEffect(new MobEffectInstance(
                        MobEffects.DIG_SLOWDOWN,
                        D, 1, false, false));
            }

            case STAGE_3 -> {

                entity.addEffect(new MobEffectInstance(
                        MobEffects.MOVEMENT_SLOWDOWN,
                        D, 2, false, false));

                entity.addEffect(new MobEffectInstance(
                        MobEffects.WEAKNESS,
                        D, 2, false, false));

                entity.addEffect(new MobEffectInstance(
                        MobEffects.CONFUSION,
                        D, 1, false, false));

                entity.addEffect(new MobEffectInstance(
                        MobEffects.HUNGER,
                        D, 2, false, false));

                entity.addEffect(new MobEffectInstance(
                        MobEffects.BLINDNESS,
                        D, 0, false, false));
            }

            case STAGE_4 -> {

                entity.addEffect(new MobEffectInstance(
                        MobEffects.MOVEMENT_SLOWDOWN,
                        D, 3, false, false));

                entity.addEffect(new MobEffectInstance(
                        MobEffects.WEAKNESS,
                        D, 3, false, false));

                entity.addEffect(new MobEffectInstance(
                        MobEffects.CONFUSION,
                        D, 1, false, false));

                entity.addEffect(new MobEffectInstance(
                        MobEffects.HUNGER,
                        D, 3, false, false));

                entity.addEffect(new MobEffectInstance(
                        MobEffects.BLINDNESS,
                        D, 0, false, false));

                entity.addEffect(new MobEffectInstance(
                        MobEffects.WITHER,
                        D, 1, false, false));
            }
        }
    }

    // == ПАКЕТЫ ==========================================================================

    private void sendRadiationPackets(ServerLevel level) {

        for (ServerPlayer player : level.players()) {

            LevelChunk chunk =
                    level.getChunkAt(player.blockPosition());

            chunk.getCapability(RadiationCapability.CHUNK_RADIATION)
                    .ifPresent(cap ->

                            PacketHandler.INSTANCE.send(
                                    net.minecraftforge.network.PacketDistributor.PLAYER.with(
                                            () -> player),

                                    new PacketChunkRadiation(
                                            cap.getRadiationLevel())
                            )
                    );
        }
    }
}
