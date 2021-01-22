package forms;

import views.ImageView;

import javax.swing.*;
import java.awt.*;

public class InputField extends Form{


    private JTextField field;

    public InputField(int width, int height) {
        super(width, height);
        common();
        build();
    }

    public InputField() {

        common();
        build();
    }

    private void common(){

    }

    private void build(){
       field = getField();

    }


}
