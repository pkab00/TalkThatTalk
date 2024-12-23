package com.talk;

import javax.swing.JOptionPane;

/*
 * AddRecordDialog
 * 
 * Диалоговое окно для добавления новой записи в таблицу.
 * Расширяет класс AbstractRecordDialog.
 * В методе processData() создёт новую запись, после чего обновляет таблицу.
 */

public class AddRecordDialog extends AbstractRecordDialog {
    private MainScreen scr;
    public AddRecordDialog(MainScreen scr){
        super("Новая запись");
        this.scr = scr;
    }
    // Реализация абстрактного метода для обработки данных после сохранения
    public void processData(){
        var data = getData();
        if(data!=null){
            new DBManager().addNewRecord(data);
            scr.refreshTable();
            scr.getTable().changeSelection(scr.getTable().getRowCount()-1, 1, false, false);
            scr.getTable().requestFocusInWindow();
            JOptionPane.showMessageDialog(this, "Новое слово добавлено в таблицу!",
            "Успех", JOptionPane.PLAIN_MESSAGE);
            dispose();
        }
        else{
            JOptionPane.showMessageDialog(this, "Недостаточно данных для сохранения.",
            "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }
}
