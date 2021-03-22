package views;

import animations.Rotate;
import containers.Card;
import containers.Flex;
import controllers.OnUITreeExplorerUpdate;
import model.FlexAlignment;
import model.FlexDirection;
import org.json.JSONArray;
import services.HPGui;
import services.UIExplorerService;
import services.UITreeService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("rawtypes")
public class UITreeExplorer extends Card implements OnUITreeExplorerUpdate{
    public static int WIDTH = 200;
    private String treeData;
    private static HPGui hp;

    private int stopUpdateOnLevel = -1;
    private static final int fixedTreeWidth = 200;
    public static final String levelKey = "level";
    public static final String nameKey = "name";
    public static final String dirKey = "directory";
    public static final String childrenKey = "children";

    private  Card treeContainer;
    private  Explorer explorer;
    private  UITreeService.TreeDataSource treeDataSource;
    private UIExplorerService uiExplorerService;
    private UITreeService uiTreeService;
    private Component footer = null;
    private JScrollPane scrollPane;


    public UITreeExplorer(String treeData, UITreeService.TreeDataSource treeDataSource) {
        super(new BorderLayout());
        common(treeData, treeDataSource);
    }

    public UITreeExplorer(String treeData, Component footer, UITreeService.TreeDataSource treeDataSource) {
        super(new BorderLayout());
        this.footer = footer;
        common(treeData, treeDataSource);
    }

    private void common(String treeData, UITreeService.TreeDataSource treeDataSource){
        this.treeData = treeData;
        this.explorer = new Explorer();
        this.treeDataSource = treeDataSource;
        this.treeContainer = new Card();//new Flex(FlexDirection.COLUMN, FlexAlignment.LEFT);
    }

    public void build(){

        HPGui.setAllSizes(this, 700, 500);
        treeContainer.setPadding(1);
        scrollPane  = HPGui.getScrollPane();

        HPGui.setAllSizes(scrollPane, fixedTreeWidth, 500);
        treeContainer.setLayout(new BoxLayout(treeContainer, BoxLayout.PAGE_AXIS));
        uiTreeService = new UITreeService(this);
        uiTreeService.drawLevels(treeData);

        uiExplorerService = new UIExplorerService(this);
        uiExplorerService.explore(treeData);


     /*   OverflowView overflowView = new OverflowView(treeContainer, fixedTreeWidth);
        overflowView.setOffsetComponentHeight(60);
        overflowView.setBorder(BorderFactory.createLineBorder(Color.RED));*/

        scrollPane.getViewport().add(treeContainer);

        add(scrollPane, BorderLayout.LINE_START);
        add(explorer, BorderLayout.CENTER);

        if(footer != null)add(footer, BorderLayout.PAGE_END);

    }


    public int getStopUpdateOnLevel() {
        return stopUpdateOnLevel;
    }

    public void treeDoubleClicked(Tree tree){

    }

    public JScrollPane getScrollPane() {
        return scrollPane;
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
        private final StringBuilder treePath = new StringBuilder();
        private JLabel lIndicator;
        private static int levelSpace = 2;
        private Card<Tree> innerCard;
        private boolean collapsed = true;
        private boolean hasChildren = false;
        private JSONArray mainTree;
        private JSONArray dirTree;
        private final JLabel label;
        private JLabel icon;
        private final Rotate rotate;

        public Tree(int level, boolean isDirectory, String name) {
            this.level = level;
            this.isDirectory = isDirectory;
            this.name = name;
            this.setToolTipText(name);
            this.lIndicator = new JLabel(HPGui.getImageIcon("/views/explorer/caret.png", 10, 10));
            this.rotate = new Rotate(lIndicator);
            this.rotate.setRate(10);
            this.label = new JLabel(this.name);
            build();
        }



        private void build(){
            setPadding(2);
            if(level > 1){
                HPGui.setLeftPadding(this, levelSpace * level);
            }

            innerCard = new Card<>();

          HPGui.setAllSizes(this, fixedTreeWidth, HEIGHT);
            if(isDirectory)innerCard.add(getJIndicator());
            innerCard.add(getJIcon());
            innerCard.add(getJLabel());
            innerCard.setObject(this);
            add(innerCard);
        }

        private Rotate getJIndicator(){
            HPGui.setRightPadding(rotate, 8);
            return rotate;
        }

        private JLabel getJLabel(){
            label.setFont(new Font(HPGui.FontStandard, Font.PLAIN, 13));

            return label;
        }

        private JLabel getJIcon(){
            String iconPath = isDirectory? "/views/explorer/folder.png" : "/views/explorer/file.png";
            icon = new JLabel(HPGui.getImageIcon(iconPath, 12, 12));

            if(!isDirectory){
                HPGui.setPadding(icon, 0, 18, 0, 5);
            }else{
                HPGui.setRightPadding(icon, 5);
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

        public Rotate getRotate() {
            return rotate;
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
    public static class Explorer extends Card {

        private String treeData;
        private final Card container;

        public Explorer() {
            setLayout(new BorderLayout());
            setPadding(5);
            container = new Card(new GridLayout(0, 6));
            JScrollPane scrollPane = HPGui.getScrollPane(container);
            scrollPane.setHorizontalScrollBar(null);

            JScrollBar scrollBar = new JScrollBar(Adjustable.VERTICAL);
            scrollBar.setPreferredSize(new Dimension(8, 0));
            scrollPane.setVerticalScrollBar(scrollBar);
            add(scrollPane, BorderLayout.CENTER);
        }

      /*
        public void addFirstLevel(JSONArray array){

        }

        public void updateBrowser(String treeDataChildren){

        }

        private void removeViews(){

        }
        */

        public Card getContainer() {
            return container;
        }
    }

}
