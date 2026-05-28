package com.ksit.nuclearwinter.radiation.world.source;

import com.ksit.nuclearwinter.radiation.calc.ActiveSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;

import java.util.List;

public class PlayerSourceScanner {
    private final ItemSourceScanner itemScanner =
            new ItemSourceScanner();

    public void scan(ServerLevel level,
                     List<ActiveSource> out) {

        for (ServerPlayer player : level.players()) {

            ChunkPos chunk = player.chunkPosition();

            itemScanner.scan(player.getMainHandItem(), chunk, out);
            itemScanner.scan(player.getOffhandItem(), chunk, out);

            for (ItemStack armor : player.getArmorSlots()) {
                itemScanner.scan(armor, chunk, out);
            }

            for (int i = 0; i < player.getInventory().getContainerSize(); i++) {

                itemScanner.scan(
                        player.getInventory().getItem(i),
                        chunk,
                        out
                );
            }
        }
    }
}
