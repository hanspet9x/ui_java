package controllers;

import views.UITreeExplorer;

public interface OnUITreeSelected extends OnTreeDoubleClicked{

    @Override
    void mainTree(UITreeExplorer.Tree tree);
}
