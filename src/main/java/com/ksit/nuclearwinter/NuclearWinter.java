package com.ksit.nuclearwinter;

import com.ksit.nuclearwinter.events.BlockBreakListener;
import com.ksit.nuclearwinter.events.BlockPlaceListener;
import com.ksit.nuclearwinter.events.TickListener;
import com.ksit.nuclearwinter.item.ModItems;
import com.ksit.nuclearwinter.network.PacketHandler;
import com.ksit.nuclearwinter.radiation.RadiationCapability;
import com.ksit.nuclearwinter.radiation.commands.CommandRadiationInfo;
import com.ksit.nuclearwinter.radiation.handlers.ChunkEventHandler;
import com.ksit.nuclearwinter.radiation.handlers.EntityEventHandler;
import com.ksit.nuclearwinter.radiation.handlers.WorldRadiationHandler;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import com.ksit.nuclearwinter.block.ModBlocks;
import com.ksit.nuclearwinter.item.ModCreativeTabs;
import org.slf4j.Logger;
import software.bernie.geckolib.GeckoLib;

@Mod(NuclearWinter.MOD_ID)
public class NuclearWinter {


    public static final String MOD_ID = "nuclearwinter";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public NuclearWinter(FMLJavaModLoadingContext context) {
        IEventBus modBus = context.getModEventBus();

        ModItems.ITEMS.register(modBus);
        ModBlocks.BLOCKS.register(modBus);
        ModCreativeTabs.TABS.register(modBus);

        ModBlocks.registerBlockItems();
        GeckoLib.initialize();

        modBus.addListener(RadiationCapability::register);
        modBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(new ChunkEventHandler());
        MinecraftForge.EVENT_BUS.register(new WorldRadiationHandler());
        MinecraftForge.EVENT_BUS.register(new EntityEventHandler());
        MinecraftForge.EVENT_BUS.register(new CommandRadiationInfo());
        MinecraftForge.EVENT_BUS.register(new BlockPlaceListener());
        MinecraftForge.EVENT_BUS.register(new BlockBreakListener());
        MinecraftForge.EVENT_BUS.register(new TickListener());
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        PacketHandler.register();
    }
}