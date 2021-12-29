package net.kunmc.lab.spotbilledduck.game;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerEventHandler implements Listener {
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        if (!GameModeManager.isRunning() || !PlayerStateManager.isParentPlayer(player.getUniqueId())) return;

        Location blockLocation = player.getLocation().getBlock().getLocation();
        PlayerStateManager.updatePlayerState(player, blockLocation);
    }
}