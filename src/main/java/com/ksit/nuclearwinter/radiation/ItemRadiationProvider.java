package com.ksit.nuclearwinter.radiation;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// Провайдер прикрепляет RadiationImpl к ItemStack через систему Capability
public class ItemRadiationProvider implements ICapabilitySerializable<CompoundTag> {

    private final RadiationImpl instance = new RadiationImpl();
    private final LazyOptional<IRadiation> optional = LazyOptional.of(() -> instance);

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(
            @NotNull Capability<T> cap, @Nullable Direction side) {
        return RadiationCapability.RADIATION_SOURCE.orEmpty(cap, optional);
    }

    @Override
    public CompoundTag serializeNBT() { return instance.serializeNBT(); }

    @Override
    public void deserializeNBT(CompoundTag nbt) { instance.deserializeNBT(nbt); }
}