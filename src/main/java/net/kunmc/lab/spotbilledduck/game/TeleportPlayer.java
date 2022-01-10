package net.kunmc.lab.spotbilledduck.game;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;

import java.util.*;

public class TeleportPlayer {
    public static boolean shouldTeleportPlayer(Player player) {
        // Playerの真下のブロックを判定する

        // 以下のケースは取り除く
        // プレイヤーが地上にいないかつ水中や乗り物にもいない
        // クリエ・スぺク
        if ( (!((LivingEntity) player).isOnGround() && (!player.isInWaterOrBubbleColumn() && !player.isInsideVehicle())) ||
                (player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR))) return false;

        // 足元のブロックが着地できないところなら移動しない
        Block underBlock = player.getLocation().add(0, -1, 0).getBlock();
        return PlayerStateManager.canStand(underBlock);
    }

    public static void teleportPlayer(Player player) {
        if (PlayerStateManager.isParentPlayer(player.getName())) return;

        Block checkBlock = player.getLocation().add(0, -1, 0).getBlock();
        if (isReachedBlock(player, checkBlock)) return;

        Location location = getTeleportLocation(player);
        if (location == null) return;

        location.setPitch(player.getLocation().getPitch());
        location.setYaw(player.getLocation().getYaw());
        player.teleport(location);
    }

    public static boolean isReachedBlock(Player player, Block block){
        Object result;
        if (GameModeManager.isSoloMode()) {
            List<MetadataValue> meta = block.getMetadata(GameModeManager.getStartTime() + GameConst.REACH);
            if (meta.size() == 0) return false;
            result = meta.get(0).value();
        } else {
            String teamName = TeamManager.getTeamName(player);
            if (teamName == null) return false;
            List<MetadataValue> meta = block.getMetadata(GameModeManager.getStartTime() + GameConst.REACH + teamName);
            if (meta.size() == 0) return false;
            result = meta.get(0).value();

        }
        if (result == null) return false;
        return (Boolean) result;
    }

    private static Location getTeleportLocation(Player player) {
        Location location;
        Set<String> toPlayers;
        if (GameModeManager.isSoloMode()) {
            toPlayers = PlayerStateManager.getParentPlayers();
        } else {
            toPlayers = TeamManager.getTeamPlayers(player);
        }
        String destPlayerName = getRandomStringFromSet(toPlayers);
        if (destPlayerName == null) return null;
        location = Bukkit.getPlayer(destPlayerName).getLocation();
        return location;
    }

    private static String getRandomStringFromSet(Set<String> set) {
        List<String> setArray= new ArrayList(Arrays.asList(set.toArray()));
        Collections.shuffle(setArray);
        for (int i = 0; i < setArray.size(); i++) {
            if (Bukkit.getPlayerExact(setArray.get(i)) != null) {
                return setArray.get(i);
            }
        }
        return null;
    }
}
