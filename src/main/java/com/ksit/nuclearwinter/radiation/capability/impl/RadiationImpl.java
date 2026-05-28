package com.ksit.nuclearwinter.radiation.capability.impl;

import com.ksit.nuclearwinter.radiation.api.IRadiation;
import net.minecraft.nbt.CompoundTag;

// Данные источника радиации
public class RadiationImpl implements IRadiation {

    private float initialPower  = 0f;
    private float radius        = 0f;
    private float decayPerChunk = 0f;

    private static final String NBT_POWER = "initialPower";
    private static final String NBT_RADIUS = "radius";
    private static final String NBT_DECAY = "decayPerChunk";

    @Override public float getInitialPower()         { return initialPower;  }
    @Override public void  setInitialPower(float p)  { this.initialPower  = p; }

    @Override public float getRadius()               { return radius;        }
    @Override public void  setRadius(float r)        { this.radius        = r; }

    @Override public float getDecayPerChunk()        { return decayPerChunk; }
    @Override public void  setDecayPerChunk(float d) { this.decayPerChunk = d; }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putFloat(NBT_POWER,  initialPower);
        tag.putFloat(NBT_RADIUS,        radius);
        tag.putFloat(NBT_DECAY, decayPerChunk);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.initialPower = nbt.contains(NBT_POWER)
                ? nbt.getFloat(NBT_POWER)
                : 0f;

        this.radius = nbt.contains(NBT_RADIUS)
                ? nbt.getFloat(NBT_RADIUS)
                : 0f;

        this.decayPerChunk = nbt.contains(NBT_DECAY)
                ? nbt.getFloat(NBT_DECAY)
                : 0f;
    }
}