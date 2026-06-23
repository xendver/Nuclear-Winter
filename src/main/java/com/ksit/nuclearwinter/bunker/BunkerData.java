package com.ksit.nuclearwinter.bunker;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;

import java.util.UUID;

public class BunkerData {

    public UUID id;
    public BlockPos controllerPos;
    public boolean sealed;

    public BunkerData(UUID id, BlockPos controllerPos, boolean sealed) {
        this.id = id;
        this.controllerPos = controllerPos;
        this.sealed = sealed;
    }

    public CompoundTag save() {
        CompoundTag tag = new CompoundTag();

        tag.putUUID("id", id);
        tag.putLong("controller", controllerPos.asLong());
        tag.putBoolean("sealed", sealed);

        return tag;
    }

    public static BunkerData load(CompoundTag tag) {

        UUID id = tag.getUUID("id");
        BlockPos pos = BlockPos.of(tag.getLong("controller"));
        boolean sealed = tag.getBoolean("sealed");

        return new BunkerData(id, pos, sealed);
    }
}
