package controllers;


import views.UIViewScroller;

public interface OnUIViewScroller {

    void first();
    void last();
    void next();
    void scrollTo(int scrollCardIndex);
    void scrollTo(String scrollCardName);
    void addScrollCard(UIViewScroller.ScrollCard scrollCard);

}
