package net.kunmc.lab.spotbilledduck;

import dev.kotx.flylib.FlyLib;
import net.kunmc.lab.spotbilledduck.command.Main;
import org.bukkit.plugin.java.JavaPlugin;

public final class SpotBilledDuck extends JavaPlugin {

    public static SpotBilledDuck plugin;

    @Override
    public void onEnable() {
        plugin = this;
        FlyLib.create(this, builder -> {
            builder.command(new Main());
        });
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
