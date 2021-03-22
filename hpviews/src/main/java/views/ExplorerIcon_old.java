package views;

import containers.Card;
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
public class ExplorerIcon_old extends Card {

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

    public ExplorerIcon_old(String name, boolean levelUp, boolean isDirectory, UITreeService.TreeDataSource treeDataSource) {
        setLayout(new FlowLayout(FlowLayout.CENTER));
        init(name, levelUp, isDirectory, treeDataSource);

        try {
            build();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ExplorerIcon_old(String name, String iconPath, boolean levelUp, boolean isDirectory, UITreeService.TreeDataSource treeDataSource) {
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
        icon.setIcon(HPGui.getImageIconFromSystem(iconPath, iconSize+20, iconSize));
    }

    private void init(String name, boolean levelUp, boolean isDirectory, UITreeService.TreeDataSource treeDataSource){
        this.name = name;
        this.setToolTipText(name);
        this.isDirectory = isDirectory;
        this.levelUp = levelUp;
        icon = new JLabel();
        this.treeDataSource = treeDataSource;
        if(treeDataSource == UITreeService.TreeDataSource.PATH)this.path = Paths.get(name);

        fileIcon = HPGui.getImageIcon(filePath, iconSize, iconSize);
        folderIcon = HPGui.getImageIcon(folderPath, iconSize, iconSize);
        emptyFolderIcon = HPGui.getImageIcon(emptyFolderPath, iconSize, iconSize);
        levelUpIcon =  HPGui.getImageIcon(levelUpPath, iconSize, iconSize);

        bgOnEnter = HPGui.getColor("#efeeff", .8f);
        bgOnOut = HPGui.getColor("#ffffff");
    }

    private void build() throws IOException {
        setIcon();
            /*
            set container
             */

        int iconWidth = 100;
        int iconHeight = 125;
        HPGui.setAllSizes(this, iconWidth, iconHeight);
        this.setPadding(10);

        Border insideBorder = BorderFactory.createLineBorder(HPGui.getColor("#efefef"), 1, false);
        HPGui.setPadding(this, insideBorder, 5);

        add(icon);
        add(getIconLabel());

//        new ExplorerIconService(this);
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
        HPGui.setAllSizes(label, 75, 25);
        label.setForeground(HPGui.getColor("#666666"));
        HPGui.setTopPadding(label, 5);
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

    private String chooseFilePath(String filePath){

        switch (HPGui.getExt(filePath)){

            case ".jpg", ".png", ".gif", ".jpeg", ".tiff" -> {
                return getFileIcon("image_file.png");
            }
            case ".mp4", ".avi", ".mov", ".mkv", ".flv", ".swf" -> {
                return getFileIcon("video_file.png");
            }
            case ".ogg", ".mp3", ".m4a", ".wav", ".mpeg-4", ".midi", ".wma", ".aac" ->{
                return getFileIcon("audio_file.png");
            }
            case ".pdf" -> {
                return getFileIcon("pdf.png");
            }
            case ".doc", ".docx" -> {
                return getFileIcon("doc.png");
            }
            case ".xls", ".xlsx" -> {
                return getFileIcon("xls.png");
            }
            case ".ppt", ".pptx" -> {
                return getFileIcon("ppt.png");
            }
            default -> {
                return getFileIcon("file.png");
            }
        }
    }

    private String getFileIcon(String name){
        return "/views/chooser/"+name;
    }
}
