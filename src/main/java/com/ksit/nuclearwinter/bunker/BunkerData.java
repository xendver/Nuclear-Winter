package com.ksit.nuclearwinter.bunker;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class BunkerData {
    private static final String NBT_ID = "id";
    private static final String NBT_CONTROLLER = "controller";
    private static final String NBT_SEALED = "sealed";
    private static final String NBT_SHELL = "shell";
    private static final String NBT_INNER = "inner";
    private static final String NBT_POS = "pos";

    public UUID id;
    public BlockPos controllerPos;
    public boolean sealed;
    public Set<BlockPos> shellBlocks;
    public Set<BlockPos> innerBlocks;

    public BunkerData(UUID id, BlockPos controllerPos, boolean sealed, Set<BlockPos> shellBlocks, Set<BlockPos> innerBlocks) {
        this.id = id;
        this.controllerPos = controllerPos;
        this.sealed = sealed;
        this.shellBlocks = new HashSet<>(shellBlocks);
        this.innerBlocks = new HashSet<>(innerBlocks);
    }

    public CompoundTag save() {
        CompoundTag tag = new CompoundTag();

        tag.putUUID(NBT_ID, id);
        tag.putLong(NBT_CONTROLLER, controllerPos.asLong());
        tag.putBoolean(NBT_SEALED, sealed);

        tag.put(NBT_SHELL, writePosSet(shellBlocks));
        tag.put(NBT_INNER, writePosSet(innerBlocks));

        return tag;
    }

    public static BunkerData load(CompoundTag tag) {

        return new BunkerData(
                tag.getUUID(NBT_ID),
                BlockPos.of(tag.getLong(NBT_CONTROLLER)),
                tag.getBoolean(NBT_SEALED),
                readPosSet(tag.getList(NBT_SHELL, Tag.TAG_COMPOUND)),
                readPosSet(tag.getList(NBT_INNER, Tag.TAG_COMPOUND))
        );
    }

    private static ListTag writePosSet(Set<BlockPos> positions) {
        ListTag list = new ListTag();

        for (BlockPos pos : positions) {
            CompoundTag entry = new CompoundTag();
            entry.putLong("pos", pos.asLong());
            list.add(entry);
        }

        return list;
    }

    private static Set<BlockPos> readPosSet(ListTag list) {
        Set<BlockPos> result = new HashSet<>();

        for (Tag t : list) {
            CompoundTag tag = (CompoundTag) t;
            result.add(BlockPos.of(tag.getLong("pos")));
        }

        return result;
    }
}
