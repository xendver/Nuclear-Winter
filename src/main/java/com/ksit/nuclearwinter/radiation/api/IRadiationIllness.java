package com.ksit.nuclearwinter.radiation.api;

import net.minecraft.nbt.CompoundTag;

// Интерфейс накопленного облучения entity/player
// Прикрепляется через Capability к каждой entity
public interface IRadiationIllness {

    // Накопленные points облучения
    float getRadiationPoints();

    void setRadiationPoints(float points);

    void addRadiationPoints(float amount); // amount может быть отрицательным (лечение)

    // Текущая стадия болезни (вычисляется из points)
    IllnessStage getStage();

    CompoundTag serializeNBT();

    void deserializeNBT(CompoundTag nbt);
}