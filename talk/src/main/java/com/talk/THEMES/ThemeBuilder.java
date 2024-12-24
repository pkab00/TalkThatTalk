package com.talk.THEMES;

import java.awt.Color;

public class ThemeBuilder {
    private Color 
    MAIN_COLOR1, MAIN_COLOR2, CONTRAST_COLOR,
    MAIN_TEXT_COLOR, CONTRAST_TEXT_COLOR, NEUTRAL_TEXT_COLOR;

    public ThemeBuilder() {}

    public ThemeBuilder setMainColor1(Color color) {MAIN_COLOR1 = color; return this;}
    public ThemeBuilder setMainColor2(Color color) {MAIN_COLOR2 = color; return this;}
    public ThemeBuilder setContrastColor(Color color) {CONTRAST_COLOR = color; return this;}
    public ThemeBuilder setMainTextColor(Color color) {MAIN_TEXT_COLOR = color; return this;}
    public ThemeBuilder setContrastTextColor(Color color) {CONTRAST_TEXT_COLOR = color; return this;}
    public ThemeBuilder setNeutralTextColor(Color color) {NEUTRAL_TEXT_COLOR = color; return this;}

    public Color getMainColor1() {return MAIN_COLOR1;}
    public Color getMainColor2() {return MAIN_COLOR2;}
    public Color getContrastColor() {return CONTRAST_COLOR;}
    public Color getMainTextColor() {return MAIN_TEXT_COLOR;}
    public Color getContrastTextColor() {return CONTRAST_TEXT_COLOR;}
    public Color getNeutralTextColor() {return NEUTRAL_TEXT_COLOR;}

    public Theme build() {return new Theme(this);}
}
