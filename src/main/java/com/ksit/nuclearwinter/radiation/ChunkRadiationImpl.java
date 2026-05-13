package com.ksit.nuclearwinter.radiation;

import net.minecraft.nbt.CompoundTag;

// IChunkRadiation, хранение уровня радиации одного чанка
public class ChunkRadiationImpl implements IChunkRadiation {

    private float radiationLevel = 0f;

    @Override
    public float getRadiationLevel() {
        return radiationLevel;
    }

    @Override
    public void setRadiationLevel(float level) {
        // Чтобы уровень не ушёл в минус
        this.radiationLevel = Math.max(0f, level);
    }

    @Override
    public void addRadiation(float amount) {
        // amount может быть отрицательным
        // Math.max(0, ...) не даёт уровню уйти ниже нуля
        this.radiationLevel = Math.max(0f, this.radiationLevel + amount);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putFloat("radiation", radiationLevel);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.radiationLevel = nbt.getFloat("radiation");
    }
}