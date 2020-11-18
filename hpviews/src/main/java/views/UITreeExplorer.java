package views;

import controllers.OnUITreeExplorerUpdate;
import org.json.JSONArray;
import services.HPGui;
import services.UIExplorerService;
import services.UITreeService;
import javax.swing.*;
import java.awt.*;

@SuppressWarnings("rawtypes")
public class UITreeExplorer extends Card implements OnUITreeExplorerUpdate{
    public static int WIDTH = 200;
    private String treeData;
    private static HPGui hp;

    private int stopUpdateOnLevel = -1;
    public static final String levelKey = "level";
    public static final String nameKey = "name";
    public static final String dirKey = "directory";
    public static final String childrenKey = "children";

    private final Card treeContainer;
    private final Explorer explorer;
    private final UITreeService.TreeDataSource treeDataSource;
    private UIExplorerService uiExplorerService;
    private UITreeService uiTreeService;
    private Component footer = null;


    public UITreeExplorer(String treeData, UITreeService.TreeDataSource treeDataSource) {
        super(new BorderLayout());
        this.treeData = treeData;
        this.explorer = new Explorer();
        this.treeDataSource = treeDataSource;
        this.treeContainer = new Card();
        hp = new HPGui();
    }

    public UITreeExplorer(String treeData, Component footer, UITreeService.TreeDataSource treeDataSource) {
        super(new BorderLayout());
        this.footer = footer;
        this.treeData = treeData;
        this.explorer = new Explorer();
        this.treeDataSource = treeDataSource;
        this.treeContainer = new Card();
        hp = new HPGui();
    }

    public void build(){

        treeContainer.setPadding(1);
        treeContainer.setPreferredSize(new Dimension(WIDTH, 500));

        JScrollPane scrollPane = hp.getScrollPane(treeContainer);
        hp.setAllSizes(scrollPane, WIDTH, 500);

        hp.setAllSizes(this, 700, 500);

        add(scrollPane, BorderLayout.LINE_START);

        add(explorer, BorderLayout.CENTER);

        if(footer != null)add(footer, BorderLayout.PAGE_END);

        uiTreeService = new UITreeService(this);
        uiTreeService.drawLevels(treeData);

        uiExplorerService = new UIExplorerService(this);
        uiExplorerService.explore(treeData);
    }


    public int getStopUpdateOnLevel() {
        return stopUpdateOnLevel;
    }

    public void treeDoubleClicked(Tree tree){

    }

    public void setStopUpdateOnLevel(int stopUpdateOnLevel) {
        this.stopUpdateOnLevel = stopUpdateOnLevel;
    }

    public String getTree() {
        return treeData;
    }

    public Card getTreeContainer() {
        return treeContainer;
    }

    public Explorer getExplorer() {
        return explorer;
    }

    public UITreeService.TreeDataSource getTreeDataSource() {
        return treeDataSource;
    }

    public UIExplorerService getUiExplorerService() {
        return uiExplorerService;
    }

    @Override
    public void updateTreeExplorer(String data, UITreeService.TreeDataSource source) {
        uiTreeService.update(data, source);
        uiExplorerService.update(data, source);
    }

    @Override
    public void updateTreeExplorer(JSONArray data, UITreeService.TreeDataSource source) {
        uiTreeService.update(data, source);
        uiExplorerService.update(data, source);
    }


    public void setFooterVisibility(boolean visible){
        if(footer != null)footer.setVisible(visible);
    }
    /**
     * Explorer Tree
     */
    @SuppressWarnings("rawtypes")
    public static class Tree extends Card{

        public static final int HEIGHT = 25;
        private final int level;
        private final boolean isDirectory;
        private final String name;
        private StringBuilder treePath = new StringBuilder();
        private JLabel lIndicator;
        private static int levelSpace = 2;
        private Card<Tree> innerCard;
        private boolean collapsed = true;
        private boolean hasChildren = false;
        private JSONArray mainTree;
        private JSONArray dirTree;
        private JLabel label;
        private JLabel icon;

        public Tree(int level, boolean isDirectory, String name) {
            this.level = level;
            this.isDirectory = isDirectory;
            this.name = name;
            this.setToolTipText(name);
            this.lIndicator = new JLabel(hp.getImageIcon("/views/explorer/caret.png", 10, 10));
            this.label = new JLabel(this.name);
            build();
        }


        private void build(){
            setPadding(2);
            if(level > 1){
                hp.setLeftPadding(this, levelSpace * level);
            }
//            setBackground(hp.getColor("#eeeeff"));
            innerCard = new Card<>();

          hp.setAllSizes(this, 200, HEIGHT);
            if(isDirectory)innerCard.add(getJIndicator());
            innerCard.add(getJIcon());
            innerCard.add(getJLabel());
            innerCard.setObject(this);
            add(innerCard);
        }

        private JLabel getJIndicator(){
            hp.setRightPadding(lIndicator, 8);
            return lIndicator;
        }

        private JLabel getJLabel(){
            label.setFont(new Font(HPGui.FontStandard, Font.PLAIN, 13));

            return label;
        }

        private JLabel getJIcon(){
            String iconPath = isDirectory? "/views/explorer/folder.png" : "/views/explorer/file.png";
            icon = new JLabel(hp.getImageIcon(iconPath, 12, 12));

            if(!isDirectory){
                hp.setPadding(icon, 0, 18, 0, 5);
            }else{
                hp.setRightPadding(icon, 5);
            }
            return icon;
        }

        public Card getInnerCard() {
            return innerCard;
        }

        public boolean isCollapsed() {
            return collapsed;
        }

        public void setCollapsed(boolean collapsed) {
            this.collapsed = collapsed;
        }

        @Override
        public String getName() {
            return name;
        }

        public int getLevel() {
            return level;
        }

        public boolean isHasChildren() {
            return hasChildren;
        }

        public void setHasChildren(boolean hasChildren) {
            this.hasChildren = hasChildren;
        }

        public JSONArray getMainTree() {
            return mainTree;
        }

        public void setMainTree(JSONArray mainTree) {
            this.mainTree = mainTree;
        }

        public JSONArray getDirTree() {
            return dirTree;
        }

        public void setDirTree(JSONArray dirTree) {
            this.dirTree = dirTree;
        }

        public boolean isDirectory() {
            return isDirectory;
        }

        public JLabel getLabel() {
            return label;
        }

        public JLabel getIcon() {
            return icon;
        }

        public JLabel getlIndicator() {
            return lIndicator;
        }

        public String getTreePath() {
            return treePath.toString();
        }

        public void addToTreePath(String treePath) {
            this.treePath.append(treePath);
        }


    }


    /**
     * Explorer Browser
     */
    @SuppressWarnings("rawtypes")
    public static class Explorer extends Card{

        private String treeData;
        private final Card container;
        private HPGui hp;
        public Explorer() {
            setLayout(new BorderLayout());
            this.hp = new HPGui();
            setPadding(5);
            container = new Card(new GridLayout(0, 6));
            JScrollPane scrollPane = hp.getScrollPane(container);
            scrollPane.setHorizontalScrollBar(null);

            JScrollBar scrollBar = new JScrollBar(Adjustable.VERTICAL);
            scrollBar.setPreferredSize(new Dimension(8, 0));
            scrollPane.setVerticalScrollBar(scrollBar);
            add(scrollPane, BorderLayout.CENTER);
        }

      /*  public void addFirstLevel(JSONArray array){

        }

        public void updateBrowser(String treeDataChildren){

        }

        private void removeViews(){

        }*/

        public Card getContainer() {
            return container;
        }
    }

}
