package com.talk;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

/*
 * CustomCellRenderer
 * 
 * Класс, управляющий визуальным представлением ячеек таблицы.
 */

public class CustomCellRenderer extends DefaultTableCellRenderer {
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column)
    {
        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, 
                                                                    hasFocus, row, column);
        label.setForeground(AppPalette.DARK_BLUE);
        setToolTipText(label.getText());
        if(isSelected){
            label.setBackground(AppPalette.DARK_BLUE);
            label.setForeground(AppPalette.WHITE);
        }
        else if(row%2==0){
            label.setBackground(AppPalette.BLUE);
        } else{
            label.setBackground(AppPalette.LIGHT_BLUE);
        }
        if(column==3){
            label.setText("<html><i>"+label.getText()+"</html></i>");
        }
        return label;
    }
}
