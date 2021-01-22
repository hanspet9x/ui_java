package views;


import containers.Card;
import services.HPGui;
import services.UIFilePickerService;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.*;

@SuppressWarnings("rawtypes")
public class UIFilePicker extends Card {

    private final Path sourcePath;
    private static HPGui hp;
    private Card iconsContainer;
    private boolean showFolder = true;
    private boolean watch = false;
    /*
    footer ui
     */
    private final JLabel lSelected, lCurrentPath;
    private JButton bSelect, bSelectAll;
    /**
     * if only one file should be selected.
     */
    private boolean oneFile;

    private int columns = 6;

    private UIFilePickerService chooserService;

    public UIFilePicker(Path sourcePath) {

        super(new BorderLayout());
        hp = new HPGui();

        if(!sourcePath.toFile().exists()) throw new FileSystemNotFoundException();
        lSelected = new JLabel("0 Selected");
        lCurrentPath = new JLabel("");
        this.sourcePath = sourcePath;

    }




    public void explore() throws IOException {

        iconsContainer = new Card(new GridLayout(0, columns));
        setPadding(10);
        JScrollPane scrollPane = hp.getScrollPane(iconsContainer);
        add(scrollPane);
        add(getFooter(), BorderLayout.PAGE_END);

        chooserService = new UIFilePickerService(this);
        chooserService.addIcons(sourcePath);

    }

    public void setShowFolder(boolean showFolder) {
        this.showFolder = showFolder;
    }

    public boolean isShowFolder() {
        return showFolder;
    }

    public void update(Path source) throws IOException {
        chooserService.update(source);
    }

    public Path getSourcePath() {
        return sourcePath;
    }

    public Card getIconsContainer() {
        return iconsContainer;
    }

    public JLabel getlSelected() {
        return lSelected;
    }

    public JLabel getlCurrentPath() {
        return lCurrentPath;
    }

    public JButton getbSelectButton() {
        return bSelect;
    }

    public boolean isOneFile() {
        return oneFile;
    }

    private Card getFooter(){

        Font font = new Font(HPGui.FontStandard, Font.PLAIN, 16);


        lSelected.setFont(font);
        lCurrentPath.setFont(font);
        lCurrentPath.setForeground(hp.getColor("#999999"));

        bSelect = new JButton("select");
        bSelect.setFont(font);
        bSelect.setActionCommand("s1");

        bSelectAll = new JButton("select files");
        bSelectAll.setActionCommand("s2");
        bSelectAll.setFont(font);

        Card card = new Card(new BorderLayout());

        card.add(hp.setAlignCenter(lSelected), BorderLayout.LINE_START);
        card.add(hp.setAlignCenter(lCurrentPath), BorderLayout.CENTER);
        Card buttons = HPGui.twoColumns(bSelect, bSelectAll, 10);
//        card.add(hp.setAlignCenter(bSelect));
        card.add(hp.setAlignCenter(buttons), BorderLayout.LINE_END);

        return card;
    }

    public void togglePathVisibility() {
        lCurrentPath.setVisible(!lCurrentPath.isVisible());
    }

    public void setOneFile(boolean oneFile) {
        this.oneFile = oneFile;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public boolean isWatch() {
        return watch;
    }

    public UIFilePickerService getChooserService() {
        return chooserService;
    }

    public void setWatch(boolean watch) {
        this.watch = watch;
    }

    public JButton getbSelectAll() {
        return bSelectAll;
    }
}
