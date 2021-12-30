package net.kunmc.lab.spotbilledduck.game;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedParticle;
import net.kunmc.lab.spotbilledduck.SpotBilledDuck;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.RayTraceResult;

public class ParticleManager {
    private static BukkitTask showParticleTack;
    private static BukkitTask deleteParticleTack;

    public static void startShowParticle() {
        showParticleTack = new BukkitRunnable() {
            @Override
            public void run() {
                if (!GameModeManager.isRunning()) return;

                // パーティクル表示
                // playerの周囲5マスに表示する
                PlayerStateManager.getChildPlayers().forEach(player -> {
                    // 表示範囲（前後上下左右5マス）
                    int rx = 5;
                    int ry = 5;
                    int rz = 5;
                    double px = player.getLocation().getX();
                    double py = player.getLocation().getY();
                    double pz = player.getLocation().getZ();
                    for (int x = rx*-1; x<rx; x++) {
                        for (int y = ry*-1; y<ry; y++) {
                            for (int z = rz*-1; z<rz; z++) {
                                Block block = new Location(player.getWorld(), px + x, py + y, pz + z).getBlock();
                                if (block == null) continue;
                                Location targetLocation = block.getLocation();
                                // 1ブロック上に乗ることが可能か表示する
                                targetLocation.add(0, 1, 0);
                                String targetPlace = Place.getXyzPlaceStringFromLocation(targetLocation);
                                if (!PlayerStateManager.isParentReachedPlace(player, targetPlace) || !PlayerStateManager.isTargetBlock(block)) continue;

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
        }.runTaskTimer(SpotBilledDuck.getPlugin(), 0, 3);
    }
    public static void deleteParticle() {
        // 表示する必要がなくなったParticleをすぐに削除するためのタスク
        deleteParticleTack = new BukkitRunnable() {
            @Override
            public void run() {
                if (!GameModeManager.isRunning()) return;

                PlayerStateManager.getChildPlayers().forEach(player -> {
                    // パーティクル表示
                    RayTraceResult rayTraceResult = player.rayTraceBlocks(8, FluidCollisionMode.ALWAYS);
                    if (rayTraceResult == null) {
                        return;
                    }

                    // 視線の先のブロックに乗れるかチェックする
                    Location targetLocation = rayTraceResult.getHitBlock().getLocation();
                    // 視線の先の1ブロック上に侵入可能かチェックする
                    targetLocation.add(0, 1, 0);
                    String targetPlace = Place.getXyzPlaceStringFromLocation(targetLocation);

                    // 対象地点に乗ることができるなら何も表示しない
                    if (PlayerStateManager.isParentReachedPlace(player, targetPlace)) return;

                    // particleをブロックの中心に表示する
                    targetLocation.add(0.5, 0.5, 0.5);
                    PacketContainer packetContainer = SpotBilledDuck.getProtocolManager().createPacket(
                            PacketType.Play.Server.WORLD_PARTICLES);
                    packetContainer.getDoubles()
                            .write(0, targetLocation.getX())
                            .write(1, targetLocation.getY())
                            .write(2, targetLocation.getZ());
                    packetContainer.getNewParticles().write(0, WrappedParticle.create(Particle.BARRIER, null));

                    try {
                        SpotBilledDuck.getProtocolManager().sendServerPacket(player, packetContainer);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        }.runTaskTimer(SpotBilledDuck.getPlugin(), 0, 1);
    }

    public static void stopShowParticle() {
        showParticleTack.cancel();
        showParticleTack = null;
    }
}
