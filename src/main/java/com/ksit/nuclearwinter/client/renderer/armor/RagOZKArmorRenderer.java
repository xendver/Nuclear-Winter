package com.ksit.nuclearwinter.client.renderer.armor;

import com.ksit.nuclearwinter.NuclearWinter;
import com.ksit.nuclearwinter.item.armor.RagOZKArmorItem;

import net.minecraft.resources.ResourceLocation;

import software.bernie.geckolib.GeckoLib;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class RagOZKArmorRenderer extends GeoArmorRenderer<RagOZKArmorItem> {

    public RagOZKArmorRenderer() {

        super(new DefaultedItemGeoModel<>(new ResourceLocation(NuclearWinter.MODID, "armor/ragozkmodel")));
    }
}
