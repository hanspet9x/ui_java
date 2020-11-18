import com.google.gson.Gson;
import controllers.OnFilesPicked;
import controllers.OnUIExplorerSelected;
import controllers.OnUITreeExplorerUpdate;
import org.json.JSONArray;
import services.HPGui;
import services.UIExplorerService;
import services.UIFilePickerService;
import services.UITreeService;
import views.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Map;


public class TestUI extends JFrame implements OnFilesPicked, OnUIExplorerSelected {

    public TestUI() throws HeadlessException {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 550);
        setLocationRelativeTo(null);
        UIFilePickerService.setOnFilesPicked(this);
        UIExplorerService.setOnUIExplorerSelected(this);
    }

    public UIScroller getScroller(){
        UIScroller uiScroller = new UIScroller(500, 500);

        UIScroller.ScrollCard card1 = new UIScroller.ScrollCard("one");
        card1.add(new JLabel("One"));
        UIScroller.ScrollCard card2 = new UIScroller.ScrollCard("two");
        card2.add(new JLabel("Two"));
        UIScroller.ScrollCard card3 = new UIScroller.ScrollCard("three");
        card3.add(new JLabel("Three"));
        uiScroller.addScrollCard(card1);
        uiScroller.addScrollCard(card2);
        uiScroller.addScrollCard(card3);

        return uiScroller.build();
    }
    public static void main(String[] args) throws InterruptedException, IOException {
        TestUI testUI = new TestUI();
        String json2 = "[{\"id\":0,\"name\":\"Booble\",\"children\":[{\"id\":0,\"name\":\"Bande and Collection\",\"children\":[{\"id\":0,\"name\":\"TAWOL\",\"children\":[{\"id\":0,\"name\":\"Bidding report\",\"children\":[],\"level\":4,\"directory\":true}],\"level\":3,\"directory\":true}],\"level\":2,\"directory\":true}],\"level\":1,\"directory\":true}]";

        String json = "[{\"id\":0,\"name\":\"Billing\",\"children\":[{\"id\":0,\"name\":\"Billing and Collection\",\"children\":[{\"id\":0,\"name\":\"TAWOL\",\"children\":[{\"id\":0,\"name\":\"Bidding report\",\"children\":[],\"level\":4,\"directory\":true}],\"level\":3,\"directory\":true}],\"level\":2,\"directory\":true}],\"level\":1,\"directory\":true}]";
        UITreeExplorer treeExplorer = new UITreeExplorer(json, new JButton("add"), UITreeService.TreeDataSource.JSON);
        treeExplorer.setStopUpdateOnLevel(3);
        treeExplorer.build();

        setOnUITreeExplorerUpdate(treeExplorer);

        UIFilePicker filePicker = new UIFilePicker(Paths.get("C:\\Users\\Peter A. Akinlolu\\Documents\\edms"));
        filePicker.setShowFolder(false);
        filePicker.setWatch(true);
        filePicker.explore();
        UIFilePickerService.setOnFilesPicked(testUI);
        filePicker.getChooserService().setOnFilesPickedInst(new OnFilesPicked() {
            @Override
            public void picked(Map<Path, ExplorerIcon> files) {

            }

            @Override
            public void picked(Path path) {
                System.out.println(path);
            }
        });

        testUI.add(filePicker);

/*
        SelectView view = new SelectView(new String[]{"yes", "no"});
        view.setBorder(BorderFactory.createLineBorder(Color.BLACK));



        HPGui hp = new HPGui();
        hp.setAllSizes(view, 100, 80);
        Card c = new Card();
        c.add(view);

        testUI.add(c);*/
        testUI.setVisible(true);

    }


    @Override
    public void picked(Map<Path, ExplorerIcon> files) {
        System.out.println(files.size());
    }

    @Override
    public void picked(Path path) {
        System.out.println("dey here");
    }

    @Override
    public void mainIcon(ExplorerIcon icon) {
        System.out.println(icon.getFullPath().toString());
    }

    static OnUITreeExplorerUpdate onUITreeExplorerUpdate;

    public static void setOnUITreeExplorerUpdate(OnUITreeExplorerUpdate onUITreeExplorerUpdate) {
        TestUI.onUITreeExplorerUpdate = onUITreeExplorerUpdate;
    }
}
