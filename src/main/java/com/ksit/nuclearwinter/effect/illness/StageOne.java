package com.ksit.nuclearwinter.effect.illness;

import com.ksit.nuclearwinter.effect.ModEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class StageOne extends MobEffect {
    private static final MobEffect[] STAGE_EFFECTS = new MobEffect[] {
            MobEffects.MOVEMENT_SLOWDOWN,
            MobEffects.WEAKNESS,
            MobEffects.CONFUSION
    };

    int duration = -1;

    public StageOne() {
        super(
                MobEffectCategory.HARMFUL, // положительный эффект
                0x00FFFF // цвет в GUI
        );
    }

    private void apply(Player player, MobEffect effect, int amp, int duration) {

        MobEffectInstance current = player.getEffect(effect);

        if (current == null) {
            player.addEffect(new MobEffectInstance(
                    effect,
                    duration,
                    amp,
                    false,
                    false
            ));
        }
    }


    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity instanceof Player player) {

            if (player.tickCount % 20 != 0) return;

            apply(player, MobEffects.MOVEMENT_SLOWDOWN, 0, duration);
            apply(player, MobEffects.WEAKNESS, 0, duration);
            apply(player, MobEffects.CONFUSION, 0, duration);
        }

    }

    public static void clear(LivingEntity entity) {
        entity.removeEffect(ModEffects.STAGE_ONE.get());

        for (MobEffect effect : STAGE_EFFECTS) {
            entity.removeEffect(effect);
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        // частота срабатывания
        return true;
    }

}
