package com.ksit.nuclearwinter.radiation.pipeline;

import com.ksit.nuclearwinter.radiation.calc.ActiveSource;
import com.ksit.nuclearwinter.radiation.calc.ChunkRadiationSystem;
import com.ksit.nuclearwinter.radiation.illness.IllnessSystem;
import com.ksit.nuclearwinter.radiation.network.RadiationNetworkSystem;
import com.ksit.nuclearwinter.radiation.spatial.ChunkSpatialIndex;
import com.ksit.nuclearwinter.radiation.spatial.SpatialIndexBuilder;
import com.ksit.nuclearwinter.radiation.world.source.SourceCollector;
import net.minecraft.server.level.ServerLevel;


import java.util.List;


public class RadiationPipeline {
    public static final RadiationPipeline INSTANCE = new RadiationPipeline();

    private final SourceCollector sourceCollector = new SourceCollector();

    private final SpatialIndexBuilder spatialIndex = new SpatialIndexBuilder();

    private final ChunkRadiationSystem chunkSystem = new ChunkRadiationSystem();

    private final IllnessSystem illnessSystem = new IllnessSystem();

    private final RadiationNetworkSystem networkSystem = new RadiationNetworkSystem();

    public void tick(ServerLevel level) {

        // 1. SOURCES
        List<ActiveSource> sources = sourceCollector.collect(level);

        // 2. SPATIAL INDEX (FIXED TYPE)
        ChunkSpatialIndex index = spatialIndex.build(sources);

        // 3. RADIATION FIELD
        chunkSystem.update(level, index);

        // 4. ILLNESS
        illnessSystem.apply(level, index);

        // 5. NETWORK SYNC
        networkSystem.send(level);
    }
}
