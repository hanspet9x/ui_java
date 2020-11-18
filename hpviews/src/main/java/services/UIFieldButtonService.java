package services;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UIFieldButtonService extends MouseAdapter {

    private final String placeholder;
    private final String placeholderColor;
    private final String color;
    private final HPGui hp;

    public UIFieldButtonService(String placeholder, String placeholderColor, String color, HPGui hp) {
        this.placeholder = placeholder;
        this.placeholderColor = placeholderColor;
        this.color = color;
        this.hp = hp;
    }

    private void exited(JTextField textField){

        if(textField.getText().trim().length() == 0){
            textField.setText(placeholder);
            textField.setForeground(hp.getColor(placeholderColor));
        }else{
            textField.setForeground(hp.getColor(color));
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);

        JTextField textField = (JTextField) e.getSource();

        if(textField.getText().equals(placeholder)){
            textField.setText("");
            textField.setForeground(hp.getColor(color));
        }

    }

    @Override
    public void mouseExited(MouseEvent e) {
        super.mouseExited(e);

        JTextField textField = (JTextField) e.getSource();

        exited(textField);

    }

/*    static class OnKeyListener extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            super.keyReleased(e);
            if(textField.getText().trim().length() == 0){
                textField.setText(placeholder);
                textField.setForeground(hp.getColor(placeholderColor));
            }else{
                textField.setForeground(hp.getColor(color));
            }
        }
    }*/
}
