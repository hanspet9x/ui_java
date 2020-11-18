package views;

import controllers.OnUIScroller;
import services.HPGui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Arrays;

@SuppressWarnings("rawtypes")
public class UIScroller extends Card implements OnUIScroller {

    private Card header;
    private Card footer;
    private final Card body;

    private final HPGui hp;

    public UIScroller(int width, int height) {
        super(new BorderLayout());
        hp = new HPGui();
        setPadding(10);
        setBackground(Color.green);
//        hp.setAllSizes(this, width, height);
//        this.setBackground(Color.GREEN);
        this.body = new Card();

    }




    public UIScroller build(){


        body.setLayout(new BoxLayout(body, BoxLayout.LINE_AXIS));

        body.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension d = e.getComponent().getSize();

                hp.setAllSizes(body, d.width*30, d.height);
                System.out.println(d);
                body.removeComponentListener(this);

//                setScrollCardSizes(e);
            }
        });

        add(body, BorderLayout.CENTER);
        return this;
    }

    private void setScrollCardSizes(ComponentEvent e){

        Arrays.stream(body.getComponents())
                .forEach(scrollCard -> {
//                    hp.setAllSizes(scrollCard, e.getComponent().getSize());
                });
    }


    @Override
    public void first() {

    }

    @Override
    public void last() {

    }

    @Override
    public void next() {

    }

    @Override
    public void scrollTo(int scrollCardIndex) {

    }

    @Override
    public void scrollTo(String scrollCardName) {

    }

    @Override
    public void addScrollCard(ScrollCard scrollCard) {
        scrollCard.setIndex(body.getComponentCount());
        body.add(scrollCard);
    }

    @Override
    public Component add(Component comp) {
        ScrollCard card = new ScrollCard(comp.getName());
        return super.add(card);
    }

    @Override
    public Component add(Component comp, int index) {
        ScrollCard card = new ScrollCard(comp.getName());
        card.setIndex(body.getComponentCount());
        return super.add(card, body.getComponentCount());
    }

    public static class ScrollCard extends Card{

        private int index;
        private String name;

        public ScrollCard(String name) {
            this.name = name;

            setPadding(10);
            setBorderRadius(10);
            setBackground(Color.LIGHT_GRAY);
        }


        @Override
        public String getName() {
            return name;
        }

        @Override
        public void setName(String name) {
            this.name = name;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }
}
