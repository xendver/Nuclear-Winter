package com.ksit.nuclearwinter.radiation.handlers;

import com.ksit.nuclearwinter.NuclearWinter;
import com.ksit.nuclearwinter.radiation.capability.chunk.ChunkRadiationProvider;
import com.ksit.nuclearwinter.radiation.capability.RadiationCapability;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.level.ChunkDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ChunkEventHandler {

    // Хранение capability в чанке
    private static final ResourceLocation CAP_KEY =
            ResourceLocation.fromNamespaceAndPath(NuclearWinter.MOD_ID, "chunk_radiation");

    // Вызывается при создании каждого нового чанка
    // AttachCapabilitiesEvent<LevelChunk> - прикрепляем провайдер к каждому чанку
    @SubscribeEvent
    public void onAttachChunkCapabilities(AttachCapabilitiesEvent<LevelChunk> event) {
        if (!event.getObject().getCapability(
                RadiationCapability.CHUNK_RADIATION).isPresent()){
            event.addCapability(CAP_KEY, new ChunkRadiationProvider());
        }
    }

    // Вызывается при сохранении чанка на диск
    @SubscribeEvent
    public void onChunkSave(ChunkDataEvent.Save event) {

        if (!(event.getChunk() instanceof LevelChunk chunk))
            return;

        chunk.getCapability(RadiationCapability.CHUNK_RADIATION)
                .ifPresent(cap ->
                        event.getData().put(
                                "NW_ChunkRadiation",
                                cap.serializeNBT())
                );
    }

    // Вызывается при загрузке чанка с диска
    @SubscribeEvent
    public void onChunkLoad(ChunkDataEvent.Load event) {

        if (!(event.getChunk() instanceof LevelChunk chunk))
            return;

        if (!event.getData().contains("NW_ChunkRadiation"))
            return;

        chunk.getCapability(RadiationCapability.CHUNK_RADIATION)
                .ifPresent(cap ->
                        cap.deserializeNBT(
                                event.getData()
                                        .getCompound("NW_ChunkRadiation"))
                );
    }
}