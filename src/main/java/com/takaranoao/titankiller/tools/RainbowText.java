package com.takaranoao.titankiller.tools;

import net.minecraft.util.EnumChatFormatting;

public class RainbowText {

    private static final EnumChatFormatting[] manaita_infinity = new EnumChatFormatting[]{EnumChatFormatting.RED, EnumChatFormatting.GOLD, EnumChatFormatting.YELLOW, EnumChatFormatting.GREEN, EnumChatFormatting.AQUA, EnumChatFormatting.BLUE, EnumChatFormatting.LIGHT_PURPLE};

    private static final EnumChatFormatting[] WRB = new EnumChatFormatting[]{EnumChatFormatting.WHITE,EnumChatFormatting.WHITE,EnumChatFormatting.WHITE,EnumChatFormatting.WHITE,EnumChatFormatting.WHITE,EnumChatFormatting.WHITE,EnumChatFormatting.WHITE,EnumChatFormatting.WHITE,EnumChatFormatting.WHITE,EnumChatFormatting.WHITE,EnumChatFormatting.BLUE,EnumChatFormatting.BLUE,EnumChatFormatting.BLUE,EnumChatFormatting.BLUE,EnumChatFormatting.RED,EnumChatFormatting.BLUE,EnumChatFormatting.WHITE,EnumChatFormatting.RED,EnumChatFormatting.BLUE,EnumChatFormatting.WHITE,EnumChatFormatting.RED,EnumChatFormatting.BLUE,EnumChatFormatting.WHITE,EnumChatFormatting.RED,EnumChatFormatting.BLUE,EnumChatFormatting.WHITE,EnumChatFormatting.WHITE,EnumChatFormatting.WHITE,EnumChatFormatting.WHITE,EnumChatFormatting.WHITE};

    private static final EnumChatFormatting[] manaita_mode = new EnumChatFormatting[]{EnumChatFormatting.YELLOW, EnumChatFormatting.YELLOW, EnumChatFormatting.YELLOW, EnumChatFormatting.YELLOW, EnumChatFormatting.YELLOW, EnumChatFormatting.YELLOW, EnumChatFormatting.GOLD, EnumChatFormatting.RED, EnumChatFormatting.YELLOW, EnumChatFormatting.YELLOW, EnumChatFormatting.YELLOW, EnumChatFormatting.YELLOW, EnumChatFormatting.YELLOW, EnumChatFormatting.YELLOW, EnumChatFormatting.GOLD, EnumChatFormatting.RED};
    private static final EnumChatFormatting[] manaita_enchantment = new EnumChatFormatting[]{EnumChatFormatting.LIGHT_PURPLE, EnumChatFormatting.LIGHT_PURPLE, EnumChatFormatting.LIGHT_PURPLE, EnumChatFormatting.LIGHT_PURPLE, EnumChatFormatting.BLUE, EnumChatFormatting.DARK_PURPLE, EnumChatFormatting.LIGHT_PURPLE, EnumChatFormatting.LIGHT_PURPLE, EnumChatFormatting.LIGHT_PURPLE, EnumChatFormatting.LIGHT_PURPLE, EnumChatFormatting.AQUA, EnumChatFormatting.DARK_PURPLE};

    public static String makeRainbow(String input) {
        return formatting(input, manaita_infinity, 80.0D);
    }
    public static String makeRBW(String input) {
        return formatting(input, WRB, 40.0);
    }

    public static String makeManaitaMode(String input) {
        return formatting(input, manaita_mode, 120.0D);
    }

    public static String makeManaitaEnchantment(String input) {
        return formatting(input, manaita_enchantment, 120.0D);
    }

    public static String formatting(String input, EnumChatFormatting[] colours, double delay) {
        StringBuilder sb = new StringBuilder(input.length() * 3);
        if(delay <= 0.0D) {
            delay = 0.001D;
        }

        int offset = (int)Math.floor((double)(System.currentTimeMillis() & 16383L) / delay) % colours.length;

        for(int i = 0; i < input.length(); ++i) {
            char c = input.charAt(i);
            int col = (i + colours.length - offset) % colours.length;
            sb.append(colours[col].toString());
            sb.append(c);
        }

        return sb.toString();
    }

}
