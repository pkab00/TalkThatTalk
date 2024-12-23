package com.talk;

import java.util.*;
import javax.swing.table.*;
import java.sql.*;

/*
 * DBTableModel
 * 
 * Модель таблицы, поддерживающая подключение к БД.
 * Для осуществления подключения использует класс DBManager.
 * 
 * TODO: Реализовать метод, получающий истинный индекс строки вне зависимости от режима сортировки.
 */

public class DBTableModel extends AbstractTableModel {
    private ArrayList<ArrayList<Object>> data = new ArrayList<>();
    private ArrayList<Class> columnTypes = new ArrayList<>
    (Arrays.asList(Integer.class, String.class, String.class, String.class));
    private ArrayList<Integer> rowIDS = new ArrayList<>();
    private ArrayList<String> columnNames = new ArrayList<>
    (Arrays.asList("№", "Русский", "Корейский", "Транслитерация"));

    public boolean isEditable(int row, int column){
        return false;
    }
    public int getRowCount(){
        return data.size();
    }
    public int getColumnCount(){
        return columnNames.size();
    }
    public Class getColumnClass(int column){
        return columnTypes.get(column);
    }
    public String getColumnName(int column){
        return columnNames.get(column);
    }
    public Object getValueAt(int row, int column){
        return (data.get(row)).get(column);
    }
    public ArrayList<Object> getRow(int row){
        return data.get(row);
    }
    public int getIDByIndex(int index){
        return rowIDS.get(index);
    }
    public void setDataSource(ResultSet rs) throws Exception{
        data.clear();
        rowIDS.clear();

        ResultSetMetaData metaData = rs.getMetaData();
        int columnsCount = metaData.getColumnCount();
        for(int i = 0; i < columnsCount; i++){
            Class type = Class.forName(metaData.getColumnClassName(i+1));
            columnTypes.add(type);
        }

        int columnCoutnter = 1;
        while(rs.next()){
            ArrayList<Object> record = new ArrayList<>();
            record.add(columnCoutnter++);
            for(int i = 0; i < columnsCount; i++){
                if(i==0) rowIDS.add((int)rs.getObject(i+1));
                else record.add(rs.getObject(i+1));
            }
            data.add(record);
        }
        fireTableStructureChanged();
    }
}
