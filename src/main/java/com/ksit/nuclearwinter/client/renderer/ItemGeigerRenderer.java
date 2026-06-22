package com.ksit.nuclearwinter.client.renderer;

import com.ksit.nuclearwinter.NuclearWinter;
import com.ksit.nuclearwinter.item.custom.ItemGeigerCounter;
import com.ksit.nuclearwinter.network.PacketChunkRadiation;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import net.minecraft.client.renderer.RenderType;
import software.bernie.geckolib.util.RenderUtils;

@OnlyIn(Dist.CLIENT)
public class ItemGeigerRenderer extends GeoItemRenderer<ItemGeigerCounter> {

    public ItemGeigerRenderer() {
        super(new DefaultedItemGeoModel<>(ResourceLocation.fromNamespaceAndPath(NuclearWinter.MOD_ID, "geiger_counter")));
    }
    public void actuallyRender(
            PoseStack poseStack,
            ItemGeigerCounter animatable,
            BakedGeoModel model,
            RenderType renderType,
            MultiBufferSource bufferSource,
            VertexConsumer buffer,
            boolean isReRender,
            float partialTick,
            int packedLight,
            int packedOverlay,
            float red,
            float green,
            float blue,
            float alpha) {
        super.actuallyRender(
                poseStack,
                animatable,
                model,
                renderType,
                bufferSource,
                buffer,
                isReRender,
                partialTick,
                packedLight,
                packedOverlay,
                red,
                green,
                blue,
                alpha
        );
        GeoBone displayBone = this.model.getBone("transparent_display").orElse(null);

        if (displayBone==null) return;
        float radiation = PacketChunkRadiation.ClientRadiationData.radiationLevel;

        poseStack.pushPose();
        String text = String.format("%.1f RAD", radiation);

        RenderUtils.translateMatrixToBone(poseStack, displayBone);
        RenderUtils.translateToPivotPoint(poseStack, displayBone);

        poseStack.mulPose(Axis.XP.rotationDegrees(90.0f));
        poseStack.mulPose(Axis.YP.rotationDegrees(0f));
        poseStack.mulPose(Axis.ZP.rotationDegrees(0f));

        // Scale
        float scale = 0.01f;
        poseStack.scale(scale, scale, scale);

        // Color
        int color;
        if      (radiation >= 1500.0f) color = 0xFF0000; // красный
        else if (radiation >= 120.0f)  color = 0xFF2200; // тёмно-красный
        else if (radiation >= 6.0f)    color = 0xFF6600; // оранжевый
        else if (radiation >= 0.8f)    color = 0xFFAA00; // жёлтый
        else if (radiation >= 0.2f)    color = 0xFFFF00; // светло-жёлтый
        else
            color = 0x44FF44;  // Зелёный

        Minecraft mc = Minecraft.getInstance();
        Font font = mc.font;

        font.drawInBatch(
                text,
                -font.width(text) / 2f,
                0,
                color,
                false,
                poseStack.last().pose(), bufferSource,
                Font.DisplayMode.NORMAL,
                0,
                packedLight
        );

        poseStack.popPose();
    }
}