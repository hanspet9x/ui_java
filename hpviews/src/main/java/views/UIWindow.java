package views;

import containers.Card;
import containers.Center;
import containers.TransparentContainer;
import services.HPGui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("rawtypes")
public class UIWindow extends JDialog {
    
    private  String title;
    private JLabel close;
    private  Component content;
    private  Card header;
    private boolean addClose = true;

    public UIWindow(Component content) {
        common("Window", content);
    }

    public UIWindow(Component content, boolean addClose) {
        this.addClose = addClose;
        common("Window", content);
    }

    public UIWindow(String title, Component content) {

        common(title, content);
    }

    public UIWindow(String title, Component content, boolean addClose) {
        this.addClose = addClose;
        common(title, content);
    }

    private void common(String title, Component content){
        this.title = title;
        this.close = new JLabel("x");
        this.content = content;
        header = new Card();

        build();
    }

    private void build(){

        setSize(HPGui.getScreenSize());
        setModalityType(ModalityType.APPLICATION_MODAL);
        setUndecorated(true);
        setBackground(HPGui.getColor(Color.BLACK, .8f));
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font(HPGui.FontStandard, Font.PLAIN, 18));
        titleLabel.setForeground(HPGui.getColor("#eeeeee"));

        close.setFont(new Font(HPGui.FontStandard, Font.PLAIN, 24));
        close.setForeground(HPGui.getColor("#aaaaaa"));
        close.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        close.addMouseListener(getAdapter(this));

        header.setBackground(HPGui.getColor("#666666"));
        header.setLayout(new BoxLayout(header, BoxLayout.LINE_AXIS));
        header.setPadding(5, 8, 5, 8);

        header.add(titleLabel);
        header.add(Box.createHorizontalGlue());
        header.add(close);

        Center contentWrapper;
        if(addClose){
            JLabel close = new JLabel(HPGui.getImageIcon("/icons/red_close.png"));
            close.setCursor(HPGui.getHandCursor());
            close.addMouseListener(getAdapter(this));
            HPGui.setPadding(close, 10);

            TransparentContainer transparentContainer = new TransparentContainer();
            transparentContainer.setLayout(new BorderLayout());

            transparentContainer.add(close, BorderLayout.PAGE_START);
            transparentContainer.add(content, BorderLayout.CENTER);
            contentWrapper = new Center(transparentContainer);
        }else{
            contentWrapper = new Center(content);
        }

        contentWrapper.setBorder(BorderFactory.createLineBorder(HPGui.getColor("#666666"), 1));
        contentWrapper.setContainerSize(getWidth(), getHeight() - 80);
        add(header, BorderLayout.PAGE_START);
        add(contentWrapper, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setLocationRelativeTo(null);
//        setAlwaysOnTop(true);
    }

    public void open(){
        setVisible(true);
    }

    private MouseAdapter getAdapter(UIWindow dialog){
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                dialog.dispose();
            }
        };
    }

}
