package views;

import javax.swing.*;
import java.awt.*;

public class Loader extends Card {

    private Card loading;
    private int x = 0;
    private String dir = "r";
    private Timer timer;


    public Loader() {
        super(new FlowLayout(FlowLayout.LEADING));
        setBounds(0, 0, 200, 10);
        setPadding(0);
        setBackground(Color.DARK_GRAY);
        setBorderRadius(4);

        loader();
    }

    private void loader(){
        loading = new Card();

        loading.setBackground(Color.RED);
        setPadding(0);
        loading.setPreferredSize(new Dimension(50, 2));
        add(loading);
        animate();
        setVisible(false);
    }

    private void animate(){

        timer = new Timer(20, (e) -> {
            condition();
            if(dir.equals("l")){
                x -=5;
            }else{
                x +=5;
            }
            loading.setLocation(x, loading.getY());

        });

    }

    private void condition(){
        if(x >= loading.getParent().getSize().width)dir = "l";
        if(x <= 0)dir = "r";
    }

    public void start(){

        timer.start();
        setVisible(true);
    }

    public  void stop(){
        timer.stop();
        loading.setLocation(0, loading.getY());
        setVisible(false);
    }
}
