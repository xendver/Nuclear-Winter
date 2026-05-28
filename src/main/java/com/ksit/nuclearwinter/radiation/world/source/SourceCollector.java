package com.ksit.nuclearwinter.radiation.world.source;

import com.ksit.nuclearwinter.radiation.calc.ActiveSource;
import net.minecraft.server.level.ServerLevel;

import java.util.ArrayList;
import java.util.List;

public class SourceCollector {
    private final PlayerSourceScanner playerScanner = new PlayerSourceScanner();
    private final BlockSourceScanner blockScanner = new BlockSourceScanner();

    public List<ActiveSource> collect(ServerLevel level) {

        List<ActiveSource> sources = new ArrayList<>();

        // 1. игроки + их предметы
        playerScanner.scan(level, sources);

        // 2. блоки из storage
        blockScanner.scan(level, sources);

        return sources;
    }
}
