package com.ksit.nuclearwinter.radiation.handlers;

import com.ksit.nuclearwinter.network.PacketChunkRadiation;
import com.ksit.nuclearwinter.network.PacketHandler;
import com.ksit.nuclearwinter.radiation.api.RadiationConfig;
import com.ksit.nuclearwinter.radiation.calc.ActiveSource;
import com.ksit.nuclearwinter.radiation.calc.RadiationCalculator;
import com.ksit.nuclearwinter.radiation.calc.ResistanceRegistry;
import com.ksit.nuclearwinter.radiation.capability.RadiationCapability;
import com.ksit.nuclearwinter.radiation.capability.impl.RadiationImpl;
import com.ksit.nuclearwinter.radiation.pipeline.RadiationPipeline;
import com.ksit.nuclearwinter.radiation.storage.BlockRadiationStorage;
import com.ksit.nuclearwinter.radiation.illness.IllnessStage;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.*;


public class WorldRadiationHandler {
    @SubscribeEvent
    public void onLevelTick(TickEvent.LevelTickEvent event) {

        if (event.phase != TickEvent.Phase.END) return;
        if (event.level.isClientSide()) return;
        if (event.level.getGameTime() % 20 != 0) return;

        ServerLevel level = (ServerLevel) event.level;

        RadiationPipeline.INSTANCE.tick(level);
    }
}
