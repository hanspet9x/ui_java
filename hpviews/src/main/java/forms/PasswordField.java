package forms;


import services.HPGui;
import views.ImageView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordField extends Form {

    private ImageView imageView;
    private String openEye = "views/forms/eye.png";
    private String closedEye = "views/forms/eye_close.png";
    private boolean passwordVisible = false;
    private JTextField field;
    private List<String> fieldValue = new ArrayList<String>();
    private String fieldHiddenChar = "*";
    private Pattern pattern = Pattern.compile("[A-Za-z 0-9.,?!@#$%^&*()_+=-]");
    private String realFieldValue = "";

    public PasswordField() {
        super();
        common();
        build();
    }

    public PasswordField(int width, int height){
        super(width, height);
        common();
        build();
    }


    private void common(){

        field = getField();
        field.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyPressed(e);

               if(pattern.matcher(String.valueOf(e.getKeyChar())).find()){
                   fieldValue.add(String.valueOf(e.getKeyChar()));
               }else if(e.getKeyCode() == 8){
                   fieldValue.remove(fieldValue.size()-1);
               }

               field.setText(getClosedFieldValue());
            }


        });

        field.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                fieldValue.clear();
                realFieldValue = "";
                field.setText("");
            }
        });

        imageView = new ImageView(openEye);
        imageView.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        imageView.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setPasswordVisible(!passwordVisible);
            }
        });
    }

    private String getClosedFieldValue() {

        return String.valueOf(fieldHiddenChar).repeat(fieldValue.size());
    }

    public void build(){
        /*Dimension dimension = getCardSize();*/
        setRightComp(imageView);
    }

    public boolean isPasswordVisible() {
        return passwordVisible;
    }

    private void setPasswordVisible(boolean passwordVisible) {
        this.passwordVisible = passwordVisible;
        imageView.swap(passwordVisible ? closedEye : openEye);

        if(passwordVisible){
            setPassword();
            field.setText(realFieldValue);
        }else{
            field.setText(getClosedFieldValue());
        }
    }

    private void setPassword(){
        realFieldValue = "";
        fieldValue.forEach(character -> realFieldValue+=character);
    }

    @Override
    public String getValue() {
        setPassword();
        return realFieldValue;
    }

    @Override
    public void setValue(String value) {
        fieldValue.clear();
        String [] values = value.split("");
        fieldValue.addAll(Arrays.asList(values));

        super.setValue(fieldHiddenChar.repeat(values.length));
    }
}
