package com.ksit.nuclearwinter.item;

import com.ksit.nuclearwinter.NuclearWinter;
import com.ksit.nuclearwinter.item.armor.RagOZKArmorItem;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {


    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, NuclearWinter.MODID);
    public static final RegistryObject<Item> RAW_URANIUM =
            ITEMS.register(
                    "raw_uranium",
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
    //Счётчик Гейгера
    public static final RegistryObject<ItemGeigerCounter> GEIGER_COUNTER =
            ITEMS.register("geiger_counter", ItemGeigerCounter::new);

    public static final RegistryObject<RagOZKArmorItem> RAG_OZK_HELMET =
            ITEMS.register("rag_ozk_helmet",
                    () -> new RagOZKArmorItem(
                            ArmorMaterials.DIAMOND,
                            ArmorItem.Type.HELMET,
                            new Item.Properties()
                    ));

    public static final RegistryObject<RagOZKArmorItem> RAG_OZK_CHESTPLATE =
            ITEMS.register("rag_ozk_chestplate",
                    () -> new RagOZKArmorItem(
                            ArmorMaterials.DIAMOND,
                            ArmorItem.Type.CHESTPLATE,
                            new Item.Properties()
                    ));

    public static final RegistryObject<RagOZKArmorItem> RAG_OZK_LEGGINGS =
            ITEMS.register("rag_ozk_leggings",
                    () -> new RagOZKArmorItem(
                            ArmorMaterials.DIAMOND,
                            ArmorItem.Type.LEGGINGS,
                            new Item.Properties()
                    ));

    public static final RegistryObject<RagOZKArmorItem> RAG_OZK_BOOTS =
            ITEMS.register("rag_ozk_boots",
                    () -> new RagOZKArmorItem(
                            ArmorMaterials.DIAMOND,
                            ArmorItem.Type.BOOTS,
                            new Item.Properties()
                    ));

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}