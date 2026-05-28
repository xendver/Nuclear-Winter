package com.ksit.nuclearwinter.radiation.spatial;

import com.ksit.nuclearwinter.radiation.calc.ActiveSource;
import net.minecraft.world.level.ChunkPos;

import java.util.*;

public class ChunkSpatialIndex {
    private final Map<ChunkPos, List<ActiveSource>> chunks =
            new HashMap<>();

    public void addSource(ChunkPos pos,
                          ActiveSource source) {

        chunks.computeIfAbsent(
                pos,
                k -> new ArrayList<>()
        ).add(source);
    }

    public List<ActiveSource> getSources(ChunkPos pos) {

        return chunks.getOrDefault(
                pos,
                Collections.emptyList()
        );
    }

    public Set<ChunkPos> getOccupiedChunks() {
        return chunks.keySet();
    }
}
