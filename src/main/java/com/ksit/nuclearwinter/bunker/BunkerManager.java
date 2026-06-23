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

        System.out.println("Добавили бункер");

        indexBunker(bunker);
    }

    public static void onBlockChanged(ServerLevel level, BlockPos pos) {

        ChunkPos chunk = new ChunkPos(pos);

        Set<UUID> near = chunkIndex.get(chunk);
        if (near == null) return;

        for (UUID id : near) {

            Bunker bunker = bunkers.get(id);
            if (bunker == null) continue;

            if(bunker.getControllerPos().equals(pos)) {
                deleteBunker(level, pos);
                break;
            }

            level.getServer().execute(() -> {
                bunker.recalculate(level);
            });

            reindex(bunker);

        }
    }


    private static void reindex(Bunker bunker) {

        for (Set<UUID> list : chunkIndex.values()) {
            list.remove(bunker.getId());
        }

        indexBunker(bunker);
    }

    public static List<Bunker> getBunkersAt(BlockPos pos) {

        ChunkPos chunk = new ChunkPos(pos);

        Set<UUID> ids =
                chunkIndex.get(chunk);


        if (ids == null)
            return List.of();


        return ids.stream()
                .map(bunkers::get)
                .toList();
    }

    public static boolean isPlayerInBunker(ServerPlayer player) {

        BlockPos pos = player.blockPosition();

        for (Bunker bunker : bunkers.values()) {
            if (bunker.contains(pos) && bunker.isSealed()) {
                return true;
            }
        }

        return false;
    }

    // Создаем бункер
    public static void createBunker(ServerLevel level, BlockPos controllerPos) {

        Bunker bunker = new Bunker(controllerPos);
        bunker.recalculate(level);
        addBunker(bunker);

        BunkerWorldData data = BunkerWorldDataProvider.get(level);

        data.add(new BunkerData(
                bunker.getId(),
                controllerPos,
                bunker.isSealed()
        ));

    }

    // Удаляем бункер
    public static void deleteBunker(ServerLevel level, BlockPos controllerPos) {

        BunkerWorldData data = BunkerWorldDataProvider.get(level);

        UUID targetId = null;

        // 1. ищем бункер по controllerPos
        for (BunkerData bd : data.getBunkers().values()) {

            if (bd.controllerPos.equals(controllerPos)) {
                targetId = bd.id;
                break;
            }
        }

        if (targetId == null) {
            System.out.println("Bunker not found at: " + controllerPos);
            return;
        }

        // 2. удаляем из runtime
        Bunker removed = bunkers.remove(targetId);

        // 3. удаляем из saved data
        data.remove(targetId);

        // 4. чистим chunk index
        if (removed != null) {
            for (BlockPos pos : removed.getShellBlocks()) {

                ChunkPos chunk = new ChunkPos(pos);

                Set<UUID> set = chunkIndex.get(chunk);

                if (set != null) {
                    set.remove(targetId);

                    if (set.isEmpty()) {
                        chunkIndex.remove(chunk);
                    }
                }
            }
        }

        System.out.println("Deleted bunker at: " + controllerPos);
    }

    //Расскладываем бункер по чанкам
    private static void indexBunker(Bunker bunker) {
        // Берем все стены бункера и записываем в каком они чанке
        for (BlockPos pos : bunker.getShellBlocks()) {

            ChunkPos chunk = new ChunkPos(pos);

            chunkIndex
                    .computeIfAbsent(chunk, k -> new HashSet<>())
                    .add(bunker.getId());
        }
    }

    public static void loadAll(ServerLevel level) {

        BunkerWorldData data = BunkerWorldDataProvider.get(level);

        for (BunkerData bd : data.getBunkers().values()) {

            Bunker bunker = new Bunker(bd.controllerPos, bd.id);

            bunker.recalculate(level);
            bunkers.put(bd.id, bunker);
            indexBunker(bunker);
        }

        System.out.println("Loaded bunkers: " + bunkers.size());
    }
}
