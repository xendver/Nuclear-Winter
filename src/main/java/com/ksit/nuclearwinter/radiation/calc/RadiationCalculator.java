package com.ksit.nuclearwinter.radiation.calc;

import com.ksit.nuclearwinter.radiation.api.IRadiation;
import com.ksit.nuclearwinter.radiation.api.RadiationConfig;

import java.util.ArrayList;
import java.util.List;

// Калькулятор радиации чанка с синергией
public final class RadiationCalculator {

    private RadiationCalculator() {}

    // Вклад одного источника в один чанк
    private static class Contrib {
        final float value; // c = max(0, ipower - dpc * distance)
        final float posX, posZ;   // позиция источника в чанках [cx, cz]

        Contrib(float value, float posX, float posZ) {
            this.value = value;
            this.posX  = posX;
            this.posZ  = posZ;
        }
    }

    // === ВСПОМОГАТЕЛЬНЫЙ КЛАСС ActiveSource =======================================
    // Нужен калькулятору - позиция источника в чанках + его свойства
    public static class ActiveSource {
        public final int        chunkX;
        public final int        chunkZ;
        public final IRadiation radiation;

        public ActiveSource(int chunkX, int chunkZ, IRadiation radiation) {
            this.chunkX    = chunkX;
            this.chunkZ    = chunkZ;
            this.radiation = radiation;
        }
    }

    // Суммарный уровень радиации для чанка (chunkX, chunkZ) от списка активных источников, с учётом синергии
    // chunkX - координата чанка по X
    // chunkZ - координата чанка по Z
    // sources - список активных источников
    // Возвращает итоговый уровень радиации для этого чанка
    public static float calcChunkRadiation(int chunkX, int chunkZ,
                                           List<ActiveSource> sources) {
        List<Contrib> contribs = new ArrayList<>();

        for (ActiveSource src : sources) {
            // Расстояние от источника до чанка (в чанках)
            float dx = chunkX - src.chunkX;
            float dz = chunkZ - src.chunkZ;
            float d  = (float) Math.sqrt(dx * dx + dz * dz);

            // Вклад источника: ipower - dpc * distance, не меньше 0
            float c  = src.radiation.getInitialPower()
                    - src.radiation.getDecayPerChunk() * d;

            // Источник влияет только если чанк в радиусе И вклад положительный
            if (c > 0f && src.radiation.getRadius() > d) {
                contribs.add(new Contrib(c, src.chunkX, src.chunkZ));
            }
        }

        int n = contribs.size();
        if (n == 0) return 0f;

        // Суммарный вклад всех источников
        float S = 0f;
        for (Contrib c : contribs) S += c.value;

        // Если мало источников или суммарный вклад ниже порога - без синергии
        // (оптимизация: не считать phi/psi/omega для слабых чанков)
        if (S <= RadiationConfig.P_THRESH || n < 2) return S;

        float meanC  = S / n;

        // == phi: резонанс ==================================================
        // exp(-spread / LAMBDA) * exp(-BETA_G * cv)
        // Источники близко и одинаковой мощности → phi близко к 1
        float spread = calcSpread(contribs);
        float cv     = calcCV(contribs, meanC);
        float phi  = (float)(Math.exp(-spread / RadiationConfig.LAMBDA)
                * Math.exp(-RadiationConfig.BETA_G * cv));

        // == psi: каскад =====================================================
        // n^ALPHA_N / (1 + MU * n^ALPHA_N)
        // Насыщается при большом n: при n→∞, psi → 1/MU
        float nPow = (float) Math.pow(n, RadiationConfig.ALPHA_N);
        float psi  = nPow / (1f + RadiationConfig.MU * nPow);

        // == omega: штраф за доминирование ===================================
        // 1 - (max/S)^GAMMA_DOM
        // Один источник даёт 90% → omega мал → синергия слабее
        float maxC  = 0f;
        for (Contrib c : contribs) if (c.value > maxC) maxC = c.value;
        float omega      = 1f - (float) Math.pow(maxC / S, RadiationConfig.GAMMA_DOM);

        // == soft_thresh: мягкий порог ========================================
        // S / (S + S0)
        // При малом S синергия ослаблена пропорционально
        float softThresh = S / (S + RadiationConfig.S0);

        float synergy = RadiationConfig.K_SYNERGY * phi * psi * omega * softThresh;
        return S * (1f + synergy);
    }

    // Средний разброс позиций источников вокруг их центроида
    // spread = 0 если все источники в одном месте
    private static float calcSpread(List<Contrib> contribs) {
        int n = contribs.size();
        if (n < 2) return 0f;

        // Центроид
        float cx = 0f, cz = 0f;
        for (Contrib c : contribs) { cx += c.posX; cz += c.posZ; }
        cx /= n; cz /= n;

        // Среднеквадратическое отклонение от центроида
        float sum = 0f;
        for (Contrib c : contribs) {
            float dx = c.posX - cx, dz = c.posZ - cz;
            sum += dx * dx + dz * dz;
        }
        return (float) Math.sqrt(sum / n);
    }

    // Коэффициент вариации мощностей источников
    // cv = 0 → все источники равны; cv → ∞ → один доминирует
    private static float calcCV(List<Contrib> contribs, float mean) {
        if (mean == 0f) return 0f;
        float variance = 0f;
        for (Contrib c : contribs) {
            float diff = c.value - mean;
            variance += diff * diff;
        }
        variance /= contribs.size();
        return (float) Math.sqrt(variance) / mean;
    }
}