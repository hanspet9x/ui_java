package views;

import animations.Rotate;
import containers.Flex;
import controllers.OnCollapsible;
import model.FlexDirection;
import services.HPGui;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import containers.*;

public class Collapsible extends Card2 {

    private Card2 header;
    private Card2 body;
    private Flex contentsWrapper;
    private Rotate rotateView;
    private OnCollapsible onCollapsible = null;
    private boolean isCollapsed = true;
    private int index;

    public Collapsible(Component header, Component ...contents) {

        common();
        addAnchor(header);
        populateContents(contents);
    }

    public Collapsible() {

        common();
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    private void common(){
        header = new Card2();
        header.setLayout(new BorderLayout());
        header.setPadding(5);

//      add imageview
        ImageView view = new ImageView("views/forms/expand.png");
        view.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        view.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(contentsWrapper.getComponentCount() > 0){
                    isCollapsed = !isCollapsed;
                    setCollapseAction();
                    if(onCollapsible != null) onCollapsible.collapsed();
                }
            }
        });



        rotateView = new Rotate(view, 10);
        this.header.add(rotateView, BorderLayout.LINE_END);

        body = new Card2();
        body.setLayout(new BorderLayout());
        body.setPadding(0);

        contentsWrapper = new Flex(FlexDirection.COLUMN);
        contentsWrapper.setBackground(Color.GREEN);

    }

    private void close(){
        rotateView.animate(-180, 0);
        setCardSize(body.getPreferredSize().width, header.getPreferredSize().height);
    }

    private void setCollapseAction() {

        if(isCollapsed){
            rotateView.animate(-180, 0);
            setCardSize(body.getPreferredSize().width, header.getPreferredSize().height);
            getParent().setPreferredSize(new Dimension(getParent().getPreferredSize().width, getParent().getPreferredSize().height - body.getPreferredSize().height));

        }else{
            rotateView.animate(0, -180);
            setCardSize(body.getPreferredSize().width, header.getPreferredSize().height+body.getPreferredSize().height);
            getParent().setPreferredSize(new Dimension(getParent().getPreferredSize().width, getParent().getPreferredSize().height + body.getPreferredSize().height + 10));
        }

        HPGui.log(getPreferredSize());
        revalidate();
        repaint();

    }


    public Collapsible addContents(Component ...contents){

        populateContents(contents);
        return this;
    }

    private void populateContents(Component ...components){
        for (Component comp :
                components) {
            contentsWrapper.add(comp);
        }
    }


    public Collapsible addAnchor(Component anchor){
        this.header.add(anchor, BorderLayout.CENTER);
        return this;
    }

    public Collapsible addLeftComponent(Component component){
        this.header.add(component, BorderLayout.LINE_START);
        return this;
    }

    public Collapsible build() {

        body.add(contentsWrapper);

        setLayout(new BorderLayout());
        setPadding(0);
        add(header, BorderLayout.PAGE_START);
        add(body, BorderLayout.CENTER);

        if(contentsWrapper.getComponentCount() > 0){
            setCardSize(body.getPreferredSize().width, header.getPreferredSize().height);
        }

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
//                HPGui.log("location"+getLocation());
            }
        });
        return this;
    }

    public void setOnCollapsible(OnCollapsible onCollapsible){
        this.onCollapsible = onCollapsible;
    }
}
