package containers;

import services.HPGui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class TransparentContainer extends JPanel {

    private int opacity = 0;
    private int radius = 0;
    private boolean isResized = false;
    public TransparentContainer() {
        common();
    }

    public TransparentContainer(int opacity) {
        this.opacity = opacity;
        common();
    }

    public TransparentContainer(int opacity, int radius) {
        this.opacity = opacity;
        this.radius = radius;
        common();
    }


    private void common(){
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if(!isResized){
                    setPreferredSize(e.getComponent().getPreferredSize());
                    isResized = true;
                }
            }
        });
    }
    @Override
    public void setOpaque(boolean isOpaque) {
        super.setOpaque(false);
    }

    public int getOpacity() {
        return opacity;
    }

    public void setOpacity(int opacity) {
        this.opacity = opacity;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setContainerSize(Dimension dimension){
//        setPreferredSize(dimension);
        HPGui.setAllSizes(this, dimension);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D gd = (Graphics2D)g;
        if(opacity > 0){
            gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            gd.setColor(new Color(getBackground().getRed(), getBackground().getGreen(), getBackground().getBlue(), opacity));
            gd.fillRoundRect(0, 0, getPreferredSize().width, getPreferredSize().height, radius, radius);
        }
    }
}
