package com.talk;

import java.awt.*;
import javax.swing.*;

public class LearningScreen extends JFrame {
    private MainScreen scr;
    public LearningScreen(MainScreen scr){
        this.scr = scr;
        this.setTitle("Режим заучивания");
        this.setSize(900, 750);
        this.setResizable(false);
        add(createPanel());
    }

    private JPanel createPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 3));
        for(int i = 1; i <= 9; i++){
            JButton newButton = new JButton("Button "+i);
            newButton.setFont(MainScreen.loadFont().deriveFont(35f));
            newButton.setBackground(MainScreen.THEME.getMainColor2());
            newButton.setForeground(MainScreen.THEME.getMainTextColor());
            newButton.setBorderPainted(false);
            panel.add(newButton);
        }
        return panel;
    }
}
