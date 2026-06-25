package com.ksit.nuclearwinter.radiation.storage;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.HashMap;
import java.util.Map;

// WorldSavedData - данные сохраняются в файл мира и переживают рестарт сервера
// "NW_BlockRadiation" - имя файла сохранения в папке мира
public class BlockRadiationStorage extends SavedData {

    private static final String DATA_NAME = "NW_BlockRadiation";

    // Позиция блока -> [initialPower, radius, decayPerChunk]
    private final Map<BlockPos, float[]> radiationMap = new HashMap<>();

    public BlockRadiationStorage() {}

    // Получение или создание хранилище для данного мира
    public static BlockRadiationStorage get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(
                // Вызывается если файл уже есть на диске
                tag -> {
                    BlockRadiationStorage storage = new BlockRadiationStorage();
                    storage.load(tag);
                    return storage;
                },
                // Вызывается если файла нет
                BlockRadiationStorage::new,
                DATA_NAME
        );
    }

    // Запись свойства радиации для блока по позиции
    public void setRadiation(BlockPos pos, float power, float radius, float decay) {
        radiationMap.put(pos, new float[]{ power, radius, decay });
        setDirty();
    }

    // Получение свойства радиации блока (null если блок не источник)
    public float[] getRadiation(BlockPos pos) {
        return radiationMap.get(pos);
    }

    // Удаление свойства радиации с блока
    public void removeRadiation(BlockPos pos) {
        radiationMap.remove(pos);
        setDirty();
    }

    // Получение всей карты (для обхода в WorldRadiationHandler)
    public Map<BlockPos, float[]> getAll() {
        return radiationMap;
    }

    // == NBT =================================================================
    @Override
    public CompoundTag save(CompoundTag compound) {
        ListTag list = new ListTag();
        for (Map.Entry<BlockPos, float[]> entry : radiationMap.entrySet()) {
            CompoundTag tag = new CompoundTag();
            // Сохранение координат блока
            tag.putInt("x",     entry.getKey().getX());
            tag.putInt("y",     entry.getKey().getY());
            tag.putInt("z",     entry.getKey().getZ());
            // Сохранение трёх значений радиации
            tag.putFloat("power",  entry.getValue()[0]);
            tag.putFloat("radius", entry.getValue()[1]);
            tag.putFloat("decay",  entry.getValue()[2]);
            list.add(tag);
        }
        compound.put("blocks", list);
        return compound;
    }

    private void load(CompoundTag compound) {
        radiationMap.clear();
        ListTag list = compound.getList("blocks", Tag.TAG_COMPOUND);
        for (int i = 0; i < list.size(); i++) {
            CompoundTag tag = list.getCompound(i);
            BlockPos pos = new BlockPos(
                    tag.getInt("x"),
                    tag.getInt("y"),
                    tag.getInt("z")
            );
            radiationMap.put(pos, new float[]{
                    tag.getFloat("power"),
                    tag.getFloat("radius"),
                    tag.getFloat("decay")
            });
        }
    }
}