package com.ksit.nuclearwinter.events;

import com.ksit.nuclearwinter.block.ModBlocks;
import com.ksit.nuclearwinter.bunker.BunkerManager;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class BlockPlaceListener {
    @SubscribeEvent
    public void onBlockPlace(BlockEvent.EntityPlaceEvent event) {

        if (!(event.getLevel() instanceof ServerLevel level)) return;

        if (event.getPlacedBlock().getBlock() == ModBlocks.BUNKER_CONTROLLER.get()) {
            BunkerManager.createBunker(level, event.getPos());
        } else {
            BunkerManager.onBlockChanged(level, event.getPos());
        }

    }
}
