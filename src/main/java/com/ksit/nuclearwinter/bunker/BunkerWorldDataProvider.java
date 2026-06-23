package com.ksit.nuclearwinter.bunker;

import net.minecraft.server.level.ServerLevel;

public class BunkerWorldDataProvider {
    private static final String NAME = "nuclearwinter_bunkers";

    public static BunkerWorldData get(ServerLevel level) {

        return level.getDataStorage().computeIfAbsent(
                BunkerWorldData::load,
                BunkerWorldData::new,
                NAME
        );
    }
}
