package com.ksit.nuclearwinter.item.food;

import com.ksit.nuclearwinter.effect.ModEffects;
import com.ksit.nuclearwinter.effect.illness.StageOne;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class Chelator extends Item {
    public Chelator() {
        super(new Properties()
                .food(new FoodProperties.Builder()
                        .nutrition(6)
                        .saturationMod(1.0f)
                        .build()
                )
        );
    }


    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {

        ItemStack result = super.finishUsingItem(stack, level, entity);

        if (!level.isClientSide && entity instanceof Player player) {

            if (player.hasEffect(ModEffects.STAGE_ONE.get())) {
                StageOne.clear(entity);
            }
            if (player.hasEffect(ModEffects.IODINE_EFFECT.get())) {
                Iodine.clear(entity);
            }
            if (player.hasEffect(ModEffects.ANTIRAD_EFFECT.get())) {
                AntiRad.clear(entity);
            }

            player.addEffect(
                    new MobEffectInstance(
                            ModEffects.CHELATOR_EFFECT.get(),
                            20 * 60 * 5,
                            0,
                            false,
                            false
                    )
            );
        }


        return result;
    }
}
