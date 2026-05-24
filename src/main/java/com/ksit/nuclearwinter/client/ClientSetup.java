package com.ksit.nuclearwinter.client;

import com.ksit.nuclearwinter.NuclearWinter;
import com.ksit.nuclearwinter.client.renderer.ItemGeigerRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(
        modid = NuclearWinter.MOD_ID,
        bus = Mod.EventBusSubscriber.Bus.MOD,
        value = Dist.CLIENT
)
public class ClientSetup {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new ItemGeigerRenderer());
    }
}