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

public class MainScreen extends JFrame{
    private final JTable mainTable;

    public MainScreen(){
        super("TalkThatTalk");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000,800);
        setVisible(true);
        setIconImage(new ImageIcon("talk\\src\\main\\resources\\images\\icon.png").getImage());
        setLayout(new BorderLayout());
        mainTable = createTable();
        add(new JScrollPane(mainTable), BorderLayout.CENTER);
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        menuBar.add(createMenu());
        CustomToolBar toolBar = new CustomToolBar(this);
        toolBar.setFloatable(false);
        toolBar.setBackground(AppPalette.LIGHT_BLUE);
        add(toolBar, BorderLayout.NORTH);
    }

    public static Font loadFont(){
        try{
            Font font = Font.createFont(Font.TRUETYPE_FONT,
            new File("talk\\src\\main\\resources\\NotoSansKR-Medium.ttf"));
            return font;
        } catch(FontFormatException | IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public static void setLook(){
        Font font = loadFont();
        float fontSize = 12f;
        UIManager.put("ToolTip.font", font.deriveFont(fontSize));
        UIManager.put("Label.font", font.deriveFont(fontSize));
        UIManager.put("Menu.font", font.deriveFont(fontSize));
        UIManager.put("MenuItem.font", font.deriveFont(fontSize));
        UIManager.put("TextField.font", font.deriveFont(fontSize));
        UIManager.put("OptionPane.buttonFont", font.deriveFont(fontSize));
        UIManager.put("OptionPane.messageFont", font.deriveFont(fontSize));
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
            Font font = loadFont();
            setLook();
            table.setFont(font.deriveFont(20f));
            table.getTableHeader().setFont(font.deriveFont(15f));
            table.setRowHeight(30);
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            table.setAutoCreateRowSorter(true);
            table.getTableHeader().setReorderingAllowed(false);
            table.getTableHeader().setResizingAllowed(false);
            table.getTableHeader().setBackground(AppPalette.DARK_BLUE);
            table.getTableHeader().setForeground(AppPalette.WHITE);

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
