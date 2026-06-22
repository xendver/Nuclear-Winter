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
                    NuclearWinter.MOD_ID
            );

    public static final RegistryObject<CreativeModeTab> NUCLEAR_TAB =
            TABS.register(
                    "nuclear_tab",
                    () -> CreativeModeTab.builder()
                            .title(Component.literal("Nuclear Winter"))
                            .icon(() ->
                                    new ItemStack(ModItems.RAW_URANIUM.get()))
                            .displayItems((params, output) -> {

                                // Сырые руды
                                output.accept(ModItems.RAW_URANIUM.get());
                                output.accept(ModItems.RAW_LEAD.get());


                                //Блоки
                                output.accept(ModBlocks.RAW_URANIUM_BLOCK.get());
                                output.accept(ModBlocks.LEAD_BLOCK.get());
                                output.accept(ModBlocks.RAW_LEAD_BLOCK.get());

                                // Блокы руды
                                output.accept(ModBlocks.URANIUM_ORE.get());
                                output.accept(ModBlocks.DEEPSLATE_URANIUM_ORE.get());
                                output.accept(ModBlocks.LEAD_ORE.get());
                                output.accept(ModBlocks.DEEPSLATE_LEAD_ORE.get());


                                // Слитки
                                output.accept(ModItems.LEAD_INGOT.get());

                                //Крафтовые предметы
                                output.accept(ModItems.LEAD_PLATE.get());
                                output.accept(ModItems.LEAD_NUGGET.get());
                                output.accept(ModItems.FILTER.get());
                                output.accept(ModItems.HERMETIC_GASKET.get());
                                output.accept(ModItems.RADIO_ABSORBING_COMPOSITE.get());
                                output.accept(ModItems.MICROREACTOR.get());
                                output.accept(ModItems.URANIUM_STEEL.get());
                                output.accept(ModItems.NUCLEAR_FILTER.get());

                                // Броня
                                //tier1
                                output.accept(ModItems.RAGGED_ARMOR_HELMET.get());
                                output.accept(ModItems.RAGGED_ARMOR_CHESTPLATE.get());
                                output.accept(ModItems.RAGGED_ARMOR_LEGGINGS.get());
                                output.accept(ModItems.RAGGED_ARMOR_BOOTS.get());

                                //tier2
                                output.accept(ModItems.MOPP_ARMOR_HELMET.get());
                                output.accept(ModItems.MOPP_ARMOR_CHESTPLATE.get());
                                output.accept(ModItems.MOPP_ARMOR_LEGGINGS.get());
                                output.accept(ModItems.MOPP_ARMOR_BOOTS.get());

                                //tier3
                                output.accept(ModItems.HAZMAT_ARMOR_HELMET.get());
                                output.accept(ModItems.HAZMAT_ARMOR_CHESTPLATE.get());
                                output.accept(ModItems.HAZMAT_ARMOR_LEGGINGS.get());
                                output.accept(ModItems.HAZMAT_ARMOR_BOOTS.get());

                                //tier4
                                output.accept(ModItems.ARK_ARMOR_HELMET.get());
                                output.accept(ModItems.ARK_ARMOR_CHESTPLATE.get());
                                output.accept(ModItems.ARK_ARMOR_LEGGINGS.get());
                                output.accept(ModItems.ARK_ARMOR_BOOTS.get());

                                // Функциональные предметы
                                output.accept(ModItems.YELLOW_CAKE.get());
                                output.accept(ModItems.GEIGER_COUNTER.get());



                            })
                            .build()
            );

    private ModCreativeTabs() {}
}