package com.ksit.nuclearwinter.bunker;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;

public class BunkerManager {

    private static final Map<UUID, Bunker> bunkers = new HashMap<>();
    private static final Map<ChunkPos, Set<UUID>> chunkIndex = new HashMap<>();


    // Добавляем бункер в мир
    public static void addBunker(Bunker bunker) {
        bunkers.put(bunker.getId(), bunker);
        indexBunker(bunker);
    }

    // Создаем бункер
    public static void createBunker(ServerLevel level, BlockPos controllerPos) {
        Bunker bunker = new Bunker(controllerPos);
        bunker.recalculate(level);

        addBunker(bunker);
        save(level, bunker);
    }

    // Обновляем бункер
    private static void updateBunker(ServerLevel level, Bunker bunker) {
        bunker.recalculate(level);
        reindex(bunker);
        save(level, bunker);
    }

    // Удаляем бункер
    public static void deleteBunker(ServerLevel level, UUID bunkerId) {
        Bunker bunker = bunkers.remove(bunkerId);
        if (bunker == null) return;

        // remove from saved data
        BunkerWorldData data = BunkerWorldDataProvider.get(level);
        data.remove(bunkerId);

        // clean chunk index
        unindexBunker(bunkerId, bunker);
    }

    // Сохраняем на диск
    private static void save(ServerLevel level, Bunker bunker) {
        BunkerWorldData data = BunkerWorldDataProvider.get(level);
        data.put(bunker.toData());
    }

    // Загрузка бункеров с диска
    public static void loadAll(ServerLevel level) {
        BunkerWorldData data = BunkerWorldDataProvider.get(level);

        for (BunkerData bunkerData : data.getBunkers().values()) {
            Bunker bunker = new Bunker(bunkerData);

            bunkers.put(bunkerData.id, bunker);
            indexBunker(bunker);
        }
    }

    public static void onBlockChanged(ServerLevel level, BlockPos pos) {
        ChunkPos chunk = new ChunkPos(pos);
        Set<UUID> affected = chunkIndex.get(chunk);

        if (affected == null || affected.isEmpty()) return;
        // копия чтобы избежать concurrent modification
        List<UUID> targets = new ArrayList<>(affected);


        for (UUID id : targets) {
            Bunker bunker = bunkers.get(id);
            if (bunker == null) continue;

            // если сломан контроллер — удаляем сразу
            if (pos.equals(bunker.getControllerPos())) {
                deleteBunker(level, id);
                continue;
            }

            //Обновляем бункер
            level.getServer().execute(() -> updateBunker(level, bunker));
        }
    }

    private static void reindex(Bunker bunker) {
        unindexBunker(bunker.getId(), bunker);
        indexBunker(bunker);
    }

    //Расскладываем бункер по чанкам
    private static void indexBunker(Bunker bunker) {
        UUID id = bunker.getId();

        for (BlockPos pos : bunker.getShellBlocks()) {
            chunkIndex
                    .computeIfAbsent(new ChunkPos(pos), k -> new HashSet<>())
                    .add(id);
        }
    }

    private static void unindexBunker(UUID id, Bunker bunker) {
        Set<ChunkPos> affectedChunks = new HashSet<>();

        // 1. Собираем уникальные чанки (убираем дубли)
        for (BlockPos pos : bunker.getShellBlocks()) {
            affectedChunks.add(new ChunkPos(pos));
        }

        // 2. Работаем уже только с уникальными чанками
        for (ChunkPos chunk : affectedChunks) {
            Set<UUID> set = chunkIndex.get(chunk);
            if (set == null) continue;

            set.remove(id);

            if (set.isEmpty()) {
                chunkIndex.remove(chunk);
            }
        }
    }

    // Возвращает значение в бункере ли игрок
    public static boolean isPlayerInBunker(ServerPlayer player) {
        BlockPos pos = player.blockPosition();

        for (Bunker bunker : bunkers.values()) {
            if (bunker.isSealed() && bunker.contains(pos)) {
                return true;
            }
        }
        return false;
    }

}
