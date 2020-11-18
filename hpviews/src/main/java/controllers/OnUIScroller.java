package controllers;


import views.UIScroller;

public interface OnUIScroller{

    void first();
    void last();
    void next();
    void scrollTo(int scrollCardIndex);
    void scrollTo(String scrollCardName);
    void addScrollCard(UIScroller.ScrollCard scrollCard);

}
