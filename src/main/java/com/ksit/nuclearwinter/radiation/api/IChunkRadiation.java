package com.ksit.nuclearwinter.radiation.api;

import net.minecraft.nbt.CompoundTag;

// Интерфейс уровня радиации чанка
// Прикрепляется через Capability к каждому чанку мира
public interface IChunkRadiation {

    float getRadiationPollutionLevel();
    void setRadiationPollutionLevel(float level);

    // Добавление радиации; amount может быть отрицательным
    void addRadiationPollution(float amount);

    public float getRadiationActivityLevel();
    public void setRadiationActivityLevel(float level);
    public void addRadiationActivity(float amount);

    CompoundTag serializeNBT();
    void        deserializeNBT(CompoundTag nbt);
}