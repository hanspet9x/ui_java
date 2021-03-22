package controllers;


import views.UIViewScroller;

import java.awt.*;

public interface OnUIViewScroller {

    void first();
    void last();
    void next();
    void previous();
    void scrollTo(int index);
    void scrollTo(String id);
    void addComponent(Component component, String id);
    void setExpandValue(Boolean expand);

}
