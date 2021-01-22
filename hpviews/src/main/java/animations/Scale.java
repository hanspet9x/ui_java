package animations;

import services.HPGui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.text.DecimalFormat;
import java.time.Duration;

@SuppressWarnings("rawtypes")
public class Scale extends Transform {


    private final Scale context = this;
    private Timer timer;

    private double dx = 0;
    private double dy = 0;
    private double rate = 0.1;

    DecimalFormat format = new DecimalFormat("#.#");

    public Scale(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public Scale(Component component, double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
        this.component = component;
        add(component);
    }

    public Scale(Component component, double dx, double dy, double rate) {
        this.dx = dx;
        this.dy = dy;
        this.rate = rate;
        this.component = component;
        add(component);
    }

    public double getDx() {
        return dx;
    }

    public double getDy() {
        return dy;
    }

    public void setDistance(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
        update();
    }

    public void setDX(double dx) {
        this.dx = dx;
        update();
    }
    public void setDY(double dy) {
        this.dy = dy;
        update();
    }

    private void update(){

        repaint();
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public void animate (double dx, double dy){

        animate(dx, dy, Duration.ofMillis(1));
    }

    public void animate(double dx, double dy, Duration duration){


        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {

                Graphics2D graphics2D = (Graphics2D) getGraphics();
                timer = new Timer((int)duration.toMillis(), e -> {

                    if(context.dx == dx && context.dy == dy){
                        timer.stop();

                    }else{
                        if(context.dx < dx){
                            context.dx = getDouble(context.dx + rate);
                        }else{
                            context.dx = getDouble(context.dx - rate);
                        }

                        if(context.dy < dy){
                            context.dy = getDouble(context.dy + rate);
                        }else{
                            context.dy = getDouble(context.dy - rate);

                        }

                        HPGui.log(context.dx, context.dy);


                    }

                    paintComponent(graphics2D);
                    repaint();

                });
                timer.start();
                return null;
            }
        };

        worker.execute();
    }

    @Override
    public Dimension getPreferredSize() {
        return super.getPreferredSize();
    }

    private Double getDouble(double num){
        return Double.parseDouble(format.format(num));
    }
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D)g;
        AffineTransform transform = graphics2D.getTransform();
        transform.scale(dx, dy);
        graphics2D.setTransform(transform);
    }
}
