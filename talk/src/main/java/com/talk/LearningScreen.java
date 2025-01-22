package com.talk;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import java.util.*;

public class LearningScreen extends JFrame {
    private final int NUM_CARDS = 6;
    private MainScreen scr;
    public LearningScreen(MainScreen scr){
        this.scr = scr;
        this.setTitle("Режим заучивания");
        this.setSize(900, 750);
        this.setResizable(true);
        add(createPanel());
    }

    private JPanel createPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 3));
        ArrayList<Map<String,String>> preprocessedData = prepareData();
        Collections.shuffle(preprocessedData);
        ArrayList<String> processedData = new ArrayList<>();
        for(int i = 0; i < preprocessedData.size(); i++){
            processedData.addAll(preprocessedData.get(i).keySet());
            processedData.addAll(preprocessedData.get(i).values());
        }
        Collections.shuffle(processedData);
        for(int i = 0; i < NUM_CARDS; i++){
            JButton newButton = new JButton(processedData.get(i));
            newButton.setFont(MainScreen.loadFont().deriveFont(30f));
            newButton.setBackground(MainScreen.THEME.getMainColor2());
            newButton.setForeground(MainScreen.THEME.getMainTextColor());
            newButton.setBorderPainted(false);
            panel.add(newButton);
        }
        return panel;
    }

    private ArrayList<Map<String,String>> prepareData(){
        ArrayList<Map<String,String>> resList = new ArrayList<>();
        try{
            ResultSet set = new DBManager().getRusAndKor();
            while(set.next()){
                Map<String,String> pair = Map.of(set.getString("rus"), set.getString("kor"));
                resList.add(pair);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        Collections.shuffle(resList);
        return new ArrayList<>(resList.stream().limit(NUM_CARDS/2).toList());
    }
}
