package com.ksit.nuclearwinter.radiation.network;

import com.ksit.nuclearwinter.network.PacketChunkRadiation;
import com.ksit.nuclearwinter.network.PacketHandler;
import com.ksit.nuclearwinter.radiation.capability.RadiationCapability;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.network.PacketDistributor;

public class RadiationNetworkSystem {
    public void send(ServerLevel level) {

        for (ServerPlayer player : level.players()) {

            LevelChunk chunk =
                    level.getChunkAt(player.blockPosition());

            chunk.getCapability(RadiationCapability.CHUNK_RADIATION)
                    .ifPresent(cap -> {

                        PacketHandler.INSTANCE.send(
                                PacketDistributor.PLAYER.with(() -> player),
                                new PacketChunkRadiation(
                                        cap.getRadiationLevel()
                                )
                        );
                    });
        }
    }
}