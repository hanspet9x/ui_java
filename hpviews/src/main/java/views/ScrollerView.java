package views;

import controllers.OnOverflowScroll;
import services.HPGui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;

public class ScrollerView extends JPanel {

    int width = 10, containerSize = 250, contentSize = 350, moved = 0;
    int scrollBarLength = 0, arcSize = 10, increment = 0;

    private ScrollPolicy policy = ScrollPolicy.VERTICAL;
    private BarHorizontal barHorizontal;
    private BarVertical barVertical;
    private BufferedImage image = null;

    public ScrollerView(int width, int containerSize, int contentSize, ScrollPolicy policy) {
        this.width = width;
        this.containerSize = containerSize;
        this.contentSize = contentSize;
        this.policy = policy;
        common();
    }

    public void update(int contentSize){
        this.contentSize = contentSize;

        if(policy == ScrollPolicy.HORIZONTAL){
            scrollBarLength = getScrollBarLength(width, contentSize);
            image = null;
            barHorizontal.repaint();
        }else{
            scrollBarLength = getScrollBarLength(containerSize, contentSize);
            image = null;
            barVertical.repaint();
        }
    }

    private void common(){
        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        if(policy == ScrollPolicy.HORIZONTAL){
            barHorizontal = new BarHorizontal();
            barHorizontal.setCursor(HPGui.getHandCursor());
            add(barHorizontal);
        }else{
            barVertical = new BarVertical();
            barVertical.setCursor(HPGui.getHandCursor());
            add(barVertical);
        }


    }


    private int getScrollBarLength(int viewLength, int contentLength){
        //return (int) (((double)viewLength / (double)contentLength) * (double) viewLength);
        double opened = 100 - ((double)(100 * viewLength) / (double)contentLength);
        double rem = (viewLength * opened) / 100;
        increment = (int) Math.round((double) (contentLength - viewLength) / rem);
        return (int) (viewLength - rem);
    }


    private void positionScrolls(Canvas canvas, int pos){
        moved = pos;
        canvas.repaint();

        if(onOverflowScroll != null)onOverflowScroll.scroll(pos * increment);

    }

    private class BarVertical extends Canvas{

        public BarVertical() {

            scrollBarLength = getScrollBarLength(containerSize, contentSize);

            addMouseMotionListener(new MouseAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    int y = e.getY();

                    if(containerSize >= (scrollBarLength + y) && y >= 0){
                        positionScrolls(BarVertical.this, y);
                    }
                }
            });

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int y = e.getY();
                    if(scrollBarLength + y > containerSize){
                        y = containerSize - scrollBarLength;
                    }
                    positionScrolls(BarVertical.this, y);
                }
            });
        }



        @Override
        public Dimension getPreferredSize() {
            return new Dimension(width, containerSize);
        }

        @Override
        public void paint(Graphics g) {
            Graphics2D graphics2D = (Graphics2D)g;
            if(image == null){
                image = new BufferedImage(width, scrollBarLength, BufferedImage.TYPE_INT_ARGB);
                Graphics2D imageGraphics2D = image.createGraphics();
                imageGraphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                imageGraphics2D.setColor(Color.GRAY);
                imageGraphics2D.fillRoundRect(0, 0, width, scrollBarLength, arcSize, arcSize);
            }
            graphics2D.drawImage(image, 0, moved, null);


        }
    }

    private class BarHorizontal extends Canvas{

        public BarHorizontal() {
            scrollBarLength = getScrollBarLength(width, contentSize);

            addMouseMotionListener(new MouseAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    int x =e.getX();
                    if(width >= (scrollBarLength + x) && x >= 0){
                        positionScrolls(BarHorizontal.this, x);
                    }
                }
            });

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int x = e.getX();
                    if(scrollBarLength + x > containerSize){
                        x = containerSize - scrollBarLength;
                    }
                    positionScrolls(BarHorizontal.this, x);
                }
            });
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(width, containerSize);
        }

        @Override
        public void paint(Graphics g) {
            Graphics2D graphics2D = (Graphics2D)g;
            if(image == null){
                image = new BufferedImage(scrollBarLength, containerSize, BufferedImage.TYPE_INT_ARGB);
                Graphics2D imageGraphics2D = image.createGraphics();
                imageGraphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                imageGraphics2D.setColor(Color.GRAY);
                imageGraphics2D.fillRoundRect(0, 0, scrollBarLength, containerSize, arcSize, arcSize);
            }
            graphics2D.drawImage(image, moved, 0, null);


        }
    }

    public enum ScrollPolicy{
        HORIZONTAL, VERTICAL
    }

    public OnOverflowScroll onOverflowScroll;

    public void onScroll(OnOverflowScroll onOverflowScroll){
        this.onOverflowScroll = onOverflowScroll;
    }
}
