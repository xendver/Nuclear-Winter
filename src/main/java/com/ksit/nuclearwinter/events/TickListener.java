package com.ksit.nuclearwinter.events;

import com.ksit.nuclearwinter.bunker.BunkerManager;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class TickListener {
    @SubscribeEvent
    public void onLevelTick(TickEvent.LevelTickEvent event) {

        if (event.phase != TickEvent.Phase.END) return;
        if (event.level.isClientSide()) return;
        if (event.level.getGameTime() % 20 != 0) return;

        ServerLevel level = (ServerLevel) event.level;

        for (ServerPlayer player : level.players()) {

            boolean inside = BunkerManager.isPlayerInBunker(player);

            if (inside) {
                player.sendSystemMessage(
                        net.minecraft.network.chat.Component.literal("§aIN BUNKER")
                );
            } else {
                player.sendSystemMessage(
                        net.minecraft.network.chat.Component.literal("§cOUTSIDE BUNKER")
                );
            }
        }

    }

    @SubscribeEvent
    public void onServerStarted(ServerStartedEvent event) {

        ServerLevel level = event.getServer().overworld();

        BunkerManager.loadAll(level);
    }
}
