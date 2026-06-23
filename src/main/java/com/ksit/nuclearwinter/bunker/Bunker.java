package com.ksit.nuclearwinter.bunker;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;

public class Bunker {

    private final UUID id;
    private BlockPos controllerPos;
    private BlockPos min;
    private BlockPos max;
    private boolean sealed;
    // блоки оболочки (стены)
    private final Set<BlockPos> shellBlocks = new HashSet<>();
    // внутренний объём
    private final Set<BlockPos> innerBlocks = new HashSet<>();


    public Bunker(BlockPos controllerPos) {
        this.id = UUID.randomUUID();
        this.controllerPos = controllerPos;
    }

    public Bunker(BlockPos controllerPos, UUID id) {
        this.id = id;
        this.controllerPos = controllerPos;
    }

    public void recalculate(ServerLevel level) {

        shellBlocks.clear();
        innerBlocks.clear();

        sealed = true;


        Set<BlockPos> visited = new HashSet<>();
        Queue<BlockPos> queue = new LinkedList<>();


        BlockPos start = controllerPos.above();

        queue.add(start);
        visited.add(start);


        final int MAX_DISTANCE = 50;
        final int MAX_BLOCKS = 10000;


        while (!queue.isEmpty()) {

            BlockPos current = queue.poll();

            innerBlocks.add(current);


            for (Direction direction : Direction.values()) {

                BlockPos next = current.relative(direction);


                if (visited.contains(next)) {
                    continue;
                }

                visited.add(next);


                if (Math.abs(next.getX() - controllerPos.getX()) > MAX_DISTANCE ||
                        Math.abs(next.getY() - controllerPos.getY()) > MAX_DISTANCE ||
                        Math.abs(next.getZ() - controllerPos.getZ()) > MAX_DISTANCE
                ) {

                    sealed = false;
                    continue;
                }


                BlockState state = level.getBlockState(next);


                if (state.isAir()) {

                    queue.add(next);

                } else {

                    shellBlocks.add(next);
                }


                if (innerBlocks.size() > MAX_BLOCKS) {

                    sealed = false;
                    break;
                }
            }
        }

        // ===== DEBUG =====

        System.out.println("========== BUNKER CHECK ==========");
        System.out.println("Inner blocks: " + innerBlocks.size());
        System.out.println("Shell blocks: " + shellBlocks.size());

        if (sealed) {
            System.out.println("✅ БУНКЕР ГЕРМЕТИЧЕН");
        } else {
            System.out.println("❌ БУНКЕРА НЕТ / ЕСТЬ УТЕЧКА");
        }

        System.out.println("==================================");

    }

    public void setSealed(boolean sealed) {
        this.sealed = sealed;
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
}
