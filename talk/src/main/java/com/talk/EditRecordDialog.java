package com.talk;

import java.util.ArrayList;
import javax.swing.*;

/*
 * AddRecordDialog
 * 
 * Диалоговое окно для изменения выбранной записи в таблице.
 * Расширяет класс AbstractRecordDialog.
 * В методе processData() изменяет запись, после чего обновляет таблицу.
 */

public class EditRecordDialog extends AbstractRecordDialog {
    private MainScreen scr;
    int actualIndex;
    public EditRecordDialog(MainScreen scr){
        super("Редактировать запись");
        this.scr = scr;
        JTable table = scr.getTable();
        DBTableModel tableModel = (DBTableModel)table.getModel();
        // отображаемый номер выбранного ряда - 1
        this.actualIndex = (int)table.getValueAt(table.getSelectedRow(), 0)-1;
        var row = tableModel.getRow(actualIndex);
        ArrayList<String> strlist = new ArrayList<>();
        for(int i = 1; i < row.size(); i++){
            strlist.add((String)row.get(i));
        }
        setFieldsText(strlist);
    }
    public void processData(){
        var data = getData();
        DBTableModel model = (DBTableModel)scr.getTable().getModel();
        // айди в базе данных, соответствующий номеру строки
        int actualID = model.getIDByIndex(actualIndex);
        ArrayList<Object> newData = new ArrayList<>();
        newData.add(actualID);
        newData.addAll(data);
        if(data!=null){
            new DBManager().editRecord(newData);
            scr.refreshTable();
            scr.getTable().changeSelection(actualIndex, 1, false, false);
            scr.getTable().requestFocusInWindow(); // возвращаем фокус
            JOptionPane.showMessageDialog(this, "Слово отредактировано!",
            "Успех", JOptionPane.PLAIN_MESSAGE);
            dispose();
        } else{
            JOptionPane.showMessageDialog(this, "Одно или несколько полей пусты.",
            "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }
}
