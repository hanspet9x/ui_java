package containers;

import controllers.AncestorAdapter;
import services.HPGui;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Center extends TransparentContainer{


    private boolean hasWidth = false;
    private ComponentEvent event;

    public Center(Component component) {
        common();
        add(component);
    }

    public Center() {
        common();
    }

    public Center(int opacity) {
        super(opacity);
        common();
    }

    private void common(){

        setLayout(null);

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {

                event = e;
                resized(e);

            }
        });
    }

    private void resized(ComponentEvent e){
        if(hasWidth){
//                    HPGui.log(Center.this.getPreferredSize().width, Center.this.getPreferredSize().height);
            adjust(Center.this.getPreferredSize().width, Center.this.getPreferredSize().height);
        }else{
            Container parent = e.getComponent().getParent();
            int w;
            int h;
            if(parent == null){
                w = Center.this.getPreferredSize().width;
                h = Center.this.getPreferredSize().height;
            }else{
                w = parent.getWidth();//Math.max(parent.getWidth(), parent.getPreferredSize().width);
                h = parent.getHeight();//Math.max(parent.getHeight(), parent.getPreferredSize().height);

            }
            if(!HPGui.compareDimensions(Center.this.getPreferredSize(), new Dimension(w, h))){
                HPGui.setAllSizes(Center.this, w, h);
            }
            adjust(w, h);
        }
    }

    private void adjust(int width, int height){

        if(this.getComponents().length > 0){
            Component comp = Center.this.getComponent(0);
            int w = Math.max(comp.getWidth(), comp.getPreferredSize().width);
            int h = Math.max(comp.getHeight(), comp.getPreferredSize().height);

            comp.setBounds(
                    (width / 2) - (w / 2),
                    (height / 2) - (h / 2),
                    w,
                    h
            );
            comp.revalidate();
        }
    }

    @Override
    public Component add(Component comp) {
        if(getComponentCount() > 0){
            removeAll();
            super.add(comp);
            resized(event);
            revalidate();
            repaint();
        }else{
            super.add(comp);
        }
        return comp;
    }


    public void setContainerSize(int width, int height){
        HPGui.setAllSizes(this, width, height);
        hasWidth = true;
    }

    public void setContainerSize(Dimension dimension){
        HPGui.setAllSizes(this, dimension);
        hasWidth = true;
    }
/*    private ComponentAdapter resizing(){
        return new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Container container = getParent();
                if(container != null){
                    HPGui.setAllSizes(Center.this, container.getPreferredSize());
                   *//* if(container.getName() != null && container.getName().contains("contentPane")){
                        int w = container.getWidth();
                        int h = container.getHeight();
                        HPGui.setAllSizes(Center.this, w, h);
                    }else{
                        HPGui.setAllSizes(Center.this, container.getPreferredSize());
                    }*//*
                }
            }
        };
    }*/



}
