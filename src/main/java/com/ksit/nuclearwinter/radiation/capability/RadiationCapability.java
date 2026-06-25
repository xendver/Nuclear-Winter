package com.ksit.nuclearwinter.radiation.capability;

import com.ksit.nuclearwinter.radiation.api.IChunkRadiation;
import com.ksit.nuclearwinter.radiation.api.IRadiation;
import com.ksit.nuclearwinter.radiation.api.IRadiationIllness;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;

public class RadiationCapability {

    public static final Capability<IRadiation> RADIATION_SOURCE =
            CapabilityManager.get(new CapabilityToken<>() {});

    public static final Capability<IChunkRadiation> CHUNK_RADIATION =
            CapabilityManager.get(new CapabilityToken<>() {});

    // Capability накопленного облучения (прикрепляется к player и entity)
    public static final Capability<IRadiationIllness> RADIATION_ILLNESS =
            CapabilityManager.get(new CapabilityToken<>() {});

    public static void register(RegisterCapabilitiesEvent event) {
        event.register(IRadiation.class);
        event.register(IChunkRadiation.class);
        event.register(IRadiationIllness.class);
    }
}