package com.ksit.nuclearwinter.radiation.calc;

import com.ksit.nuclearwinter.radiation.api.RadiationConfig;
import com.ksit.nuclearwinter.radiation.capability.RadiationCapability;
import com.ksit.nuclearwinter.radiation.spatial.ChunkSpatialIndex;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.LevelChunk;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChunkRadiationSystem {

    public void update(ServerLevel level, ChunkSpatialIndex index) {

        Set<ChunkPos> affectedChunks = new HashSet<>();

        // 1. собираем affected chunks
        for (ChunkPos sourceChunk : index.getOccupiedChunks()) {

            for (ActiveSource source : index.getSources(sourceChunk)) {

                int radius = (int) Math.ceil(
                        source.radiation().getRadius()
                );

                for (int dx = -radius; dx <= radius; dx++) {
                    for (int dz = -radius; dz <= radius; dz++) {

                        affectedChunks.add(
                                new ChunkPos(
                                        source.chunkX() + dx,
                                        source.chunkZ() + dz
                                )
                        );
                    }
                }
            }
        }

        // 2. считаем радиацию
        for (ChunkPos pos : affectedChunks) {

            LevelChunk chunk = level.getChunk(pos.x, pos.z);

            chunk.getCapability(
                    RadiationCapability.CHUNK_RADIATION
            ).ifPresent(cap -> {

                // decay
                cap.setRadiationLevel(
                        cap.getRadiationLevel() *
                                (1f - RadiationConfig.DECAY_RATE)
                );

                // collect nearby sources (через query, а не вручную)
                List<ActiveSource> nearby =
                        collectNearby(index, pos, 8);

                if (!nearby.isEmpty()) {

                    float newRad =
                            RadiationCalculator.calcChunkRadiation(
                                    pos.x,
                                    pos.z,
                                    nearby
                            );

                    cap.addRadiation(newRad);
                }
            });
        }
    }

    public List<ActiveSource> collectNearby(
            ChunkSpatialIndex index,
            ChunkPos center,
            int radius
    ) {

        List<ActiveSource> result =
                new ArrayList<>();

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {

                ChunkPos pos = new ChunkPos(
                        center.x + dx,
                        center.z + dz
                );

                result.addAll(index.getSources(pos));
            }
        }

        return result;
    }
}
