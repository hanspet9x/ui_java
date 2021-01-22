package services;

import containers.Card;
import controllers.OnTreeDoubleClicked;
import controllers.OnUITreeSelected;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import views.UITreeExplorer;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static views.UITreeExplorer.*;

@SuppressWarnings("rawtypes")
public class UITreeService {


    private TreeDataSource dataSource;
    private final Card treeContainer;
    private final HPGui hp = new HPGui();


    public UITreeService(UITreeExplorer uiTreeExplorer) {
        this.treeContainer = uiTreeExplorer.getTreeContainer();
        this.dataSource = uiTreeExplorer.getTreeDataSource();
    }

/*    public void oDrawLevels(){
        oTreeData.forEach(model -> {

        });
    }*/
    public void update(String newTreeData, TreeDataSource source){
        this.dataSource = source;
        treeContainer.removeAll();
        drawLevels(newTreeData);
        updateTreeContainer();
    }

    public void update(JSONArray newTreeData, TreeDataSource source){
        update(newTreeData.toString(), source);
    }

    public void drawLevels(String treeData){

        if(dataSource == TreeDataSource.JSON){
            JSONArray jsonArray = new JSONArray(treeData);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject levelData = jsonArray.getJSONObject(i);
                treeContainer.add(getLevelView(levelData, jsonArray));
            }
        }else{
            System.out.println("Explorer...");
        }
    }

    private Tree getLevelView(JSONObject levelData, JSONArray mainTree){

        Tree tree = getTree(levelData, mainTree);

                try {
                    getAnotherLevel(levelData, tree);
                    tree.setHasChildren(true);

                }catch (JSONException e){
//                    System.err.println(e.getMessage());
                }
        return tree;
    }

    private void getAnotherLevel(JSONObject levelData, Tree container){

        JSONArray dataArray = levelData.getJSONArray(childrenKey);
        container.setDirTree(dataArray);

        for (int i = 0; i < dataArray.length(); i++) {
            JSONObject childrenLevel = dataArray.getJSONObject(i);

            Tree tree = getTree(childrenLevel, dataArray);

            tree.addToTreePath(container.getTreePath().replace("/"+container.getName(), ""));
            tree.addToTreePath("/");
            tree.addToTreePath(container.getName());
            tree.addToTreePath("/");
            tree.addToTreePath(tree.getName());

                    container.add(tree);
                    try{
                        getAnotherLevel(childrenLevel, tree);
                        tree.setHasChildren(true);
                    }catch (JSONException e){
//                        System.err.println(e.getMessage());
                    }
        }

    }

    private void showFolderContents(Tree owner) {

    }

    private Tree getTree(JSONObject data, JSONArray dataArray){
        Tree tree = new Tree(
                data.getInt(levelKey),
                data.getBoolean(dirKey),
                data.getString(nameKey));
//                    tree.getInnerCard().addMouseListener(this);
        tree.setMainTree(dataArray);
        tree.getRotate().addMouseListener(new OnTreeCollapsible(tree));
        tree.getLabel().addMouseListener(new OnLabelClicked(tree));
        tree.getIcon().addMouseListener(new OnLabelClicked(tree));

        return tree;
    }

    private void showSubLevels(Tree levels){
        int compNo =  levels.getComponents().length;

        int h = (compNo * Tree.HEIGHT) + (compNo * 3);
        hp.setAllSizes(levels, UITreeExplorer.WIDTH, h);
//        hp.setAllSizes(levels.getComponent(1), UITreeExplorer.WIDTH,UITreeExplorer.Tree.HEIGHT+10 );

        if(levels.getLevel() > 1){
            Container cont = levels.getParent();
            for (int i = 0; i < levels.getLevel()-1; i++) {
                int hh = (cont.getPreferredSize().height + h) - Tree.HEIGHT;
                hp.setAllSizes(cont, UITreeExplorer.WIDTH, hh);
                cont = cont.getParent();
            }

        }
        updateTreeContainer();
        levels.setCollapsed(false);

    }

    private void hideSubLevels(Tree tree){
        int oHeight = tree.getPreferredSize().height - Tree.HEIGHT;
        hp.setAllSizes(tree, UITreeExplorer.WIDTH, Tree.HEIGHT + 2);

        if(tree.getLevel() > 1){
            Container cont = tree.getParent();
            for (int i = 0; i < tree.getLevel()-1; i++) {
                hp.setAllSizes(cont, UITreeExplorer.WIDTH, cont.getPreferredSize().height - oHeight);
                cont = cont.getParent();
            }
        }
        updateTreeContainer();
        tree.setCollapsed(true);
    }

    private void updateTreeContainer(){
        treeContainer.revalidate();
        treeContainer.repaint();
    }

    public enum TreeDataSource{
        JSON, PATH
    }

    static OnTreeDoubleClicked onTreeDoubleClicked;

    public static void setOnTreeDoubleClicked(OnTreeDoubleClicked onTreeDoubleClicked) {
        UITreeService.onTreeDoubleClicked = onTreeDoubleClicked;
    }

    static OnUITreeSelected onUITreeSelected = null;

    public static void setOnUITreeSelected(OnUITreeSelected onUITreeSelected) {
        UITreeService.onUITreeSelected = onUITreeSelected;
    }

    private class OnTreeCollapsible extends MouseAdapter{

        private final Tree owner;
        public OnTreeCollapsible(Tree tree) {
            this.owner = tree;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);

            if(owner.isHasChildren()) {

                if(owner.isCollapsed()){
                    showSubLevels(owner);
                    owner.getRotate().animate(0, 90);
                }else{
                    hideSubLevels(owner);
                    owner.getRotate().animate(90, 0);
                }
                if(e.getClickCount() > 1){
                    showFolderContents(owner);
                }
            }
        }
    }

    private static class OnLabelClicked extends MouseAdapter{

        private final Tree owner;
        public OnLabelClicked(Tree tree) {
            this.owner = tree;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);

            /*
        update explorer
         */
            if(e.getClickCount() > 1 && owner.isDirectory()){
                onTreeDoubleClicked.mainTree(owner);
                if(onUITreeSelected != null){
                    onUITreeSelected.mainTree(owner);
                }
            }
        }
    }
}
