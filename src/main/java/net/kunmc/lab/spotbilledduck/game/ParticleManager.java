package net.kunmc.lab.spotbilledduck.game;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedParticle;
import net.kunmc.lab.spotbilledduck.SpotBilledDuck;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class ParticleManager {
    private static BukkitTask showParticleTack;

    public static void startShowParticle() {
        showParticleTack = new BukkitRunnable() {
            @Override
            public void run() {
                if (!GameModeManager.isRunning()) return;

                // パーティクル表示
                // playerの周囲5マスに表示する
                PlayerStateManager.getChildPlayers().forEach(player -> {
                    // 表示範囲（前後上下左右4マス）
                    int rx = 4;
                    int ry = 4;
                    int rz = 4;
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
                                // particleを表示できないなら飛ばす
                                if (!canShowParticleBlock(targetLocation.getBlock())) continue;
                                String targetPlace = Place.getXyzPlaceStringFromLocation(targetLocation);
                                // particleを表示する必要がなければ飛ばす
                                if (!PlayerStateManager.canStand(block) || !PlayerStateManager.isParentReachedPlace(player, targetPlace))
                                    continue;

                                targetLocation.add(0.5, 0.5, 0.5);
                                PacketContainer packetContainer = SpotBilledDuck.getProtocolManager().createPacket(
                                        PacketType.Play.Server.WORLD_PARTICLES);
                                packetContainer.getDoubles()
                                        .write(0, targetLocation.getX())
                                        .write(1, targetLocation.getY())
                                        .write(2, targetLocation.getZ());
                                packetContainer.getNewParticles().write(0, WrappedParticle.create(Particle.COMPOSTER, null));
                                try {
                                    SpotBilledDuck.getProtocolManager().sendServerPacket(player, packetContainer);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
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

    public static boolean canShowParticleBlock(Block block) {
        // 対象のブロックが強制移動の対象になるかを判定
        Material type = block.getType();
        return type.equals(Material.AIR) ||
                type.equals(Material.CAVE_AIR) ||
                type.equals(Material.VOID_AIR) ||
                type.equals(Material.GRASS) ||
                type.equals(Material.TALL_GRASS) ||
                type.equals(Material.WATER) ||
                type.equals(Material.BUBBLE_COLUMN) ||
                type.equals(Material.SEAGRASS) ||
                type.equals(Material.TALL_SEAGRASS);
    }
}
