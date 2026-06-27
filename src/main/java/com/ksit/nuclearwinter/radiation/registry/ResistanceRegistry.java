package com.ksit.nuclearwinter.radiation.registry;

import com.ksit.nuclearwinter.item.armor.ArkArmorItem;
import com.ksit.nuclearwinter.item.armor.HazmatArmorItem;
import com.ksit.nuclearwinter.item.armor.MOPPArmorItem;
import com.ksit.nuclearwinter.item.armor.RaggedArmorItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

// Классы энтити, которые устойчивы к радиации (в будущем)
public final class ResistanceRegistry {

    public static final class ProtectionData {
        private final float doseMultiplier;
        private final float illnessDecayBonus;

        public ProtectionData(float doseMultiplier, float illnessDecayBonus) {
            this.doseMultiplier = Math.max(0f, doseMultiplier);
            this.illnessDecayBonus = Math.max(0f, illnessDecayBonus);
        }

        public float doseMultiplier() {
            return doseMultiplier;
        }

        public float illnessDecayBonus() {
            return illnessDecayBonus;
        }
    }

    // Энтити с частичной устойчивостью: класс -> множитель (0.0 - 1.0)
    // 0.0 = полный иммунитет, 1.0 = нет устойчивости, 0.5 = получает 50% дозы
    private static final Map<Class<? extends Entity>, ProtectionData> entityProtection = new HashMap<>();
    public static final ProtectionData NO_PROTECTION = new ProtectionData(1.0f, 0.0f);
    public static final ProtectionData FULL_IMMUNITY = new ProtectionData(0.0f, 0.0f);

    // Регистрация частичной устойчивости
    // multiplier: 0.0 = иммунитет, 1.0 = без защиты, 0.5 = половина дозы
    public static void registerPartial(Class<? extends Entity> entityClass,
                                       float multiplier) {
        entityProtection.put(entityClass, new ProtectionData(additional(multiplier), 0.0f));
    }

    // Регистрация полной устойчивости
    public static void registerImmune(Class<? extends Entity> entityClass) {
        entityProtection.put(entityClass, FULL_IMMUNITY);
    }


    public static void registerProtection(Class<? extends Entity> entityClass,
                                          float doseMultiplier, float illnessDecayBonus) {
        entityProtection.put(entityClass,
                new ProtectionData(additional(doseMultiplier),
                        Math.max(0f, illnessDecayBonus)));

    }


    public static ProtectionData getProtectionData(Entity entity) {

        // проверка брони на игроке
        if (entity instanceof Player player) {
            ProtectionData armorProtection = getPlayerProtection(player);
            if (armorProtection != NO_PROTECTION) return armorProtection;
        }

        // проверка на зарегистрированную защиту по классу сущности
        for (Map.Entry<Class<? extends Entity>, ProtectionData> entry : entityProtection.entrySet()) {
            if (entry.getKey().isInstance(entity)) {
                return entry.getValue();
            }
        }

        return NO_PROTECTION;
    }


    // МНОЖИТЕЛЬ ДОЗЫ ДЛЯ ЭНТИТИ
    // Возвращает 0 если есть иммунитет, от 0 до 1 если частично есть, 1 если нет устойчивости
    public static float getDoseMultiplier(Entity entity) {
        return getProtectionData(entity).doseMultiplier();
    }

    public static float getIllnessDecayBonus(Entity entity) {
        return getProtectionData(entity).illnessDecayBonus;
    }

    public static boolean isImmune(Entity entity) {
        return getProtectionData(entity).doseMultiplier() == 0f;
    }

    private static ProtectionData getPlayerProtection(Player player) {

        if (isWearingFullSet(player, ArkArmorItem.class)) {
            return new ProtectionData(0.0001f, 0.002f);
        }

        if (isWearingFullSet(player, HazmatArmorItem.class)) {
            return new ProtectionData(0.20f, 0.0003f);
        }

        if (isWearingFullSet(player, MOPPArmorItem.class)) {
            return new ProtectionData(0.55f, 0.00005f);
        }

        if (isWearingFullSet(player, RaggedArmorItem.class)) {
            return new ProtectionData(0.85f, 0.0f);
        }

        return NO_PROTECTION;
    }

    private static boolean isWearingFullSet(Player player, Class<?> armorClass) {

        int armorPieces = 0;

        for (ItemStack stack : player.getArmorSlots()) {
            if (stack.isEmpty()) {
                return false;
            }

            if (!armorClass.isInstance(stack.getItem())) {
                return false;
            }

            armorPieces++;
        }

        return armorPieces == 4;
    }


    private static float additional(float value) {
        return Math.max(0f, Math.min(1f, value));
    }


}