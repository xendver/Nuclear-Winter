package com.ksit.nuclearwinter.radiation.capability.impl;

import com.ksit.nuclearwinter.radiation.api.IRadiationIllness;
import com.ksit.nuclearwinter.radiation.illness.IllnessStage;
import net.minecraft.nbt.CompoundTag;

public class RadiationIllnessImpl implements IRadiationIllness {

    private float radiationPoints = 0f;

    @Override public float getRadiationPoints() { return radiationPoints; }

    @Override
    public void setRadiationPoints(float points) {
        this.radiationPoints = Math.max(0f, points);
    }

    @Override
    public void addRadiationPoints(float amount) {
        this.radiationPoints = Math.max(0f, this.radiationPoints + amount);
    }

    // Динамически вычисляется стадия из текущих points
    @Override
    public IllnessStage getStage() {
        return IllnessStage.fromPoints(radiationPoints);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putFloat("radiationPoints", radiationPoints);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.radiationPoints = nbt.getFloat("radiationPoints");
    }
}