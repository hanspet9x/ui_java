package services;

import containers.Card;
import controllers.OnFilesPicked;
import views.ExplorerIcon;
import views.UIDialogLoader;
import views.UIFilePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.List;

@SuppressWarnings("rawtypes")
public class UIFilePickerService extends MouseAdapter implements ActionListener {

    private Path source;

    private final Card iconsContainer;

    private final JLabel lSelected;

    private final JLabel lCurrentPath;

    private final HPGui hp;

    private static final int currentPathSize = 30;

    private String basePath;

    private String currentPath;

    private String previousPath;

    private final boolean oneFile;

    private final boolean showFolder;

    private final boolean watch;

    private boolean startWatch = true;

    private Map<Path, ExplorerIcon> selectedFiles = new HashMap<>();

    private List<ExplorerIcon> explorerCopy = new ArrayList<>();

    public UIFilePickerService(UIFilePicker uiFilePicker) {

        this.iconsContainer = uiFilePicker.getIconsContainer();
        this.source = uiFilePicker.getSourcePath();
        this.showFolder = uiFilePicker.isShowFolder();
        this.watch = uiFilePicker.isWatch();

        JButton bSelect = uiFilePicker.getbSelectButton();

        bSelect.addActionListener(this);
        uiFilePicker.getbSelectAll().addActionListener(this);

        this.lSelected = uiFilePicker.getlSelected();
        this.lCurrentPath = uiFilePicker.getlCurrentPath();
        this.hp = new HPGui();
        this.oneFile = uiFilePicker.isOneFile();

        basePath = this.source.toString();
        previousPath = basePath;


    }

    public void update(Path source) throws IOException {
        basePath = source.toString();
        previousPath = basePath;
       addIcons(source);
    }

    public void addIcons(Path source) throws IOException {

        currentPath = source.toString();
        setPreviousPath();
        purgeContainer();

        if(!basePath.equals(currentPath)){
            addLevelUpIcon();
        }
        add(source);

        setCurrentPath();

        this.source = source;

        if(watch && startWatch){
            new Thread(()-> {
                try {
                    startWatching();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        refreshContainer();
    }

    private void addLevelUpIcon(){
        setIconParam(Paths.get(previousPath), true);
    }

    /**
     * Add ExplorerIcon to iconsContainer.
     * keep a copy of icons added to a explorerCopy per copy.
     * @param source Path
     * @throws IOException
     */
    private void add(Path source) {
        explorerCopy.clear();
        try {
            Files.newDirectoryStream(source)
                    .forEach(path -> {
                        if(Files.isReadable(path)){
                            setIconParam(path, false);
                        }
                    });
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

       /* new UIDialogLoader("Loading", "please wait..")
                .open( e -> {

                });*/
    }

    private void setIconParam(Path path, boolean isLevelUp) {
        boolean isDir = path.toFile().isDirectory();

        if(isDir && !showFolder) return;
        ExplorerIcon icon = new ExplorerIcon
                (path.toString(), isLevelUp, isDir, UITreeService.TreeDataSource.PATH);

//        ExplorerIcon icon = new ExplorerIcon(path, isLevelUp);
        icon.addMouseListener(this);

        explorerCopy.add(icon);

        Card wrapper = new Card(new FlowLayout(FlowLayout.CENTER));
        wrapper.setPadding(2);
        wrapper.add(icon);
        iconsContainer.add(wrapper);
    }

    private void purgeContainer(){
        if(iconsContainer.getComponentCount() >  0)iconsContainer.removeAll();
    }

    private void refreshContainer(){
        iconsContainer.revalidate();
        iconsContainer.repaint();
    }

    private void setCurrentPath(){
        int l = currentPath.length();
        String sub = currentPath.substring(l > currentPathSize ? l - currentPathSize : 0, l);
        if(l > currentPathSize){
            lCurrentPath.setText("..."+sub);
        }
        else{
            lCurrentPath.setText(currentPath);
        }
    }

    private void setPreviousPath(){

        if(currentPath.substring(currentPath.lastIndexOf("\\")).trim().length() > 1){

            previousPath = currentPath.substring(0, currentPath.lastIndexOf("\\")+1);
        }


    }


    private void setIconBg(boolean active, ExplorerIcon icon){
        if(active){
            icon.setBackground(hp.getColor("#efeeff", .8f));
        }else{
            icon.setBackground(hp.getColor("#ffffff"));
        }

        icon.repaint();
    }

    private void fileSelection(MouseEvent e, ExplorerIcon icon) {
        /*
        if not dir
            if list is empty add file to list
            else add if ctrl isDown
         else
            empty list

         */

        if(!icon.isDirectory()){
            if(oneFile){
                oneFileOption(icon);
                return;
            }
            if (!e.isControlDown()) {
                selectedFiles.values().forEach(mIcon -> setIconBg(false, mIcon));
                selectedFiles = new HashMap<>();
            }
            selectedFiles.put(icon.getPath(), icon);

            setIconBg(true, icon);
        }

        lSelected.setText(selectedFiles.size()+" Selected.");
    }

    /*
    *
    * deselect previously selected, empty map, add the new icon.
    * */
    private void oneFileOption(ExplorerIcon icon) {
        selectedFiles.values().forEach(mIcon -> setIconBg(false, mIcon));
        selectedFiles = new HashMap<>();
        selectedFiles.put(icon.getPath(), icon);
        lSelected.setText(selectedFiles.size()+" Selected.");
    }

    private void startWatching() throws IOException {
        startWatch = false;
        WatchService service = FileSystems.getDefault().newWatchService();
        WatchKey key = Paths.get(basePath).register(service,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_DELETE,
                StandardWatchEventKinds.ENTRY_MODIFY);
        while(key.isValid()){

            List<WatchEvent<?>> watchEventList = key.pollEvents();

            watchEventList.forEach(watchEvent -> {
                try {
                    update(Paths.get(basePath));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);


        ExplorerIcon icon  = (ExplorerIcon)e.getSource();

        if(e.getClickCount() == 2 && !icon.isDirectory()){

            oneFileOption(icon);
            if(onFilesPicked != null){
                onFilesPicked.picked(icon.getPath());
            }
            if(onFilesPickedInst != null){
                onFilesPickedInst.picked(icon.getPath());
            }
        }

        if(e.getClickCount() == 2 && icon.isDirectory()){

            lSelected.setText("0 Selected.");
            try {
                addIcons(icon.getPath());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }else{
            fileSelection(e, icon);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        super.mouseEntered(e);
        ExplorerIcon icon = (ExplorerIcon)e.getSource();
        if(!selectedFiles.containsKey(icon.getPath())){
            setIconBg(true, icon);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        super.mouseExited(e);
        ExplorerIcon icon = (ExplorerIcon)e.getSource();
        if(!selectedFiles.containsKey(icon.getPath())){
            setIconBg(false, icon);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /*
        * add explorericon file to selected file
        * */
        if(e.getActionCommand().equals("s2")){
            selectedFiles = new HashMap<>();
            explorerCopy.forEach(explorerIcon -> {
                if(!explorerIcon.isDirectory()){
                    selectedFiles.put(explorerIcon.getPath(), explorerIcon);
                    setIconBg(true, explorerIcon);
                }
            });

            lSelected.setText(selectedFiles.size()+" Selected.");
        }

        //trigger interface;

        if(selectedFiles.size() > 0){
            if(onFilesPicked != null)
                onFilesPicked.picked(selectedFiles);
            if(onFilesPickedInst != null)
                onFilesPickedInst.picked(selectedFiles);
        }
    }

    static OnFilesPicked onFilesPicked = null;

    OnFilesPicked onFilesPickedInst = null;

    public void setOnFilesPickedInst(OnFilesPicked onFilesPickedInst) {
        this.onFilesPickedInst = onFilesPickedInst;
    }

    public static void setOnFilesPicked(OnFilesPicked onFilesPicked) {
        UIFilePickerService.onFilesPicked = onFilesPicked;
    }
}
