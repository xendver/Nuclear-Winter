package com.ksit.nuclearwinter;

import com.ksit.nuclearwinter.client.renderer.ItemGeigerRenderer;
import com.ksit.nuclearwinter.item.ModItems;
import com.ksit.nuclearwinter.network.PacketHandler;
import com.ksit.nuclearwinter.radiation.RadiationCapability;
import com.ksit.nuclearwinter.radiation.commands.CommandRadiationInfo;
import com.ksit.nuclearwinter.radiation.handlers.ChunkEventHandler;
import com.ksit.nuclearwinter.radiation.handlers.EntityEventHandler;
import com.ksit.nuclearwinter.radiation.handlers.WorldRadiationHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(NuclearWinter.MODID)
public class NuclearWinter {

    public static final String MODID = "nuclearwinter";

    public NuclearWinter() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Итемы
        ModItems.ITEMS.register(modBus);

        // Capability
        modBus.addListener(RadiationCapability::register);

        // Пакеты, общая инициализация
        modBus.addListener(this::commonSetup);

        // Рендерер
        // FMLEnvironment.dist
        if (FMLEnvironment.dist == Dist.CLIENT) {
            modBus.addListener(this::clientSetup);
        }

        // Обработчики
        MinecraftForge.EVENT_BUS.register(new ChunkEventHandler());
        MinecraftForge.EVENT_BUS.register(new WorldRadiationHandler());
        MinecraftForge.EVENT_BUS.register(new EntityEventHandler());

        // Команды в чат
        MinecraftForge.EVENT_BUS.register(new CommandRadiationInfo());
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        PacketHandler.register();
    }

    private void clientSetup(FMLClientSetupEvent event) {
        // Регистрация рендерер только на клиенте
        MinecraftForge.EVENT_BUS.register(new ItemGeigerRenderer());
    }
}