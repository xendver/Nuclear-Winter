package com.ksit.nuclearwinter.item;

import com.ksit.nuclearwinter.NuclearWinter;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {


    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, NuclearWinter.MODID);

    //Счётчик Гейгера
    public static final RegistryObject<ItemGeigerCounter> GEIGER_COUNTER =
            ITEMS.register("geiger_counter", ItemGeigerCounter::new);
}