package net.kunmc.lab.spotbilledduck;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import dev.kotx.flylib.FlyLib;
import lombok.Getter;
import net.kunmc.lab.spotbilledduck.command.Main;
import net.kunmc.lab.spotbilledduck.game.PlayerEventHandler;
import org.bukkit.plugin.java.JavaPlugin;

public final class SpotBilledDuck extends JavaPlugin {

    @Getter
    private static SpotBilledDuck plugin;
    @Getter
    private static ProtocolManager protocolManager;

    @Override
    public void onEnable() {
        plugin = this;
        protocolManager = ProtocolLibrary.getProtocolManager();
        FlyLib.create(this, builder -> {
            builder.command(new Main());
        });
        getServer().getPluginManager().registerEvents(new PlayerEventHandler(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
