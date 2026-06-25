package com.ksit.nuclearwinter.radiation.capability.source;

import com.ksit.nuclearwinter.radiation.api.IRadiation;
import net.minecraft.nbt.CompoundTag;

// Данные источника радиации
public class RadiationImpl implements IRadiation {

    private float initialPower;
    private float radius;
    private float decayPerChunk;

    public RadiationImpl() {
        this(0f, 0f, 0f);
    }

    public RadiationImpl(float initialPower, float radius, float decayPerChunk) {
        this.initialPower = initialPower;
        this.radius = radius;
        this.decayPerChunk = decayPerChunk;
    }


    @Override public float getInitialPower()         { return initialPower;  }
    @Override public void  setInitialPower(float p)  { this.initialPower  = p; }

    @Override public float getRadius()               { return radius;        }
    @Override public void  setRadius(float r)        { this.radius        = r; }

    @Override public float getDecayPerChunk()        { return decayPerChunk; }
    @Override public void  setDecayPerChunk(float d) { this.decayPerChunk = d; }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putFloat("initialPower",  initialPower);
        tag.putFloat("radius",        radius);
        tag.putFloat("decayPerChunk", decayPerChunk);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.initialPower  = nbt.getFloat("initialPower");
        this.radius        = nbt.getFloat("radius");
        this.decayPerChunk = nbt.getFloat("decayPerChunk");
    }
}