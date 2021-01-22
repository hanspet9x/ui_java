package animations;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.time.Duration;

@SuppressWarnings("rawtypes")
public class Translate extends Transform {

    private JPanel panel;
    private final Translate context = this;
    private Timer timer;
    private double dx = 0;
    private double dy = 0;

    public Translate(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public Translate(Component component, double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
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
        Graphics g = getGraphics();
        paintComponent(g);
        repaint();
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
                            ++context.dx;
                        }else{
                            --context.dx;
                        }

                        if(context.dy < dy){
                            ++context.dy;
                        }else{
                            --context.dy;
                        }
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
    protected void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D)g;
        graphics2D.setColor(Color.BLUE);
        graphics2D.fillRect(0, 0, getPreferredSize().width, getPreferredSize().height);
        AffineTransform transform = graphics2D.getTransform();
        transform.translate(dx, dy);
        graphics2D.setTransform(transform);


//        graphics2D.dispose();
    }
}
