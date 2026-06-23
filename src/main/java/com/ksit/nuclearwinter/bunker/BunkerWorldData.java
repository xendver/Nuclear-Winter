package com.ksit.nuclearwinter.bunker;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BunkerWorldData extends SavedData {

    private final Map<UUID, BunkerData> bunkers = new HashMap<>();

    // ===== GET =====

    public Map<UUID, BunkerData> getBunkers() {
        return bunkers;
    }

    public void add(BunkerData data) {
        bunkers.put(data.id, data);
        setDirty();
    }

    public void remove(UUID id) {
        bunkers.remove(id);
        setDirty();
    }

    // ===== SAVE =====

    @Override
    public CompoundTag save(CompoundTag tag) {

        ListTag list = new ListTag();

        for (BunkerData data : bunkers.values()) {
            list.add(data.save());
        }

        tag.put("bunkers", list);

        return tag;
    }

    // ===== LOAD =====

    public static BunkerWorldData load(CompoundTag tag) {

        BunkerWorldData data = new BunkerWorldData();

        ListTag list = tag.getList("bunkers", Tag.TAG_COMPOUND);

        for (Tag t : list) {
            CompoundTag ct = (CompoundTag) t;
            BunkerData bunker = BunkerData.load(ct);
            data.bunkers.put(bunker.id, bunker);
        }

        return data;
    }
}
