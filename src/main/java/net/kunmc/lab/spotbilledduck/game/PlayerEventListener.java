package net.kunmc.lab.spotbilledduck.game;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static net.kunmc.lab.spotbilledduck.game.PlayerStateManager.removeParentPlayerReachedPlace;

public class PlayerEventListener implements Listener {
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        // 親プレイやーの移動した場所を把握する
        Player player = e.getPlayer();

        if (!GameModeManager.isRunning() || !PlayerStateManager.isParentPlayer(player.getUniqueId()) || !PlayerMoveCalculator.shouldMovePlayer(player))
            return;

        Location blockLocation = player.getLocation().getBlock().getLocation();
        PlayerStateManager.updatePlayerState(player, blockLocation);
    }

    @EventHandler
    public void onPlayerBlockPlaceEvent(BlockPlaceEvent e) {
        // ブロックが新規に置かれたら踏める場所を更新する
        Map<UUID, Set<String>> parentPlayers = PlayerStateManager.getParentPlayers();
        String blockPlace = Place.getXyzPlaceStringFromLocation(e.getBlock().getLocation());
        for (UUID playerId : parentPlayers.keySet()) {
            Set<String> removeTarget = new HashSet<>();
            for (String place : parentPlayers.get(playerId)) {
                if (place.equals(blockPlace))
                    removeTarget.add(place);
            }
            for (String place : removeTarget) {
                removeParentPlayerReachedPlace(playerId, place);
            }
        }
    }
}