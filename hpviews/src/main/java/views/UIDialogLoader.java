package views;

import containers.*;
import services.HPGui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

@SuppressWarnings("rawtypes")
public class UIDialogLoader extends JDialog {

    private  JLabel subject = null;
    private JLabel message = null;
    private ImageView image = null;

    public UIDialogLoader(ImageView image, String subject, String message) {

        this.subject = new JLabel(subject);
        this.message = new JLabel(message);
        this.image = image;
        common();
    }

    public UIDialogLoader(ImageView image, String subject) {

        this.subject = new JLabel(subject);
        this.image = image;
        common();
    }

    public UIDialogLoader(String subject) {

        this.subject = new JLabel(subject);
        common();
    }

    public UIDialogLoader(String subject, String message) {

        this.subject = new JLabel(subject);
        this.message = new JLabel(message);
        common();
    }

    public UIDialogLoader(ImageView image) {

        this.image = image;
        common();
    }

    private void common(){

        setUndecorated(true);
        setBackground(HPGui.getColTranslucent());
        setAlwaysOnTop(true);
//        setModalityType(ModalityType.APPLICATION_MODAL);
        setSize(new Dimension(300, 200));
        setLocationRelativeTo(null);


        Card container = new Card();
        container.setPadding(20);
        container.setBorderRadius(10);
        container.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 3){
                    UIDialogLoader.this.dispose();
                }
            }
        });
        container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));

        if(image != null){
            image.setAlignmentX(Component.CENTER_ALIGNMENT);
            container.add(image);
            container.add(HPGui.getBox(0, 5));
        }

        if(subject != null){
            subject.setAlignmentX(Component.CENTER_ALIGNMENT);
            subject.setFont(HPGui.getFontStandard(18));
            subject.setForeground(HPGui.getColor("#333344"));
            container.add(subject);
            container.add(HPGui.getBox(0, 2));
        }

        if(message != null){
            message.setAlignmentX(Component.CENTER_ALIGNMENT);
            message.setFont(HPGui.getFontWriting(10));
            message.setForeground(HPGui.getColor("#666677"));
            container.add(message);
        }

        Card main = new Card(new BorderLayout());
        main.setPadding(0);
        main.setBackground(HPGui.getColor(Color.DARK_GRAY, .4f));
        main.setBorderRadius(10);
        main.add(new Center(container));
        add(main);


    }

    public void setSubject(String subject) {
        this.subject.setText(subject);
    }

    public void setMessage(String message) {
        this.message.setText(message);
    }

    public void setImage(
            String imagePath) {
        this.image.swap(imagePath);
    }

    public UIDialogLoader open(Consumer<Boolean> consumer){
        new Timer(100, e -> {
            consumer.accept(true);
            dispose();
            ((Timer)e.getSource()).stop();
        }).start();
        setVisible(true);
        return this;
    }

    public UIDialogLoader open(){
        setVisible(true);
        return this;
    }

}
