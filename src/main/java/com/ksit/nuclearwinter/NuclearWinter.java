package com.ksit.nuclearwinter;

import com.ksit.nuclearwinter.item.ModItems;
import com.ksit.nuclearwinter.network.PacketHandler;
import com.ksit.nuclearwinter.radiation.RadiationCapability;
import com.ksit.nuclearwinter.radiation.commands.CommandRadiationInfo;
import com.ksit.nuclearwinter.radiation.handlers.ChunkEventHandler;
import com.ksit.nuclearwinter.radiation.handlers.EntityEventHandler;
import com.ksit.nuclearwinter.radiation.handlers.WorldRadiationHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(NuclearWinter.MODID)
public class NuclearWinter {

    public static final String MODID = "nuclearwinter";

    public NuclearWinter() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.ITEMS.register(modBus);
        modBus.addListener(RadiationCapability::register);
        modBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(new ChunkEventHandler());
        MinecraftForge.EVENT_BUS.register(new WorldRadiationHandler());
        MinecraftForge.EVENT_BUS.register(new EntityEventHandler());
        MinecraftForge.EVENT_BUS.register(new CommandRadiationInfo());
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        PacketHandler.register();
    }
}