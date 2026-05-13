package com.ksit.nuclearwinter.radiation;

import net.minecraft.nbt.CompoundTag;

// Интерфейс свойства радиации (является/не является)
// Прикрепляется через Capability к блокам, энтити и итемам
public interface IRadiation {

    float getInitialPower();
    void  setInitialPower(float power);

    // Радиус в чанках
    float getRadius();
    void  setRadius(float radius);

    // Потеря силы радиации за каждый чанк расстояния
    float getDecayPerChunk();
    void  setDecayPerChunk(float decay);

    CompoundTag serializeNBT();
    void        deserializeNBT(CompoundTag nbt);
}