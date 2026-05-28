package com.ksit.nuclearwinter.radiation.api;

import net.minecraft.nbt.CompoundTag;

// Интерфейс уровня радиации чанка
// Прикрепляется через Capability к каждому чанку мира
public interface IChunkRadiation {

    float getRadiationLevel();

    void setRadiationLevel(float level);

    // Добавление радиации; amount может быть отрицательным
    void addRadiation(float amount);

    CompoundTag serializeNBT();

    void deserializeNBT(CompoundTag nbt);
}