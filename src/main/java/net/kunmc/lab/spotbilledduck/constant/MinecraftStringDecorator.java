package net.kunmc.lab.spotbilledduck.constant;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class MinecraftStringDecorator {
    public final static String BLACK = "§0";
    public final static String DARK_BLUE = "§1";
    public final static String DARK_GREEN = "§2";
    public final static String DARK_AQUA = "§3";
    public final static String DARK_RED = "§4";
    public final static String DARK_PURPLE = "§5";
    public final static String GOLD = "§6";
    public final static String GRAY = "§7";
    public final static String DARK_GRAY = "§8";
    public final static String BLUE = "§9";
    public final static String GREEN = "§a";
    public final static String AQUA = "§b";
    public final static String RED = "§c";
    public final static String LIGHT_PURPLE = "§d";
    public final static String YELLOW = "§e";
    public final static String WHITE = "§f";
    public final static String OBFUSCATED = "§k";
    public final static String BOLD = "§l";
    public final static String STRIKETHROUGH = "§m";
    public final static String UNDERLINE = "§n";
    public final static String ITALIC = "§o";
    public final static String RESET = "§r";

    private static Map<String, String> formattingCodes = new HashMap<>();

    static {
        for (Field field : MinecraftStringDecorator.class.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                formattingCodes.put(field.getName(), (String) field.get(MinecraftStringDecorator.class));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public static String DecorateString(String str, String color) {
        return formattingCodes.get(color) + str + formattingCodes.get("RESET");
    }
}
