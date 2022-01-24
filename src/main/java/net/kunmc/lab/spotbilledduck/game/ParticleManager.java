package net.kunmc.lab.spotbilledduck.game;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedParticle;
import lombok.Getter;
import net.kunmc.lab.spotbilledduck.SpotBilledDuck;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class ParticleManager {
    @Getter
    private static Color particleColor = Color.WHITE;

    private static BukkitTask showParticleTack;

    public static void startShowParticle() {
        showParticleTack = new BukkitRunnable() {
            @Override
            public void run() {
                if (!GameModeManager.isRunning()) return;
                // パーティクル表示
                // playerの周囲5マスに表示する
                Bukkit.getOnlinePlayers().forEach(player -> {
                    // 表示範囲（前後上下左右4マス）
                    int rx = 5;
                    int ry = 5;
                    int rz = 5;
                    double px = player.getLocation().getX();
                    double py = player.getLocation().getY();
                    double pz = player.getLocation().getZ();
                    for (int x = rx * -1; x < rx; x++) {
                        for (int y = ry * -1; y < ry; y++) {
                            for (int z = rz * -1; z < rz; z++) {
                                Block block = new Location(player.getWorld(), px + x, py + y, pz + z).getBlock();
                                if (block == null) continue;
                                Location targetLocation = block.getLocation();
                                // 1ブロック上に乗ることが可能か表示する
                                targetLocation.add(0, 1, 0);

                                // particleを表示しないケース
                                if (!TeleportPlayer.isReachedBlock(player, block) ||
                                        !PlayerStateManager.canStand(block)) continue;
                                showParticleTack(targetLocation, player);
                            }
                        }
                    }
                });
            }
        }.runTaskTimer(SpotBilledDuck.getPlugin(), 0, 5);
    }

    public static void stopShowParticle() {
        showParticleTack.cancel();
        showParticleTack = null;
    }

    public static void showParticleTack(Location location, Player player) {
        PacketContainer packetContainer = SpotBilledDuck.getProtocolManager().createPacket(PacketType.Play.Server.WORLD_PARTICLES);
        packetContainer.getDoubles()
                .write(0, location.getX() + 0.5)
                .write(1, location.getY() + 0.4)
                .write(2, location.getZ() + 0.5);
        packetContainer.getFloat().write(0, 1.0f);
        packetContainer.getNewParticles().write(0, WrappedParticle.create(Particle.REDSTONE, new Particle.DustOptions(particleColor, 1)));
        try {
            SpotBilledDuck.getProtocolManager().sendServerPacket(player, packetContainer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean setParticleColor(String color) {
        boolean valid = true;
        switch (color) {
            case "white":
                particleColor = Color.WHITE;
                break;
            case "black":
                particleColor = Color.BLACK;
                break;
            case "red":
                particleColor = Color.RED;
                break;
            case "blue":
                particleColor = Color.BLUE;
                break;
            case "green":
                particleColor = Color.GREEN;
                break;
            case "purple":
                particleColor = Color.PURPLE;
                break;
            default:
                valid = false;
        }
        return valid;
    }
}
