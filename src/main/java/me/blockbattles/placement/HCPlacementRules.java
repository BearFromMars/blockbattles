package me.blockbattles.placement;

import net.minestom.server.MinecraftServer;
import net.minestom.server.event.EventBinding;
import net.minestom.server.event.EventFilter;
import me.blockbattles.event.player.PlayerBlockPlaceEvent;
import me.blockbattles.event.player.PlayerBlockUpdateNeighborEvent;
import net.minestom.server.event.trait.BlockEvent;
import net.minestom.server.gamedata.tags.Tag;
import net.minestom.server.instance.block.Block;
import net.minestom.server.utils.NamespaceID;

import java.util.Objects;
import java.util.Set;

@SuppressWarnings("UnstableApiUsage")
public final class HCPlacementRules {

    //TODO:
    // Twisting Vines
    // Weeping Vines
    // Anvils (flip X/Z rotation)
    // Small Dripleaf (convert Y)
    // Big Dripleaf (convert Y)
    // Candles (stacking)
    // Non-collding blocks to place inside player
    // Waterlogged state
    // Bells
    // Banners (int rot)
    // Signs (int rot)
    // Doors (place upper door)
    // Sunflower (place upper sunflower)
    // Fern (place upper fern)
    // Beds (place 2nd block)
    // Turtle eggs (stacking)
    // Snow layer combination
    // Tripwire

    /* Filters */

    private static final EventBinding<BlockEvent> STAIRS_BINDING = EventBinding.filtered(EventFilter.BLOCK, HCPlacementRules::isStairs)
            .map(PlayerBlockPlaceEvent.class, StairPlacement::onPlace)
            .map(PlayerBlockUpdateNeighborEvent.class, StairPlacement::onNeighbor)
            .build();

    private static final EventBinding<BlockEvent> WALLS_BINDING = EventBinding.filtered(EventFilter.BLOCK, HCPlacementRules::isWall)
            .map(PlayerBlockPlaceEvent.class, WallPlacement::onPlace)
            .map(PlayerBlockUpdateNeighborEvent.class, WallPlacement::onNeighbor)
            .build();

    private static final EventBinding<BlockEvent> SLAB_BINDING = EventBinding.filtered(EventFilter.BLOCK, HCPlacementRules::isSlab)
            .map(PlayerBlockPlaceEvent.class, SlabPlacement::onPlace)
            .build();

    private static final EventBinding<BlockEvent> BUTTON_BINDING = EventBinding.filtered(EventFilter.BLOCK, HCPlacementRules::isButton)
            .map(PlayerBlockPlaceEvent.class, AxisPlacementButton::onPlace)
            .build();

    private static final EventBinding<BlockEvent> CHEST_BINDING = EventBinding.filtered(EventFilter.BLOCK, HCPlacementRules::isChest)
            .map(PlayerBlockPlaceEvent.class, ChestPlacement::onPlace)
            .map(PlayerBlockUpdateNeighborEvent.class, ChestPlacement::onNeighbor)
            .build();

    private static final EventBinding<BlockEvent> FENCE_BINDING = EventBinding.filtered(EventFilter.BLOCK, HCPlacementRules::isFence)
            .map(PlayerBlockPlaceEvent.class, FencePlacement::onPlace)
            .map(PlayerBlockUpdateNeighborEvent.class, FencePlacement::onNeighbor)
            .build();

    private static final EventBinding<BlockEvent> POINTED_DRIPSTONE_BINDING = EventBinding.filtered(EventFilter.BLOCK, HCPlacementRules::isPointedDripstone)
            .map(PlayerBlockPlaceEvent.class, PointedDripstonePlacement::onPlace)
            .map(PlayerBlockUpdateNeighborEvent.class, PointedDripstonePlacement::onNeighbor)
            .build();

    private static final EventBinding<BlockEvent> GLOW_LICHEN_BINDING = EventBinding.filtered(EventFilter.BLOCK, HCPlacementRules::isGlowLichen)
            .map(PlayerBlockPlaceEvent.class, GlowLichenPlacement::onPlace)
            .build();

    private static final EventBinding<BlockEvent> VINE_BINDING = EventBinding.filtered(EventFilter.BLOCK, HCPlacementRules::isVine)
            .map(PlayerBlockPlaceEvent.class, VinePlacement::onPlace)
            .build();

    private static final EventBinding<BlockEvent> ROTATION_BINDING = EventBinding.filtered(EventFilter.BLOCK, HCPlacementRules::hasRotation)
            .map(PlayerBlockPlaceEvent.class, RotationPlacement::onPlace)
            .build();

    private static final EventBinding<BlockEvent> AXIS_BINDING = EventBinding.filtered(EventFilter.BLOCK, HCPlacementRules::hasAxis)
            .map(PlayerBlockPlaceEvent.class, AxisPlacement::onPlace)
            .build();

    private static final EventBinding<BlockEvent> HALF_BINDING = EventBinding.filtered(EventFilter.BLOCK, HCPlacementRules::hasHalf)
            .map(PlayerBlockPlaceEvent.class, HalfPlacement::onPlace)
            .build();

    private static final EventBinding<BlockEvent> WALL_REPLACEMENT_BINDING =
            EventBinding.filtered(EventFilter.BLOCK, WallReplacement::shouldReplace)
                    .map(PlayerBlockPlaceEvent.class, WallReplacement::onPlace)
                    .build();

    /* Checks */

    public static final Tag MINECRAFT_STAIRS = Objects.requireNonNull(MinecraftServer.getTagManager().getTag(Tag.BasicType.BLOCKS, "minecraft:stairs"));

    private static boolean isStairs(Block block) {
        return MINECRAFT_STAIRS.contains(block.namespace());
    }

    public static final Tag MINECRAFT_WALLS = Objects.requireNonNull(MinecraftServer.getTagManager().getTag(Tag.BasicType.BLOCKS, "minecraft:walls"));


    public static boolean isWall(Block block) {
        return MINECRAFT_WALLS.contains(block.namespace());
    }

    public static final Tag MINECRAFT_SLABS = Objects.requireNonNull(MinecraftServer.getTagManager().getTag(Tag.BasicType.BLOCKS, "minecraft:slabs"));

    private static boolean isSlab(Block block) {
        return MINECRAFT_SLABS.contains(block.namespace());
    }

    public static final Tag MINECRAFT_BUTTONS = Objects.requireNonNull(MinecraftServer.getTagManager().getTag(Tag.BasicType.BLOCKS, "minecraft:buttons"));

    private static boolean isButton(Block block) {
        return MINECRAFT_BUTTONS.contains(block.namespace()) || block.compare(Block.LEVER);
    }

    private static boolean isChest(Block block) {
        return block.compare(Block.CHEST) || block.compare(Block.TRAPPED_CHEST);
    }

    public static final Tag MINECRAFT_FENCES = Objects.requireNonNull(MinecraftServer.getTagManager().getTag(Tag.BasicType.BLOCKS, "minecraft:fences"));

    private static boolean isFence(Block block) {
        return MINECRAFT_FENCES.contains(block.namespace());
    }

    public static final Tag MINECRAFT_WALL_SIGNS = Objects.requireNonNull(MinecraftServer.getTagManager().getTag(Tag.BasicType.BLOCKS, "minecraft:wall_signs"));

    public static boolean isWallSign(Block block) {
        return MINECRAFT_WALL_SIGNS.contains(block.namespace());
    }

    private static boolean isPointedDripstone(Block block) {
        return block.compare(Block.POINTED_DRIPSTONE);
    }

    private static boolean isGlowLichen(Block block) {
        return block.compare(Block.GLOW_LICHEN);
    }

    private static boolean isVine(Block block) {
        return block.compare(Block.VINE);
    }

    private static boolean isLever(Block block) {
        return block.compare(Block.LEVER);
    }

    private static boolean hasRotation(Block block) {
        return block.getProperty("facing") != null;
    }

    private static boolean hasAxis(Block block) {
        return block.getProperty("axis") != null;
    }

    private static boolean hasHalf(Block block) {
        return block.getProperty("half") != null;
    }

    /* Init */

    public static void init() {
        // Replacements
        MinecraftServer.getGlobalEventHandler().register(WALL_REPLACEMENT_BINDING);

        // Blockstates
        MinecraftServer.getGlobalEventHandler().register(ROTATION_BINDING);
        MinecraftServer.getGlobalEventHandler().register(AXIS_BINDING);
        MinecraftServer.getGlobalEventHandler().register(HALF_BINDING);

        // Specific blocks
        MinecraftServer.getGlobalEventHandler().register(STAIRS_BINDING);
        MinecraftServer.getGlobalEventHandler().register(WALLS_BINDING);
        MinecraftServer.getGlobalEventHandler().register(SLAB_BINDING);
        MinecraftServer.getGlobalEventHandler().register(BUTTON_BINDING);
        MinecraftServer.getGlobalEventHandler().register(CHEST_BINDING);
        MinecraftServer.getGlobalEventHandler().register(FENCE_BINDING);
        MinecraftServer.getGlobalEventHandler().register(GLOW_LICHEN_BINDING);
        MinecraftServer.getGlobalEventHandler().register(VINE_BINDING);
        MinecraftServer.getGlobalEventHandler().register(POINTED_DRIPSTONE_BINDING);

        for (short stateId = 0; stateId < Short.MAX_VALUE; stateId++) {
            Block block = Block.fromStateId(stateId);
            if (block == null) continue;

            RotationPlacement.updateDataFromBlock(block);
        }
    }

}