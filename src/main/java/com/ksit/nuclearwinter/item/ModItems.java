package com.ksit.nuclearwinter.item;

import com.ksit.nuclearwinter.NuclearWinter;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {


    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, NuclearWinter.MODID);
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

    public static final RegistryObject<Item> SADFASDF_HELMET = ITEMS.register("sadfasdf_helmet", () -> new SadfasdfItem.Helmet());
    public static final RegistryObject<Item> SADFASDF_CHESTPLATE = ITEMS.register("sadfasdf_chestplate", () -> new SadfasdfItem.Chestplate());
    public static final RegistryObject<Item> SADFASDF_LEGGINGS = ITEMS.register("sadfasdf_leggings", () -> new SadfasdfItem.Leggings());
    public static final RegistryObject<Item> SADFASDF_BOOTS = ITEMS.register("sadfasdf_boots", () -> new SadfasdfItem.Boots());

}