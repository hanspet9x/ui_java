package containers;

import services.HPGui;

import javax.swing.*;
import java.awt.*;

public class Placeholder extends Center{

    private boolean showContent;
    private Component content;

    public Placeholder(String text) {

        JLabel info = new JLabel(text);
        info.setFont(new Font(HPGui.FontText, Font.PLAIN, 20));
        info.setForeground(Color.LIGHT_GRAY);
        add(info);
    }


    public Component getContent() {
        return content;
    }

    public void setContent(Component content) {
        this.content = content;
        removeAll();
        add(content);
        revalidate();
        repaint();
    }
}
