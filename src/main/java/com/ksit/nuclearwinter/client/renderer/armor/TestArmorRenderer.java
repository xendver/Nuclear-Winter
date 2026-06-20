package com.ksit.nuclearwinter.client.renderer.armor;

import com.ksit.nuclearwinter.NuclearWinter;
import com.ksit.nuclearwinter.item.armor.TestArmorItem;

import net.minecraft.resources.ResourceLocation;

import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class TestArmorRenderer extends GeoArmorRenderer<TestArmorItem> {

    public TestArmorRenderer() {

        super(new DefaultedItemGeoModel<>(ResourceLocation.fromNamespaceAndPath(NuclearWinter.MOD_ID, "armor/testarmor")));
    }




}
