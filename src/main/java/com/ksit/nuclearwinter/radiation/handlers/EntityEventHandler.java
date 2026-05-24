package com.ksit.nuclearwinter.radiation.handlers;

import com.ksit.nuclearwinter.NuclearWinter;
import com.ksit.nuclearwinter.radiation.EntityRadiationProvider;
import com.ksit.nuclearwinter.radiation.RadiationCapability;
import com.ksit.nuclearwinter.radiation.illness.RadiationIllnessProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EntityEventHandler {

    private static final ResourceLocation ENTITY_RAD_KEY =
            ResourceLocation.fromNamespaceAndPath(NuclearWinter.MOD_ID, "entity_radiation");
    private static final ResourceLocation ILLNESS_KEY =
            ResourceLocation.fromNamespaceAndPath(NuclearWinter.MOD_ID, "radiation_illness");

    // Только для Entity; ItemStack полностью убран отсюда
    // Capability для итемов теперь через ItemGeigerCounter.initCapabilities()
    @SubscribeEvent
    public void onAttachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
        // Прикрепление свойства источника радиации
        if (!event.getObject().getCapability(
                RadiationCapability.RADIATION_SOURCE).isPresent()) {
            event.addCapability(ENTITY_RAD_KEY, new EntityRadiationProvider());
        }
        // Прикрепление счётчика облучения
        if (!event.getObject().getCapability(RadiationCapability.RADIATION_ILLNESS).isPresent()) {
            event.addCapability(ILLNESS_KEY, new RadiationIllnessProvider());
        }
    }
}