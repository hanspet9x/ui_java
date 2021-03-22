package views;


import containers.Card;
import containers.Center;
import controllers.ImageViewerImpl;
import services.HPGui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("rawtypes")
public class ImageViewer extends JDialog implements ImageViewerImpl {



    private int screenWidth = 0;
    private int screenHeight = 0;
    private String filePath;
    private  ImageView imageView;
    private  int currentFileIndex = 0;
    private  List<String> imageFileNames = new ArrayList<>();
    private  String imagesDirectory = null;
    private  Center imageWrapper;

    private  Consumer<ImageViewerOption> consumer = null;
    /**
     * It takes the filePath, initiate, and resizes the image
     * @param filePath String
     */

    public  void open(String filePath) throws IOException {
        common(filePath);
    }

    public  void open(String path, Consumer<ImageViewerOption> consumer) throws IOException {
        this.consumer = consumer;
        common(path);
    }

    private  void common(String filePath) throws IOException {

       this.filePath = filePath;

        Dimension dim = HPGui.getScreenSize();
        screenHeight = dim.height;
        screenWidth = dim.width;

        init();

        imageWrapper = new Center();
        Card inner = new Card();
        inner.setLayout(new BorderLayout());
        inner.setBackground(HPGui.getColTranslucent());

        add(inner, BorderLayout.CENTER);
        inner.add(getImageOption(), BorderLayout.PAGE_START);
        inner.add(imageWrapper, BorderLayout.CENTER);

        ImageIcon icon = HPGui.getImageIconFromSystem(filePath);
        assert icon != null;

        imageFileNames = listDirImages();
        setNavigationIcons();
        resample();
        display();
    }


    /**
     * Set up the dialog view sizes..
     */
    private void init(){

        setTitle(Paths.get(filePath).getFileName().toString());
        setSize(screenWidth, screenHeight);
        setAlwaysOnTop(true);

    }

    /**
     * add Navigation buttons to the Image viewer
     * to the left and right of the dialog.
     */
    private void setNavigationIcons(){
        if(imageFileNames.size() > 1){
            add(getNextAndPrevious(false), BorderLayout.LINE_START);
            add(getNextAndPrevious(true), BorderLayout.LINE_END);
        }
    }



    @Override
    public void resample() throws IOException {

        BufferedImage bImage = ImageIO.read(new File(filePath));

        if(bImage.getHeight() < screenHeight && bImage.getWidth() < screenWidth){
            imageView = new ImageView(bImage);
        }else{
            Image image =  bImage.getScaledInstance(screenWidth, screenHeight, Image.SCALE_SMOOTH);
            imageView = new ImageView(image);
        }

    }

    public void display(){
        imageWrapper.add(imageView);
        setVisible(true);
    }

    public void display(int fileIndex) throws IOException {
        filePath = getImagePath(imageFileNames.get(fileIndex));
        resample();
        imageWrapper.add(imageView);
    }

    @Override
    public void zoom() {
        invokeConsumer(ImageViewerOption.Zoom);
    }

    @Override
    public void delete() {
        invokeConsumer(ImageViewerOption.Delete);
    }

    @Override
    public void share() {
        invokeConsumer(ImageViewerOption.Share);
    }

    @Override
    public void forward() {
        invokeConsumer(ImageViewerOption.Forward);
    }

    private void invokeConsumer(ImageViewerOption option){
        if(consumer != null){
            consumer.accept(option);
        }
    }
    @Override
    public void next() throws IOException {

        if(currentFileIndex < imageFileNames.size()-1){
            currentFileIndex +=1;
            display(currentFileIndex);
        }
    }

    @Override
    public void previous() throws IOException {

        if(currentFileIndex > 0){
            currentFileIndex -=1;
            display(currentFileIndex);

        }
    }


    private String getImagePath(String fileName){
        return imagesDirectory+fileName;
    }

    /**
     * It compiles the list of images in a list and set the first index of the selected image in the list.
     * @return List<String>
     * @throws IOException exception
     */
    @Override
    public List<String> listDirImages() throws IOException {
        Path p =  Paths.get(filePath).toAbsolutePath();
        imagesDirectory = p.toString().replace(p.getFileName().toString(), "");

        List<String> fileNames = new ArrayList<>();

        Files.newDirectoryStream(Paths.get(imagesDirectory), new HPGui.filter(HPGui.FilterType.Images)).forEach(pth -> {
            if(Files.isReadable(pth)){
                fileNames.add(pth.getFileName().toString());
            }
        });

        currentFileIndex = fileNames.indexOf(p.getFileName().toString());
        return fileNames;
    }


    @Override
    public void rotate() {

    }

    @Override
    public void download() {
        invokeConsumer(ImageViewerOption.Download);
    }

    private ImageView getNextAndPrevious(boolean isNext){
        ImageView view = new ImageView(isNext ? "icons/right.png" : "icons/left.png");
        view.setCursor(HPGui.getHandCursor());
        view.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isNext){
                    try {
                        next();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }else{
                    try {
                        previous();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });

        return view;
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

     enum ImageViewerOption{
        Delete, Download, Share, Forward, Zoom
    }
}
