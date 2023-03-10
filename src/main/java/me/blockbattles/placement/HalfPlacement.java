package me.blockbattles.placement;



import me.blockbattles.event.player.PlayerBlockPlaceEvent;
import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.block.BlockFace;
import org.jetbrains.annotations.NotNull;

public class HalfPlacement {

    public static void onPlace(Block block, PlayerBlockPlaceEvent event) {
        onPlace(block, event, "half", "top", "bottom");
    }

    public static void onPlace(Block block, PlayerBlockPlaceEvent event,
                               String propertyName) {
        onPlace(block, event, propertyName, "top", "bottom");
    }

    public static void onPlace(Block block, PlayerBlockPlaceEvent event,
                               String propertyName, String propertyValTop, String propertyValBot) {
        block = event.getBlock();

        BlockFace face = event.getBlockFace();
        if (face == BlockFace.TOP) {
            event.setBlock(block.withProperty(propertyName, propertyValBot));
        } else if (face == BlockFace.BOTTOM) {
            event.setBlock(block.withProperty(propertyName, propertyValTop));
        } else if (event.getCursorPosition().y() % 1 > 0.5f) {
            event.setBlock(block.withProperty(propertyName, propertyValTop));
        } else {
            event.setBlock(block.withProperty(propertyName, propertyValBot));
        }
    }
}