package com.ksit.nuclearwinter.effect.illness;

import com.ksit.nuclearwinter.effect.ModEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class StageOne extends MobEffect {
    private static final MobEffect[] STAGE_EFFECTS = new MobEffect[]{
            MobEffects.MOVEMENT_SLOWDOWN,
            MobEffects.WEAKNESS,
            MobEffects.CONFUSION
    };

    int duration = -1;

    public StageOne() {
        super(
                MobEffectCategory.HARMFUL,
                0x00FFFF
        );
    }

    private void apply(LivingEntity entity, MobEffect effect, int amp, int duration) {

        MobEffectInstance current = entity.getEffect(effect);

        if (current == null) {
            entity.addEffect(new MobEffectInstance(
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

        if (entity.tickCount % 20 != 0) return;

        apply(entity, MobEffects.MOVEMENT_SLOWDOWN, 0, duration);
        apply(entity, MobEffects.WEAKNESS, 0, duration);
        apply(entity, MobEffects.CONFUSION, 0, duration);


    }

    public static void clear(LivingEntity entity) {
        entity.removeEffect(ModEffects.STAGE_ONE.get());

        for (MobEffect effect : STAGE_EFFECTS) {
            entity.removeEffect(effect);
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

}
