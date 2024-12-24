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
        label.setForeground(CoreScreen.THEME.getContrastColor());
        setToolTipText(label.getText());
        if(isSelected){
            label.setBackground(CoreScreen.THEME.getContrastColor());
            label.setForeground(CoreScreen.THEME.getContrastTextColor());
        }
        else if(row%2==0){
            label.setBackground(CoreScreen.THEME.getMainColor1());
        } else{
            label.setBackground(CoreScreen.THEME.getMainColor2());
        }
        if(column==3){
            label.setText("<html><i>"+label.getText()+"</html></i>");
        }
        return label;
    }
}
