package com.talk;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.util.ArrayList;

/*
 * AbstractRecordDialog
 * 
 * Абстрактный класс диалогового окна для работы с записями.
 * По умолчанию предоставляет графический интерфейс в конструкторе, геттер и сеттер для данных.
 * Абстракный метод processData() для обработки полученных данных должен быть переопределён.
 */

public abstract class AbstractRecordDialog extends JFrame {
    private static ArrayList<String> data = null;
    private JTextField field1, field2, field3;
    private JButton actionButton;
    public AbstractRecordDialog(String title){
        super();
        setIconImage(new ImageIcon("talk\\src\\main\\resources\\images\\icon.png").getImage());
        MainScreen.setLook();
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        add(createPanel(title), BorderLayout.CENTER);
        setResizable(false);
    }
    private JPanel createPanel(String title){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(AppPalette.DARK_BLUE);
        titleLabel.setBackground(AppPalette.BLUE);
        titleLabel.setFont(MainScreen.loadFont().deriveFont(25f));
        JPanel upPanel = new JPanel();
        upPanel.setBackground(AppPalette.LIGHT_BLUE);
        upPanel.add(titleLabel);
        panel.add(upPanel, BorderLayout.NORTH);

        JPanel firstRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel label1 = new JLabel();
        label1.setText("Русский: ");
        label1.setForeground(AppPalette.DARK_BLUE);
        firstRow.add(label1);
        firstRow.setBackground(AppPalette.LIGHT_BLUE);
        field1 = new JTextField();
        field1.setPreferredSize(new Dimension(350,30));
        firstRow.add(field1);

        JPanel secondRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel label2 = new JLabel();
        label2.setText("Корейский: ");
        label2.setForeground(AppPalette.DARK_BLUE);
        secondRow.add(label2);
        secondRow.setBackground(AppPalette.LIGHT_BLUE);
        field2 = new JTextField();
        field2.setPreferredSize(new Dimension(330,30));
        secondRow.add(field2);

        JPanel thirdRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel label3 = new JLabel();
        label3.setText("Транскрипция: ");
        label3.setForeground(AppPalette.DARK_BLUE);
        thirdRow.add(label3);
        thirdRow.setBackground(AppPalette.LIGHT_BLUE);
        field3 = new JTextField();
        field3.setPreferredSize(new Dimension(310,30));
        thirdRow.add(field3);

        panel.add(firstRow);
        panel.add(secondRow);
        panel.add(thirdRow);
        actionButton = new JButton("Готово");
        actionButton.setBackground(AppPalette.BLUE);
        actionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                setData();
                processData();
            }
        });
        JPanel lowPanel = new JPanel();
        lowPanel.setBackground(AppPalette.LIGHT_BLUE);
        lowPanel.add(actionButton);
        panel.add(lowPanel, BorderLayout.SOUTH);
        return panel;
    }
    public void setFieldsText(ArrayList<String> data){
        field1.setText(data.get(0));
        field2.setText(data.get(1));
        field3.setText(data.get(2));
    }
    public void setData(){
        data = new ArrayList<>();
        data.add(field1.getText());
        data.add(field2.getText());
        data.add(field3.getText());
        for(String i: data){
            if(i.isBlank() || i.isEmpty()) data = null;
        }
    }
    public static ArrayList<String> getData(){
        return data;
    }
    public abstract void processData();
}
