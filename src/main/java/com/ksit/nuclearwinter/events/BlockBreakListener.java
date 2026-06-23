package com.ksit.nuclearwinter.events;

import com.ksit.nuclearwinter.bunker.BunkerManager;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class BlockBreakListener {
    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {

        if (!(event.getLevel() instanceof ServerLevel level)) return;

        BunkerManager.onBlockChanged(level, event.getPos());
    }
}
