package views;

import containers.*;
import controllers.OnUIViewScrolled;
import controllers.OnUIViewScroller;
import model.FlexAlignment;
import model.FlexDirection;
import services.HPGui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


@SuppressWarnings("rawtypes")
public class UIViewScroller implements OnUIViewScroller {


    private int index = 0;
    private final Map<String, Integer> holderMap = new HashMap<>();
    private final Flex rowWrapper = new Flex(FlexDirection.ROW, FlexAlignment.LEFT){
        @Override
        public boolean isValidateRoot() {
            rowWrapperValidated();
            return super.isValidateRoot();
        }
    };

    private int currentPageIndex = 0;
    private boolean animate = false;
    private boolean widthDefined = false;
    private int maxComponentWidth = 0, maxComponentHeight = 0;
    private Map<String, Component> contents;

    private final Card2 parentWrapper = new Card2();

    private final Card wrapper = new Card();
    private int padding = 0, width = 0, height = 0;

    private boolean expandView = true;

    public UIViewScroller(int width, int height) {
        this.width = width;
        this.height = height;
        widthDefined = true;
        common();
    }

    public UIViewScroller(Dimension dimension) {
        this.width = dimension.width;
        this.height = dimension.height;
        widthDefined = true;
        common();
    }

    public UIViewScroller() {

        contents = new HashMap<>();
        common();
        wrapper.addComponentListener(adjustLayoutForUndefinedWidth());
    }

    private void common(){

        parentWrapper.setLayout(new BorderLayout());
        parentWrapper.setPadding(padding);

        wrapper.setBackground(HPGui.getColTranslucent());
        wrapper.setPadding(0);

        if(widthDefined){
            HPGui.setAllSizes(parentWrapper, width, height);
            HPGui.setAllSizes(wrapper, width, height);
        }

    }

    private void rowWrapperValidated() {

        new Timer(2, e -> {
            setPageView(currentPageIndex);
            ((Timer)e.getSource()).stop();
        }).start();
    }

    @Override
    public void first() {

        setPageView(0);
    }

    @Override
    public void last() {

        setPageView(holderMap.size()-1);
    }

    @Override
    public void next() {
        if(currentPageIndex < holderMap.size()-1){
            setPageView(currentPageIndex + 1);
        }

    }

    @Override
    public void previous() {
        if(currentPageIndex > 0){
            setPageView(currentPageIndex - 1);
        }
    }

    @Override
    public void scrollTo(int index) {
        if(holderMap.containsValue(index)){
            setPageView(index);
        }
    }

    @Override
    public void scrollTo(String id) {

        setPageView(holderMap.get(id)-1);
    }

    @Override
    public void addComponent(Component component, String id) {
        if(widthDefined){

            if(!expandView){

                rowWrapper.addComponent(new ContentHolder(component));

            }else{

                component.setPreferredSize(parentWrapper.getPreferredSize());
                rowWrapper.addComponent(component);
            }
            holderMap.put(id, ++index);

        }else{
            maxComponentWidth = Math.max(maxComponentWidth, component.getPreferredSize().width);
            maxComponentHeight = Math.max(maxComponentHeight, component.getPreferredSize().height);
            contents.put(id, component);
        }
    }


    public Card2 build(){
        wrapper.add(rowWrapper.build());
        parentWrapper.add(wrapper);
        return parentWrapper;

    }

    boolean resized = false;

    private ComponentAdapter adjustLayoutForUndefinedWidth(){

        return new ComponentAdapter(){

            @Override
            public void componentResized(ComponentEvent e) {

                    if (wrapper.getParent().getParent().getLayout() instanceof BorderLayout){

                        adjustViews(new Dimension(wrapper.getWidth(), wrapper.getHeight()));

                    }else{
                        adjustViews(new Dimension(maxComponentWidth, maxComponentHeight));
                    }
            }
        };
    }

    private void adjustViews(Dimension dim){

        rowWrapper.removeAll();

        HPGui.setAllSizes(rowWrapper, contents.size() * dim.width, dim.height);

        contents.forEach((key, component) -> {

            if(!expandView){

                rowWrapper.addComponent(new ContentHolder(component, dim));

            }else{

                HPGui.setAllSizes(component, dim);
                rowWrapper.addComponent(component);
            }
            holderMap.putIfAbsent(key, ++index);
        });

        rowWrapper.revalidate();
        wrapper.removeAll();
        wrapper.add(rowWrapper.build());
        resized = true;
    }

    private void setPageView(int x){
        final int width = widthDefined ? wrapper.getPreferredSize().width : wrapper.getWidth();
        if(!animate){
            currentPageIndex = x;
            rowWrapper.setLocation((-x * width), 0);

        }else{
            animate(x, width);
        }

        //fire onScrolled of UIViewScroller
        if(onUIViewScrolledList.size() > 0){
            holderMap.forEach((s, i) -> {
                if(i == x+1){
                    onUIViewScrolledList.forEach(e -> e.scrolled(s));
                }
            });

        }
    }

    @Override
    public void setExpandValue(Boolean expand) {

    }

    private void animate(int x, int width){
         final int moveSize = 20;
        AtomicInteger integer = new AtomicInteger(currentPageIndex * width);
        final int pos = (-x * width);

        new Timer(0, e -> {
            if(x > currentPageIndex ){
                //100 < 200
                if(integer.get() < x * width){
                    rowWrapper.setLocation(- integer.addAndGet(moveSize), 0);
                }else{
                    rowWrapper.setLocation(pos, 0);
                    ((Timer)e.getSource()).stop();
                    currentPageIndex = x;
                }
            }else{
                //200 > 100 - from 200 to 100
                if(integer.get() >  x * width){
                    rowWrapper.setLocation(-integer.addAndGet(-moveSize), 0);
                }else{
                    rowWrapper.setLocation(pos, 0);
                    ((Timer)e.getSource()).stop();
                    currentPageIndex = x;
                }

            }
        }).start();
    }

    public void setAnimate(boolean animate) {
        this.animate = animate;
    }

    public void setBgColor(Color bgColor){
        parentWrapper.setBackground(bgColor);
    }

    public void setPadding(int padding){
        this.padding = padding;
        common();
    }

    public void setExpandView(boolean expandView) {
        this.expandView = expandView;
    }

    public boolean isExpandView() {
        return expandView;
    }

    public int getCurrentPageIndex() {
        return currentPageIndex;
    }

    public void setCurrentPageIndex(int currentPageIndex) {
        this.currentPageIndex = currentPageIndex;
    }

    private class ContentHolder extends TransparentContainer {

        public ContentHolder(Component component) {

            HPGui.setAllSizes(this, wrapper.getPreferredSize());
            add(component);
        }

        public ContentHolder(Component component, Dimension dim) {
            HPGui.setAllSizes(this, dim);
            add(component);
        }

    }

//    OnUIViewScrolled onUIViewScrolled = null;

    static java.util.List<OnUIViewScrolled> onUIViewScrolledList = new ArrayList<>();

    public static void setOnUIViewScrolled(OnUIViewScrolled onUIViewScrolledImpl) {
       if(!onUIViewScrolledList.contains(onUIViewScrolledImpl)){
           onUIViewScrolledList.add(onUIViewScrolledImpl);
       }
    }
}
