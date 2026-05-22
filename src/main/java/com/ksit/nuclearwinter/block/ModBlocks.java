package com.ksit.nuclearwinter.block;

import com.ksit.nuclearwinter.NuclearWinter;
import com.ksit.nuclearwinter.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(
                    ForgeRegistries.BLOCKS,
                    NuclearWinter.MODID
            );

    public static final RegistryObject<Block> URANIUM_ORE =
            BLOCKS.register(
                    "uranium_ore",
                    () -> new DropExperienceBlock(
                            BlockBehaviour.Properties.of()
                                    .mapColor(MapColor.STONE)
                                    .strength(7f, 9f)
                                    .requiresCorrectToolForDrops()
                    )
            );

    public static final RegistryObject<Block> DEEPSLATE_URANIUM_ORE =
            BLOCKS.register(
                    "deepslate_uranium_ore",
                    () -> new DropExperienceBlock(
                            BlockBehaviour.Properties.of()
                                    .mapColor(MapColor.DEEPSLATE)
                                    .strength(9f, 12f)
                                    .requiresCorrectToolForDrops()
                    )
            );

    public static final RegistryObject<Block> RAW_URANIUM_BLOCK =
            BLOCKS.register(
                    "raw_uranium_block",
                    () -> new Block(
                            BlockBehaviour.Properties.of()
                                    .mapColor(MapColor.COLOR_GREEN)
                                    .strength(10f, 14f)
                                    .sound(SoundType.METAL)
                                    .requiresCorrectToolForDrops()
                    )
            );

    public static void registerBlockItems() {

        ModItems.ITEMS.register(
                "uranium_ore.json",
                () -> new BlockItem(
                        URANIUM_ORE.get(),
                        new Item.Properties()
                )
        );

        ModItems.ITEMS.register(
                "deepslate_uranium_ore.json",
                () -> new BlockItem(
                        DEEPSLATE_URANIUM_ORE.get(),
                        new Item.Properties()
                )
        );

        ModItems.ITEMS.register(
                "raw_uranium_block",
                () -> new BlockItem(
                        RAW_URANIUM_BLOCK.get(),
                        new Item.Properties()
                )
        );
    }
}