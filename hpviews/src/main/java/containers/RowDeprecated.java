package containers;


import services.HPGui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RowDeprecated extends TransparentContainer {

    private List<Dimension> sizes = new ArrayList<>();
    private int totalWidth = 0, totalHeight = 0;
    private Dimension parentDimension = new Dimension(0, 0);
    private boolean isWithinBounds = true;
    private boolean fitToContents = false;
    private boolean fitted = false;
    private int totalComponents = 0;
    private int lineMaxWidth = 0;

    public RowDeprecated() {
        setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
        setBorder(BorderFactory.createLineBorder(Color.RED));
    }

    public RowDeprecated(Align alignment) {

        int align = 0;
        switch (alignment){
            case LEFT -> align = FlowLayout.LEADING;
            case RIGHT -> align = FlowLayout.RIGHT;
            case CENTER -> align = FlowLayout.CENTER;
        }
        setLayout(new FlowLayout(align, 0, 0));

    }

    @Override
    public Component add(Component comp) {

        comp.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);

                sizes.add(e.getComponent().getPreferredSize());
                ++totalComponents;


                if(fitToContents && !fitted){
                    if(totalComponents == getComponentCount()){
                        makeContainerFit();
                    }
                }
            }
        });
        fitted = false;
        return super.add(comp);
    }

    private void makeContainerFit() {
        Integer[] sizes = getLayoutSizes();
        parentDimension =new Dimension(sizes[0], sizes[1]);
        fitted = true;
        revalidate();
        repaint();
    }

    enum Align{
        LEFT, CENTER, RIGHT
    }


    @Override
    public Dimension getPreferredSize() {
        Container container = getParent();

        if(container.getName().contains("contentPane")){
            setBorder(BorderFactory.createLineBorder(Color.RED));
            int w = container.getWidth() - 10;
            int h = container.getHeight() - 10;
            if(!fitted){
                parentDimension = new Dimension(w, h);
            }

        }else{
            if(!fitted){
                parentDimension = super.getParent().getPreferredSize();
            }
        }
        totalComponents = 0;
        return parentDimension;
    }

    public void adjustLayoutWidth (){
        sizes.forEach(dimension -> totalWidth += dimension.width);
    }

    public boolean isFitToContents() {
        return fitToContents;
    }

    public void setFitToContents(boolean fitToContents) {
        this.fitToContents = fitToContents;
    }

    int prevHeight = 0, prevWidth = 0;

    public Integer [] getLayoutSizes (){

        AtomicInteger i = new AtomicInteger(0);

        sizes.forEach(dimension -> {
            i.incrementAndGet();

            totalWidth += dimension.width;

            if(totalWidth <= parentDimension.width){

                lineMaxWidth = totalWidth;

                prevHeight = Math.max(prevHeight, dimension.height);

                prevWidth = Math.max(prevWidth, dimension.width);

                isWithinBounds = true;

                HPGui.log("first",totalWidth, prevWidth);

            }else{
                totalHeight += prevHeight;
                prevHeight = Math.max(prevHeight, dimension.height);
                if(i.get() == sizes.size()){
                    totalHeight += prevHeight;
                    if(dimension.width > lineMaxWidth){
                        lineMaxWidth = dimension.width;
                    }
                }else{
                    totalWidth = dimension.width;
                }


                HPGui.log("second",totalWidth, parentDimension.width);



                isWithinBounds = false;
            }

        });

        if(sizes.size() > 0){

            totalHeight = isWithinBounds ? totalHeight + prevHeight : totalHeight;

            this.setPreferredSize(new Dimension(parentDimension.width, totalHeight));

HPGui.log(lineMaxWidth);
            return new Integer[]{lineMaxWidth+(i.get()), totalHeight + (i.get())};
        }
        return new Integer[]{0, 0};

    }

}
