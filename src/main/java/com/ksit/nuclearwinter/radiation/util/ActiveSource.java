package com.ksit.nuclearwinter.radiation.util;

import com.ksit.nuclearwinter.radiation.capability.source.RadiationImpl;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;

public class ActiveSource {
    private final BlockPos blockPos;
    private final int chunkX;
    private final int chunkZ;
    private final RadiationImpl radiation;

    public ActiveSource(BlockPos blockPos, RadiationImpl radiation) {
        this.blockPos = blockPos.immutable();
        ChunkPos chunkPos = new ChunkPos(blockPos);
        this.chunkX = chunkPos.x;
        this.chunkZ = chunkPos.z;
        this.radiation = radiation;
    }

    public BlockPos blockPos() {
        return blockPos;
    }

    public int chunkX() {
        return chunkX;
    }

    public int chunkZ() {
        return chunkZ;
    }

    public RadiationImpl radiation() {
        return radiation;
    }

    public ChunkPos chunkPos() {
        return new ChunkPos(chunkX, chunkZ);
    }

//    @Override
//    public boolean equals(Object object) {
//        if (this == object) return true;
//        if (!(object instanceof ActiveSource that)) return false;
//        return Objects.equals(blockPos, that.blockPos);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(blockPos);
//    }
}