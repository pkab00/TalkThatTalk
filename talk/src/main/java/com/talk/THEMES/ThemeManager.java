package com.talk.THEMES;

import java.io.*;
import java.util.*;

public abstract class ThemeManager {
    private final static String THEME_SETTINGS = "talk\\src\\main\\resources\\settings\\theme.txt";
    private final static Map<String,Theme> themeMap = Map.of(
        "Winter", new ThemeBuilder()
        .setMainColor1(AppPalette.BLUE)
        .setMainColor2(AppPalette.LIGHT_BLUE)
        .setContrastColor(AppPalette.DARK_BLUE)
        .setMainTextColor(AppPalette.DARK_BLUE)
        .setContrastTextColor(AppPalette.WHITE)
        .setNeutralTextColor(AppPalette.BLACK).build(),
        "Autumn", new ThemeBuilder()
        .setMainColor1(AppPalette.ORANGE)
        .setMainColor2(AppPalette.LIGHT_ORANGE)
        .setContrastColor(AppPalette.AUTUMN_PURPLE)
        .setMainTextColor(AppPalette.AUTUMN_BLUE)
        .setContrastTextColor(AppPalette.WHITE)
        .setMainTextColor(AppPalette.BLACK).build(),
        "Black'n'White", new ThemeBuilder()
        .setMainColor1(AppPalette.WHITE)
        .setMainColor2(AppPalette.WHITE)
        .setContrastColor(AppPalette.BLACK)
        .setMainTextColor(AppPalette.BLACK)
        .setNeutralTextColor(AppPalette.BLACK)
        .setContrastTextColor(AppPalette.WHITE).build()
    );

    public static Vector<String> getThemesList(){
        Vector<String> themes = new Vector<>();
        for(String e: themeMap.keySet()){
            themes.add(e);
        }
        return themes;
    }

    public static Theme getTheme(String themeName){
        return themeMap.get(themeName);
    }

    public static Theme getTheme(){
        return themeMap.get(readSettingsFile());
    }

    public static String readSettingsFile(){
        String output = themeMap.keySet().iterator().next();
        try{
            Scanner scanner = new Scanner(new File(THEME_SETTINGS));
            if(scanner.hasNextLine()){
                output = scanner.nextLine();
            }
            else{
                output = "WINTER";
                changeSettingsFile(output);
            }
            scanner.close();
        } catch(FileNotFoundException e){
            changeSettingsFile(output);
            e.printStackTrace();
        }
        return output;
    }

    public static void changeSettingsFile(String themeName){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(THEME_SETTINGS), false));
            writer.append(themeName);
            writer.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
