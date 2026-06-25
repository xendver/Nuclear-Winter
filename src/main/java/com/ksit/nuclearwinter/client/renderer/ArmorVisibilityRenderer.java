package com.ksit.nuclearwinter.client.renderer;

import com.ksit.nuclearwinter.NuclearWinter;
import com.ksit.nuclearwinter.item.armor.ArkArmorItem;
import com.ksit.nuclearwinter.item.armor.HazmatArmorItem;
import com.ksit.nuclearwinter.item.armor.MOPPArmorItem;
import com.ksit.nuclearwinter.item.armor.RaggedArmorItem;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = NuclearWinter.MOD_ID, value = Dist.CLIENT)
public final class ArmorVisibilityRenderer {

    // pre render
    @SubscribeEvent
    public static void onRenderPlayerPre(RenderPlayerEvent.Pre event) {
        Player player = event.getEntity();

        PlayerModel<AbstractClientPlayer> model = event.getRenderer().getModel();

        resetModelVisibility(model);

        // броня по слотам
        ItemStack headStack = player.getItemBySlot(EquipmentSlot.HEAD);
        ItemStack chestStack = player.getItemBySlot(EquipmentSlot.CHEST);
        ItemStack legsStack = player.getItemBySlot(EquipmentSlot.LEGS);

        // скрытие частей тела в зависимости от надетой брони
        hideForHelmet(model, headStack);
        hideForChestplate(model, chestStack);
        hideForLeggings(model, legsStack);
    }

    // post render
    @SubscribeEvent
    public static void onRenderPlayerPost(RenderPlayerEvent.Post event) {
        PlayerModel<AbstractClientPlayer> model = event.getRenderer().getModel();

        resetModelVisibility(model);
    }

    // ========================================================================================================
    // основная логика скрытия частей брони
    private static void hideForHelmet(PlayerModel<AbstractClientPlayer> model, ItemStack stack) {
        if (stack.isEmpty()) {
            return;
        }

        Item item = stack.getItem();

        if (isOurArmor(item)) {
            model.head.visible = false;
            model.hat.visible = false;
        }
    }

    private static void hideForChestplate(PlayerModel<AbstractClientPlayer> model, ItemStack stack) {
        if (stack.isEmpty()) {
            return;
        }

        Item item = stack.getItem();

        if (isOurArmor(item)) {
            model.body.visible = false;
            model.leftArm.visible = false;
            model.rightArm.visible = false;

            model.jacket.visible = false;
            model.leftSleeve.visible = false;
            model.rightSleeve.visible = false;
        }
    }

    private static void hideForLeggings(PlayerModel<AbstractClientPlayer> model, ItemStack stack) {
        if (stack.isEmpty()) {
            return;
        }

        Item item = stack.getItem();

        if (isOurArmor(item)) {
            model.leftLeg.visible = false;
            model.rightLeg.visible = false;

            model.leftPants.visible = false;
            model.rightPants.visible = false;
        }
    }

    // ========================================================================================================
    private static boolean isOurArmor(Item item) {
        return item instanceof ArkArmorItem
                || item instanceof HazmatArmorItem
                || item instanceof MOPPArmorItem
                || item instanceof RaggedArmorItem;
    }

    private static void resetModelVisibility(PlayerModel<AbstractClientPlayer> model) {
        // голова
        model.head.visible = true;
        model.hat.visible = true;

        // тело
        model.body.visible = true;
        model.jacket.visible = true;


        // руки
        model.leftArm.visible = true;
        model.rightArm.visible = true;
        model.leftSleeve.visible = true;
        model.rightSleeve.visible = true;

        // ноги
        model.leftLeg.visible = true;
        model.rightLeg.visible = true;
        model.leftPants.visible = true;
        model.rightPants.visible = true;
    }
}
