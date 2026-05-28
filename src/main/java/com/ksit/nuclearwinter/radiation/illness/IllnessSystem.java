package com.ksit.nuclearwinter.radiation.illness;

import com.ksit.nuclearwinter.radiation.calc.ResistanceRegistry;
import com.ksit.nuclearwinter.radiation.capability.RadiationCapability;
import com.ksit.nuclearwinter.radiation.spatial.ChunkSpatialIndex;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.LevelChunk;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IllnessSystem {

    public void apply(ServerLevel level,
                      ChunkSpatialIndex index) {

        List<LivingEntity> stageDamage1 = new ArrayList<>();
        List<LivingEntity> stageDamage3 = new ArrayList<>();

            for (ChunkPos pos :index.getOccupiedChunks()) {

                LevelChunk chunk = level.getChunk(pos.x, pos.z);

                chunk.getCapability(RadiationCapability.CHUNK_RADIATION)
                        .ifPresent(chunkRad -> {

                            if (chunkRad.getRadiationLevel() <= 0) return;

                            float chunkLevel = chunkRad.getRadiationLevel();

                            level.getEntitiesOfClass(
                                    LivingEntity.class,
                                    new net.minecraft.world.phys.AABB(
                                            pos.x * 16, -64, pos.z * 16,
                                            pos.x * 16 + 16, 320, pos.z * 16 + 16
                                    )
                            ).forEach(living -> {

                                float multiplier =
                                        ResistanceRegistry.getDoseMultiplier(living);

                                if (multiplier <= 0f) return;

                                living.getCapability(
                                        RadiationCapability.RADIATION_ILLNESS
                                ).ifPresent(illness -> {

                                    illness.addRadiationPoints(
                                            chunkLevel * 0.1f * multiplier
                                    );

                                    applyStagePoisons(living, illness.getStage());

                                    if (illness.getStage() == IllnessStage.STAGE_3)
                                        stageDamage1.add(living);

                                    if (illness.getStage() == IllnessStage.STAGE_4)
                                        stageDamage3.add(living);
                                });
                            });
                        });
            }


        for (LivingEntity e : stageDamage1) {
            e.hurt(e.level().damageSources().magic(), 1.0f);
        }

        for (LivingEntity e : stageDamage3) {
            e.hurt(e.level().damageSources().magic(), 3.0f);
        }
    }

    private void applyStagePoisons(LivingEntity entity,
                                   IllnessStage stage) {

        final int D = 600;

        switch (stage) {

            case NONE -> {
            }

            case STAGE_1 -> {
                entity.addEffect(new MobEffectInstance(
                        MobEffects.MOVEMENT_SLOWDOWN, D, 0, false, false));
                entity.addEffect(new MobEffectInstance(
                        MobEffects.WEAKNESS, D, 0, false, false));
                entity.addEffect(new MobEffectInstance(
                        MobEffects.CONFUSION, D, 0, false, false));
            }

            case STAGE_2 -> {
                entity.addEffect(new MobEffectInstance(
                        MobEffects.MOVEMENT_SLOWDOWN, D, 1, false, false));
                entity.addEffect(new MobEffectInstance(
                        MobEffects.WEAKNESS, D, 1, false, false));
                entity.addEffect(new MobEffectInstance(
                        MobEffects.CONFUSION, D, 0, false, false));
                entity.addEffect(new MobEffectInstance(
                        MobEffects.HUNGER, D, 1, false, false));
                entity.addEffect(new MobEffectInstance(
                        MobEffects.DIG_SLOWDOWN, D, 1, false, false));
            }

            case STAGE_3 -> {
                entity.addEffect(new MobEffectInstance(
                        MobEffects.MOVEMENT_SLOWDOWN, D, 2, false, false));
                entity.addEffect(new MobEffectInstance(
                        MobEffects.WEAKNESS, D, 2, false, false));
                entity.addEffect(new MobEffectInstance(
                        MobEffects.CONFUSION, D, 1, false, false));
                entity.addEffect(new MobEffectInstance(
                        MobEffects.HUNGER, D, 2, false, false));
                entity.addEffect(new MobEffectInstance(
                        MobEffects.BLINDNESS, D, 0, false, false));
            }

            case STAGE_4 -> {
                entity.addEffect(new MobEffectInstance(
                        MobEffects.MOVEMENT_SLOWDOWN, D, 3, false, false));
                entity.addEffect(new MobEffectInstance(
                        MobEffects.WEAKNESS, D, 3, false, false));
                entity.addEffect(new MobEffectInstance(
                        MobEffects.CONFUSION, D, 1, false, false));
                entity.addEffect(new MobEffectInstance(
                        MobEffects.HUNGER, D, 3, false, false));
                entity.addEffect(new MobEffectInstance(
                        MobEffects.BLINDNESS, D, 0, false, false));
                entity.addEffect(new MobEffectInstance(
                        MobEffects.WITHER, D, 1, false, false));
            }
        }
    }
}
