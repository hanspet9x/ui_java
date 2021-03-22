package views;


import containers.Flex;
import containers.Card;
import controllers.PDFViewerImpl;
import model.FlexAlignment;
import model.FlexDirection;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import services.HPGui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@SuppressWarnings("rawtypes")
public class PDFViewerOld extends JDialog implements PDFViewerImpl {

    private static int currentIndex = 0;
    private static int thumbnailWidth = 0;
    private static int thumbnailHeight = 0;
    private static final int sidePadding = 20;
    private static String filePath = "";
    private static PDFViewerOld pdfViewer = null;
    private static PDDocument document;
    private static PDFRenderer renderer;
    private JScrollPane thumbnailScroller;
    private static final int thumbnailBottomMargin = 5;

    private static ImageView selectedImageView;

    public static void open(String path) throws IOException {
        reset(path);
      filePath = path;
      document = PDDocument.load(new File(path));
      renderer = new PDFRenderer(document);

      new UIDialogLoader("Loading", "please wait")
              .open( e -> {
                  try {
                      getInstance().init();

                  } catch (IOException ioException) {
                      ioException.printStackTrace();
                  }
              });

    }

    private static void reset(String path){
        if(!filePath.equals(path)){
            currentIndex = 0;
            thumbnailWidth = 0;
            thumbnailHeight = 0;
            filePath = path;
        }
    }

    private void init() throws IOException {

        addKeyListener(getKeyAdapter());

        Dimension screenDimension = HPGui.getScreenSize();
        int screenWidth = screenDimension.width;
        int screenHeight = screenDimension.height;

        setSize(screenWidth, screenHeight);

        thumbnailWidth = HPGui.getPercent(20, screenWidth) - (sidePadding * 2);
        thumbnailHeight = HPGui.getA4Height(thumbnailWidth);

        Card header = new Card();
        header.setLayout(new BoxLayout(header, BoxLayout.LINE_AXIS));
        header.add(getImageOption2());
        header.add(Box.createHorizontalGlue());
        header.add(getImageOption());
        header.setSize(screenWidth, 70);

        int height = screenHeight - header.getPreferredSize().height;

        Card sideContainer = new Card(new BorderLayout());
        sideContainer.setBackground(HPGui.getColor("#efefef"));
        sideContainer.setPadding(0);
        sideContainer.setPaddingTop(sidePadding);
        sideContainer.setPaddingBottom(sidePadding);
        sideContainer.add(getThumbNailsContainer());
        sideContainer.setCardSize(HPGui.getPercent(20, screenWidth), height);

        int imgWidth = HPGui.getPercent(80, screenWidth);

        add(header, BorderLayout.PAGE_START);
        add(sideContainer, BorderLayout.LINE_START);
        add(getSelectedImageView(imgWidth, HPGui.getA4Height(imgWidth)), BorderLayout.CENTER);

        setTitle(Paths.get(filePath).getFileName().toString());
        setAlwaysOnTop(true);
        setVisible(true);

    }

    private JScrollPane getThumbNailsContainer() throws IOException {

        Flex flex = new Flex(FlexDirection.COLUMN, FlexAlignment.CENTER);
        flex.setYSpace(5);

        for (int i = 0; i < document.getNumberOfPages(); i++) {
            flex.addComponent(createThumbNail(i));
        }

        thumbnailScroller = HPGui.getScrollPane(flex.build());
        return thumbnailScroller;
    }

    private Card getImageOption2(){
        UIFieldButton fieldButton = new
                UIFieldButton("/icons/right_small.png", "page", 80, 25, "#333333");
        fieldButton.setBackground(HPGui.getColor("#efefef"));
        fieldButton.onClickSubmit( e -> {
            try {
                currentIndex = Integer.parseInt(fieldButton.getText()) - 1;
                showImage(currentIndex);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        });

        JLabel total = new JLabel(" of "+document.getNumberOfPages());
        JButton previous = new JButton(HPGui.getImageIcon("/icons/circled_left.png"));
        JButton next = new JButton(HPGui.getImageIcon("/icons/circled_right.png"));

        previous.addActionListener( e -> {
            try {
                previous();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        next.addActionListener( e -> {
            try {
                next();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        Card card = new Card();
        card.setLayout(new BoxLayout(card, BoxLayout.LINE_AXIS));
        card.setPadding(0);

        card.add(fieldButton);
        card.add(total);
        card.add(HPGui.getBox(10, 0));
        card.add(previous);
        card.add(next);

        return card;
    }

    private Card getImageOption(){


        JButton delete = new JButton(HPGui.getImageIcon("/icons/delete.png"));
        JButton share = new JButton(HPGui.getImageIcon("/icons/share.png"));
        JButton forward = new JButton(HPGui.getImageIcon("/icons/forward.png"));
        JButton download = new JButton(HPGui.getImageIcon("/icons/download.png"));

        delete.addActionListener(e-> delete());
        share.addActionListener(e -> share());
        forward.addActionListener(e -> forward());
        download.addActionListener(e -> download());


        Card options = new Card();
        options.setBackground(HPGui.getColTranslucent());
        options.setPadding(0);
        options.setLayout(new FlowLayout(FlowLayout.CENTER));
        options.add(delete);
        options.add(share);
        options.add(forward);
        options.add(download);
        return options;
    }

    private JScrollPane getSelectedImageView(int w, int h) throws IOException {
        selectedImageView = new ImageView(renderer.renderImage(0), w, h);
        return HPGui.getScrollPane(selectedImageView);
    }

    private static PDFViewerOld getInstance(){
        if(pdfViewer == null){
            pdfViewer = new PDFViewerOld();
        }
        return pdfViewer;
    }

    @Override
    public void next() throws IOException {

        if(currentIndex < document.getNumberOfPages() -1){
            currentIndex +=1;
            showImage(currentIndex);
            scrollThumbNail(currentIndex);
        }
    }

    @Override
    public void previous() throws IOException {
        if(currentIndex > 0){
            currentIndex -= 1;
            showImage(currentIndex);
            scrollThumbNail(currentIndex);
        }
    }



    @Override
    public Flex createFlexThumbNail(int index) throws IOException {
        BufferedImage thumbnail = renderer.renderImage(index);
        ImageView imageView = new ImageView(thumbnail.getScaledInstance(thumbnailWidth, thumbnailHeight, Image.SCALE_DEFAULT));

        imageView.onClick( e -> {
            try {
                showImage(index);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        imageView.setCursor(HPGui.getHandCursor());

        JLabel label = new JLabel(String.valueOf(index + 1));

        Flex flex = new Flex(FlexDirection.COLUMN, FlexAlignment.CENTER);
        flex.addComponent(imageView);
        flex.addComponent(label);

        return flex.build();
    }

    @Override
    public ImageView createThumbNail(int index) throws IOException {
        BufferedImage thumbnail = renderer.renderImage(index);
        ImageView imageView = new ImageView(thumbnail.getScaledInstance(thumbnailWidth, thumbnailHeight, Image.SCALE_DEFAULT));
        imageView.onClick( e -> {
            try {
                currentIndex = index;
                showImage(index);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        imageView.setCursor(HPGui.getHandCursor());

        return imageView;
    }


    @Override
    public void showImage(int index) throws IOException {
        selectedImageView.swap(renderer.renderImage(index));
    }

    @Override
    public void scrollThumbNail(int index) {
        thumbnailScroller.getViewport().setViewPosition(new Point(0, (thumbnailHeight * index) + (thumbnailBottomMargin) * index));
    }

    @Override
    public void download() {

    }

    @Override
    public void delete() {

    }

    @Override
    public void share() {

    }

    @Override
    public void forward() {

    }

    private KeyAdapter getKeyAdapter(){
        return new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                HPGui.log(e.getKeyCode());
                super.keyPressed(e);
            }

            @Override
            public void keyTyped(KeyEvent e) {
                HPGui.log(e.getKeyCode());
                super.keyTyped(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                HPGui.log(e.getKeyCode());
                super.keyReleased(e);
            }
        };
    }
}
