package com.ksit.nuclearwinter.radiation.handlers;

import com.ksit.nuclearwinter.radiation.service.ChunkRadiationService;
import com.ksit.nuclearwinter.radiation.service.RadiationIllnessService;
import com.ksit.nuclearwinter.radiation.service.RadiationNetworkService;
import com.ksit.nuclearwinter.radiation.service.RadiationSourceManager;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.*;

public class WorldRadiationHandler {
    // главный кэш/менеджер всех активных источников радиации мира
    private final RadiationSourceManager sourceManager = new RadiationSourceManager();

    private final ChunkRadiationService chunkService = new ChunkRadiationService(sourceManager);

    private final RadiationIllnessService illnessService = new RadiationIllnessService();

    private final RadiationNetworkService networkService = new RadiationNetworkService();


    // Подписка на событие Tick
    @SubscribeEvent
    public void onLevelTick(TickEvent.LevelTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (event.level.isClientSide()) return;
        if (event.level.getGameTime() % 20 != 0) return; // раз в секунду

        ServerLevel level = (ServerLevel) event.level;

        sourceManager.rebuildFromStorage(level);
        this.chunkService.updateChunkRadiation(level);
        this.illnessService.applyIllnessEffects(level);
        this.networkService.sendRadiationPackets(level);
    }

    // == МЕНЕДЖЕР ИСТОЧНИКОВ ==========================================================================
    public RadiationSourceManager getSourceManager() {
        return sourceManager;
    }

    // СБОР ИСТОЧНИКОВ
    //    private List<ActiveSource> collectSourcesRaw(ServerLevel level) {
    //
    //        List<ActiveSource> sources = new ArrayList<>();
    //
    //       for (ServerPlayer player : level.players()) {
    //            collectPlayerItemSources(player, sources);
    //       }
    //
    //        BlockRadiationStorage storage = BlockRadiationStorage.get(level);
    //
    //        for (Map.Entry<BlockPos, float[]> entry : storage.getAll().entrySet()) {
    //
    //            BlockPos pos = entry.getKey();
    //            float[] values = entry.getValue();
    //
    //            ChunkPos cpos = new ChunkPos(pos);
    //
    //            RadiationImpl rad = new RadiationImpl(values[0], values[1], values[2]);
    //
    //            sources.add(new ActiveSource(cpos.x, cpos.z, rad));
    //        }
    //
    //        return sources;
    //    }
    //
    //    private void rebuildSpatialIndex(ServerLevel level) {
    //
    //        this.spatialSources.clear();
    //
    //        for (ActiveSource source : collectSourcesRaw(level)) {
    //
    //            ChunkPos pos = new ChunkPos(source.chunkX(), source.chunkZ());
    //            List<ActiveSource> list = this.spatialSources.computeIfAbsent(pos, k -> new ArrayList<>());
    //            list.add(source);
    //        }
    //    }
    //    private void collectPlayerItemSources(Player player, List<ActiveSource> sources) {
    //
    //        int cx = level(player).getChunkAt(player.blockPosition()).getPos().x;
    //        int cz = level(player).getChunkAt(player.blockPosition()).getPos().z;
    //
    //        checkItem(player.getMainHandItem(), cx, cz, sources);
    //        checkItem(player.getOffhandItem(), cx, cz, sources);
    //
    //        for (ItemStack armor : player.getArmorSlots())
    //            checkItem(armor, cx, cz, sources);
    //
    //        for (int i = 0; i < player.getInventory().getContainerSize(); i++)
    //            checkItem(player.getInventory().getItem(i), cx, cz, sources);
    //    }
    //
    //    private void checkItem(ItemStack stack, int cx, int cz, List<ActiveSource> sources) {
    //
    //        if (stack.isEmpty()) return;
    //
    //        stack.getCapability(RadiationCapability.RADIATION_SOURCE).ifPresent(rad -> {
    //            if (rad.getInitialPower() > 0) {
    //                sources.add(new ActiveSource(cx, cz, rad));
    //            }
    //        });
    //    }
}