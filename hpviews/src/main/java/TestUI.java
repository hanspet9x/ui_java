
import containers.*;
import controllers.AncestorAdapter;
import controllers.OnUIExplorerSelected;
import controllers.OnUIViewScrolled;
import forms.InputField;
import forms.PasswordField;
import forms.SelectField;
import model.FlexAlignment;
import model.FlexDirection;
import services.HPGui;
import services.UIExplorerService;
import services.UITreeService;
import views.*;
import views.Button;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.AncestorEvent;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Objects;


public class TestUI extends JFrame{

    public TestUI() throws HeadlessException {
        setLayout(new BorderLayout());
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void testUIScroller(){

        UIViewScroller viewScroller = new UIViewScroller();
        for (int i = 0; i < 4; i++) {
            Card card = new Card();
            card.add(new JLabel("card "+i));
            JButton button  = new JButton("Button");

            button.addActionListener(e -> {
                card.revalidate();
                HPGui.log(viewScroller.getCurrentPageIndex());
            });
            card.add(button);


            card.setBackground(HPGui.getRandomColor());
            card.setPreferredSize(new Dimension(300, 400));
            viewScroller.addComponent(card, "card "+i);
        }

        UIViewScroller.setOnUIViewScrolled(new OnUIViewScrolled() {
            @Override
            public void scrolled(String id) {
                HPGui.log(id);
            }
        });

        viewScroller.setBgColor(Color.RED);
        viewScroller.setPadding(20);

        JButton next = new JButton("next");
        JButton previous = new JButton("prev");

        next.addActionListener(event->viewScroller.next());
        previous.addActionListener(event->viewScroller.previous());

        add(next, BorderLayout.PAGE_START);
        add(previous, BorderLayout.PAGE_END);
        add(viewScroller.build());
    }

    int height = 300;
    private void testOverflowView(){

        Flex flex = new Flex(FlexDirection.COLUMN, FlexAlignment.CENTER);
        flex.setYSpace(20);

        for (int i = 0; i < 2; i++) {

            Card card = new Card();
            card.setBackground(HPGui.getRandomColor());
            HPGui.setAllSizes(card, 700, height);
            flex.add(card);
        }

        Card card = new Card();
        HPGui.setAllSizes(card, 200, height);
        card.setBackground(Color.RED);
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                HPGui.setAllSizes(card, 200, height+=100);
            }
        });

//        setPreferredSize(new Dimension(200, 900));
        OverflowView overflowView = new OverflowView(card, 200);

        add(new JButton("normal button"), BorderLayout.CENTER);
        add(overflowView, BorderLayout.LINE_START);
    }

    private void testBorderLayoutWidth(){
        Card child = new Card();
        child.setBackground(Color.RED);
        child.addAncestorListener(new AncestorAdapter() {
            @Override
            public void ancestorAdded(AncestorEvent event) {
                HPGui.log(event.getComponent().getHeight());
            }
        });

        Card last  = new Card();
        HPGui.setAllSizes(last, 200, 400);
        last.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        last.addAncestorListener(new AncestorAdapter() {
            @Override
            public void ancestorAdded(AncestorEvent event) {
                HPGui.log(event.getComponent().getWidth(), event.getComponent().getHeight());
            }
        });

        JButton button = new JButton("nam");
        button.setPreferredSize(new Dimension(198, 2000));
        OverflowView overflowView = new OverflowView(button, 200);
        overflowView.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        last.add(overflowView);

        Card parent  = new Card();
        parent.setLayout(new BorderLayout());
        parent.add(child);
        parent.add(last, BorderLayout.LINE_START);
        add(parent);
    }

    JScrollPane scrollPane = null;

    private void testScrollPane(){



//        Card container = new Card();
//        container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
//        container.setBorder(BorderFactory.createLineBorder(Color.BLUE));
//        container.add(child);

        Flex flex = new Flex(FlexDirection.COLUMN, FlexAlignment.CENTER);
        flex.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        flex.setYSpace(10);

        for (int i = 0; i < 10; i++) {
            Card child = new Card();
            HPGui.setAllSizes(child, 100, 200);
            child.setBackground(Color.RED);
            flex.addComponent(child);
        }
        scrollPane = HPGui.getScrollPane(flex.build());
        add(scrollPane, BorderLayout.LINE_START);
        add(new JButton("Ss"), BorderLayout.CENTER);
    }

    private void testExplorer(){
        String data = """
                [
                    {"children": [{"children":
                            [{"children": [
                                        {"children":[],"level":4,"name":"bidding","id":0,"directory":true},
                                        {"children":[],"level":4,"name":"TOR","id":0,"directory":true},
                                        {"children":[],"level":4,"name":"report","id":0,"directory":true}
                                       ],
                            "level":3,"name":"tawol","id":0,"directory":true},
                            {"children":[{"children":[],"level":4,"name":"bidding","id":0,"directory":true},{"children":[],"level":4,"name":"TOR","id":0,"directory":true}],"level":3,"name":"DAX","id":0,"directory":true}],"level":2,"name":"bidding","id":0,"directory":true},{"children":[{"children":[{"children":[],"level":4,"name":"bidding","id":0,"directory":true}],"level":3,"name":"Bing","id":0,"directory":true}],"level":2,"name":"Financials","id":0,"directory":true}],"level":1,"name":"workx","id":0,"directory":true},{"children":[{"children":[{"children":[{"children":[],"level":4,"name":"TOR","id":0,"directory":true},{"children":[],"level":4,"name":"report","id":0,"directory":true}],"level":3,"name":"DAX","id":0,"directory":true},{"children":[{"children":[],"level":4,"name":"bidding","id":0,"directory":true},{"children":[],"level":4,"name":"report","id":0,"directory":true}],"level":3,"name":"tawol","id":0,"directory":true}],"level":2,"name":"Financials","id":0,"directory":true},{"children":[{"children":[{"children":[],"level":4,"name":"bidding","id":0,"directory":true}],"level":3,"name":"Bing","id":0,"directory":true}],"level":2,"name":"BIMG","id":0,"directory":true}],"level":1,"name":"consultancies","id":0,"directory":true}]
                """;

        UITreeExplorer treeExplorer = new UITreeExplorer(data, UITreeService.TreeDataSource.JSON);
        UIExplorerService.setOnUIExplorerSelected(new OnUIExplorerSelected() {
            @Override
            public void mainIcon(ExplorerIcon icon) {
//                HPGui.log("interface", icon.getFullPath());
            }
        });
        UIFilePicker filePicker = new UIFilePicker(Paths.get("C:\\Users\\payod\\Documents"));

        try {
            filePicker.explore();
        } catch (IOException e) {
            e.printStackTrace();
        }

        containers.Card cont = treeExplorer.getExplorer().getContainer();
        cont.setLayout(new BorderLayout());
        cont.add(filePicker);
        treeExplorer.build();

        add(treeExplorer);
    }

    private void testFilePicker(){
        UIFilePicker filePicker = new UIFilePicker(Paths.get("C:\\Users\\payod\\Documents"));

        try {
            filePicker.explore();
        } catch (IOException e) {
            e.printStackTrace();
        }

        add(filePicker);
    }

    private void testValidator(){
        Card card = new Card(){
            @Override
            public boolean isValidateRoot() {
                HPGui.log("validated");
                return super.isValidateRoot();
            }
        };
        JButton button = new JButton("validate"){
            @Override
            public boolean isValidateRoot() {
                HPGui.log("isValidateRoot");
                return super.isValidateRoot();
            }
        };

        button.addActionListener( e -> button.revalidate());


        card.add(button);
        add(card);
    }

    private void testUIDialog(){

        Card card = new Card();
        HPGui.setAllSizes(card, 400, 120);
        card.setBackground(Color.RED);

        new UIDialog(card)
                .setActionTwoText("Start Process")
                .setCallbacks(System.out::println)
                .open();
    }

    private void testFlexComponent(){

        Flex flex = new Flex(FlexDirection.ROW, FlexAlignment.RIGHT);
        flex.setXSpace(10);
        JButton button = new JButton("name");
        button.addActionListener( e -> {
           UIDialogLoader ui =  new UIDialogLoader("Loading", "please wait...");
            ui.open( d -> {
                HPGui.log("active");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            });
        });
        flex.addComponent(button);
        flex.addComponent(new JButton("school"));
        flex.addComponent(new JButton("email"));

        Card card = new Card();
        card.add(flex.build());

        add(card);
    }

    private void testCenterComponent() throws InterruptedException {

        Card card = new Card();
        card.setBackground(Color.RED);
        HPGui.setAllSizes(card, 200, 200);

        Card card2 = new Card();
        card.setBackground(Color.BLUE);
        HPGui.setAllSizes(card2, 200, 200);

        Center center = new Center(card);
        add(center);

    }

    private void testIconTag(){
        ImageView imageView = new ImageView("views/chooser/folder.png", 30, 30);
        IconTag iconTag = new IconTag(imageView, "10");

        Card card = new Card();
        add(iconTag.build());

        add(card);
    }

    private void testImageViewer(){
        try {
            String imagePath = "C:\\Users\\payod\\OneDrive\\Pictures\\WEBPHOTOS\\Anchor\\block.JPG";
            new ImageViewer().open(imagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void testPDFViewer(){

        try {
            String imagePath = "C:\\Users\\payod\\OneDrive\\Documents\\pdfs\\157581.pdf";
            new PDFViewer().open(imagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void testSelectView(){

        InputField inputField = new InputField();
        inputField.setBackgroundColor(Color.RED);
        inputField.formBuild();

        PasswordField passwordField = new PasswordField();
        SelectField<String> selectField = new SelectField<>("ade", "tola", "tona");

        JComboBox<String> comboBox  = new JComboBox<>(new String[]{"Ade", "tolu", "bimpe", "sola"});

        SelectField<String> comboBox2 = new SelectField<>(new String[]{"Ade", "tolu"});

        JButton button = new JButton("submit");
        button.addActionListener(e -> {
            comboBox.showPopup();
        });

        Flex flex = new Flex(FlexDirection.COLUMN, FlexAlignment.CENTER);
        flex.setYSpace(10);
        flex.addComponent(button);
        flex.addComponent(inputField);
        flex.addComponent(passwordField);
        flex.addComponent(selectField);
        flex.addComponent(comboBox);
        flex.addComponent(comboBox2);

        Card card = new Card();
        card.add(comboBox2);
        add(card);
    }

    private void testUIWindow(){
        Card card = new Card();
        card.setCardSize(200, 200);
        card.setBackground(Color.RED);

        UIWindow window = new UIWindow("card", card, true);
        window.open();
    }

    private ImageView testImageView() throws InterruptedException, IOException {

        ImageView imageView = new ImageView("/icons/red_close.png", 50, 50);


        Card card = new Card();
        card.add(imageView);

        add(card);

        return imageView;
    }

    private void testImageColor() throws IOException {
        BufferedImage bufferedImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().
                getResource("icons/download.png")));

        for (int x = 0; x < bufferedImage.getWidth(); x++) {

            for (int y = 0; y < bufferedImage.getHeight(); y++) {

                Color imageColor = new Color(bufferedImage.getRGB(x, y), true);

                String type = "";

                switch (imageColor.getTransparency()){
                    case Transparency.BITMASK -> type = "bitmask";
                    case Transparency.TRANSLUCENT -> type = "translucent";
                    case Transparency.OPAQUE -> type = "opaque";
                }

                HPGui.log(imageColor.getRed(), imageColor.getGreen(), imageColor.getBlue(), type);
            }
        }
    }

     void testButton(){
        Card card = new Card();
        Button button = new Button("Submit");
        button.setBackground(Color.GREEN);
        card.add(button.build());

        add(card);
    }
    NotificationIcon testNotificationIcon(){
        NotificationIcon icon = new NotificationIcon("views/chooser/folder.png");
        Card card = new Card();
        card.add(icon);
        add(card);
        return icon;
    }

    void testPlaceholder(){
        Card card = new Card(new BorderLayout());
        card.add(new JLabel("Header"), BorderLayout.PAGE_START);
        card.add(new Placeholder("No Text"));

        UIViewScroller viewScroller = new UIViewScroller();
        viewScroller.addComponent(card, "card");
        add(viewScroller.build());
    }

    void testExpandedView(){
        Card wrapper  = new Card();
        wrapper.setBorder(BorderFactory.createLineBorder(Color.RED));

        ExpandedView view = new ExpandedView(FlexDirection.COLUMN);
        for (int i = 0; i < 10; i++) {
            Card card = new Card();
            card.setCardSize(250, 60);
            card.setBorder(BorderFactory.createLineBorder(Color.BLUE));

            view.add(card);
            view.addSpace(10);

        }

        wrapper.add(view.build());
        add(wrapper);
    }


    void testHPDialog(){

        HPDialog hp = new HPDialog(this);
        hp.error("title", "hello");
    }

    UIDialogLoader testUIDialogLoader(){
        return new UIDialogLoader("Loading", "").open();
    }
    public static void main(String[] args) throws InterruptedException, IOException {

        HPGui.init();
        TestUI testUI = new TestUI();
//        testUI.testFilePicker();
//        testUI.testExplorer();
//        testUI.testCenterComponent();
//        testUI.testScrollPane();
        testUI.testImageViewer();
//        testUI.testPDFViewer();
//        testUI.testOverflowView();
//        testUI.testSelectView();
//        testUI.testUIWindow();
//        testUI.testUIDialog();
//        testUI.testUIScroller();
//        testUI.testImageColor();
//        ImageView im = testUI.testImageView();
//        im.setColor(Color.BLUE);
//        NotificationIcon icon = testUI.testNotificationIcon();
//        testUI.testPlaceholder();
//        testUI.testButton();
//        testUI.testExpandedView();
//        testUI.setVisible(true);
//
//        Thread.sleep(2000);
//        icon.setActive(true);
//        im.toggleAlpha(false);
//        Thread.sleep(2000);
//        im.toggleAlpha(true);
//        testUI.testHPDialog();
//        testUI.testUIDialogLoader();
    }

}

