package com.talk;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.*;
import java.util.*;
import java.util.List;

public class LearningScreen extends CoreScreen {
    private final int NUM_COLS = 2;
    private int NUM_CARDS = NUM_COLS*2;
    private final int LEVEL_MULTIPLIER = NUM_CARDS;
    private int LEVELS_COMPLETED = 0;
    private int LEARNED_CARDS = 0;
    private static MemoCard SELECTED_CARD = null;
    private ArrayList<MemoCard> cardsList = new ArrayList<>();
    private JPanel panel;
    private JLabel pairsCountingLabel;
    private JLabel timerLabel;
    private MainScreen scr;
    
    public LearningScreen(MainScreen scr){
        this.scr = scr;
        this.setTitle("Режим заучивания");
        this.setSize(NUM_COLS*600, 750);
        this.setResizable(true);
        setLayout(new BorderLayout());
        add(createTopBar(), BorderLayout.NORTH);
        add(createPanel(), BorderLayout.CENTER);
        new TimerWotker(this).execute();
    }

    private JPanel createTopBar(){
        JPanel topBar = new JPanel();
        Font font = MainScreen.loadFont().deriveFont(25f);
        pairsCountingLabel = new JLabel();
        pairsCountingLabel.setFont(font);
        pairsCountingLabel.setText("Очки: "+LEARNED_CARDS);
        pairsCountingLabel.setForeground(MainScreen.THEME.getContrastTextColor());
        timerLabel = new JLabel();
        timerLabel.setFont(font);
        timerLabel.setForeground(MainScreen.THEME.getContrastTextColor());
        topBar.setLayout(new BoxLayout(topBar, BoxLayout.Y_AXIS));
        topBar.setBackground(MainScreen.THEME.getContrastColor());
        
        topBar.add(Box.createHorizontalStrut(10));
        topBar.add(pairsCountingLabel);
        topBar.add(Box.createHorizontalStrut(20));
        topBar.add(timerLabel);
        return topBar;
    }

    private JPanel createPanel(){
        panel = new JPanel();
        panel.setLayout(new GridLayout(NUM_CARDS/NUM_COLS, NUM_COLS));
        updateCards();
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
        return processedData;
    }

    private void updateCards(){
        class EndLevelListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e){
                MemoCard sourceCard = (MemoCard)e.getSource();
                if(sourceCard.getPair().equals(SELECTED_CARD)){
                    LEARNED_CARDS++;
                    updateCounterLabel();
                    if(endLevelCondition(sourceCard)){
                        LEVELS_COMPLETED++;
                        if(LEVELS_COMPLETED%5==0){
                            NUM_CARDS += LEVEL_MULTIPLIER;
                        }
                        updateCards();
                   }
               }
            }
        }

        cardsList.clear();
        panel.removeAll();
        var preprocessedData = prepareData();
        var words = processData(preprocessedData);
        
        for(int word = 0, pair = 1; pair <= NUM_CARDS/2; word+=2, pair++){
            MemoCard firstCard = new MemoCard(words.get(word));
            MemoCard secondCard = new MemoCard(words.get(word+1));
            MemoCard.makePair(firstCard, secondCard);
            firstCard.addActionListener(new EndLevelListener());
            secondCard.addActionListener(new EndLevelListener());
            cardsList.add(firstCard);
            cardsList.add(secondCard);
        }
        Collections.shuffle(cardsList);
        for(MemoCard card: cardsList){
            panel.add(card);
        }
    }

    private void updateCounterLabel(){
        pairsCountingLabel.setText("Очки: "+getLearnedCards());
    }

    public void updateTimer(String timeString){
        timerLabel.setText(timeString);
    }

    private boolean endLevelCondition(MemoCard lastCard){
        for(MemoCard card: cardsList){
            if(!card.isMatched() && !card.equals(lastCard) && !card.equals(lastCard.getPair())){
                return false;
            }
        }
        return true;
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
    public ArrayList<MemoCard> getButtons(){
        return cardsList;
    }
    public static MemoCard getSelectedCard(){
        return SELECTED_CARD;
    }
    public static void setSelectedCard(MemoCard card){
        SELECTED_CARD = card;
    }
}
