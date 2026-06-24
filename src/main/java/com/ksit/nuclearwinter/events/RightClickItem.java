package com.ksit.nuclearwinter.events;


import com.ksit.nuclearwinter.effect.ModEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RightClickItem {
    @SubscribeEvent
    public void onMilkUsed(PlayerInteractEvent.RightClickItem event) {
        if (event.getItemStack().getItem() == Items.MILK_BUCKET) {
            Player player = event.getEntity();

            if (player.hasEffect(ModEffects.STAGE_ONE.get())
                    || player.hasEffect(ModEffects.STAGE_TWO.get())
                    || player.hasEffect(ModEffects.STAGE_THREE.get())
                    || player.hasEffect(ModEffects.STAGE_FOUR.get())) {

                event.setCanceled(true); // полностью запрещаем пить молоко
            }
        }
    }
}