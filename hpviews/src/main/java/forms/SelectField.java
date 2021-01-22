package forms;

import animations.Rotate;
import animations.Scale;
import containers.Flex;
import model.FlexAlignment;
import model.FlexDirection;
import services.HPGui;
import views.ImageView;
import views.OverflowView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SelectField<T> extends Form{

    private T [] values = null;
    private T value = null;
    private Rotate rotatedImageView;
    private Flex flex;
    private boolean isShowingValues = false;
    private OverflowView valuesWrapper;
    private Scale scale;
    private JDialog dialog;
    private int valueHeight = 40;

    public SelectField(T ...values) {
        this.values = values;
        common();
    }

    public SelectField() {
        common();
    }

    private void common(){
        rotatedImageView = new Rotate(new ImageView("views/forms/expand.png"), 10);
        setRightComp(rotatedImageView);

        rotatedImageView.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                onClickCaret();
            }
        });

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                    createValues();
            }
        });

        flex = new Flex(FlexDirection.COLUMN, FlexAlignment.CENTER);
        getField().setEnabled(false);
        if(values != null){

            for (int i = 0; i < values.length; i++) {

                JLabel item = new JLabel((String) values[i]);
                item.setFont(new Font(HPGui.FontText, Font.PLAIN, 20));
                item.setOpaque(true);
                item.setBackground( i % 2 == 0 ? Color.lightGray : Color.white);
//                item.setPreferredSize(new Dimension(super.getPreferredSize().width, valueHeight));
                hp.setAllSizes(item, super.getPreferredSize().width, valueHeight);
                item.setHorizontalAlignment(JLabel.CENTER);
                item.setVerticalAlignment(JLabel.CENTER);

                hp.setBottomPadding(item, 5);
                flex.add(item);
            }

        }

    }

    private void createValues() {

        int height  = values.length > 3 ? valueHeight * 3 : values.length * valueHeight;

        valuesWrapper = new OverflowView(flex, getPreferredSize().width, height);

        dialog = new JDialog();
        dialog.setUndecorated(true);
        dialog.add(valuesWrapper);
        dialog.setAlwaysOnTop(true);
        dialog.setSize(getPreferredSize().width, height);
        dialog.setLocation(getLocationOnScreen().x, getLocationOnScreen().y + getPreferredSize().height);

    }

    private void onClickCaret() {
        if(isShowingValues){
            rotatedImageView.animate(-180, 0);
            dialog.setVisible(false);
            isShowingValues = false;
        }else {
            rotatedImageView.animate(0, -180);
            dialog.setVisible(true);
            isShowingValues = true;
        }
    }



}
