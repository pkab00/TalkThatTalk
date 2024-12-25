package com.talk;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.util.*;

/*
 * CustomToolBar
 * 
 * Панель инструметов приложения.
 */

public class CustomToolBar extends JToolBar {
    private final MainScreen screen;
    public CustomToolBar(MainScreen screen){
        super();
        this.screen = screen;
        List<JButton> buttonsList = List.of(
            new JButton(new AddAction()),
            new JButton(new EditAction()),
            new JButton(new DeleteAction()),
            new JButton(new SettingsAction()));

            buttonsList.stream()
            .forEach((x) -> {
                x.setBackground(CoreScreen.THEME.getMainColor1());
                x.setBorderPainted(false);
                x.setFocusable(false);
                add(x);
                addSeparator();
            });
    }
    private boolean hasSelectedString(){
        return screen.getTable().getSelectedRow()!=-1;
    }
    private class AddAction extends AbstractAction{
        public AddAction(){
            putValue(AbstractAction.SMALL_ICON, new ImageIcon(CoreScreen.IMAGES+"\\add.png"));
            putValue(AbstractAction.SHORT_DESCRIPTION, "Добавить новую запись...");
        }
        public void actionPerformed(ActionEvent e){
            AddRecordDialog dialog = new AddRecordDialog(screen);
            dialog.setVisible(true);
        }
    }
    private class EditAction extends AbstractAction{
        public EditAction(){
            putValue(AbstractAction.SMALL_ICON, new ImageIcon(CoreScreen.IMAGES+"\\edit.png"));
            putValue(AbstractAction.SHORT_DESCRIPTION, "Редактировать запись...");
        }
        public void actionPerformed(ActionEvent e){
            if(hasSelectedString()){
                EditRecordDialog dialog = new EditRecordDialog(screen);
                dialog.setVisible(true);
            } else{
                JOptionPane.showMessageDialog(screen, "Сначала выберете строку в таблице.",
                "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private class DeleteAction extends AbstractAction{
        public DeleteAction(){
            putValue(AbstractAction.SMALL_ICON, new ImageIcon(CoreScreen.IMAGES+"\\delete.png"));
            putValue(AbstractAction.SHORT_DESCRIPTION, "Удалить запись...");
        }
        public void actionPerformed(ActionEvent e){
            if(hasSelectedString()){
                String[] options = {"Да", "Нет"};
                int res = JOptionPane.showOptionDialog(screen, "Удалить выбранное слово?",
                 "Подтверждение", 0, JOptionPane.QUESTION_MESSAGE, null, options, null);
                if(res==0){
                    JTable table = screen.getTable();
                    DBTableModel model = (DBTableModel)table.getModel();
                    int actualIndex = (int)table.getValueAt(table.getSelectedRow(), 0)-1;
                    int actualID = model.getIDByIndex(actualIndex);
                    new DBManager().deleteRecord(actualID);
                    screen.refreshTable();
                    JOptionPane.showMessageDialog(screen, "Слово удалено!",
                    "Успех", JOptionPane.PLAIN_MESSAGE);
                }
            } else{
                JOptionPane.showMessageDialog(screen, "Сначала выберете строку в таблице.",
                "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private class SettingsAction extends AbstractAction{
        public SettingsAction(){
            putValue(AbstractAction.SMALL_ICON, new ImageIcon(CoreScreen.IMAGES+"//settings.png"));
            putValue(AbstractAction.SHORT_DESCRIPTION, "Открыть меню настроек...");
        }
        public void actionPerformed(ActionEvent e){

        }
    }
}
