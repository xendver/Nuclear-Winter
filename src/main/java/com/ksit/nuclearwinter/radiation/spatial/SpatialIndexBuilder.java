package com.ksit.nuclearwinter.radiation.spatial;

import com.ksit.nuclearwinter.radiation.calc.ActiveSource;
import net.minecraft.world.level.ChunkPos;

import java.util.List;

public class SpatialIndexBuilder {
    public ChunkSpatialIndex build(List<ActiveSource> sources) {

        ChunkSpatialIndex index =
                new ChunkSpatialIndex();

        for (ActiveSource source : sources) {

            ChunkPos pos = new ChunkPos(
                    source.chunkX(),
                    source.chunkZ()
            );

            index.addSource(pos, source);
        }

        return index;
    }
}
