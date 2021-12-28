package net.kunmc.lab.spotbilledduck.game;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedParticle;
import net.kunmc.lab.spotbilledduck.SpotBilledDuck;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.RayTraceResult;

public class ParticleManager {
    private static BukkitTask showParticleTack;

    public static void showParticle() {
        /**
         * 腐食処理等のループメソッド
         */
        showParticleTack = new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(player -> {
                    // パーティクル表示
                    RayTraceResult rayTraceResult = player.rayTraceBlocks(8);
                    if (rayTraceResult == null) {
                        return;
                    }
                    Location targetLocation = rayTraceResult.getHitBlock().getLocation();
                    targetLocation.add(0.5, 1.5, 0.5);
                    String targetPlace = Place.getXyzPlaceStringFromLocation(targetLocation);

                    // 対象地点がセーフなら何も表示しない
                    if (PlayerStateManager.isSafePlace(player, targetPlace)) return;

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
