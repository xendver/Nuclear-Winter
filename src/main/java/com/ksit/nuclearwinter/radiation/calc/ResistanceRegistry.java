package com.ksit.nuclearwinter.radiation.calc;

import net.minecraft.world.entity.Entity;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
// Классы энтити, которые устойчивы к радиации (в будущем)
public final class ResistanceRegistry {

    private ResistanceRegistry() {}

    // Энитити, которые полностью устойчивы к радиации
    private static final Set<Class<? extends Entity>>         IMMUNE  = new HashSet<>();

    // Энтити с частичной устойчивостью: класс -> множитель (0.0 - 1.0)
    // 0.0 = полный иммунитет, 1.0 = нет устойчивости, 0.5 = получает 50% дозы
    private static final Map<Class<? extends Entity>, Float>  PARTIAL = new HashMap<>();

    // Регистрация полного иммунитета к радиации
    public static void registerImmune(Class<? extends Entity> entityClass) {
        IMMUNE.add(entityClass);
    }
    // Регистрация частичной устойчивости
    // multiplier: 0.0 = иммунитет, 1.0 = без защиты, 0.5 = половина дозы
    public static void registerPartial(Class<? extends Entity> entityClass,
                                       float multiplier) {
        PARTIAL.put(entityClass, Math.max(0f, Math.min(1f, multiplier)));
    }

    // МНОЖИТЕЛЬ ДОЗЫ ДЛЯ ЭНТИТИ
    // Возвращает 0 если есть иммунитет, от 0 до 1 если частично есть, 1 если нет устойчивости
    public static float getDoseMultiplier(Entity entity) {
        // Проверка для полного иммунитета
        for (Class<? extends Entity> c : IMMUNE)
            if (c.isInstance(entity)) return 0f;
        // Проверка для частичной устойчивости
        for (Map.Entry<Class<? extends Entity>, Float> e : PARTIAL.entrySet())
            if (e.getKey().isInstance(entity)) return e.getValue();
        return 1f; // нет устойчивости (полная доза)
    }

    public static boolean isImmune(Entity entity) {
        return getDoseMultiplier(entity) == 0f;
    }
}