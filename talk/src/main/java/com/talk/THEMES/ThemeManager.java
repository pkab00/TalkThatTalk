package com.talk.THEMES;

public abstract class ThemeManager {
    public static Theme MAIN = new ThemeBuilder()
    .setMainColor1(AppPalette.BLUE)
    .setMainColor2(AppPalette.LIGHT_BLUE)
    .setContrastColor(AppPalette.DARK_BLUE)
    .setMainTextColor(AppPalette.DARK_BLUE)
    .setContrastTextColor(AppPalette.WHITE)
    .setNeutralTextColor(AppPalette.BLACK).build();
}
