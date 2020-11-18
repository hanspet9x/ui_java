package views;

import org.json.JSONArray;
import services.ExplorerIconService;
import services.HPGui;
import services.UITreeService;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SuppressWarnings("rawtypes")
public class ExplorerIcon extends Card{

    private String name;
    private boolean isDirectory;
    private StringBuilder fullPath = new StringBuilder();
    private boolean levelUp;
    private JLabel icon;
    private  JLabel label;
    private static ImageIcon folderIcon, emptyFolderIcon, fileIcon, levelUpIcon;
    String levelUpPath = "/views/chooser/level.png";
    String folderPath = "/views/chooser/folder.png";
    String emptyFolderPath = "/views/chooser/empty_folder.png";
    String filePath = "/views/chooser/file.png";
    private Path path;
    private int levelNo;
    /**
     * The main directory where the folder is found.
     * in e.g {name: deji, children: []}
     */
    private JSONArray mainTree;
    /**
     * The directory schema of this icon's container.
     * e.g children: []
     */
    private JSONArray dirTree;

    private UITreeService.TreeDataSource treeDataSource;

    private final HPGui hp = new HPGui();

    private Color bgOnEnter, bgOnOut;
    private boolean processIcon = true;
    private final int iconSize = 60;

    public ExplorerIcon(String name, boolean levelUp, boolean isDirectory, UITreeService.TreeDataSource treeDataSource) {
        setLayout(new FlowLayout(FlowLayout.CENTER));
        init(name, levelUp, isDirectory, treeDataSource);

        try {
            build();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ExplorerIcon(String name, String iconPath, boolean levelUp, boolean isDirectory, UITreeService.TreeDataSource treeDataSource) {
        setLayout(new FlowLayout(FlowLayout.CENTER));
        init(name, levelUp, isDirectory, treeDataSource);

        try {
            setIconImage(iconPath);
            build();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setIconImage(String iconPath) {
        processIcon = false;
        icon.setIcon(hp.getImageIconFromSystem(iconPath, iconSize+20, iconSize));
    }

    private void init(String name, boolean levelUp, boolean isDirectory, UITreeService.TreeDataSource treeDataSource){
        this.name = name;
        this.setToolTipText(name);
        this.isDirectory = isDirectory;
        this.levelUp = levelUp;
        icon = new JLabel();
        this.treeDataSource = treeDataSource;
        if(treeDataSource == UITreeService.TreeDataSource.PATH)this.path = Paths.get(name);

        fileIcon = hp.getImageIcon(filePath, iconSize, iconSize);
        folderIcon = hp.getImageIcon(folderPath, iconSize, iconSize);
        emptyFolderIcon = hp.getImageIcon(emptyFolderPath, iconSize, iconSize);
        levelUpIcon =  hp.getImageIcon(levelUpPath, iconSize, iconSize);

        bgOnEnter = hp.getColor("#efeeff", .8f);
        bgOnOut = hp.getColor("#ffffff");
    }

    private void build() throws IOException {
        setIcon();
            /*
            set container
             */

        int iconWidth = 100;
        int iconHeight = 125;
        hp.setAllSizes(this, iconWidth, iconHeight);
        this.setPadding(10);

        Border insideBorder = BorderFactory.createLineBorder(hp.getColor("#efefef"), 1, false);
        hp.setPadding(this, insideBorder, 5);

        add(icon);
        add(getIconLabel());

        new ExplorerIconService(this);
    }

    private JLabel getIconLabel(){
             /*
            set Label styling
             */

        if(treeDataSource == UITreeService.TreeDataSource.JSON){
            if(levelUp){
                label = new JLabel("../");
            }else{
                label = new JLabel(name);
            }
        }else{
            if(levelUp){
                label = new JLabel("../");
            }else{
                label = new JLabel(Paths.get(name).getFileName().toString());
            }
        }
        label.setFont(new Font(HPGui.FontStandard, Font.PLAIN, 12));
        hp.setAllSizes(label, 75, 25);
        label.setForeground(hp.getColor("#666666"));
        hp.setTopPadding(label, 5);
        label.setHorizontalAlignment(JLabel.CENTER);
        return label;
    }

    private void setIcon() throws IOException {

        if(!processIcon)return;

        if(levelUp){
            icon.setIcon(levelUpIcon);
            return;
        }

        if(isDirectory){
            if(treeDataSource == UITreeService.TreeDataSource.PATH){
                if(Files.size(path) > 0){
                    icon.setIcon(folderIcon);
                }else{
                    icon.setIcon(emptyFolderIcon);
                }
            }

        }else{
            icon.setIcon(fileIcon);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public JLabel getLabel() {
        return label;
    }

    public void setLabel(JLabel label) {
        this.label = label;
    }

    public Path getPath() {
        return path;
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
       if(isDirectory){
           if(dirTree.length() > 0){
               icon.setIcon(folderIcon);
           }else{
               icon.setIcon(emptyFolderIcon);
           }
       }else{
           icon.setIcon(fileIcon);
       }
    }

    public Color getBgOnEnter() {
        return bgOnEnter;
    }

    public Color getBgOnOut() {
        return bgOnOut;
    }

    public boolean isLevelUp() {
        return levelUp;
    }

    public String getFullPath() {
        return fullPath.toString();
    }

    public void setFullPath(String path){
        this.fullPath = new StringBuilder();
        fullPath.append(path);
    }
    public void addFullPath(String fullPath) {

        this.fullPath.append(fullPath);
    }

    public int getLevelNo() {
        return levelNo;
    }

    public void setLevelNo(int levelNo) {
        this.levelNo = levelNo;
    }
}
