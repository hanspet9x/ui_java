package animations;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

@SuppressWarnings("rawtypes")
public class Rotate extends Transform {

    private double fromDegree;
    private double toDegree;
    private double initialDegree;
    private int rate = 1;
    private boolean repeat = false;
    private Timer timer;

    public Rotate(double fromDegree) {
        this.fromDegree = fromDegree;
    }

    public Rotate(double fromDegree, double toDegree) {
        this.fromDegree = fromDegree;
        this.toDegree = toDegree;
    }

    public Rotate(Component component, double fromDegree) {
        this.fromDegree = fromDegree;
        this.component = component;
        add(component);
    }

    public Rotate(Component component) {
        this.component = component;
        add(component);

    }

    public Rotate(Component component, int rate) {
        this.component = component;
        this.rate = rate;
        add(component);

    }

    public double getFromDegree() {
        return fromDegree;
    }


    public void setDegrees(double fromDegree, double toDegree) {
        this.fromDegree = fromDegree;
        this.toDegree = toDegree;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    public void animate(double fromDegree, double toDegree) {
        this.fromDegree = fromDegree;
        this.toDegree = toDegree;
        animate();
    }

    public void stop(){
        repeat = false;
    }

    public void animate(){
        initialDegree = fromDegree;
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {

                timer = new Timer(1, e -> {
                    if(Rotate.this.fromDegree == toDegree){

                        if(repeat){
                            fromDegree = initialDegree;
                        }else{
                            timer.stop();
                        }

                    }else if(Rotate.this.fromDegree > toDegree){

                        Rotate.this.fromDegree -= rate;

                    }else{

                        Rotate.this.fromDegree +=rate;
                    }
                    repaint();
                });
                timer.start();
                return null;
            }
        };

        worker.execute();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D)g;

        AffineTransform transform = graphics2D.getTransform();

        transform.rotate(Math.toRadians(fromDegree), component.getX()+(component.getPreferredSize().width/2),
                component.getY()+(component.getPreferredSize().height/2));
        graphics2D.setTransform(transform);

    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
