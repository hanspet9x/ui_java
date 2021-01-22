package services;

import containers.Card;
import controllers.OnTreeDoubleClicked;
import controllers.OnUIExplorerSelected;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import views.ExplorerIcon;
import views.UITreeExplorer;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

@SuppressWarnings("rawtypes")
public class UIExplorerService extends MouseAdapter implements OnTreeDoubleClicked{

    private final String treeData;
    private final Card explorerContainer;
    private String mainDirectory;
    private String currentDirectory = "";
    private final List<JSONArray> arrays = new ArrayList<>();
    private final int stopUpdateOnLevel;
    private UITreeService.TreeDataSource dataSource;

    public UIExplorerService(UITreeExplorer uiTreeExplorer) {
        treeData = uiTreeExplorer.getTree();
        this.stopUpdateOnLevel = uiTreeExplorer.getStopUpdateOnLevel();
        UITreeExplorer.Explorer explorer = uiTreeExplorer.getExplorer();
        dataSource = uiTreeExplorer.getTreeDataSource();
        this.explorerContainer = explorer.getContainer();
        UITreeService.setOnTreeDoubleClicked(this);
    }

    public void update(String newData, UITreeService.TreeDataSource source){
        this.dataSource = source;
        removeIcons();
        explore(newData);
        validateContainer();
    }

    public void update(JSONArray newData, UITreeService.TreeDataSource source){
        update(newData.toString(), source);
    }

    public void explore(String treeData){
        JSONArray array = new JSONArray(treeData);
        mainDirectory = array.toString();
        add(array);
    }

    private void add(JSONArray array){

        for (int i = 0; i < array.length(); i++) {

            JSONObject tree = array.getJSONObject(i);
            ExplorerIcon icon = getIcon(tree, array);
            icon.addFullPath("/"+icon.getName());
            explorerContainer.add(getIconWrapper(icon));

        }
    }

    private void add(JSONArray array, ExplorerIcon icon){

        for (int i = 0; i < array.length(); i++) {

            JSONObject tree = array.getJSONObject(i);

            ExplorerIcon nIcon = getIcon(tree, array);
            nIcon.addFullPath(icon.getFullPath().replace("/"+icon.getName(), ""));
            nIcon.addFullPath("/");
            nIcon.addFullPath(icon.getName());
            nIcon.addFullPath("/");
            nIcon.addFullPath(nIcon.getName());

            explorerContainer.add(getIconWrapper(nIcon));

        }
    }

    private Card getIconWrapper(ExplorerIcon icon){
        Card wrapper = new Card(new FlowLayout(FlowLayout.CENTER));
        wrapper.setPadding(0);
        wrapper.add(icon);
        return wrapper;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        ExplorerIcon icon = (ExplorerIcon)e.getSource();

        if(e.getClickCount() > 1){
            removeIcons();

            if(!icon.isLevelUp()){
                enterDirectory(icon);
                onUIExplorerSelected.mainIcon(icon);
            }else{
                exitDirectory(icon);
            }

            validateContainer();
        }
    }

    private void enterDirectory(ExplorerIcon icon){
//        if(icon.getDirTree().length() > 0){
            addLevelUpIcon(icon.getMainTree());
            add(icon.getDirTree(), icon);
            arrays.add(icon.getMainTree());
            currentDirectory = icon.getDirTree().toString();
//        }
    }

    private void exitDirectory(ExplorerIcon icon){
       int index = arrays.size()-1;
       currentDirectory = arrays.get(index).toString();
        if(!mainDirectory.equals(currentDirectory)){

            addLevelUpIcon(arrays.get(index));
        }
        add(arrays.get(index));
        arrays.remove(index);

    }

    private  void addLevelUpIcon(JSONArray mainTree){

        ExplorerIcon icon = new ExplorerIcon("../", true, true, UITreeService.TreeDataSource.JSON);
        icon.setMainTree(mainTree);
        icon.addMouseListener(this);
        explorerContainer.add(getIconWrapper(icon));
    }

    private ExplorerIcon getIcon(JSONObject tree, JSONArray array){

        ExplorerIcon icon = new ExplorerIcon(
                tree.getString(UITreeExplorer.nameKey),
                false,
                tree.getBoolean(UITreeExplorer.dirKey),

                UITreeService.TreeDataSource.JSON);

        icon.setLevelNo(tree.getInt(UITreeExplorer.levelKey));
        icon.setMainTree(array);
        try {
            icon.setDirTree(tree.getJSONArray(UITreeExplorer.childrenKey));
        }catch (JSONException e){
//            System.out.println(e.getMessage());
            icon.setDirTree(new JSONArray());
        }
        icon.addMouseListener(this);
        return icon;
    }

    public void removeIcons(){
        if(explorerContainer.getComponentCount() > 0){
            explorerContainer.removeAll();
        }
    }

    public void validateContainer(){
        explorerContainer.validate();
        explorerContainer.repaint();
    }

    public void updateByTree(UITreeExplorer.Tree tree){
        JSONArray mainTree = tree.getMainTree();
        JSONArray dirTree = tree.getDirTree();
        int level = tree.getLevel();

        removeIcons();
        if(level == 1){

            add(mainTree);

        }else{
            addLevelUpIcon(mainTree);
            add(dirTree);
            arrays.add(mainTree);
            currentDirectory = dirTree.toString();
        }

        validateContainer();
    }

    @Override
    public void mainTree(UITreeExplorer.Tree tree) {
        if(tree.getLevel() != stopUpdateOnLevel){
            updateByTree(tree);
        }
    }

    static OnUIExplorerSelected onUIExplorerSelected;

    public static void setOnUIExplorerSelected(OnUIExplorerSelected onUIExplorerSelected) {
        UIExplorerService.onUIExplorerSelected = onUIExplorerSelected;
    }
}
