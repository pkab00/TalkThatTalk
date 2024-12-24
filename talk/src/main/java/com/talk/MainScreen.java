package com.talk;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.*;
import java.io.*;
import com.talk.DBManager.ORDER_BY;

/*
 * MainScreen
 * 
 * Основное окно приложения. 
 * Вклюаяет в себя строку меню, панель инструментов и таблицу слов.
 */

public class MainScreen extends CoreScreen{
    private final JTable mainTable;

    public MainScreen(){
        setTitle("TalkThatTalk");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000,800);
        setVisible(true);
        setLayout(new BorderLayout());
        mainTable = createTable();
        add(new JScrollPane(mainTable), BorderLayout.CENTER);
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        menuBar.add(createMenu());
        CustomToolBar toolBar = new CustomToolBar(this);
        toolBar.setFloatable(false);
        toolBar.setBackground(THEME.getMainColor2());
        add(toolBar, BorderLayout.NORTH);
    }

    public void refreshTable(){
        try{
            DBTableModel model = (DBTableModel)mainTable.getModel();
            model.setDataSource(new DBManager().getBase(ORDER_BY.ID));
            CustomCellRenderer renderer = new CustomCellRenderer();
            renderer.setHorizontalAlignment(JLabel.CENTER);
            for(int i = 0; i < model.getColumnCount(); i++){
                mainTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
            }
            mainTable.repaint();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private JTable createTable(){
        try{
            DBTableModel model = new DBTableModel();
            model.setDataSource(new DBManager().getBase(ORDER_BY.ID));
            JTable table = new JTable(model);
            setLook();
            table.setFont(FONT.deriveFont(20f));
            table.getTableHeader().setFont(FONT.deriveFont(15f));
            table.setRowHeight(30);
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            table.setAutoCreateRowSorter(true);
            table.getTableHeader().setReorderingAllowed(false);
            table.getTableHeader().setResizingAllowed(false);
            table.getTableHeader().setBackground(THEME.getContrastColor());
            table.getTableHeader().setForeground(THEME.getContrastTextColor());

            DefaultTableCellRenderer renderer = new CustomCellRenderer();
            renderer.setHorizontalAlignment(JLabel.CENTER);
            for(int i = 0; i < table.getColumnCount(); i++){
                table.getColumnModel().getColumn(i).setCellRenderer(renderer);
                if(i==0) table.getColumnModel().getColumn(i).setPreferredWidth(1);
            }
            return table;
        } catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private JMenu createMenu(){
        JMenu menu = new JMenu("Меню");
        JMenuItem about = new JMenuItem("О программе...");
        about.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                JOptionPane.showMessageDialog(MainScreen.this,
                "<html>"+
                "<b>Приложение представляет из себя русско-корейский разговорник с функцией "+
                "добавления, редактирования и удаления записей.</b><br><br>"+
                "<i>Чтобы отсортировать слова по возрастанию/убыванию, кликните по заголовку столбца левой "
                +"кнопкой мыши."
                +"</html>", 
                about.getText(), JOptionPane.PLAIN_MESSAGE);
            }
        });
        JMenuItem dev = new JMenuItem("О разработчике...");
        dev.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                JOptionPane.showMessageDialog(MainScreen.this, "dev. by @vbshkn, 2024",
                dev.getText(), JOptionPane.PLAIN_MESSAGE);
            }
        });
        menu.add(about);
        menu.add(dev);
        return menu;
    }
    public JTable getTable(){
        return mainTable;
    }
}
