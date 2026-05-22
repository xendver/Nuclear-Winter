
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package com.ksit.nuclearwinter.init;

import com.ksit.nuclearwinter.NuclearWinter;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.core.registries.Registries;

import com.ksit.nuclearwinter.item.ModItems;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class AsddModTabs {
	public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, NuclearWinter.MODID);

	@SubscribeEvent
	public static void buildTabContentsVanilla(BuildCreativeModeTabContentsEvent tabData) {
		if (tabData.getTabKey() == CreativeModeTabs.COMBAT) {
			tabData.accept(ModItems.SADFASDF_HELMET.get());
			tabData.accept(ModItems.SADFASDF_CHESTPLATE.get());
			tabData.accept(ModItems.SADFASDF_LEGGINGS.get());
			tabData.accept(ModItems.SADFASDF_BOOTS.get());
		}
	}
}
