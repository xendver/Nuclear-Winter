package com.ksit.nuclearwinter.radiation.illness;

// Стадии лучевой болезни
// Points облучения накапливаются со временем пока entity находится в заражённом чанке
public enum IllnessStage {

    // Нет болезни
    NONE   (0f,    "Здоров"),

    // I стадия (лёгкая): 100-200 points
    STAGE_1(100f,  "Лучевая болезнь I степени"),

    // II стадия (средняя): 200-400 points
    STAGE_2(200f,  "Лучевая болезнь II степени"),

    // III стадия (тяжёлая): 400-600 points
    STAGE_3(400f,  "Лучевая болезнь III степени"),

    // IV стадия (крайне тяжёлая): 600+ points
    STAGE_4(600f,  "Лучевая болезнь IV степени");

    // Порог points с которого начинается эта стадия
    public final float  threshold;
    public final String displayName;

    IllnessStage(float threshold, String displayName) {
        this.threshold   = threshold;
        this.displayName = displayName;
    }

    // Определение стадии по количеству накопленных points
    public static IllnessStage fromPoints(float points) {
        if (points >= STAGE_4.threshold) return STAGE_4;
        if (points >= STAGE_3.threshold) return STAGE_3;
        if (points >= STAGE_2.threshold) return STAGE_2;
        if (points >= STAGE_1.threshold) return STAGE_1;
        return NONE;
    }
}