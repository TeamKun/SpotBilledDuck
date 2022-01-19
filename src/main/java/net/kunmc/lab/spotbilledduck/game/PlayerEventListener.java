package net.kunmc.lab.spotbilledduck.game;

import net.kunmc.lab.spotbilledduck.SpotBilledDuck;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class PlayerEventListener implements Listener {
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        // 親プレイやーの移動した場所を把握する
        Player player = e.getPlayer();
        if (!GameModeManager.isRunning() || !TeleportPlayer.shouldTeleportPlayer(player))
            return;

        if (PlayerStateManager.isParentPlayer(player.getName())) {
            Block block = player.getLocation().add(0, -1, 0).getBlock();
            block.setMetadata(GameModeManager.getStartTime() + GameConst.REACH, new FixedMetadataValue(SpotBilledDuck.getPlugin(), true));

            if (!GameModeManager.isSoloMode()) {
                String teamName = TeamManager.getTeamName(player);
                block.setMetadata(GameModeManager.getStartTime() + GameConst.REACH + teamName,
                        new FixedMetadataValue(SpotBilledDuck.getPlugin(), true));
            }
        } else {
            if (TeleportPlayer.shouldTeleportPlayer(player)) {
                TeleportPlayer.teleportPlayer(player);
            }
        }
    }
}