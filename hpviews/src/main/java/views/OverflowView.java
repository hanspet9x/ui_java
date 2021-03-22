package views;

import controllers.AncestorAdapter;
import services.HPGui;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import containers.*;


@SuppressWarnings("rawtypes")
public class OverflowView extends Card2{

    private final Component component;
    private final int increment = 5;
    private int offsetComponentHeight = 0, offsetComponentWidth = 0;
    private int width = 0, height = 0, barSize = 10, definedWidth = 0;

    private Card2<Object> contentWrapper;
    private ScrollerView scrollerVertical = null;
    private ScrollerView scrollerHorizontal = null;

    public OverflowView(Component component, int width, int height) {
        this.component = component;
        this.width = width;
        this.height = height;
        common();
    }

    public OverflowView(Component component, int width) {
        this.component = component;
        this.definedWidth = width;
        this.height = 0;
        common();
    }

    public OverflowView(Component component) {
        this.component = component;
        common();

    }

    private void common(){

        this.addAncestorListener(new AncestorAdapter() {
            @Override
            public void ancestorAdded(AncestorEvent event) {

                if(width == 0 || height == 0){
                    width = event.getAncestor().getWidth();
                    height = event.getAncestor().getHeight();
                }

                if(definedWidth > 0) {
                    definedWidth -= 1;
                    width = definedWidth;
                }

                setPadding(0);
                setLayout(new BorderLayout());
                HPGui.setAllSizes(OverflowView.this, width, height);

                contentWrapper = new Card2<>();
                contentWrapper.setLayout(new BoxLayout(contentWrapper, BoxLayout.PAGE_AXIS));
                contentWrapper.setCardSize(width, height);
                contentWrapper.setPadding(0);
                contentWrapper.add(component);

                add(contentWrapper, BorderLayout.CENTER);
                setScroller();

            }
        });

            onComponentResized();
    }

    public int getOffsetComponentHeight() {
        return offsetComponentHeight;
    }

    public void setOffsetComponentHeight(int offsetComponentHeight) {
        this.offsetComponentHeight = offsetComponentHeight;
    }

    public int getOffsetComponentWidth() {
        return offsetComponentWidth;
    }

    public void setOffsetComponentWidth(int offsetComponentWidth) {
        this.offsetComponentWidth = offsetComponentWidth;
    }

    private void onComponentResized(){
        component.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if(e.getComponent().getWidth() != component.getWidth()){
                    HPGui.log("overflow view",e.getComponent().getWidth(), e.getComponent().getHeight());
                    HPGui.setAllSizes(component, e.getComponent().getWidth(), e.getComponent().getHeight());
                }
                setScroller();
            }
        });
    }
    private void setScroller() {

        int h = component.getPreferredSize().height + offsetComponentHeight;
        int w = component.getPreferredSize().width + offsetComponentWidth;

        int h2 = component.getHeight() + offsetComponentHeight;
        int w2 = component.getWidth() + offsetComponentWidth;

//        HPGui.log(this.height, h, h2, "width", this.width, w, w2);

        if(h > this.height || h2 > this.height){

           if(scrollerVertical ==  null){

               scrollerVertical = new ScrollerView(barSize, this.height, Math.max(h, h2), ScrollerView.ScrollPolicy.VERTICAL);

               scrollerVertical.onScroll(value -> scrollContent(component.getX(), -value));

               add(scrollerVertical, BorderLayout.LINE_END);

               revalidate();

               repaint(scrollerVertical.getBounds());

           }else{
               scrollerVertical.update(Math.max(h, h2));
           }
        }

        if(w > this.width || w2 > this.width){
            if(scrollerHorizontal == null){
                scrollerHorizontal = new ScrollerView(this.width, barSize, Math.max(w, w2), ScrollerView.ScrollPolicy.HORIZONTAL);
                scrollerHorizontal.onScroll(value -> scrollContent(-value, component.getY()));
                add(scrollerHorizontal, BorderLayout.PAGE_END);
                revalidate();
                repaint(scrollerHorizontal.getBounds());
            }else{
                scrollerHorizontal.update(Math.max(w, w2));
            }
        }else{
            if(scrollerHorizontal != null){
//                HPGui.log("resetting.");
                scrollerHorizontal.update(Math.max(w, w2));
            }
        }

        addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
//                scrollContent(component.getX(), )
            }
        });
    }

    public void scrollContent(int x, int y){
        component.setLocation(x, y);
    }

    public int getBarSize() {
        return barSize;
    }

    public void setBarSize(int barSize) {
        this.barSize = barSize;
    }


}
