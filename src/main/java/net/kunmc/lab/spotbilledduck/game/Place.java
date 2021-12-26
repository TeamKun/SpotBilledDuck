package net.kunmc.lab.spotbilledduck.game;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;

public class Place {
    private static StringBuilder sb = new StringBuilder();

    public static String getXyzPlaceStringFromLocation(Location location) {
        sb.append(location.getWorld().getName());
        sb.append(" ");
        sb.append(location.getX());
        sb.append(" ");
        sb.append(location.getY());
        sb.append(" ");
        sb.append(location.getZ());
        String ret = sb.toString();
        sb.setLength(0);
        return ret;
    }

    public static Block getBlockFromPlaceString(String pos) {
        String[] loc = pos.split(" ");
        return Bukkit.getWorld(loc[0]).getBlockAt(
                (int) Double.parseDouble(loc[1]),
                (int) Double.parseDouble(loc[2]),
                (int) Double.parseDouble(loc[3])
        );
    }
}