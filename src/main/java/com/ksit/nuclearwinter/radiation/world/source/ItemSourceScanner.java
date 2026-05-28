package com.ksit.nuclearwinter.radiation.world.source;

import com.ksit.nuclearwinter.radiation.calc.ActiveSource;
import com.ksit.nuclearwinter.radiation.capability.RadiationCapability;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;

import java.util.List;

public class ItemSourceScanner {
    public void scan(ItemStack stack,
                     ChunkPos chunk,
                     List<ActiveSource> out) {

        if (stack.isEmpty()) return;

        stack.getCapability(RadiationCapability.RADIATION_SOURCE)
                .ifPresent(rad -> {

                    if (rad.getInitialPower() <= 0) return;

                    out.add(new ActiveSource(
                            chunk.x,
                            chunk.z,
                            rad
                    ));
                });
    }
}
