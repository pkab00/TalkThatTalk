package com.talk;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import java.util.*;
import java.util.List;

public class LearningScreen extends CoreScreen {
    private final int NUM_CARDS = 6;
    private final int NUM_COLS = 3;
    private int LEARNED_CARDS = 0;
    private JButton PICKED_CARD = null;
    private MainScreen scr;
    public LearningScreen(MainScreen scr){
        this.scr = scr;
        this.setTitle("Режим заучивания");
        this.setSize(NUM_COLS*300, 750);
        this.setResizable(true);
        add(createPanel());
    }

    private JPanel createPanel(){
        Map<String,JButton> buttonMap = new HashMap<>();
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(NUM_CARDS/NUM_COLS, NUM_COLS));
        HashMap<String,String> preprocessedData = prepareData();
        ArrayList<String> processedData = processData(preprocessedData);
        for(int i = 0; i < NUM_CARDS; i++){
            JButton newButton = new JButton(processedData.get(i));
            newButton.addActionListener((e) -> {
                if(PICKED_CARD == null){
                    PICKED_CARD = newButton;
                    newButton.setBackground(MainScreen.THEME.getMainColor1());
                }
                else{
                    String TEXT = newButton.getText();
                    String PICKED_TEXT = PICKED_CARD.getText();
                    if((preprocessedData.containsKey(PICKED_TEXT) && preprocessedData.get(PICKED_TEXT).equals(TEXT)) 
                    || (preprocessedData.containsKey(TEXT) && preprocessedData.get(TEXT).equals(PICKED_TEXT))){
                        newButton.setBackground(MainScreen.THEME.getMainColor1());
                        newButton.setText("<html><s>"+TEXT+"</html></s>");
                        PICKED_CARD.setText("<html><s>"+PICKED_TEXT+"</html></s>");
                        newButton.setEnabled(false);
                        PICKED_CARD.setEnabled(false);
                        PICKED_CARD = null;
                        LEARNED_CARDS++;
                        if(LEARNED_CARDS == NUM_CARDS/2){
                            // todo
                        }
                    }
                }
            });
            newButton.setFont(MainScreen.loadFont().deriveFont(30f));
            newButton.setBackground(MainScreen.THEME.getMainColor2());
            newButton.setForeground(MainScreen.THEME.getMainTextColor());
            newButton.setBorderPainted(false);
            buttonMap.put(newButton.getText(), newButton);
            panel.add(newButton);
        }
        return panel;
    }

    private HashMap<String,String> prepareData(){
        HashMap<String,String> resList = new HashMap<>();
        try{
            ResultSet set = new DBManager().getRusAndKor();
            while(set.next()){
                resList.put(set.getString("rus"), set.getString("kor"));
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        ArrayList<Map.Entry<String, String>> entryList = new ArrayList<>(resList.entrySet().stream().toList());
        Collections.shuffle(entryList);
        HashMap<String,String> outputMap = new HashMap<>();
        for(int i = 0; i < NUM_CARDS/2; i++){
            outputMap.put(entryList.get(i).getKey(), entryList.get(i).getValue());
        };
        return outputMap;
    }

    private ArrayList<String> processData(HashMap<String,String> preprocessedData){
        ArrayList<String> processedData = new ArrayList<>();
        List<Map.Entry<String,String>> entryList = preprocessedData.entrySet().stream().toList();
        for(int i = 0; i < preprocessedData.size(); i++){
            Map.Entry<String,String> entry = entryList.get(i);
            processedData.add(entry.getKey());
            processedData.add(entry.getValue());
        }
        Collections.shuffle(processedData);
        return processedData;
    }
}
