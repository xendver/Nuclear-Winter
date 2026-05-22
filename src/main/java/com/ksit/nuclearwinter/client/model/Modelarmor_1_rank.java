package com.ksit.nuclearwinter.client.model;

import net.minecraft.world.entity.Entity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.EntityModel;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;
// Made with Blockbench 5.1.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class Modelarmor_1_rank<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("nuclearwinter", "armor_1_rank"), "main");
	public final ModelPart Head;
	public final ModelPart Body;
	public final ModelPart RightArm;
	public final ModelPart LeftArm;
	public final ModelPart RightLeg;
	public final ModelPart LeftLeg;
	public final ModelPart LeftBoot;
	public final ModelPart RightBoot;
	private final ModelPart bb_main;

	public Modelarmor_1_rank (ModelPart root) {
		this.Head = root.getChild("Head");
		this.Body = root.getChild("Body");
		this.RightArm = root.getChild("RightArm");
		this.LeftArm = root.getChild("LeftArm");
		this.RightLeg = root.getChild("RightLeg");
		this.LeftLeg = root.getChild("LeftLeg");
		this.LeftBoot = root.getChild("LeftBoot");
		this.RightBoot = root.getChild("RightBoot");
		this.bb_main = root.getChild("bb_main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Head = partdefinition.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -8.5F, -5.1F, 10.0F, 1.0F, 11.0F, new CubeDeformation(0.0F))
		.texOffs(0, 23).addBox(-4.0F, -9.2F, -3.9F, 8.0F, 1.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(0, 49).addBox(-5.0F, -8.0F, -4.5F, 10.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(24, 33).addBox(-4.8F, -8.0F, -4.1F, 1.0F, 8.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(0, 58).addBox(-4.0F, -8.0F, 3.6F, 8.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(56, 61).addBox(-3.8F, -8.0F, 4.3F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(38, 62).addBox(-1.3F, -8.0F, 4.3F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 12).addBox(-5.0F, -0.3F, -4.6F, 10.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(40, 12).addBox(3.8F, -8.0F, -4.1F, 1.0F, 8.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(60, 28).addBox(-3.0F, -3.4F, 4.3F, 6.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(60, 32).addBox(-3.0F, -2.9F, 4.8F, 6.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(60, 42).addBox(-2.0F, -3.0F, 5.1F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(60, 42).addBox(-2.0F, -0.6F, 5.1F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r1 = Head.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(60, 42).addBox(-2.0F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.7F, -1.3F, 6.1F, 0.0F, 0.0F, -1.5708F));

		PartDefinition cube_r2 = Head.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(60, 44).addBox(-2.0F, -0.5F, -1.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.3F, 6.1F, 0.0F, 0.0F, -3.1416F));

		PartDefinition cube_r3 = Head.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(60, 44).addBox(-2.0F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -1.3F, 6.1F, 0.0F, 0.0F, -1.5708F));

		PartDefinition cube_r4 = Head.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(60, 42).addBox(-2.0F, -1.0F, -1.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.7F, -1.3F, 6.1F, 0.0F, 0.0F, -1.5708F));

		PartDefinition cube_r5 = Head.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(60, 36).addBox(-2.0F, 0.0F, -1.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -1.1F, 4.7F, 0.8727F, 0.0F, 0.0F));

		PartDefinition cube_r6 = Head.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(56, 61).addBox(-3.0F, -1.0F, -1.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.6F, -9.3F, 5.3F, 0.0F, 0.0F, -1.5708F));

		PartDefinition cube_r7 = Head.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(56, 61).addBox(-3.0F, -1.0F, -1.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.6F, -6.7F, 5.3F, 0.0F, 0.0F, -1.5708F));

		PartDefinition Body = partdefinition.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 33).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(18, 58).addBox(-3.0F, 0.0F, 1.6F, 1.0F, 11.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(60, 54).addBox(2.0F, 4.6F, -2.6F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(38, 50).addBox(2.0F, 0.0F, 1.6F, 1.0F, 11.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(60, 46).addBox(-3.0F, 4.6F, -2.6F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(42, 65).addBox(2.0F, 10.5F, -2.8F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(64, 63).addBox(-3.0F, 10.5F, -2.8F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(64, 55).addBox(1.1F, 10.5F, -3.8F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(48, 61).addBox(0.6F, 9.9F, -3.8F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(60, 38).addBox(0.6F, 9.9F, -3.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(34, 29).addBox(0.6F, 10.0F, -3.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(8, 67).addBox(-1.2F, 11.0F, 1.7F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(12, 67).addBox(-0.5F, 11.7F, 1.7F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(4, 67).addBox(-0.5F, 10.3F, 1.7F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 67).addBox(0.2F, 11.0F, 1.7F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(42, 0).addBox(-4.5F, 10.5F, -2.5F, 9.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(34, 23).addBox(1.5F, 9.9F, 2.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(42, 61).addBox(-3.5F, 9.9F, 2.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(42, 7).addBox(-4.0F, 0.8F, -3.0F, 8.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(60, 7).addBox(-4.0F, 0.8F, -4.0F, 8.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(60, 12).addBox(-4.0F, 0.8F, -4.7F, 8.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(64, 57).addBox(-3.0F, 4.6F, -2.7F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(32, 63).addBox(-3.0F, -1.3F, -2.6F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(46, 68).addBox(-3.0F, 0.5F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(68, 53).addBox(-3.0F, 4.6F, -2.8F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(68, 51).addBox(2.0F, 4.6F, -2.8F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(52, 67).addBox(2.0F, 0.5F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(52, 63).addBox(2.0F, 1.3F, -4.9F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(64, 51).addBox(-3.0F, 1.3F, -4.9F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(64, 60).addBox(2.0F, 4.6F, -2.7F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(48, 63).addBox(2.0F, -1.3F, -2.6F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r8 = Body.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(42, 68).addBox(0.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(68, 38).addBox(-5.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 2.4F, -4.0F, -0.7418F, 0.0F, 0.0F));

		PartDefinition cube_r9 = Body.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(64, 66).addBox(0.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(26, 66).addBox(-5.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 4.0F, -3.4F, 0.7418F, 0.0F, 0.0F));

		PartDefinition cube_r10 = Body.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(38, 68).addBox(0.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(68, 40).addBox(-5.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 1.9F, -3.8F, -1.2654F, 0.0F, 0.0F));

		PartDefinition cube_r11 = Body.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(34, 68).addBox(0.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(26, 68).addBox(-5.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 3.8F, -3.0F, 1.3701F, 0.0F, 0.0F));

		PartDefinition cube_r12 = Body.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(30, 68).addBox(0.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(56, 67).addBox(-5.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 4.4F, -2.0F, 1.0036F, 0.0F, 0.0F));

		PartDefinition cube_r13 = Body.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(64, 46).addBox(-1.0F, -1.0F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.6F, -1.25F, 4.0F, 0.0F, 1.5708F, -2.2689F));

		PartDefinition cube_r14 = Body.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(64, 46).addBox(-1.0F, -1.0F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.8F, -6.95F, 4.0F, 0.0F, 1.5708F, 2.2689F));

		PartDefinition cube_r15 = Body.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(26, 63).addBox(-0.2F, 0.2021F, -1.7879F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.3F, 5.7F, 0.0F, 1.5708F, -2.3562F));

		PartDefinition cube_r16 = Body.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(26, 63).addBox(-0.2F, 0.2021F, -1.7879F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.1F, -2.4F, 5.7F, 0.0F, 1.5708F, -2.3562F));

		PartDefinition cube_r17 = Body.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(26, 63).addBox(-1.0F, -1.0F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.6F, 4.9F, 0.0F, 1.5708F, -0.7854F));

		PartDefinition cube_r18 = Body.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(26, 63).addBox(-1.0F, -1.0F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.1F, -2.7F, 4.9F, 0.0F, 1.5708F, -0.7854F));

		PartDefinition cube_r19 = Body.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(64, 46).addBox(-1.0F, -1.0F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.8F, -0.6F, 4.0F, 0.0F, 1.5708F, -0.8727F));

		PartDefinition cube_r20 = Body.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(64, 46).addBox(-1.0F, -1.0F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.6F, -6.3F, 4.0F, 0.0F, 1.5708F, 0.8727F));

		PartDefinition cube_r21 = Body.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(64, 49).addBox(-4.0F, 10.0755F, 1.1776F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(34, 27).addBox(1.0F, 10.0755F, 1.1776F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 0.5F, -0.4F, 0.1309F, 0.0F, 0.0F));

		PartDefinition RightArm = partdefinition.addOrReplaceChild("RightArm", CubeListBuilder.create().texOffs(44, 29).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 2.0F, 0.0F));

		PartDefinition LeftArm = partdefinition.addOrReplaceChild("LeftArm", CubeListBuilder.create().texOffs(44, 45).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 2.0F, 0.0F));

		PartDefinition RightLeg = partdefinition.addOrReplaceChild("RightLeg", CubeListBuilder.create().texOffs(22, 50).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.9F, 12.0F, 0.0F));

		PartDefinition LeftLeg = partdefinition.addOrReplaceChild("LeftLeg", CubeListBuilder.create().texOffs(22, 50).addBox(-1.8F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(1.9F, 12.0F, 0.0F));

		PartDefinition LeftBoot = partdefinition.addOrReplaceChild("LeftBoot", CubeListBuilder.create().texOffs(60, 17).addBox(0.1F, -3.0F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition RightBoot = partdefinition.addOrReplaceChild("RightBoot", CubeListBuilder.create().texOffs(60, 17).addBox(-3.9F, -3.0F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(60, 62).addBox(0.3F, -32.0F, 4.3F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(22, 63).addBox(2.8F, -32.0F, 4.3F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition cube_r22 = bb_main.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(60, 62).addBox(-3.0F, -1.0F, -1.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -33.3F, 5.3F, 0.0F, 0.0F, -1.5708F));

		PartDefinition cube_r23 = bb_main.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(60, 62).addBox(-3.0F, -1.0F, -1.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -30.7F, 5.3F, 0.0F, 0.0F, -1.5708F));

		PartDefinition cube_r24 = bb_main.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(60, 24).addBox(-1.0F, -2.0F, -1.0F, 7.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, -24.2F, -2.0F, 1.3701F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		Head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		Body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		RightArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		LeftArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		RightLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		LeftLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		LeftBoot.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		RightBoot.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}