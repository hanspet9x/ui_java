package views;

import controllers.OnClick;
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
        this.name = new JLabel(name);
        HPGui.setLeftPadding(this.name, 5);
        this.icon.setIcon(getIcons(icon));

        add(this.icon);
        common();


    }

    public Button(String name) {
        this.name = new JLabel(name);
        this.name.setHorizontalTextPosition(JLabel.CENTER);
        common();
    }

    @Override
    public void setBackground(Color bg) {
        this.backgroundColor = bg;
    }

    @Override
    public void setForeground(Color fg) {
        this.foregroundColor = fg;
    }

    private void common(){
        this.name.setFont(HPGui.getFontText(12));
        add(this.name);
    }


    public Button setButtonBgColor(Color color){
        this.backgroundColor = color;
        return this;
    }

    public Button setButtonFgColor(Color color){
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
        setBoxShadow(3, HPGui.getColor(Color.BLACK, .1f), ShadowPosition.BOTTOM);
        LinearGradientPaint gradientPaint = new LinearGradientPaint(
                new Point2D.Float(0, 0),
                new Point2D.Float(0, getPreferredSize().height),
                HPGui.floats(.4f, 1f),
                HPGui.colors(backgroundColor, backgroundColor.darker())
        );

        name.setFont(new Font(HPGui.FontText, Font.PLAIN, fontSize));
        name.setForeground(foregroundColor);


        setLinearGradientPaint(gradientPaint);

        return this;
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


}
