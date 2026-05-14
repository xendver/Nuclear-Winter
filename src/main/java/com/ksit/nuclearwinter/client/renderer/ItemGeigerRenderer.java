package com.ksit.nuclearwinter.client.renderer;

import com.ksit.nuclearwinter.item.ModItems;
import com.ksit.nuclearwinter.network.PacketChunkRadiation;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(Dist.CLIENT)
public class ItemGeigerRenderer {

    @SubscribeEvent
    public void onRenderHand(RenderHandEvent event) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        if (player == null)
            return;

        InteractionHand hand = event.getHand();
        ItemStack stack = player.getItemInHand(hand);

        if (stack.isEmpty() || stack.getItem() != ModItems.GEIGER_COUNTER.get())
            return;

        PoseStack poseStack = event.getPoseStack();
        MultiBufferSource buffer = event.getMultiBufferSource();

        boolean rightHand = (hand == InteractionHand.MAIN_HAND)
                ? player.getMainArm() == HumanoidArm.RIGHT
                : player.getMainArm() == HumanoidArm.LEFT;

        float radiation = PacketChunkRadiation.ClientRadiationData.radiationLevel;
        String text = String.format("%.1f RAD", radiation);
        Font font = mc.font;

        poseStack.pushPose();

        float x = 0.380F;
        float y = 0.05F;
        float z = -0.600F;

        if (rightHand) {
            poseStack.translate(x + 0.245D, y - 0.135D, z - 0.515D);
        } else {
            poseStack.translate(x - 0.999D, y - 0.135D, z - 0.515D);
        }

        // Rotate
        poseStack.mulPose(Axis.XP.rotationDegrees(-10.0f));
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0f));
        poseStack.mulPose(Axis.ZP.rotationDegrees(0f));

        // Scale
        float scale = 0.0065f;
        poseStack.scale(-scale, -scale, scale);

        // Color
        int color;
        if (radiation > 400f)
            color = 0xFF4444;  // Красный
        else if (radiation > 100f)
            color = 0xFFAA00;  // Оранжевый
        else
            color = 0x44FF44;  // Зелёный

        int width = font.width(text);

        font.drawInBatch(
                text,
                -width / 2f,
                0,
                color,
                false,
                poseStack.last().pose(),
                buffer,
                Font.DisplayMode.NORMAL,
                0,
                event.getPackedLight()
        );

        poseStack.popPose();
    }
}