
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package com.ksit.nuclearwinter.init;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.api.distmarker.Dist;

import com.ksit.nuclearwinter.client.model.Modelarmor_1_rank;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class AsddModModels {
	@SubscribeEvent
	public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(Modelarmor_1_rank.LAYER_LOCATION, Modelarmor_1_rank::createBodyLayer);
	}
}
