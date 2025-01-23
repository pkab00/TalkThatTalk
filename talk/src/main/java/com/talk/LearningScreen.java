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
    private ArrayList<JButton> buttonsList = new ArrayList<>();
    private JLabel pairsCountingLabel;
    private MainScreen scr;
    public LearningScreen(MainScreen scr){
        this.scr = scr;
        this.setTitle("Режим заучивания");
        this.setSize(NUM_COLS*300, 750);
        this.setResizable(true);
        setLayout(new BorderLayout());
        add(createTopBar(), BorderLayout.NORTH);
        add(createPanel(), BorderLayout.CENTER);
    }

    private JPanel createTopBar(){
        JPanel topBar = new JPanel();
        Font font = MainScreen.loadFont().deriveFont(25f);
        pairsCountingLabel = new JLabel();
        pairsCountingLabel.setFont(font);
        pairsCountingLabel.setText("Очки: "+LEARNED_CARDS);
        pairsCountingLabel.setForeground(MainScreen.THEME.getContrastTextColor());
        topBar.setLayout(new BoxLayout(topBar, BoxLayout.Y_AXIS));
        topBar.setBackground(MainScreen.THEME.getContrastColor());
        
        topBar.add(Box.createHorizontalStrut(10));
        topBar.add(pairsCountingLabel);
        return topBar;
    }

    private JPanel createPanel(){
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
                        newButton.setText("<html><s><i>"+TEXT+"</html></s></i>");
                        PICKED_CARD.setText("<html><s><i>"+PICKED_TEXT+"</html></s></i>");
                        newButton.setEnabled(false);
                        PICKED_CARD.setEnabled(false);
                        PICKED_CARD = null;
                        LEARNED_CARDS++;
                        updateCounterLabel();
                        if(LEARNED_CARDS%(NUM_CARDS/2) == 0){
                            preprocessedData.clear();
                            preprocessedData.putAll(prepareData());
                            processedData.clear();
                            processedData.addAll(processData(preprocessedData));
                            updateCards(processedData);
                        }
                    }
                }
            });
            newButton.setFont(MainScreen.loadFont().deriveFont(30f));
            newButton.setBackground(MainScreen.THEME.getMainColor2());
            newButton.setForeground(MainScreen.THEME.getMainTextColor());
            newButton.setBorderPainted(false);
            buttonsList.add(newButton);
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

    private void updateCards(ArrayList<String> words){
        var buttons = getButtons();
        for(int i = 0; i < getCardsNumber(); i++){
            JButton button = buttons.get(i);
            button.setText(words.get(i));
            button.setBackground(MainScreen.THEME.getMainColor2());
            button.setForeground(MainScreen.THEME.getMainTextColor());
            button.setEnabled(true);
        }
    }

    private void updateCounterLabel(){
        pairsCountingLabel.setText("Очки: "+getLearnedCards());
    }

    public int getCardsNumber(){
        return NUM_CARDS;
    }
    public int getColumnsNumber(){
        return NUM_COLS;
    }
    public int getLearnedCards(){
        return LEARNED_CARDS;
    }
    public ArrayList<JButton> getButtons(){
        return buttonsList;
    }
}
