package com.ksit.nuclearwinter.item;

import com.ksit.nuclearwinter.radiation.IRadiation;
import com.ksit.nuclearwinter.radiation.ItemRadiationProvider;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ItemGeigerCounter extends Item {

    public ItemGeigerCounter() {
        super(new Properties().stacksTo(1));
    }
    @Override
    public @Nullable ICapabilityProvider initCapabilities(
            ItemStack stack, @Nullable CompoundTag nbt) {
        return new ItemRadiationProvider();
    }
}