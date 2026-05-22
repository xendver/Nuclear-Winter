package com.ksit.nuclearwinter.client;
import com.ksit.nuclearwinter.NuclearWinter;
import com.ksit.nuclearwinter.client.model.armor.Armor1RankModel;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(
        modid = NuclearWinter.MODID,
        bus = Mod.EventBusSubscriber.Bus.MOD,
        value = Dist.CLIENT
)
public class ClientModEvents {

    @SubscribeEvent
    public static void registerLayerDefinitions(
            EntityRenderersEvent.RegisterLayerDefinitions event
    ) {

        event.registerLayerDefinition(
                Armor1RankModel.LAYER_LOCATION,
                Armor1RankModel::createBodyLayer
        );
    }
}