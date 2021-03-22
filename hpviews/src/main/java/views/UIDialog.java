package views;

import containers.Card;
import containers.Card2;
import containers.Center;
import containers.Flex;
import model.FlexAlignment;
import model.FlexDirection;
import services.HPGui;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class UIDialog extends JDialog {


    private JFrame frame = null;
    private JButton cancel, send;
    private Card2 container;
    private String actionOneText = "Cancel", actionTwoText = "Send";
    private Color actionOneColor = Color.WHITE, actionTwoColor = Color.LIGHT_GRAY;
    private final int padding = 10;
    private Consumer<Boolean> consumer = null;

    public UIDialog(JFrame frame, Component component) {

        this.frame = frame;
        common(component);

    }

    public UIDialog(Component component){
        common(component);
    }

    private void common(Component component){
        setUndecorated(true);
        setBackground(HPGui.getColor(Color.DARK_GRAY, .4f));
        if(frame != null){
            setSize(frame.getSize());
            setLocation(frame.getLocation());
        }else{
            setSize(HPGui.getScreenSize());
        }
        setModalityType(ModalityType.APPLICATION_MODAL);

        container = new Card2<>();
        container.setPadding(padding);
        container.setLayout(new BorderLayout());
        container.add(component, BorderLayout.CENTER);
        container.setBorderRadius(padding);
        container.setBackground(HPGui.getColor("#fcfcfc"));
    }

    public UIDialog setCallbacks(Consumer<Boolean> consumer){
        this.consumer = consumer;
        return this;
    }

    public UIDialog setActionOneText(String actionOneText) {
        this.actionOneText = actionOneText;
        return this;
    }

    public UIDialog setActionTwoText(String actionTwoText) {
        this.actionTwoText = actionTwoText;
        return this;
    }

    public UIDialog setActionOneColor(Color actionOneColor) {
        this.actionOneColor = actionOneColor;
        return this;
    }

    public UIDialog setActionTwoColor(Color actionTwoColor) {
        this.actionTwoColor = actionTwoColor;
        return this;
    }


    private void build(){

        send = new JButton(actionTwoText);
        send.setBackground(actionTwoColor);
        send.setFont(new Font(HPGui.FontHead, Font.PLAIN, 13));
        send.addActionListener( e -> {
            dispose();
            if(consumer != null)consumer.accept(true);
        });

        cancel = new JButton(actionOneText);
        cancel.setBackground(actionOneColor);
        cancel.setFont(new Font(HPGui.FontHead, Font.PLAIN, 13));
        cancel.addActionListener(e -> {
            dispose();
            if(consumer != null) consumer.accept(false);
        });

        Card card = new Card();
        card.setPadding(0);
        card.setPaddingTop(padding);
        card.setLayout(new BoxLayout(card, BoxLayout.LINE_AXIS));
        card.add(Box.createHorizontalGlue());
        card.add(cancel);
        card.add(Box.createRigidArea(new Dimension(5, 0)));
        card.add(send);

        container.add(card, BorderLayout.PAGE_END);

        Center center = new Center(container);
        center.setContainerSize(this.getWidth(), this.getHeight());
        add(center);

    }

    public UIDialog open(){
        build();
        setVisible(true);
        return this;
    }
}
