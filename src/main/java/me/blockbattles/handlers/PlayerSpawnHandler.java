package me.blockbattles.handlers;

import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.GameMode;
import net.minestom.server.event.player.PlayerSpawnEvent;

public class PlayerSpawnHandler {
    public PlayerSpawnHandler(PlayerSpawnEvent event) {
        Pos pos = new Pos(0, 100, 0);
        event.getPlayer().teleport(pos).thenRun(() -> {
            event.getPlayer().setGameMode(GameMode.CREATIVE);
            event.getPlayer().setFlying(true);
            Pos start = pos.sub(0, 1, 0);
        });
    }
}
