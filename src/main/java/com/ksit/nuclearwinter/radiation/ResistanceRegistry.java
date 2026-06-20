package com.ksit.nuclearwinter.radiation;

import com.ksit.nuclearwinter.item.armor.ArkArmorItem;
import com.ksit.nuclearwinter.item.armor.HazmatArmorItem;
import com.ksit.nuclearwinter.item.armor.MOPPArmorItem;
import com.ksit.nuclearwinter.item.armor.RaggedArmorItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
// Классы энтити, которые устойчивы к радиации (в будущем)
public final class ResistanceRegistry {

    private ResistanceRegistry() {}

    // Энитити, которые полностью устойчивы к радиации
    private static final Set<Class<? extends Entity>> IMMUNE = new HashSet<>();

    // Энтити с частичной устойчивостью: класс -> множитель (0.0 - 1.0)
    // 0.0 = полный иммунитет, 1.0 = нет устойчивости, 0.5 = получает 50% дозы
    private static final Map<Class<? extends Entity>, Float>  PARTIAL = new HashMap<>();

    public static final class ProtectionData{
        private final float doseMultiplier;
        private final float illnessDecayBonus;

        public ProtectionData(float doseMultiplier, float illnessDecayBonus){
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

    public static final ProtectionData NO_PROTECTION = new ProtectionData(1.0f, 0.0f);
    public static final ProtectionData FULL_IMMUNITY = new ProtectionData(0.0f, 0.0f);

    public static ProtectionData getProtectionData(Entity entity) {

        // Полный иммунитет сущности
        for (Class<? extends Entity> entityClass : IMMUNE) {
            if (entityClass.isInstance(entity)) {
                return FULL_IMMUNITY;
            }
        }

        // Частичная устойчивость сущности
        for (Map.Entry<Class<? extends Entity>, Float> entry : PARTIAL.entrySet()) {
            if (entry.getKey().isInstance(entity)) {
                return new ProtectionData(entry.getValue(), 0.0f);
            }
        }

        // Броня игрока
        if (entity instanceof Player player) {
            return getPlayerArmorProtection(player);
        }

        return NO_PROTECTION;
    }


    // МНОЖИТЕЛЬ ДОЗЫ ДЛЯ ЭНТИТИ
    // Возвращает 0 если есть иммунитет, от 0 до 1 если частично есть, 1 если нет устойчивости
    public static float getDoseMultiplier(Entity entity) {
        return getProtectionData(entity).doseMultiplier();
    }

    public static boolean isImmune(Entity entity) {
        return getProtectionData(entity).doseMultiplier() == 0f;
    }

    private static ProtectionData getPlayerArmorProtection(Player player) {

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


}