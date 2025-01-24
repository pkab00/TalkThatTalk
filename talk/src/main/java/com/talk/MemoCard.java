package com.talk;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MemoCard extends JButton {
    private String text;
    private MemoCard pair;
    private boolean isPaired = false;
    private boolean isMatched = false;

    public MemoCard(String text){
        this.text = text;
        setDefault();
    }

    public void setDefault(){
        this.setText(text);
        this.setFont(MainScreen.loadFont().deriveFont(30f));
        this.setBackground(MainScreen.THEME.getMainColor2());
        this.setForeground(MainScreen.THEME.getMainTextColor());
        isMatched = false;
        isPaired = false;
    }

    public void setSelected(){
        this.setBackground(MainScreen.THEME.getMainColor1());
        this.setForeground(MainScreen.THEME.getContrastTextColor());
        this.setBorderPainted(false);
        this.setEnabled(false);
        isMatched = false;
    }

    public void setMatched(){
        // TODO: SOMETHING'S WRONG WITH TEXT DISPLAYING
        //this.setText(String.format("<html><i><s>%s</s></i>", text));
        this.setBackground(MainScreen.THEME.getMainColor1());
        this.setForeground(MainScreen.THEME.getContrastTextColor());
        this.setBorderPainted(false);
        this.setEnabled(false);
        isMatched = true;
    }

    public static void makePair(MemoCard first, MemoCard second){
        first.pair = second;
        second.pair = first;
        first.addActionListener(new MemoListener());
        second.addActionListener(new MemoListener());
        first.isPaired = true;
        second.isPaired = true;
    }

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public MemoCard getPair() {
        return pair;
    }
    public boolean isPaired(){
        return isPaired;
    }
    public boolean isMatched() {
        return isMatched;
    }
}

class MemoListener implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent e){
        MemoCard thisCard = (MemoCard)e.getSource();
        MemoCard thatCard = LearningScreen.getSelectedCard();
        if(thatCard == null){
            LearningScreen.setSelectedCard(thisCard);
            thisCard.setSelected();
        }
        else if(thatCard.equals(thisCard.getPair())){
            thisCard.setMatched();
            thatCard.setMatched();
            LearningScreen.setSelectedCard(null);
        }
    }
}
