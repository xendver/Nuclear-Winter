package com.ksit.nuclearwinter.radiation.capability.illness;

import com.ksit.nuclearwinter.radiation.capability.RadiationCapability;
import com.ksit.nuclearwinter.radiation.api.IRadiationIllness;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RadiationIllnessProvider implements ICapabilitySerializable<CompoundTag> {

    private final RadiationIllnessImpl instance = new RadiationIllnessImpl();
    private final LazyOptional<IRadiationIllness> optional =
            LazyOptional.of(() -> instance);

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(
            @NotNull Capability<T> cap, @Nullable Direction side) {
        return RadiationCapability.RADIATION_ILLNESS.orEmpty(cap, optional);
    }

    @Override
    public CompoundTag serializeNBT() { return instance.serializeNBT(); }

    @Override
    public void deserializeNBT(CompoundTag nbt) { instance.deserializeNBT(nbt); }
}