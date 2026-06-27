package com.ksit.nuclearwinter.events;

import com.ksit.nuclearwinter.bunker.BunkerManager;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RightClickBlockListener {
    @SubscribeEvent
    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        if (!(level instanceof ServerLevel serverLevel)) return;

        BlockPos pos = event.getPos();
        BlockState state = serverLevel.getBlockState(pos);

        // Проверяем что это дверь
        if (state.getBlock() instanceof DoorBlock) {

            boolean isOpen = state.getValue(DoorBlock.OPEN);

            // дверь сейчас будет меняться -> планируем пересчёт
            serverLevel.getServer().execute(() -> {
                BunkerManager.onBlockChanged(serverLevel, pos);
            });
        }
    }
}
