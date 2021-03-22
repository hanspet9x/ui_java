package views;

import containers.Card;
import services.HPGui;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("rawtypes")
public class NotificationIcon extends Card {

    private Card notification;
    private Color defaultColor = HPGui.getColTranslucent();
    private Color activeColor = Color.RED;

    public NotificationIcon(String imagePath) {

        common(imagePath);
    }

    public NotificationIcon(String imagePath, String description){
        setToolTipText(description);
        common(imagePath);
    }


    private void common(String imagePath){

        setLayout(null);
        setBackground(HPGui.getColTranslucent());
//        setPadding();
        int notificationSize = 5;
        int offset = 2;

        ImageView view = new ImageView(imagePath, 20, 20);
        view.setBounds(0, offset, view.getPreferredSize().width, view.getPreferredSize().height);
        int size = view.getPreferredSize().width + notificationSize;

        this.notification = new Card();
        this.notification.setBorderRadius(notificationSize);
        this.notification.setBackground(defaultColor);
        this.notification.setBounds(view.getPreferredSize().width - (notificationSize - offset), 0, notificationSize, notificationSize);
        HPGui.setAllSizes(this.notification, notificationSize, notificationSize);

        setCardSize(size, size-offset);

        add(notification);
        add(view);
    }

    public void setActive(boolean active){
        notification.setBackground(active ? activeColor : defaultColor);
        notification.repaint();
    }

    public void setActiveColor(Color color){
        this.activeColor = color;
    }

    public void setDefaultColor(Color color){
        this.defaultColor = color;
    }
}
