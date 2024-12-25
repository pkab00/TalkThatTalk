package com.talk.THEMES;

import java.io.*;
import java.util.*;

public abstract class ThemeManager {
    private final static String THEME_SETTINGS = "talk\\src\\main\\resources\\settings\\theme.txt";
    private final static Map<String,Theme> themeMap = Map.of(
        "WINTER", new ThemeBuilder()
        .setMainColor1(AppPalette.BLUE)
        .setMainColor2(AppPalette.LIGHT_BLUE)
        .setContrastColor(AppPalette.DARK_BLUE)
        .setMainTextColor(AppPalette.DARK_BLUE)
        .setContrastTextColor(AppPalette.WHITE)
        .setNeutralTextColor(AppPalette.BLACK).build()
    );

    public static Theme getTheme(String themeName){
        return themeMap.get(themeName);
    }

    public static Theme getTheme(){
        return themeMap.get(readSettingsFile());
    }

    public static String readSettingsFile(){
        String output = "WINTER";
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
