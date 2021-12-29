package net.kunmc.lab.spotbilledduck.game;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedParticle;
import net.kunmc.lab.spotbilledduck.SpotBilledDuck;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.RayTraceResult;

public class ParticleManager {
    private static BukkitTask showParticleTack;
    private static BukkitTask deleteParticleTack;

    public static void showParticle() {
        showParticleTack = new BukkitRunnable() {
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
                    packetContainer.getNewParticles().write(0, WrappedParticle.create(Particle.CLOUD, null));

                    try {
                        SpotBilledDuck.getProtocolManager().sendServerPacket(player, packetContainer);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        }.runTaskTimer(SpotBilledDuck.getPlugin(), 0, 1);
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

    public static void stopParticle() {
        showParticleTack.cancel();
        showParticleTack = null;
    }
}
