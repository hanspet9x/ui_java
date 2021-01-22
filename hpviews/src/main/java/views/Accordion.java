package views;

import containers.Flex;
import controllers.OnCollapsible;
import model.FlexAlignment;
import model.FlexDirection;
import services.HPGui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import containers.*;

public class Accordion extends Card2 implements OnCollapsible {

    private boolean isOpened = false;
    private int index;

    private Collapsible [] collapsibles;
    private Flex flex;

    public Accordion(Collapsible ...collapsibles) {
        this.collapsibles = collapsibles;
        common();
    }

    public Accordion(int index, Collapsible ...collapsibles) {
        this.index = index;
        this.collapsibles = collapsibles;
        common();
    }

    private void common(){

        flex = new Flex(FlexDirection.COLUMN, FlexAlignment.LEFT, FlexAlignment.TOP);

        int i = 0;
        for (Collapsible collapsible:
             collapsibles) {
            collapsible.setOnCollapsible(this);
            collapsible.addMouseListener(addListener());
            collapsible.setIndex(i++);
            flex.add(collapsible);
        }

        add(flex);

    }

    private void collapseAction(){

    }

    private MouseAdapter addListener(){
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Collapsible collapsible = (Collapsible)e.getSource();
                HPGui.log(collapsible.getIndex());
            }
        };
    }

    @Override
    public void collapsed() {

        setCardSize(getPreferredSize().width, flex.getPreferredSize().height);
    }
}
