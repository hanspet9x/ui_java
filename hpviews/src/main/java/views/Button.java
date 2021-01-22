package views;

import services.HPGui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import containers.*;

public class Button extends Card2 {

    private String defaultIcon = null;
    private String activeIcon;
    private String text;
    private final int iconSize = 20;
    private final JLabel icon = new JLabel();
    private final JLabel name;
    private int fontSize = 18, radius = 9;
    private Color backgroundColor = Color.white;
    private Color foregroundColor = Color.darkGray;


    private boolean enabled = true;

    HPGui hp = new HPGui();

    public Button(String name, String icon) {
        this.defaultIcon = icon;
        this.name = new JLabel("  "+name);
        this.icon.setIcon(getIcons(icon));

        add(this.icon);
        add(this.name);
    }

    public Button(String name) {
        this.name = new JLabel(name);
        this.name.setHorizontalTextPosition(JLabel.CENTER);
        add(this.name);
    }



    public Button setBackgroundColor(Color color){
        this.backgroundColor = color;
        return this;
    }

    public Button setForegroundColor(Color color){
        this.foregroundColor = color;
        return this;
    }

    public Button build(){

        setPaddingVertical(8);
        setPaddingHorizontal(10);

      /*  int bW = this.defaultIcon != null ? this.name.getPreferredSize().width+6+iconSize : this.name.getPreferredSize().width+2;
        setCardSize(bW, this.name.getPreferredSize().height);*/
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        setBorderRadius(radius);
        setBoxShadow(3, hp.getColor(Color.BLACK, .1f), ShadowPosition.BOTTOM);
        LinearGradientPaint gradientPaint = new LinearGradientPaint(
                new Point2D.Float(0, 0),
                new Point2D.Float(0, getPreferredSize().height),
                hp.floats(.4f, 1f),
                hp.colors(backgroundColor, backgroundColor.darker())
        );

        name.setFont(new Font(HPGui.FontText, Font.PLAIN, fontSize));
        name.setForeground(foregroundColor);


        setLinearGradientPaint(gradientPaint);

        addListeners();
        return this;
    }

    private void addListeners() {

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
               if(onButtonClicked != null && enabled){
                   onButtonClicked.click();
               }
            }
        });


    }


    public int getRadius() {
        return radius;
    }

    private ImageIcon getIcons (String path){
        return hp.getImageIcon(path, iconSize, iconSize);
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        setCursor(Cursor.getPredefinedCursor(enabled? Cursor.HAND_CURSOR: Cursor.WAIT_CURSOR));
    }

    @FunctionalInterface
    public interface OnButtonClicked{
        void click();
    }

    OnButtonClicked onButtonClicked = null;

    public void onClick(OnButtonClicked onButtonClicked){
        this.onButtonClicked = onButtonClicked;

    }
}
