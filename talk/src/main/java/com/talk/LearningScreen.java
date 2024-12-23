package com.talk;

import javax.swing.*;

public class LearningScreen extends JFrame {
    private MainScreen scr;
    public LearningScreen(MainScreen scr){
        this.scr = scr;
        this.setTitle("Режим заучивания");
        this.setSize(750, 750);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
