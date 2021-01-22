package views;

import containers.Card;
import services.HPGui;
import services.UIFieldButtonService;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("rawtypes")
public class UIFieldButton extends Card {
    private final JTextField field;
    private final ImageIcon icon;
    private final int height;
    private final int width;
    private final String placeholder;
    private String  placeholderColor = "#cccccc";
    private String  color = "#666666";
    private final HPGui hp;
    private JLabel iconLabel;

    public UIFieldButton(String iconPath, String placeholder, int width, int height) {
        this.hp = new HPGui();
        this.field = new JTextField();
        this.icon = hp.getImageIcon(iconPath, 20, 20);
        this.width = width;
        this.height = height;
        this.placeholder = placeholder;

        build();
    }

    public UIFieldButton(String iconPath, String placeholder, int width, int height, String placeholderColor, String textColor) {
        this.hp = new HPGui();
        this.field = new JTextField();
        this.icon = hp.getImageIcon(iconPath, 20, 20);
        this.width = width;
        this.height = height;
        this.placeholder = placeholder;
        this.placeholderColor = placeholderColor;
        this.color = textColor;
        build();
    }

    private void build(){
        field.setOpaque(false);
        field.setBackground(hp.getColTranslucent());
        field.setText(placeholder);
        field.setFont(new Font(HPGui.FontStandard, Font.PLAIN, 16));
        field.setForeground(hp.getColor(placeholderColor));
        field.setBorder(BorderFactory.createEmptyBorder());
        field.addMouseListener(new UIFieldButtonService(placeholder, placeholderColor, color, hp));
        hp.setAllSizes(field, width-40, height);

        iconLabel = new JLabel(icon);
        iconLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        hp.setAllSizes(this, width, height);
        setPadding(0);
        setPaddingLeft(10);
        setLayout(new SpringLayout());
        add(field);
        add(iconLabel);
        HPGui.Springer.makeCompactGrid(this, 1, 2, 0, 0, 2, 0);
    }

    public String getText(){
        if(field.getText().equals(placeholder)){
            return "";
        }
        return field.getText();
    }

    public void setText(String text){
        field.setText(text);
    }

    public JLabel getButton(){
        return iconLabel;
    }

}
