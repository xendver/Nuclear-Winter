package com.ksit.nuclearwinter.radiation.service;

import com.ksit.nuclearwinter.radiation.capability.RadiationCapability;
import com.ksit.nuclearwinter.radiation.config.RadiationConfig;
import com.ksit.nuclearwinter.radiation.util.ActiveSource;
import it.unimi.dsi.fastutil.longs.Long2FloatMap;
import it.unimi.dsi.fastutil.longs.Long2FloatOpenHashMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.LevelChunk;

public class ChunkRadiationService {
    private final RadiationSourceManager sourceManager;

    private final Long2FloatOpenHashMap chunkActivities = new Long2FloatOpenHashMap();

    public ChunkRadiationService(RadiationSourceManager sourceManager) {
        this.sourceManager = sourceManager;
    }

    //ОБНОВЛЕНИЕ РАДИАЦИИ ЧАНКОВ
    public void updateChunkRadiation(ServerLevel level) {

        // Ключ = ChunkPos.asLong(x, z)
        // Значение = суммарная активность чанка за эту секунду
        final Long2FloatOpenHashMap chunkActivities = this.chunkActivities;
        chunkActivities.clear();
        chunkActivities.defaultReturnValue(0f);

        //ПЕРВЫЙ ПРОХОД: Собираем сумму вкладов всех источников
        //пробег по всем источникам
        for (ActiveSource source : sourceManager.getAllSources()) {

            //получаем координаты источника
            int sourceChunkX = source.chunkX();
            int sourceChunkZ = source.chunkZ();

            //получаем радиус источника
            float radius = source.radiation().getRadius();
            int chunkRadius = (int) Math.ceil(radius);

            // добавление чанков с воздействием источников
            for (int dx = -chunkRadius; dx <= chunkRadius; dx++) {
                for (int dz = -chunkRadius; dz <= chunkRadius; dz++) {
                    int targetChunkX = sourceChunkX + dx;
                    int targetChunkZ = sourceChunkZ + dz;

                    //получаем чанк и проверяем находится он в прогрузке или нет
                    LevelChunk chunk = level.getChunkSource().getChunkNow(targetChunkX, targetChunkZ);
                    if (chunk == null) continue;

                    //ищем дистанцию от чанка до источника
                    float distanceX = targetChunkX - sourceChunkX;
                    float distanceZ = targetChunkZ - sourceChunkZ;
                    float distance = (float) Math.sqrt(distanceX * distanceX + distanceZ * distanceZ);

                    //высчитываем вклад источника в этот чанк
                    float contribution = source.radiation().getInitialPower() - source.radiation().getDecayPerChunk() * distance;

                    if (contribution <= 0) continue;

                    //добавляем это в мапу с вкладами источников
                    long key = ChunkPos.asLong(targetChunkX, targetChunkZ);
                    chunkActivities.addTo(key, contribution);
                }
            }
        }

        /* ВТОРОЙ ПРОХОД
         * Записываем activity и pollution в capability
         * перенос из таблицы в капабилити */
        for (Long2FloatMap.Entry entry : chunkActivities.long2FloatEntrySet()) {

            //получаем чанк
            long key = entry.getLongKey();
            int chunkX = ChunkPos.getX(key);
            int chunkZ = ChunkPos.getZ(key);

            LevelChunk chunk = level.getChunkSource().getChunkNow(chunkX, chunkZ);
            if (chunk == null) continue;

            //получаем вклад всех источников за последнюю секунду
            float activity = entry.getFloatValue();

            chunk.getCapability(RadiationCapability.CHUNK_RADIATION)
                    .ifPresent(cap -> {

                        // ACTIVITY: Всегда хранит сумму вкладов за текущий тик.
                        //записываем activity в capability
                        cap.setRadiationActivityLevel(activity);

                        //POLLUTION: Накопленная радиация.
                        //добавляем значение activity (вкладу всех источников за последнюю секунду)
                        //к pollution (радиационному загрязнению) и применяем decay
                        float pollution = cap.getRadiationPollutionLevel();
                        pollution = pollution * (1f - RadiationConfig.DECAY_RATE) + activity;
                        cap.setRadiationPollutionLevel(pollution);
                    });
        }
    }
}
