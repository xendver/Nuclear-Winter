package com.ksit.nuclearwinter.radiation;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// Провайдер прикрепляет ChunkRadiationImpl к чанку через систему Capability
public class ChunkRadiationProvider implements ICapabilitySerializable<CompoundTag> {

    private final ChunkRadiationImpl instance = new ChunkRadiationImpl();

    // LazyOptional создался один раз и переиспользовался
    private final LazyOptional<IChunkRadiation> optional =
            LazyOptional.of(() -> instance);

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(
            @NotNull Capability<T> cap, @Nullable Direction side) {
        // Замена null в старой версии (Capability.orEmpty() наша -> возвращает optional
        // иначе LazyOptional.empty())
        return RadiationCapability.CHUNK_RADIATION.orEmpty(cap, optional);
    }

    @Override
    public CompoundTag serializeNBT() { return instance.serializeNBT(); }

    @Override
    public void deserializeNBT(CompoundTag nbt) { instance.deserializeNBT(nbt); }
}