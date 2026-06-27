package com.ksit.nuclearwinter.bunker;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;

public class Bunker {
    private static final int MAX_DISTANCE = 50;
    private static final int MAX_BLOCKS = 10_000;

    private final UUID id;
    private BlockPos controllerPos;

    private boolean sealed;

    private final Set<BlockPos> shellBlocks = new HashSet<>();
    private final Set<BlockPos> innerBlocks = new HashSet<>();

    public Bunker(BlockPos controllerPos) {
        this.id = UUID.randomUUID();
        this.controllerPos = controllerPos;
    }

    public Bunker(BunkerData data) {
        this.id = data.id;
        this.controllerPos = data.controllerPos;
        this.sealed = data.sealed;

        this.shellBlocks.addAll(data.shellBlocks);
        this.innerBlocks.addAll(data.innerBlocks);
    }

    private void reset() {
        shellBlocks.clear();
        innerBlocks.clear();
        sealed = true;
    }

    public void recalculate(ServerLevel level) {
        reset();
        BFSResult result = floodFill(level);

        this.innerBlocks.addAll(result.inner);
        this.shellBlocks.addAll(result.shell);
        this.sealed = result.sealed;

        printDebug();
    }

    public void setSealed(boolean sealed) {
        this.sealed = sealed;
    }

    public Set<BlockPos> getInnerBlocks() {
        return this.innerBlocks;
    }

    public BlockPos getControllerPos() {
        return this.controllerPos;
    }

    public Set<BlockPos> getShellBlocks() {
        return this.shellBlocks;
    }

    public boolean isSealed() {
        return this.sealed;
    }

    public UUID getId() {
        return this.id;
    }

    public boolean isShellBlock(BlockPos pos) {
        return shellBlocks.contains(pos);
    }

    public boolean contains(BlockPos pos) {
        return innerBlocks.contains(pos);
    }

    public BunkerData toData() {
        return new BunkerData(
                id,
                controllerPos,
                sealed,
                new HashSet<>(shellBlocks),
                new HashSet<>(innerBlocks)
        );
    }

    private BFSResult floodFill(ServerLevel level) {

        Set<BlockPos> visited = new HashSet<>();
        Queue<BlockPos> queue = new LinkedList<>();

        Set<BlockPos> inner = new HashSet<>();
        Set<BlockPos> shell = new HashSet<>();

        boolean sealedLocal = true;

        BlockPos start = controllerPos.above();
        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {

            BlockPos current = queue.poll();
            inner.add(current);

            if (inner.size() > MAX_BLOCKS) {
                sealedLocal = false;
                break;
            }

            for (Direction dir : Direction.values()) {

                BlockPos next = current.relative(dir);

                if (!visited.add(next)) continue;

                if (isOutOfBounds(next)) {
                    sealedLocal = false;
                    continue;
                }

                BlockState state = level.getBlockState(next);

                if (state.isAir()) {
                    queue.add(next);
                } else {
                    shell.add(next);
                }
            }
        }

        return new BFSResult(inner, shell, sealedLocal);
    }

    private boolean isOutOfBounds(BlockPos pos) {
        return Math.abs(pos.getX() - controllerPos.getX()) > MAX_DISTANCE
                || Math.abs(pos.getY() - controllerPos.getY()) > MAX_DISTANCE
                || Math.abs(pos.getZ() - controllerPos.getZ()) > MAX_DISTANCE;
    }

    private record BFSResult(
            Set<BlockPos> inner,
            Set<BlockPos> shell,
            boolean sealed
    ) {}

    private void printDebug() {
        System.out.println("========== BUNKER CHECK ==========");
        System.out.println("Inner blocks: " + innerBlocks.size());
        System.out.println("Shell blocks: " + shellBlocks.size());
        System.out.println(sealed ? "✅ БУНКЕР ГЕРМЕТИЧЕН" : "❌ БУНКЕР НАРУШЕН");
        System.out.println("==================================");
    }
}
