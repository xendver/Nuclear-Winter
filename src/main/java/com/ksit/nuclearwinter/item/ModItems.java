package com.ksit.nuclearwinter.item;

import com.ksit.nuclearwinter.NuclearWinter;
import com.ksit.nuclearwinter.item.armor.RaggedArmorItem;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {


    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, NuclearWinter.MOD_ID);

    // Сырые руды
    public static final RegistryObject<Item> RAW_URANIUM =
            ITEMS.register(
                    "raw_uranium",
                    () -> new Item(
                            new Item.Properties()
                    )
            );

    public static final RegistryObject<Item> RAW_LEAD =
            ITEMS.register(
                    "raw_lead",
                    () -> new Item(
                            new Item.Properties()
                    )
            );

    public static final RegistryObject<Item> YELLOW_CAKE =
            ITEMS.register(
                    "yellow_cake",
                    () -> new Item(
                            new Item.Properties()
                    )
            );

    //Слитки
    public static final RegistryObject<Item> LEAD_INGOT =
            ITEMS.register(
                    "lead_ingot",
                    () -> new Item(
                            new Item.Properties()
                    )
            );

    //Крафтовые предметы
    public static final RegistryObject<Item> LEAD_PLATE =
            ITEMS.register(
                    "lead_plate",
                    () -> new Item(
                            new Item.Properties()
                    )
            );

    public static final RegistryObject<Item> LEAD_NUGGET =
            ITEMS.register(
                    "lead_nugget",
                    () -> new Item(
                            new Item.Properties()
                    )
            );

    // Функциональные предметы
    //Счётчик Гейгера
    public static final RegistryObject<ItemGeigerCounter> GEIGER_COUNTER =
            ITEMS.register("geiger_counter", ItemGeigerCounter::new);

    // Броня
    public static final RegistryObject<RaggedArmorItem> RAGGED_ARMOR_HELMET =
            ITEMS.register("ragged_armor_helmet",
                    () -> new RaggedArmorItem(
                            ArmorMaterials.DIAMOND,
                            ArmorItem.Type.HELMET,
                            new Item.Properties()
                    ));

    public static final RegistryObject<RaggedArmorItem> RAGGED_ARMOR_CHESTPLATE =
            ITEMS.register("ragged_armor_chestplate",
                    () -> new RaggedArmorItem(
                            ArmorMaterials.DIAMOND,
                            ArmorItem.Type.CHESTPLATE,
                            new Item.Properties()
                    ));

    public static final RegistryObject<RaggedArmorItem> RAGGED_ARMOR_LEGGINGS =
            ITEMS.register("ragged_armor_leggings",
                    () -> new RaggedArmorItem(
                            ArmorMaterials.DIAMOND,
                            ArmorItem.Type.LEGGINGS,
                            new Item.Properties()
                    ));

    public static final RegistryObject<RaggedArmorItem> RAGGED_ARMOR_BOOTS =
            ITEMS.register("ragged_armor_boots",
                    () -> new RaggedArmorItem(
                            ArmorMaterials.DIAMOND,
                            ArmorItem.Type.BOOTS,
                            new Item.Properties()
                    ));

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}