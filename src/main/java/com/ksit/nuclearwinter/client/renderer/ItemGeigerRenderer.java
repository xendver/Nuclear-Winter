package com.ksit.nuclearwinter.client.renderer;

import com.ksit.nuclearwinter.item.ModItems;
import com.ksit.nuclearwinter.network.PacketChunkRadiation;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

// @OnlyIn(Dist.CLIENT) — класс загружается только на клиенте
@OnlyIn(Dist.CLIENT)
public class ItemGeigerRenderer {

    @SubscribeEvent
    public void onRenderHand(RenderHandEvent event) {

        ItemStack stack = event.getItemStack();

        if (stack.isEmpty() || stack.getItem() != ModItems.GEIGER_COUNTER.get())
            return;

        boolean isRightHand = event.getHand() == InteractionHand.MAIN_HAND;

        Minecraft mc = Minecraft.getInstance();

        PoseStack pose = event.getPoseStack();

        // == РЕНДЕР МОДЕЛИ ИТЕМА =============================================
        mc.getItemRenderer().renderStatic(
                stack,
                net.minecraft.world.item.ItemDisplayContext.FIRST_PERSON_RIGHT_HAND,
                event.getPackedLight(),
                net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY,
                pose,
                event.getMultiBufferSource(),
                mc.level,
                0
        );

        // == ТЕКСТ ============================================================
        float level = PacketChunkRadiation.ClientRadiationData.radiationLevel;
        String text = String.format("%.1f rad", level);

        pose.pushPose();

        // Позиция текста
        if (isRightHand) {
            pose.translate(0.22, 0, -0.45);
        } else {
            pose.translate(-0.22, 0, -0.45);
        }

        // Поворот
        pose.mulPose(Axis.YP.rotationDegrees(180f));

        // Масштаб
        float scale = 0.01f;
        pose.scale(-scale, -scale, scale);

        // Цвет текста по уровню радиации
        int color;
        if      (level > 400f) color = 0xFF0000; // красный
        else if (level > 100f) color = 0xFFAA00; // оранжевый
        else                   color = 0x00FF00; // зелёный

        Font font = mc.font;
        int textWidth = font.width(text);

        font.drawInBatch(
                text,
                -textWidth / 2f,
                0f,
                color,
                false,
                pose.last().pose(),
                event.getMultiBufferSource(),
                Font.DisplayMode.NORMAL,
                0x000000,
                event.getPackedLight()
        );

        pose.popPose();

        event.setCanceled(true);
    }
}