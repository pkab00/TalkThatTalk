package com.talk;

import java.awt.*;
import javax.swing.*;

import com.talk.THEMES.ThemeManager;

public class SettingsScreen extends CoreScreen {
    private JComboBox<String> themeComboBox;
    
    public SettingsScreen(){
        setTitle("Настройки");
        setSize(800, 800);
        setResizable(false);
        setLayout(new BorderLayout());
        add(createTitlePanel(), BorderLayout.NORTH);
        add(createSettingsArea(), BorderLayout.CENTER);
        add(createSavePanel(), BorderLayout.SOUTH);
    }

    public JPanel createTitlePanel(){
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(CoreScreen.THEME.getMainColor2());

        JLabel titleLabel = new JLabel("Настройки");
        titleLabel.setFont(CoreScreen.FONT.deriveFont(35f));
        titleLabel.setForeground(CoreScreen.THEME.getMainTextColor());
        titlePanel.add(titleLabel);

        return titlePanel;
    }

    public JPanel createSettingsArea(){
        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));
        settingsPanel.setBackground(CoreScreen.THEME.getMainColor2());

        JLabel themesTitle = new JLabel("Настройки темы");
        themesTitle.setFont(CoreScreen.FONT.deriveFont(25f));
        themesTitle.setForeground(CoreScreen.THEME.getMainTextColor());
        settingsPanel.add(themesTitle);
        JLabel warningLabel = new JLabel("Чтобы изменения вступили в силу, перезапустите приложение");
        warningLabel.setFont(CoreScreen.FONT.deriveFont(15f));
        warningLabel.setForeground(CoreScreen.THEME.getMainTextColor());
        settingsPanel.add(warningLabel);

        settingsPanel.add(Box.createVerticalStrut(5));
        settingsPanel.add(createThemeSettingsArea());

        return settingsPanel;
    }

    public JPanel createThemeSettingsArea(){
        JPanel themePickerPanel = new JPanel();
        themePickerPanel.setLayout(new BoxLayout(themePickerPanel, BoxLayout.X_AXIS));
        themePickerPanel.setBackground(CoreScreen.THEME.getMainColor2());
        JLabel chooseThemeTitle = new JLabel("  Выберете тему:    ");
        chooseThemeTitle.setFont(CoreScreen.FONT.deriveFont(15f));
        chooseThemeTitle.setForeground(CoreScreen.THEME.getMainTextColor());
        themePickerPanel.add(chooseThemeTitle);

        themeComboBox = new JComboBox<>(ThemeManager.getThemesList());
        themeComboBox.setSelectedItem(ThemeManager.readSettingsFile());
        themeComboBox.setMaximumSize(new Dimension(200, 30));
        themePickerPanel.add(themeComboBox);
        
        return themePickerPanel;
    }

    public JPanel createSavePanel(){
        JPanel savePanel = new JPanel();
        savePanel.setBackground(CoreScreen.THEME.getMainColor2());

        JButton saveButton = new JButton("Сохранить изменеия");
        saveButton.setBackground(CoreScreen.THEME.getMainColor1());
        saveButton.setForeground(CoreScreen.THEME.getMainTextColor());
        saveButton.setSize(new Dimension(500, 50));
        saveButton.addActionListener((e) -> {
            ThemeManager.changeSettingsFile((String)themeComboBox.getSelectedItem());
            this.dispose();
        });
        savePanel.add(saveButton);

        return savePanel;
    }
}
