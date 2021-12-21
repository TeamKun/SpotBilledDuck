package net.kunmc.lab.spotbilledduck.game;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerEventHandler {
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        Location blockLocation = player.getLocation().getBlock().getLocation();
        System.out.println("test event location");
        System.out.println(blockLocation.toString());
        PlayerStateManager.updatePlayerState(player, blockLocation);
    }
}