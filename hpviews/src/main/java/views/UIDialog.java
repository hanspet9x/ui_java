package views;

import containers.Card;
import services.HPGui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("rawtypes")
public class UIDialog extends JDialog {

    private final HPGui hp;
    private final String title;
    private final JLabel close;
    private final Card contentWrapper;
    private final Component content;
    private final Card header;

    public UIDialog(String title, Component content, int width, int height) {
        this.hp = new HPGui();
        this.title = title;
        this.close = new JLabel("x");
        this.content = content;
        this.contentWrapper = new Card();
        header = new Card();
        hp.setAllSizes(this, width, height);
        setModalityType(ModalityType.APPLICATION_MODAL);
        build();
    }

    public UIDialog(String title, Component content) {
        this.hp = new HPGui();
        this.title = title;
        this.close = new JLabel("x");
        this.content = content;
        this.contentWrapper = new Card();
        header = new Card();
        UIDialog uiDialog = this;

        contentWrapper.addContainerListener(new ContainerAdapter() {
            @Override
            public void componentAdded(ContainerEvent e) {
                Dimension dm = e.getComponent().getPreferredSize();
                hp.setAllSizes(uiDialog, dm.width, dm.height+40);
            }
        });
        setModalityType(ModalityType.APPLICATION_MODAL);
        build();
    }


    private void build(){

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font(HPGui.FontStandard, Font.PLAIN, 18));
        titleLabel.setForeground(hp.getColor("#eeeeee"));

        close.setFont(new Font(HPGui.FontStandard, Font.PLAIN, 24));
        close.setForeground(hp.getColor("#aaaaaa"));
        close.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        close.addMouseListener(getAdapter(this));

        header.setBackground(hp.getColor("#666666"));
        header.setLayout(new BoxLayout(header, BoxLayout.LINE_AXIS));
        header.setPadding(5, 8, 5, 8);

        header.add(titleLabel);
        header.add(Box.createHorizontalGlue());
        header.add(close);

        contentWrapper.setPadding(2);
        contentWrapper.setBoxShadow(hp.getColor("#666666", .2f));
        contentWrapper.setBorder(BorderFactory.createLineBorder(hp.getColor("#666666"), 1));
        contentWrapper.add(content);

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

    private MouseAdapter getAdapter(UIDialog dialog){
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                dialog.dispose();
            }
        };
    }

}
