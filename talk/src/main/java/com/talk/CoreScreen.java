package com.talk;
import com.talk.THEMES.*;

import java.io.File;
import java.io.IOException;

import java.awt.*;
import javax.swing.*;


public abstract class CoreScreen extends JFrame {
    public static final String IMAGES = "talk\\src\\main\\resources\\images";
    public static final Font FONT = loadFont();
    public static Theme THEME = ThemeManager.getTheme();

    protected CoreScreen(){
        setIconImage(new ImageIcon(IMAGES+"\\icon.png").getImage());
        setLook();
    }

    protected static Font loadFont(){
        try{
            Font font = Font.createFont(Font.TRUETYPE_FONT,
            new File("talk\\src\\main\\resources\\NotoSansKR-Medium.ttf"));
            return font;
        } catch(FontFormatException | IOException e){
            e.printStackTrace();
            return null;
        }
    }

    protected static void setLook(){
        float fontSize = 12f;
        UIManager.put("ToolTip.font", FONT.deriveFont(fontSize));
        UIManager.put("Label.font", FONT.deriveFont(fontSize));
        UIManager.put("Menu.font", FONT.deriveFont(fontSize));
        UIManager.put("MenuItem.font", FONT.deriveFont(fontSize));
        UIManager.put("TextField.font", FONT.deriveFont(fontSize));
        UIManager.put("OptionPane.buttonFont", FONT.deriveFont(fontSize));
        UIManager.put("OptionPane.messageFont", FONT.deriveFont(fontSize));

        UIManager.put("OptionPane.yesButtonText", "Да");
        UIManager.put("OptionPane.noButtonText", "Нет");
        UIManager.put("OptionPane.cancelButtonText", "Отмена");
    }
}
