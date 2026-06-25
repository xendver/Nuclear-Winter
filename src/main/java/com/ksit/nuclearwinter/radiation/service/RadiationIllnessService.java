package com.ksit.nuclearwinter.radiation.service;

import com.ksit.nuclearwinter.effect.ModEffects;
import com.ksit.nuclearwinter.effect.illness.StageOne;
import com.ksit.nuclearwinter.effect.illness.StageThree;
import com.ksit.nuclearwinter.effect.illness.StageTwo;
import com.ksit.nuclearwinter.radiation.api.IllnessStage;
import com.ksit.nuclearwinter.radiation.capability.RadiationCapability;
import com.ksit.nuclearwinter.radiation.registry.ResistanceRegistry;
import com.ksit.nuclearwinter.radiation.util.ChunkUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.AABB;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

// ЛУЧЕВАЯ БОЛЕЗНЬ
// с доки для формулы (IncomingRad * GlobalDoseMultiplier * playerMultiplier)
// – (GlobalIllnessDecay + PlayerIllnessDecay)
public class RadiationIllnessService {
    private static final float GLOBAL_DOSE_MULTIPLIER = 0.10f;
    private static final float GLOBAL_ILLNESS_DECAY = 0.00002f;

    public void applyIllnessEffects(ServerLevel level) {
        /* Нужен для защиты от повторной обработки.
           Один и тот же чанк может попасть в радиус нескольких игроков.
           Без этой проверки сущности внутри чанка будут получать радиацию несколько раз за тик. */
        Set<Long> processedChunks = new HashSet<>();

        // ОБХОД ИГРОКОВ
        for (ServerPlayer player : level.players()) {

            //Получаем все загруженные чанки, которые находятся в зоне прогрузки игрока.
            for (ChunkPos pos : ChunkUtil.getLoadedChunksForPlayer(player)) {

                long chunkKey = pos.toLong();

                // Если этот чанк уже был обработан через другого игрока — пропускаем.
                if (!processedChunks.add(chunkKey))
                    continue;

                // Получаем сам чанк.
                LevelChunk chunk = level.getChunk(pos.x, pos.z);

                //ПОЛУЧАЕМ РАДИАЦИЮ ЧАНКА
                chunk.getCapability(RadiationCapability.CHUNK_RADIATION).ifPresent(chunkRad -> {

                    // Activity = текущая активность за последнюю секунду.
                    float incomingRad = chunkRad.getRadiationActivityLevel();

                    ///ПОЛУЧАЕМ СУЩНОСТИ ТОЛЬКО ИЗ ЭТОГО ЧАНКА
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

                    // ОБРАБОТКА СУЩНОСТЕЙ
                    for (Entity entity : entities) {

                        //Нас интересуют только живые сущности.
                        if (!(entity instanceof LivingEntity living))
                            continue;

                        /* Получаем защиту сущности.
                         * doseMultiplier: множитель входящей радиации.
                         * illnessDecayBonus:бонус к естественному снижению лучевой болезни. */
                        ResistanceRegistry.ProtectionData protection = ResistanceRegistry.getProtectionData(living);

                        float playerMultiplier = protection.doseMultiplier();
                        float playerIllnessDecay = protection.illnessDecayBonus();


                        // ПОЛУЧАЕМ КАПАБИЛИТИ ЛУЧЕВОЙ БОЛЕЗНИ
                        living.getCapability(RadiationCapability.RADIATION_ILLNESS).ifPresent(illness -> {

                            /* Формула начисления лучевой болезни.
                               Чем больше радиация чанка — тем быстрее растёт болезнь.
                               Чем лучше защита —тем меньше прирост. */
                            float radiationToAdd =
                                    (incomingRad * GLOBAL_DOSE_MULTIPLIER * playerMultiplier)
                                            - (GLOBAL_ILLNESS_DECAY + playerIllnessDecay);

                            // Добавляем поинты болезни.
                            illness.addRadiationPoints(radiationToAdd);

                            ///Накладываем эффекты стадии.
                            applyStagePoisons(living, illness.getStage());
                        });
                    }
                });
            }
        }
    }


    // ЭФФЕКТЫ
    private static void applyStagePoisons(LivingEntity entity, IllnessStage stage) {

        final int DURATION = -1;

        switch (stage) {
            case STAGE_1 -> {
                if (!entity.hasEffect(ModEffects.RAD_AWAY_EFFECT.get())) {
                    entity.addEffect(new MobEffectInstance(
                            ModEffects.STAGE_ONE.get(),
                            DURATION,
                            0,
                            false,
                            false
                    ));
                }
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
}
