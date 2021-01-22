package views;

import services.HPGui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import containers.*;

public class OverflowView extends Card2{

    private Component component;
    private int increment  = 5;
    private int width;
    private int height;
    private HPGui hp = new HPGui();
    private Card2<Object> contentWrapper;
    private int barSize = 10;
    private ScrollerView scrollerVertical;
    private ScrollerView scrollerHorizontal;

    public OverflowView(Component component, int width, int height) {
        this.component = component;
        this.width = width;
        this.height = height;
        common();
    }

    public OverflowView(int width, int height) {
        this.width = width;
        this.height = height;
    }

    private void common(){
        component.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                HPGui.log("resized...");
                setScroller();
            }
        });

        setPadding(0);
        setLayout(new BorderLayout());
        hp.setAllSizes(this, width, height);

        contentWrapper = new Card2<>();
//        contentWrapper.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
        contentWrapper.setLayout(new BoxLayout(contentWrapper, BoxLayout.PAGE_AXIS));
        contentWrapper.setCardSize(width, height);
        contentWrapper.setPadding(0);
        contentWrapper.add(component);

        add(contentWrapper, BorderLayout.CENTER);
    }


    private void setScroller() {

        Dimension compDimension = component.getPreferredSize();
        if(compDimension.height > this.height){
           if(scrollerVertical ==  null){
               scrollerVertical = new ScrollerView(barSize, this.height, compDimension.height, ScrollerView.ScrollPolicy.VERTICAL);
               scrollerVertical.onScroll(value -> component.setLocation(component.getX(), -value));
               add(scrollerVertical, BorderLayout.LINE_END);
               revalidate();
               repaint(scrollerVertical.getBounds());
           }else{

               scrollerVertical.update(compDimension.height);
           }
        }

        if(compDimension.width > this.width){
            if(scrollerHorizontal == null){
                scrollerHorizontal = new ScrollerView(this.width, barSize, compDimension.width, ScrollerView.ScrollPolicy.HORIZONTAL);
                scrollerHorizontal.onScroll(value -> component.setLocation(-value, component.getY()));
                add(scrollerHorizontal, BorderLayout.PAGE_END);
                revalidate();
                repaint(scrollerHorizontal.getBounds());
            }else{
                scrollerHorizontal.update(compDimension.width);
            }
        }
    }

}
