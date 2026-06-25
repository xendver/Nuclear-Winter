package com.ksit.nuclearwinter.effect.food;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class PotassiumIodideEffect extends MobEffect {
    public PotassiumIodideEffect() {
        super(
                MobEffectCategory.BENEFICIAL,
                0x00FFFF
        );
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {

    }


    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        // частота срабатывания
        return true;
    }
}
