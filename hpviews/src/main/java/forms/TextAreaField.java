package forms;

import services.HPGui;
import views.OverflowView;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import java.awt.*;

public class TextAreaField extends Form{


    private JTextArea textArea;

    public TextAreaField(int width, int height) {
        super(width, height);
        common();
    }

    public TextAreaField() {
        super(250, 200);
        common();
    }

    private void common(){

        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(getField().getFont());
        OverflowView overflowView = new OverflowView(textArea, getPreferredSize().width-20, getPreferredSize().height-20);

        getFieldWrapper().add(overflowView);

        setBackgroundColor(Color.BLUE);
    }


}
