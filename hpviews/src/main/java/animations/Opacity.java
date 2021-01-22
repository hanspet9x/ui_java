package animations;

import services.HPGui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.text.DecimalFormat;
import java.time.Duration;

@SuppressWarnings("rawtypes")
public class Opacity extends Transform {

    private final Opacity context = this;
    private Timer timer;
    private float value = 1f;
    HPGui hp = new HPGui();
    public Opacity(float value) {
        this.value = value;
    }

    public Opacity(Component component, float value) {
        this.value = value;
        this.component = component;
        add(component);

    }


    public float getValue() {
        return value;
    }

    public void setValue(float value){
        this.value = value;
        update();
    }

    private void update(){
        Graphics g = getGraphics();
        paintComponent(g);
        repaint();
    }

    public void animate (double value){

        animate(value,  Duration.ofMillis(5));
    }

    public void animate(double value, Duration duration){


        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                float diff = 0.1f;

                timer = new Timer((int)duration.toMillis(), e -> {

                    if(context.value == value){
                        timer.stop();

                    }else{
                        if(context.value < value){

                            context.value = toFloat(context.value + diff);

                        }else{
                            context.value =  toFloat(context.value - diff);

                        }
                    }

                   if(context.value > 1.0f) context.value = 1.0f;
                   if(context.value < 0.0f) context.value = 0.0f;

//                    component.setBackground(hp.getColor(component.getBackground(), context.value));
                    component.repaint();


                });
                timer.start();
                return null;
            }
        };

        worker.execute();
    }

    float toFloat(float num){
        return Float.parseFloat(String.format("%.1f %n", num));
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D graphics2D = (Graphics2D)g;
        graphics2D.fillRect(0, 0, getPreferredSize().width, getPreferredSize().height);
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, value));


    }

    @Override
    public void update(Graphics g) {
//        super.update(g);
    }
}
