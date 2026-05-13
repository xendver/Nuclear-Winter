package com.ksit.nuclearwinter.radiation.commands;

import com.ksit.nuclearwinter.radiation.BlockRadiationStorage;
import com.ksit.nuclearwinter.radiation.IChunkRadiation;
import com.ksit.nuclearwinter.radiation.IRadiation;
import com.ksit.nuclearwinter.radiation.RadiationCapability;
import com.ksit.nuclearwinter.radiation.illness.IllnessStage;
import com.ksit.nuclearwinter.radiation.illness.IRadiationIllness;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.common.util.LazyOptional;

import java.util.List;
import java.util.Map;

public class CommandRadiationInfo {

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        register(event.getDispatcher());
    }

    // /radiation info
    // /radiation illness
    // /radiation set entity <power> <radius> <decay>
    // /radiation set block  <power> <radius> <decay>
    // /radiation set item   <power> <radius> <decay>
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {

        // requiresPermissionLevel(2) = оператор / включённые читы
        LiteralArgumentBuilder<CommandSourceStack> root =
                Commands.literal("radiation")
                        .requires(src -> src.hasPermission(2));

        // /radiation info
        root.then(Commands.literal("info")
                .executes(ctx -> {
                    executeInfo(ctx.getSource());
                    return 1;
                })
        );

        // /radiation illness
        root.then(Commands.literal("illness")
                .executes(ctx -> {
                    executeIllness(ctx.getSource());
                    return 1;
                })
        );

        // /radiation set entity/block/item <power> <radius> <decay>
        root.then(Commands.literal("set")
                .then(Commands.literal("entity")
                        .then(com.mojang.brigadier.builder.RequiredArgumentBuilder
                                .<CommandSourceStack, Float>argument("power",
                                        com.mojang.brigadier.arguments.FloatArgumentType.floatArg(0))
                                .then(com.mojang.brigadier.builder.RequiredArgumentBuilder
                                        .<CommandSourceStack, Float>argument("radius",
                                                com.mojang.brigadier.arguments.FloatArgumentType.floatArg(0))
                                        .then(com.mojang.brigadier.builder.RequiredArgumentBuilder
                                                .<CommandSourceStack, Float>argument("decay",
                                                        com.mojang.brigadier.arguments.FloatArgumentType.floatArg(0))
                                                .executes(ctx -> {
                                                    executeSetEntity(ctx.getSource(),
                                                            com.mojang.brigadier.arguments.FloatArgumentType
                                                                    .getFloat(ctx, "power"),
                                                            com.mojang.brigadier.arguments.FloatArgumentType
                                                                    .getFloat(ctx, "radius"),
                                                            com.mojang.brigadier.arguments.FloatArgumentType
                                                                    .getFloat(ctx, "decay"));
                                                    return 1;
                                                })
                                        )
                                )
                        )
                )
                .then(Commands.literal("block")
                        .then(com.mojang.brigadier.builder.RequiredArgumentBuilder
                                .<CommandSourceStack, Float>argument("power",
                                        com.mojang.brigadier.arguments.FloatArgumentType.floatArg(0))
                                .then(com.mojang.brigadier.builder.RequiredArgumentBuilder
                                        .<CommandSourceStack, Float>argument("radius",
                                                com.mojang.brigadier.arguments.FloatArgumentType.floatArg(0))
                                        .then(com.mojang.brigadier.builder.RequiredArgumentBuilder
                                                .<CommandSourceStack, Float>argument("decay",
                                                        com.mojang.brigadier.arguments.FloatArgumentType.floatArg(0))
                                                .executes(ctx -> {
                                                    executeSetBlock(ctx.getSource(),
                                                            com.mojang.brigadier.arguments.FloatArgumentType
                                                                    .getFloat(ctx, "power"),
                                                            com.mojang.brigadier.arguments.FloatArgumentType
                                                                    .getFloat(ctx, "radius"),
                                                            com.mojang.brigadier.arguments.FloatArgumentType
                                                                    .getFloat(ctx, "decay"));
                                                    return 1;
                                                })
                                        )
                                )
                        )
                )
                .then(Commands.literal("item")
                        .then(com.mojang.brigadier.builder.RequiredArgumentBuilder
                                .<CommandSourceStack, Float>argument("power",
                                        com.mojang.brigadier.arguments.FloatArgumentType.floatArg(0))
                                .then(com.mojang.brigadier.builder.RequiredArgumentBuilder
                                        .<CommandSourceStack, Float>argument("radius",
                                                com.mojang.brigadier.arguments.FloatArgumentType.floatArg(0))
                                        .then(com.mojang.brigadier.builder.RequiredArgumentBuilder
                                                .<CommandSourceStack, Float>argument("decay",
                                                        com.mojang.brigadier.arguments.FloatArgumentType.floatArg(0))
                                                .executes(ctx -> {
                                                    executeSetItem(ctx.getSource(),
                                                            com.mojang.brigadier.arguments.FloatArgumentType
                                                                    .getFloat(ctx, "power"),
                                                            com.mojang.brigadier.arguments.FloatArgumentType
                                                                    .getFloat(ctx, "radius"),
                                                            com.mojang.brigadier.arguments.FloatArgumentType
                                                                    .getFloat(ctx, "decay"));
                                                    return 1;
                                                })
                                        )
                                )
                        )
                )
        );

        dispatcher.register(root);
    }

    // == /radiation info =====================================================================

    private static void executeInfo(CommandSourceStack src) {
        if (!(src.getEntity() instanceof ServerPlayer player)) {
            src.sendFailure(Component.literal("Только для игроков"));
            return;
        }

        ServerLevel level = player.serverLevel();
        LevelChunk chunk  = level.getChunkAt(player.blockPosition());
        net.minecraft.world.level.ChunkPos chunkPos = chunk.getPos();

        msg(src, ChatFormatting.GOLD,
                "=== Чанк [" + chunkPos.x + ", " + chunkPos.z + "] ===");

        // Уровень радиации чанка
        msg(src, ChatFormatting.GOLD, "-- Уровень радиации чанка --");
        LazyOptional<IChunkRadiation> optional =
                chunk.getCapability(RadiationCapability.CHUNK_RADIATION);

        if (optional.isPresent()) {
            IChunkRadiation cap = optional.orElseThrow(IllegalStateException::new);

            msg(src, ChatFormatting.YELLOW,
                    "radiationLevel: "
                            + ChatFormatting.WHITE
                            + cap.getRadiationLevel());
        } else {
            msg(src, ChatFormatting.RED, "Capability не найдена");
        }

        // Энтити-источники в чанке
        msg(src, ChatFormatting.GOLD, "-- Энтити-источники --");
        boolean[] foundEntities = { false };
        level.getEntities().getAll().forEach(entity -> {
            if (!level.getChunkAt(entity.blockPosition()).getPos().equals(chunkPos))
                return;
            entity.getCapability(RadiationCapability.RADIATION_SOURCE)
                    .ifPresent(rad -> {
                        if (rad.getInitialPower() <= 0) return;
                        foundEntities[0] = true;
                        msg(src, ChatFormatting.AQUA,
                                "Энтити: " + entity.getName().getString()
                                        + " [" + entity.getClass().getSimpleName() + "]");
                        printSource(src, rad);
                    });
        });
        if (!foundEntities[0])
            msg(src, ChatFormatting.GRAY, "Нет энтити-источников");

        // Итем-источники на земле
        msg(src, ChatFormatting.GOLD, "-- Итем-источники (на земле) --");
        boolean[] foundItems = { false };
        level.getEntities().getAll().forEach(entity -> {
            if (!(entity instanceof ItemEntity ie)) return;
            if (!level.getChunkAt(entity.blockPosition()).getPos().equals(chunkPos))
                return;
            ie.getItem().getCapability(RadiationCapability.RADIATION_SOURCE)
                    .ifPresent(rad -> {
                        if (rad.getInitialPower() <= 0) return;
                        foundItems[0] = true;
                        msg(src, ChatFormatting.GREEN,
                                "Итем: " + ie.getItem().getDisplayName().getString());
                        printSource(src, rad);
                    });
        });
        if (!foundItems[0])
            msg(src, ChatFormatting.GRAY, "Нет итем-источников");

        // Блок-источники в чанке
        msg(src, ChatFormatting.GOLD, "-- Блок-источники --");
        BlockRadiationStorage storage = BlockRadiationStorage.get(level);
        boolean foundBlocks = false;
        for (Map.Entry<BlockPos, float[]> entry : storage.getAll().entrySet()) {
            if (!level.getChunkAt(entry.getKey()).getPos().equals(chunkPos)) continue;
            foundBlocks = true;
            float[] v = entry.getValue();
            msg(src, ChatFormatting.LIGHT_PURPLE,
                    "Блок: " + entry.getKey()
                            + " | power=" + v[0]
                            + " radius=" + v[1]
                            + " decay=" + v[2]);
        }
        if (!foundBlocks)
            msg(src, ChatFormatting.GRAY, "Нет блок-источников");
    }

    // == /radiation illness =====================================================================

    private static void executeIllness(CommandSourceStack src) {
        if (!(src.getEntity() instanceof ServerPlayer player)) {
            src.sendFailure(Component.literal("Только для игроков"));
            return;
        }

        var illnessOpt =
                player.getCapability(RadiationCapability.RADIATION_ILLNESS);

        if (illnessOpt.isPresent()) {

            IRadiationIllness illness =
                    illnessOpt.orElseThrow(IllegalStateException::new);

            IllnessStage stage = illness.getStage();

            ChatFormatting color = switch (stage) {
                case STAGE_1 -> ChatFormatting.YELLOW;
                case STAGE_2 -> ChatFormatting.GOLD;
                case STAGE_3 -> ChatFormatting.RED;
                case STAGE_4 -> ChatFormatting.DARK_RED;
                default      -> ChatFormatting.GREEN;
            };

            msg(src, ChatFormatting.GOLD,
                    "=== Облучение: "
                            + player.getName().getString()
                            + " ===");

            msg(src, ChatFormatting.YELLOW,
                    "Points: "
                            + ChatFormatting.WHITE
                            + String.format("%.1f",
                            illness.getRadiationPoints()));

            msg(src, color,
                    "Стадия: " + stage.displayName);

            IllnessStage next = nextStage(stage);

            if (next != null) {
                float toNext =
                        next.threshold - illness.getRadiationPoints();

                msg(src, ChatFormatting.GRAY,
                        "До следующей стадии: "
                                + String.format("%.1f", toNext)
                                + " points");
            }

        } else {
            msg(src, ChatFormatting.RED,
                    "Capability облучения не найдена");
        }
    }

    // == /radiation set entity =====================================================================

    private static void executeSetEntity(CommandSourceStack src,
                                         float power, float radius, float decay) {
        if (!(src.getEntity() instanceof ServerPlayer player)) {
            src.sendFailure(Component.literal("Только для игроков"));
            return;
        }

        // Поиск ближайшей энтити в 5 блоках перед игроком
        net.minecraft.world.phys.Vec3 eye  = player.getEyePosition();
        net.minecraft.world.phys.Vec3 look = player.getLookAngle();
        net.minecraft.world.phys.Vec3 end  =
                eye.add(look.scale(5.0));

        List<Entity> nearby = player.level().getEntities(player,
                player.getBoundingBox().expandTowards(look.scale(5)).inflate(1.0));

        Entity closest   = null;
        double closestD  = 6.0;
        for (Entity e : nearby) {
            var rtr = e.getBoundingBox().inflate(0.1).clip(eye, end);
            if (rtr.isPresent()) {
                double d = eye.distanceTo(rtr.get());
                if (d < closestD) { closestD = d; closest = e; }
            }
        }

        if (closest == null) {
            src.sendFailure(Component.literal(
                    "Нет энтити в поле зрения (до 5 блоков)"));
            return;
        }

        Entity finalClosest = closest;
        var capOpt =
                closest.getCapability(RadiationCapability.RADIATION_SOURCE);

        if (capOpt.isPresent()) {

            IRadiation cap =
                    capOpt.orElseThrow(IllegalStateException::new);

            cap.setInitialPower(power);
            cap.setRadius(radius);
            cap.setDecayPerChunk(decay);

            msg(src, ChatFormatting.GREEN,
                    "Радиация наложена на: "
                            + finalClosest.getName().getString());

            printSource(src, cap);

        } else {

            src.sendFailure(Component.literal(
                    "У этой энтити нет capability радиации"));
        }
    }

    // == /radiation set block =====================================================================

    private static void executeSetBlock(CommandSourceStack src,
                                        float power, float radius, float decay) {
        if (!(src.getEntity() instanceof ServerPlayer player)) {
            src.sendFailure(Component.literal("Только для игроков"));
            return;
        }

        // HitResult — результат rayTrace в 1.20.1
        var hit = player.pick(5.0, 1.0f, false);
        if (hit.getType() != net.minecraft.world.phys.HitResult.Type.BLOCK) {
            src.sendFailure(Component.literal("Смотри на блок (до 5 блоков)"));
            return;
        }

        BlockPos blockPos =
                ((net.minecraft.world.phys.BlockHitResult) hit).getBlockPos();

        BlockRadiationStorage storage =
                BlockRadiationStorage.get(player.serverLevel());
        storage.setRadiation(blockPos, power, radius, decay);

        msg(src, ChatFormatting.GREEN,
                "Радиация наложена на блок: " + blockPos);
        msg(src, ChatFormatting.YELLOW,
                "  power=" + power + " radius=" + radius + " decay=" + decay);
    }

    // == /radiation set item =====================================================================

    private static void executeSetItem(CommandSourceStack src,
                                       float power, float radius, float decay) {
        if (!(src.getEntity() instanceof ServerPlayer player)) {
            src.sendFailure(Component.literal("Только для игроков"));
            return;
        }

        net.minecraft.world.item.ItemStack stack =
                player.getMainHandItem();
        if (stack.isEmpty()) {
            src.sendFailure(Component.literal("В руке нет предмета"));
            return;
        }

        var capOpt =
                stack.getCapability(RadiationCapability.RADIATION_SOURCE);

        if (capOpt.isPresent()) {

            IRadiation cap =
                    capOpt.orElseThrow(IllegalStateException::new);

            cap.setInitialPower(power);
            cap.setRadius(radius);
            cap.setDecayPerChunk(decay);

            msg(src, ChatFormatting.GREEN,
                    "Радиация наложена на: "
                            + stack.getDisplayName().getString());

            printSource(src, cap);

        } else {

            src.sendFailure(Component.literal(
                    "У предмета нет capability радиации"));
        }
    }

    // == ДОПОЛНИТЕЛЬНЫЕ МЕТОДЫ =====================================================================
    private static void printSource(CommandSourceStack src, IRadiation rad) {
        msg(src, ChatFormatting.YELLOW,
                "  initialPower:  " + rad.getInitialPower());
        msg(src, ChatFormatting.YELLOW,
                "  radius:        " + rad.getRadius() + " чанков");
        msg(src, ChatFormatting.YELLOW,
                "  decayPerChunk: " + rad.getDecayPerChunk());
    }

    private static void msg(CommandSourceStack src,
                            ChatFormatting color, String text) {
        src.sendSuccess(() ->
                Component.literal(text).withStyle(color), false);
    }

    private static IllnessStage nextStage(IllnessStage current) {
        return switch (current) {
            case NONE    -> IllnessStage.STAGE_1;
            case STAGE_1 -> IllnessStage.STAGE_2;
            case STAGE_2 -> IllnessStage.STAGE_3;
            case STAGE_3 -> IllnessStage.STAGE_4;
            default      -> null;
        };
    }
}