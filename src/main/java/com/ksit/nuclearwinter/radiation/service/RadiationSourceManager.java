package com.ksit.nuclearwinter.radiation.service;

import com.ksit.nuclearwinter.radiation.capability.source.RadiationImpl;
import com.ksit.nuclearwinter.radiation.storage.BlockRadiationStorage;
import com.ksit.nuclearwinter.radiation.util.ActiveSource;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RadiationSourceManager {

    private final Map<BlockPos, ActiveSource> sourceCache = new HashMap<>();

    public void clearCache() {
        sourceCache.clear();
    }

    public void addOrUpdateSource(BlockPos pos, RadiationImpl radiation) {
        if (pos == null || radiation == null) {
            return;
        }

        BlockPos immutablePos = pos.immutable();
        ActiveSource source = new ActiveSource(immutablePos, radiation);

        sourceCache.put(immutablePos, source);
    }

    public void removeSource(BlockPos pos) {
        if (pos == null) {
            return;
        }

        sourceCache.remove(pos);
    }

    public ActiveSource getSource(BlockPos pos) {
        return sourceCache.get(pos);
    }

    public boolean hasSource(BlockPos pos) {
        return sourceCache.containsKey(pos);
    }

    public Collection<ActiveSource> getAllSources() {
        return Collections.unmodifiableCollection(sourceCache.values());
    }

    public void rebuildFromStorage(ServerLevel level) {
        clearCache();

        BlockRadiationStorage storage = BlockRadiationStorage.get(level);

        for (Map.Entry<BlockPos, float[]> entry : storage.getAll().entrySet()) {
            BlockPos pos = entry.getKey();
            float[] values = entry.getValue();

            RadiationImpl radiation = new RadiationImpl(
                    values[0],
                    values[1],
                    values[2]
            );

            addOrUpdateSource(pos, radiation);
        }
    }

    public static ChunkPos toChunkPos(BlockPos blockPos) {
        return new ChunkPos(blockPos);
    }
}