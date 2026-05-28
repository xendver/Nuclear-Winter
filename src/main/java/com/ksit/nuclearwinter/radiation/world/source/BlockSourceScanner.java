package com.ksit.nuclearwinter.radiation.world.source;

import com.ksit.nuclearwinter.radiation.calc.ActiveSource;
import com.ksit.nuclearwinter.radiation.capability.impl.RadiationImpl;
import com.ksit.nuclearwinter.radiation.storage.BlockRadiationStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;

import java.util.List;
import java.util.Map;

public class BlockSourceScanner {
    public void scan(ServerLevel level, List<ActiveSource> out) {

        BlockRadiationStorage storage = BlockRadiationStorage.get(level);

        for (Map.Entry<BlockPos, float[]> entry : storage.getAll().entrySet()) {

            BlockPos pos = entry.getKey();
            float[] data = entry.getValue();

            ChunkPos chunk = new ChunkPos(pos);

            RadiationImpl rad = new RadiationImpl();
            rad.setInitialPower(data[0]);
            rad.setRadius(data[1]);
            rad.setDecayPerChunk(data[2]);

            out.add(new ActiveSource(
                    chunk.x,
                    chunk.z,
                    rad
            ));
        }
    }
}
