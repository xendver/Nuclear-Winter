package com.ksit.nuclearwinter.item;

import com.ksit.nuclearwinter.NuclearWinter;
import com.ksit.nuclearwinter.item.armor.ArkArmorItem;
import com.ksit.nuclearwinter.item.armor.HazmatArmorItem;
import com.ksit.nuclearwinter.item.armor.MOPPArmorItem;
import com.ksit.nuclearwinter.item.armor.RaggedArmorItem;
import com.ksit.nuclearwinter.item.custom.ItemGeigerCounter;
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

    public static final RegistryObject<Item> FILTER =
            ITEMS.register(
                    "filter",
                    () -> new Item(
                            new Item.Properties()
                    )
            );

    public static final RegistryObject<Item> RADIO_ABSORBING_COMPOSITE =
            ITEMS.register(
                    "radio_absorbing_composite",
                    () -> new Item(
                            new Item.Properties()
                    )
            );

    public static final RegistryObject<Item> HERMETIC_GASKET =
            ITEMS.register(
                    "hermetic_gasket",
                    () -> new Item(
                            new Item.Properties()
                    )
            );

    public static final RegistryObject<Item> URANIUM_STEEL =
            ITEMS.register(
                    "uranium_steel",
                    () -> new Item(
                            new Item.Properties()
                    )
            );

    public static final RegistryObject<Item> NUCLEAR_FILTER =
            ITEMS.register(
                    "nuclear_filter",
                    () -> new Item(
                            new Item.Properties()
                    )
            );

    public static final RegistryObject<Item> MICROREACTOR =
            ITEMS.register(
                    "microreactor",
                    () -> new Item(
                            new Item.Properties()
                    )
            );

    // Функциональные предметы
    //Счётчик Гейгера
    public static final RegistryObject<ItemGeigerCounter> GEIGER_COUNTER = ITEMS.register("geiger_counter",
            () -> new ItemGeigerCounter(new Item.Properties()));

    // Броня
    //tier 1
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

    //tier 2
    public static final RegistryObject<MOPPArmorItem> MOPP_ARMOR_HELMET =
            ITEMS.register("mopp_armor_helmet",
                    () -> new MOPPArmorItem(
                            ArmorMaterials.DIAMOND,
                            ArmorItem.Type.HELMET,
                            new Item.Properties()
                    ));

    public static final RegistryObject<MOPPArmorItem> MOPP_ARMOR_CHESTPLATE =
            ITEMS.register("mopp_armor_chestplate",
                    () -> new MOPPArmorItem(
                            ArmorMaterials.DIAMOND,
                            ArmorItem.Type.CHESTPLATE,
                            new Item.Properties()
                    ));

    public static final RegistryObject<MOPPArmorItem> MOPP_ARMOR_LEGGINGS =
            ITEMS.register("mopp_armor_leggings",
                    () -> new MOPPArmorItem(
                            ArmorMaterials.DIAMOND,
                            ArmorItem.Type.LEGGINGS,
                            new Item.Properties()
                    ));

    public static final RegistryObject<MOPPArmorItem> MOPP_ARMOR_BOOTS =
            ITEMS.register("mopp_armor_boots",
                    () -> new MOPPArmorItem(
                            ArmorMaterials.DIAMOND,
                            ArmorItem.Type.BOOTS,
                            new Item.Properties()
                    ));

    //tier 3
    public static final RegistryObject<HazmatArmorItem> HAZMAT_ARMOR_HELMET =
            ITEMS.register("hazmat_armor_helmet",
                    () -> new HazmatArmorItem(
                            ArmorMaterials.DIAMOND,
                            ArmorItem.Type.HELMET,
                            new Item.Properties()
                    ));

    public static final RegistryObject<HazmatArmorItem> HAZMAT_ARMOR_CHESTPLATE =
            ITEMS.register("hazmat_armor_chestplate",
                    () -> new HazmatArmorItem(
                            ArmorMaterials.DIAMOND,
                            ArmorItem.Type.CHESTPLATE,
                            new Item.Properties()
                    ));

    public static final RegistryObject<HazmatArmorItem> HAZMAT_ARMOR_LEGGINGS =
            ITEMS.register("hazmat_armor_leggings",
                    () -> new HazmatArmorItem(
                            ArmorMaterials.DIAMOND,
                            ArmorItem.Type.LEGGINGS,
                            new Item.Properties()
                    ));

    public static final RegistryObject<HazmatArmorItem> HAZMAT_ARMOR_BOOTS =
            ITEMS.register("hazmat_armor_boots",
                    () -> new HazmatArmorItem(
                            ArmorMaterials.DIAMOND,
                            ArmorItem.Type.BOOTS,
                            new Item.Properties()
                    ));

    //tier 4
    public static final RegistryObject<ArkArmorItem> ARK_ARMOR_HELMET =
            ITEMS.register("ark_armor_helmet",
                    () -> new ArkArmorItem(
                            ArmorMaterials.DIAMOND,
                            ArmorItem.Type.HELMET,
                            new Item.Properties()
                    ));

    public static final RegistryObject<ArkArmorItem> ARK_ARMOR_CHESTPLATE =
            ITEMS.register("ark_armor_chestplate",
                    () -> new ArkArmorItem(
                            ArmorMaterials.DIAMOND,
                            ArmorItem.Type.CHESTPLATE,
                            new Item.Properties()
                    ));

    public static final RegistryObject<ArkArmorItem> ARK_ARMOR_LEGGINGS =
            ITEMS.register("ark_armor_leggings",
                    () -> new ArkArmorItem(
                            ArmorMaterials.DIAMOND,
                            ArmorItem.Type.LEGGINGS,
                            new Item.Properties()
                    ));

    public static final RegistryObject<ArkArmorItem> ARK_ARMOR_BOOTS =
            ITEMS.register("ark_armor_boots",
                    () -> new ArkArmorItem(
                            ArmorMaterials.DIAMOND,
                            ArmorItem.Type.BOOTS,
                            new Item.Properties()
                    ));

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}