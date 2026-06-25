package com.ksit.nuclearwinter.radiation.util;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.LevelChunk;

import java.util.HashSet;
import java.util.Set;

public class ChunkUtil {
    // == ЧАНКИ, ЗАГРУЖЕННЫЕ ВОКРУГ ИГРОКА =============================================
    public static Set<ChunkPos> getLoadedChunksForPlayer(ServerPlayer player) {
        // получаем мир игрока
        ServerLevel level = (ServerLevel) player.level();

        //дальность загрузки чанков
        int viewDistance = 4;

        //позиция игрока в чанках
        ChunkPos playerChunk = player.chunkPosition();
        // сюда мы будем складывать чанки без дубликатов
        Set<ChunkPos> loadedChunks = new HashSet<>();

        // перебор области вокруг игрока
        for (int dx = -viewDistance; dx <= viewDistance; dx++) {
            for (int dz = -viewDistance; dz <= viewDistance; dz++) {
                //смещаемся от игрока и получаем каждый чанк вокруг него
                ChunkPos chunkPos = new ChunkPos(playerChunk.x + dx, playerChunk.z + dz);

                LevelChunk chunk = level.getChunkSource().getChunkNow(chunkPos.x, chunkPos.z);

                if (chunk != null) {
                    loadedChunks.add(chunkPos);
                }
            }
        }

        return loadedChunks;
    }
}
