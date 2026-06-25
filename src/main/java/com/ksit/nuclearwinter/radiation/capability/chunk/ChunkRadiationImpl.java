package com.ksit.nuclearwinter.radiation.capability.chunk;

import com.ksit.nuclearwinter.radiation.api.IChunkRadiation;
import net.minecraft.nbt.CompoundTag;

// IChunkRadiation, хранение уровня радиации одного чанка
public class ChunkRadiationImpl implements IChunkRadiation {

    private float radiationPollutionLevel = 0f;
    private float radiationActivityLevel = 0f;

    @Override
    public float getRadiationPollutionLevel() {
        return this.radiationPollutionLevel;
    }

    @Override
    public void setRadiationPollutionLevel(float level) {
        // Чтобы уровень не ушёл в минус
        this.radiationPollutionLevel = Math.max(0f, level);
    }

    @Override
    public void addRadiationPollution(float amount) {
        // amount может быть отрицательным
        // Math.max(0, ...) не даёт уровню уйти ниже нуля
        this.radiationPollutionLevel = Math.max(0f, this.radiationPollutionLevel + amount);
    }

    @Override
    public float getRadiationActivityLevel() { return this.radiationActivityLevel; }

    @Override
    public void setRadiationActivityLevel(float level) {
        // Чтобы уровень не ушёл в минус
        this.radiationActivityLevel = Math.max(0f, level);
    }

    @Override
    public void addRadiationActivity(float amount){
        this.radiationActivityLevel = Math.max(0f, this.radiationActivityLevel + amount);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putFloat("radiationPollution", this.radiationPollutionLevel);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.radiationPollutionLevel = nbt.getFloat("radiationPollution");
    }
}