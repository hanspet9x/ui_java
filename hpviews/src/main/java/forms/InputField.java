package forms;

import javax.swing.*;

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
