package com.ksit.nuclearwinter.radiation;

import java.util.List;


// Калькулятор радиации чанка с синергией
public final class RadiationCalculator {

    private RadiationCalculator() {}
    // Суммарный уровень радиации для чанка (chunkX, chunkZ) от списка активных источников, с учётом синергии
    // chunkX - координата чанка по X
    // chunkZ - координата чанка по Z
    // sources - список активных источников
    // Возвращает итоговый уровень радиации для этого чанка
    public static float calcChunkRadiation(int chunkX, int chunkZ, List<ActiveSource> sources) {
        float sum = 0;

        for (ActiveSource src : sources) {
            // Расстояние от источника до чанка (в чанках)

            float dx = chunkX - src.chunkX();
            float dz = chunkZ - src.chunkZ();
            float d = (float) Math.sqrt(dx * dx + dz * dz);

            // Вклад источника: ipower - dpc * distance, не меньше 0
            float c = src.radiation().getInitialPower() - src.radiation().getDecayPerChunk() * d;
            sum += c;
        }
        return sum;
    }
}