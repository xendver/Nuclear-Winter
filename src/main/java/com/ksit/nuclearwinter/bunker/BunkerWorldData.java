package com.ksit.nuclearwinter.bunker;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BunkerWorldData extends SavedData {

    private final Map<UUID, BunkerData> bunkers = new HashMap<>();

    //GET
    public Map<UUID, BunkerData> getBunkers() {
        return Map.copyOf(bunkers);
    }

    public BunkerData get(UUID id) {
        return bunkers.get(id);
    }

    public void put(BunkerData bunker) {
        if (bunker == null || bunker.id == null) return;
        bunkers.put(bunker.id, bunker);
        setDirty();
    }

    public void remove(UUID id) {
        if (id == null) return;
        bunkers.remove(id);
        setDirty();
    }

    public boolean contains(UUID id) {
        return bunkers.containsKey(id);
    }

    //SAVE
    @Override
    public @NotNull CompoundTag save(@NotNull CompoundTag tag) {
        ListTag list = new ListTag();

        for (BunkerData bunker : bunkers.values()) {
            list.add(bunker.save());
        }

        tag.put("bunkers", list);
        return tag;
    }

    //LOAD
    public static BunkerWorldData load(CompoundTag tag) {

        BunkerWorldData data = new BunkerWorldData();
        ListTag list = tag.getList("bunkers", Tag.TAG_COMPOUND);

        for (Tag t : list) {
            CompoundTag compoundTag = (CompoundTag) t;
            BunkerData bunker = BunkerData.load(compoundTag);
            data.bunkers.put(bunker.id, bunker);
        }

        return data;
    }
}
