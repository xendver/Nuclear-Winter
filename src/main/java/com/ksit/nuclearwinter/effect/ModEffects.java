package com.ksit.nuclearwinter.effect;

import com.ksit.nuclearwinter.NuclearWinter;
import com.ksit.nuclearwinter.effect.illness.StageFour;
import com.ksit.nuclearwinter.effect.illness.StageOne;
import com.ksit.nuclearwinter.effect.illness.StageThree;
import com.ksit.nuclearwinter.effect.illness.StageTwo;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {

    @SubscribeEvent
    public void onEffectRemove(MobEffectEvent.Remove event) {

        LivingEntity entity = event.getEntity();

        if (!(entity instanceof Player player)) return;

        if (event.getEffect() == ModEffects.STAGE_ONE.get()
                || event.getEffect() == ModEffects.STAGE_TWO.get()
                || event.getEffect() == ModEffects.STAGE_THREE.get()
                || event.getEffect() == ModEffects.STAGE_FOUR.get()) {

            // запрещаем снятие эффекта (включая молоко)
            event.setCanceled(true);
        }
    }

    public static final DeferredRegister<MobEffect> EFFECTS =
            DeferredRegister.create(
                    ForgeRegistries.MOB_EFFECTS,
                    NuclearWinter.MOD_ID
            );


    public static final RegistryObject<MobEffect> STAGE_ONE =
            EFFECTS.register(
                    "stage_one",
                    StageOne::new
            );

    public static final RegistryObject<MobEffect> STAGE_TWO =
            EFFECTS.register(
                    "stage_two",
                    StageTwo::new
            );

    public static final RegistryObject<MobEffect> STAGE_THREE =
            EFFECTS.register(
                    "stage_three",
                    StageThree::new
            );

    public static final RegistryObject<MobEffect> STAGE_FOUR =
            EFFECTS.register(
                    "stage_four",
                    StageFour::new
            );

}
