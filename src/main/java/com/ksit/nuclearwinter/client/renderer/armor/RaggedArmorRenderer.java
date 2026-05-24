package com.ksit.nuclearwinter.client.renderer.armor;

import com.ksit.nuclearwinter.NuclearWinter;
import com.ksit.nuclearwinter.item.armor.RaggedArmorItem;

import net.minecraft.resources.ResourceLocation;

import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class RaggedArmorRenderer extends GeoArmorRenderer<RaggedArmorItem> {

    public RaggedArmorRenderer() {

        super(new DefaultedItemGeoModel<>(ResourceLocation.fromNamespaceAndPath(NuclearWinter.MOD_ID, "armor/raggedarmor")));
    }
}
