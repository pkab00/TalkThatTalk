package com.talk.THEMES;

import java.awt.Color;

public class Theme {
    private final Color 
    MAIN_COLOR1, MAIN_COLOR2, CONTRAST_COLOR,
    MAIN_TEXT_COLOR, CONTRAST_TEXT_COLOR, NEUTRAL_TEXT_COLOR;

    public Theme(ThemeBuilder builder){
        MAIN_COLOR1 = builder.getMainColor1();
        MAIN_COLOR2 = builder.getMainColor2();
        CONTRAST_COLOR = builder.getContrastColor();
        MAIN_TEXT_COLOR = builder.getMainTextColor();
        CONTRAST_TEXT_COLOR = builder.getContrastTextColor();
        NEUTRAL_TEXT_COLOR = builder.getNeutralTextColor();
    }

    public Color getMainColor1() {return MAIN_COLOR1;}
    public Color getMainColor2() {return MAIN_COLOR2;}
    public Color getContrastColor() {return CONTRAST_COLOR;}
    public Color getMainTextColor() {return MAIN_TEXT_COLOR;}
    public Color getContrastTextColor() {return CONTRAST_TEXT_COLOR;}
    public Color getNeutralTextColor() {return NEUTRAL_TEXT_COLOR;}
}
