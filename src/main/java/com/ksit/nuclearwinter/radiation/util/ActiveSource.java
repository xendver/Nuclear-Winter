package com.ksit.nuclearwinter.radiation.util;

import com.ksit.nuclearwinter.radiation.capability.source.Radiation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;

public class ActiveSource {
    private final BlockPos blockPos;
    private final int chunkX;
    private final int chunkZ;
    private final Radiation radiation;

    public ActiveSource(BlockPos blockPos, Radiation radiation) {
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

    public Radiation radiation() {
        return radiation;
    }

    public ChunkPos chunkPos() {
        return new ChunkPos(chunkX, chunkZ);
    }
}