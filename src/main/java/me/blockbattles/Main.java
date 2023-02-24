package me.blockbattles;

import io.github.bloepiloepi.basicredstone.BasicRedstoneExtension;
import io.github.bloepiloepi.basicredstone.door.Doors;
import io.github.bloepiloepi.basicredstone.door.Trapdoors;
import io.github.bloepiloepi.basicredstone.redstone.PowerNet;
import io.github.bloepiloepi.basicredstone.redstone.Redstone;
import io.github.bloepiloepi.basicredstone.redstone.RedstoneComponent;
import io.github.bloepiloepi.basicredstone.redstone.sources.Buttons;
import io.github.bloepiloepi.basicredstone.redstone.sources.Lever;
import me.blockbattles.handlers.*;
import me.blockbattles.placement.HCPlacementRules;
import me.blockbattles.placement.RotationPlacement;
import me.blockbattles.util.RegisterBlocks;
import net.kyori.adventure.key.Key;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.event.Event;
import net.minestom.server.event.EventNode;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerBlockPlaceEvent;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.minestom.server.extras.PlacementRules;
import net.minestom.server.instance.*;
import net.minestom.server.instance.batch.ChunkBatch;
import net.minestom.server.instance.block.Block;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.instance.block.BlockHandler;
import net.minestom.server.instance.block.BlockManager;
import net.minestom.server.instance.block.rule.vanilla.AxisPlacementRule;
import net.minestom.server.instance.block.rule.vanilla.RedstonePlacementRule;
import net.minestom.server.instance.block.rule.vanilla.StairsPlacementRule;
import net.minestom.server.instance.block.rule.vanilla.WallPlacementRule;
import net.minestom.server.utils.NamespaceID;
import net.minestom.server.world.biomes.Biome;
import org.apache.commons.jcs3.JCS;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class Main {

    public static void main(String[] args) {

        // Initialization
        MinecraftServer minecraftServer = MinecraftServer.init();
        InstanceManager instanceManager = MinecraftServer.getInstanceManager();
        // Create the instance
        InstanceContainer instanceContainer = instanceManager.createInstanceContainer();
        // Set the ChunkGenerator
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        BlockManager blockManager = MinecraftServer.getBlockManager();


        HCPlacementRules.init();
        PlacementRules.init();
        for (Block block : Block.values()) {
            if (block.getProperty("axis") != null) {
                //blockManager.registerBlockPlacementRule(new AxisPlacementRule(block));
            }
            if (block.getProperty("shape") != null) {
                //blockManager.registerBlockPlacementRule(new StairsPlacementRule(block));
            }
            if (block.namespace().value().endsWith("wall")) {
                //blockManager.registerBlockPlacementRule(new WallPlacementRule(block));
            }
            if(block.namespace().value().endsWith("sign")) {
                //blockManager.registerHandler(block.namespace(), SignHandler::new);
                //blockManager.registerBlockPlacementRule(new AxisPlacementRule(block));
            }
            if(block.namespace().value().contains("banner")) {
                //blockManager.registerHandler(block.namespace(), SignHandler::new);
                //blockManager.registerBlockPlacementRule(new AxisPlacementRule(block));
            }
            if(block.namespace().value().contains("wall")) {
                blockManager.registerBlockPlacementRule(new AxisPlacementRule(block));

            }
        }
        blockManager.registerBlockPlacementRule(new RedstonePlacementRule());




        for (short stateId = 0; stateId < Short.MAX_VALUE; stateId++) {
            Block block = Block.fromStateId(stateId);
            if (block == null) continue;

            RotationPlacement.updateDataFromBlock(block);
        }

        IChunkLoader loader = null;
        try {
            loader = new AnvilLoader("../../world");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        InstanceContainer instance = MinecraftServer.getInstanceManager().createInstanceContainer(loader);
        instance.setChunkSupplier(DynamicChunk::new);
        // Add an event callback to specify the spawning instance (and the spawn position)

        globalEventHandler.addListener(PlayerLoginEvent.class, event -> {
            final Player player = event.getPlayer();
            Pos pos = new Pos(164F, 68F, -504F);
            event.setSpawningInstance(instance);
            player.setRespawnPoint(new Pos(0, 42, 0));
            event.getPlayer().setRespawnPoint(pos);
        });

        globalEventHandler.addListener(PlayerBlockPlaceEvent.class, event -> new BlockPlacementHandler(event));

        globalEventHandler.addListener(PlayerSpawnEvent.class, event -> new PlayerSpawnHandler(event));



        // Start the server on port 25565
        minecraftServer.start("0.0.0.0", 25565);

    }
}