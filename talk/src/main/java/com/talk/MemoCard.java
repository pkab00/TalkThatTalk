package com.talk;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MemoCard extends JButton {
    private MemoCard pair;
    private boolean isPaired = false;
    private boolean isMatched = false;

    public MemoCard(String text){
        super(text);
        setDefault();
    }

    public void setDefault(){
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
        isMatched = false;
    }

    public void setMatched(){
        this.setText("<html><i><s>"+this.getText()+"</s></i></html>");
        this.setBackground(MainScreen.THEME.getMainColor1());
        this.setForeground(MainScreen.THEME.getContrastTextColor());;
        this.setEnabled(false);
        isMatched = true;
        this.repaint();
    }

    public static void makePair(MemoCard first, MemoCard second){
        first.pair = second;
        second.pair = first;
        first.addActionListener(new MemoListener());
        second.addActionListener(new MemoListener());
        first.isPaired = true;
        second.isPaired = true;
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
            thatCard.setMatched();
            thisCard.setMatched();
            LearningScreen.setSelectedCard(null);
        }
    }
}