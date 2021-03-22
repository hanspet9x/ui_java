package views;

import animations.Rotate;
import containers.Center;
import containers.TransparentContainer;
import controllers.OnClick;
import shapes.CircleRim;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

public class ButtonLoader extends TransparentContainer {

    private boolean loading = false;
    private final Button button;
    private Color color = Color.lightGray;
    private Center loadingWrapper;
    private Rotate rotate;

    public ButtonLoader(String name, String icon) {
        this.button = new Button(name, icon);
        common();
    }

    public ButtonLoader(String name) {
        this.button = new Button(name);
        this.button.build();
        common();
    }

    private void common() {

        setLayout(null);

        loadingWrapper = new Center(200);
        loadingWrapper.setRadius(button.getRadius());
        loadingWrapper.setBackground(color);
        loadingWrapper.setBounds(0, 0, button.getPreferredSize().width-1, button.getPreferredSize().height-4);
        loadingWrapper.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        loadingWrapper.setPreferredSize(new Dimension(button.getPreferredSize().width-1, button.getPreferredSize().height-4));
        CircleRim circleRim = new CircleRim();
        circleRim.setXy(1);

        rotate = new Rotate(0, 360);
        rotate.setRate(10);
        rotate.add(circleRim);

        loadingWrapper.add(rotate);
        add(loadingWrapper);

        button.setBounds(0, 0, button.getPreferredSize().width, button.getPreferredSize().height);
        add(button);

        loadingWrapper.setVisible(false);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(button.getPreferredSize().width, button.getPreferredSize().height);
    }

    public boolean isLoading() {
        return loading;
    }


    public void setLoading(boolean loading) {

        this.loading = loading;
        loadingWrapper.setVisible(loading);
        button.setEnabled(!loading);
        button.setCursor(Cursor.getPredefinedCursor(loading? Cursor.WAIT_CURSOR: Cursor.HAND_CURSOR));

        if(loading){
            rotate.animate();
        }else{
            rotate.stop();
        }
        rotate.setRepeat(loading);
    }

    public ButtonLoader setBackgroundColor(Color color){
        this.button.setButtonBgColor(color);
        return this;
    }

    public ButtonLoader setForegroundColor(Color color){
        this.button.setButtonFgColor(color);
        return this;
    }

    public ButtonLoader build(){
        button.build();
        return this;
    }

    public void onClick(OnClick<MouseEvent> click){
        button.onClick(click);
    }

}
