package com.ksit.nuclearwinter.item;

import com.ksit.nuclearwinter.NuclearWinter;
import com.ksit.nuclearwinter.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public final class ModCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(
                    Registries.CREATIVE_MODE_TAB,
                    NuclearWinter.MODID
            );

    public static final RegistryObject<CreativeModeTab> NUCLEAR_TAB =
            TABS.register(
                    "nuclear_tab",
                    () -> CreativeModeTab.builder()
                            .title(Component.literal("Nuclear Winter"))
                            .icon(() ->
                                    new ItemStack(ModItems.RAW_URANIUM.get()))
                            .displayItems((params, output) -> {

                                output.accept(ModItems.RAW_URANIUM.get());
                                output.accept(ModItems.YELLOW_CAKE.get());

                                output.accept(ModBlocks.URANIUM_ORE.get());
                                output.accept(ModBlocks.DEEPSLATE_URANIUM_ORE.get());
                                output.accept(ModBlocks.RAW_URANIUM_BLOCK.get());
                            })
                            .build()
            );

    private ModCreativeTabs() {}
}