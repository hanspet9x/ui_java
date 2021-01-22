package shapes;

import containers.TransparentContainer;
import services.HPGui;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

public class CircleRim extends TransparentContainer {

    private int diameter = 17;
    private int xy = 2;
    private int strokeWidth = 5;
    private Color color = Color.lightGray;
    private Color secondaryColor = null;
    private Graphics2D graphics2D = null;
    private BufferedImage image;

    public CircleRim(int diameter, int strokeWidth) {
        this.diameter = diameter;
        this.strokeWidth = strokeWidth;
    }

    public CircleRim(int diameter, int strokeWidth, Color color) {
        this.diameter = diameter;
        this.strokeWidth = strokeWidth;
        this.color = color;
    }

    public CircleRim() {
    }



    public int getDiameter() {
        return diameter;
    }

    public void setDiameter(int diameter) {
        this.diameter = diameter;
    }

    public int getXy() {
        return xy;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getSecondaryColor() {
        return secondaryColor;
    }

    public void setSecondaryColor(Color secondaryColor) {
        this.secondaryColor = secondaryColor;
    }

    public void setXy(int xy) {
        this.xy = xy;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(diameter+xy*2, diameter +xy*2);
    }

    @Override
    protected void paintComponent(Graphics g) {


        if(image == null){
            graphics2D = (Graphics2D)g;
            paint();
        }else{

            g.drawImage(image, 0, 0, null);
        }

    }

    private void paint(){
        image = new BufferedImage(diameter+xy*2, diameter +xy*2, BufferedImage.TYPE_INT_ARGB);

        Graphics2D gImage = image.createGraphics();
        gImage.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        BasicStroke stroke = new BasicStroke(strokeWidth);

        LinearGradientPaint linearGradientPaint = new LinearGradientPaint(
                new Point2D.Float(0, 0),
                new Point2D.Float(diameter, 0),
                new float[]{.5f, 1f},
                new Color[]{color, secondaryColor == null? color.darker() : secondaryColor}
        );
        RoundRectangle2D.Float rim = new RoundRectangle2D.Float(xy, xy, diameter, diameter, diameter, diameter);

        gImage.setPaint(linearGradientPaint);
        gImage.setStroke(stroke);
        gImage.draw(rim);

        graphics2D.drawImage(image, 0, 0, null);
        gImage.dispose();
    }

}
