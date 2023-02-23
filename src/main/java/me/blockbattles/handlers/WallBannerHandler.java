package me.blockbattles.handlers;

import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.block.BlockHandler;
import net.minestom.server.utils.NamespaceID;
import org.jetbrains.annotations.NotNull;

public class WallBannerHandler implements BlockHandler {
    @Override
    public void onPlace(@NotNull Placement placement) {
        Block block = placement.getBlock();
        if (placement instanceof PlayerPlacement) {
            System.out.println("The block " + block.name() + " has been placed by"+((PlayerPlacement) placement).getPlayer());
        }
        System.out.println("The block " + block.name() + " has been placed");
    }

    @Override
    public @NotNull NamespaceID getNamespaceId() {
        // Namespace required for serialization purpose
        return NamespaceID.from("minecraft:banner");
    }

}