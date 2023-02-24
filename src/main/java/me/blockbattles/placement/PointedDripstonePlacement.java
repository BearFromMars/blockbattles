package me.blockbattles.placement;

import me.blockbattles.event.player.PlayerBlockPlaceEvent;
import net.minestom.server.coordinate.Point;
import me.blockbattles.event.player.PlayerBlockUpdateNeighborEvent;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;

public class PointedDripstonePlacement {

    public static void onNeighbor(Block block, PlayerBlockUpdateNeighborEvent event) {
        String direction = block.getProperty("vertical_direction");
        if (direction == null) return;

        Instance instance = event.getInstance();
        Point position = event.getBlockPosition();

        Block aboveState = instance.getBlock(position.blockX(), position.blockY() + (direction.equals("down") ? -1 : 1), position.blockZ());

        if (Block.POINTED_DRIPSTONE.compare(aboveState, Block.Comparator.ID)) {
            String thickness = aboveState.getProperty("thickness");

            if (aboveState.getProperty("vertical_direction").equals(direction)) {
                switch (thickness) {
                    case "tip":
                    case "tip_merge":
                        block = block.withProperty("thickness", "frustum");
                        break;
                    case "frustum":
                    case "middle":
                        Block blockOpposite = instance.getBlock(position.blockX(), position.blockY() + (direction.equals("down") ? 1 : -1), position.blockZ());
                        if (!Block.POINTED_DRIPSTONE.compare(blockOpposite, Block.Comparator.ID)) {
                            block = block.withProperty("thickness", "base");
                        } else {
                            block = block.withProperty("thickness", "middle");
                        }
                        break;
                }
            } else {
                switch (thickness) {
                    case "tip":
                    case "tip_merge":
                        block = block.withProperty("thickness", "tip_merge");
                        break;
                }
            }
        } else {
            block = block.withProperty("thickness", "tip");
        }

        event.setShouldUpdateNeighbors(true);
        event.setBlock(block);
    }

    public static void onPlace(Block block, PlayerBlockPlaceEvent event) {
        HalfPlacement.onPlace(block, event, "vertical_direction", "down", "up");
        block = event.getBlock();

        String direction = block.getProperty("vertical_direction");
        if (direction == null) return;

        Instance instance = event.getInstance();
        Point position = event.getBlockPosition();

        Block aboveState = instance.getBlock(position.blockX(), position.blockY() + (direction.equals("down") ? -1 : 1), position.blockZ());

        if (Block.POINTED_DRIPSTONE.compare(aboveState, Block.Comparator.ID)) {
            String thickness = aboveState.getProperty("thickness");

            if (!aboveState.getProperty("vertical_direction").equals(direction)) {
                switch (thickness) {
                    case "tip":
                    case "tip_merge":
                        block = block.withProperty("thickness", "tip_merge");
                        break;
                }
            }
        }

        event.setBlock(block);
    }

}