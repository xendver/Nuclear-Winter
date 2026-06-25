package com.ksit.nuclearwinter.item.food;

import com.ksit.nuclearwinter.effect.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

// Йодид калия: добавляет -0.00005 к параметру PlayerIllnessDecay на 10 минут
public class PotassiumIodide extends Item {
    public PotassiumIodide() {
        super(new Properties()
                .food(
                    new FoodProperties.Builder()
                            .nutrition(6)
                            .saturationMod(1.0f)
                            .build()
                )
        );
    }


    @Override
    public ItemStack finishUsingItem( ItemStack stack, Level level, LivingEntity entity) {

        ItemStack result = super.finishUsingItem(stack, level, entity);

        if (!level.isClientSide && entity instanceof Player player) {

            player.addEffect(
                    new MobEffectInstance(
                            ModEffects.POTASSIUM_IODIDE_EFFECT.get(),
                            20 * 60 * 10,
                            0,
                            false,
                            false
                    )
            );


            // меняем capability
//            player.getCapability(
//                    ModCapabilities.PLAYER_DATA
//            ).ifPresent(data -> {
//
//                data.setStage(true);
//
//                data.setStageTimer(
//                        20 * 60 * 5
//                );
//
//            });
        }


        return result;
    }
}
