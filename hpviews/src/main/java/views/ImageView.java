package views;

import services.HPGui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.function.Consumer;

public class ImageView extends JLabel {

    private String resourcePath;
    private boolean resized = false;
    private int imageWidth = 0;
    private int imageHeight = 0;
    private BufferedImage bufferedImage;
    private Image image;
    private final ImageSource imageSource;
    private boolean colorToggle = false;
    private boolean colorChanged = false;
    private BufferedImage bufferedImageColorToggle = null;
    private Color color = Color.GRAY;
    private Color alphaBgColor = Color.WHITE;

    public ImageView(BufferedImage bufferedImage){
        imageSource = ImageSource.BufferedImage;
        this.bufferedImage = bufferedImage;
        this.bufferedImageColorToggle = bufferedImage;
        setIcon(new ImageIcon(bufferedImage));
        common();
    }

    public ImageView(BufferedImage bufferedImage, int imageWidth, int imageHeight){
        imageSource = ImageSource.BufferedImage;
        this.bufferedImageColorToggle = bufferedImage;
        this.bufferedImage = bufferedImage;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;

        resizeImage(imageWidth, imageHeight);
        common();
    }

    public ImageView(Image image){
        imageSource = ImageSource.Image;
        this.image = image;
        setIcon(HPGui.getImageIconFromImage(image));
        common();
    }

    public ImageView(Image image,  int imageWidth, int imageHeight){
        imageSource = ImageSource.Image;
        this.image = image;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;

        resizeImage(imageWidth, imageHeight);
        common();
    }

    public ImageView(String resourcePath) {
        imageSource = ImageSource.Path;
        this.resourcePath = resourcePath;
        setIcon(getImage());
        common();
    }

    public ImageView(String resourcePath,int imageWidth, int imageHeight) {
        imageSource = ImageSource.Path;
        this.resourcePath = resourcePath;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;

        resizeImage(imageWidth, imageHeight);
        common();
    }

    private void common(){
        setOpaque(true);
        setBackground(HPGui.getColTranslucent());
    }

    public void resizeImage(int width, int height){
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = bufferedImage.createGraphics();

        try {
            graphics2D.drawImage(getResizeImage(), 0, 0, width, height, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        graphics2D.dispose();

        setIcon(new ImageIcon(bufferedImage));
        resized = true;
        imageWidth = width;
        imageHeight = height;
    }

    private Image getResizeImage() throws IOException {

        switch (imageSource){
            case Path -> {
                BufferedImage bufferedImage = ImageIO.read(Objects.requireNonNull(
                        getClass().getClassLoader().getResourceAsStream(HPGui.removeSlashFromImage(resourcePath))));
                return bufferedImage.getScaledInstance(this.imageWidth, this.imageHeight, Image.SCALE_SMOOTH);
            }
            case BufferedImage -> {
                return bufferedImage.getScaledInstance(this.imageWidth, this.imageHeight, Image.SCALE_SMOOTH);
            }
            case Image -> {
                return image.getScaledInstance(this.imageWidth, this.imageHeight, Image.SCALE_SMOOTH);
            }
            default -> {
                return null;
            }
        }
    }

    public void swap(String resourcePath){
       this.resourcePath = resourcePath;
       if(resized){
           resizeImage(imageWidth, imageHeight);
       }else{
           setIcon(getImage());
       }
    }

    public void swap(BufferedImage image){
        this.bufferedImage = image;
        if(resized){
            resizeImage(imageWidth, imageHeight);
        }else{
            setIcon(new ImageIcon(image));
        }
    }

    public void swap(Image image){
        this.image = image;
        if(resized){
            resizeImage(imageWidth, imageHeight);
        }else{
            setIcon(HPGui.getImageIconFromImage(image));
        }
    }

    public void changeColor(Color color, Color replace) throws IOException {

        bufferedImageColorToggle = getBufferedImageColorToggle();

        for (int x = 0; x < bufferedImageColorToggle.getWidth(); x++) {

            for (int y = 0; y < bufferedImageColorToggle.getHeight(); y++) {

                Color imageColor = new Color(bufferedImageColorToggle.getRGB(x, y), true);

                    if(HPGui.isSameColor(imageColor, color)){
                        bufferedImageColorToggle.setRGB(x, y, HPGui.getIntFromColor(replace));
                    }
            }
        }
        setIcon(new ImageIcon(bufferedImageColorToggle));
    }


    public void toggleAlpha() throws IOException {

        toggleColor(alphaBgColor);
        colorToggle = !colorToggle;
    }

    public void toggleAlpha(boolean useOriginal) throws IOException {
        colorToggle = useOriginal;
        toggleColor(alphaBgColor);
    }

    public void toggleColor(Color background) throws IOException {
        bufferedImageColorToggle = getBufferedImageColorToggle();
        if(!colorChanged){
            for (int x = 0; x < bufferedImageColorToggle.getWidth(); x++) {

                for (int y = 0; y < bufferedImageColorToggle.getHeight(); y++) {

                    Color imageColor = new Color(bufferedImageColorToggle.getRGB(x, y), true);

                    if(imageColor.getTransparency() == Transparency.BITMASK){
                        bufferedImageColorToggle.setRGB(x, y, HPGui.getIntFromColor(color));
                    }else{
                        bufferedImageColorToggle.setRGB(x, y, HPGui.getIntFromColor(background, imageColor.getAlpha()));
                    }
                }
            }
            colorChanged = true;
        }
        setIcon(null);
        setIcon(new ImageIcon(colorToggle ? bufferedImage : bufferedImageColorToggle));
    }

    public void toggleNonAlpha() throws IOException {

        toggleColor();
        colorToggle = !colorToggle;
    }

    public void toggleNonAlpha(boolean useOriginal) throws IOException {
        colorToggle = useOriginal;
        toggleColor();
    }
    private void toggleColor() throws IOException {

        bufferedImageColorToggle = getBufferedImageColorToggle();

        if(!colorChanged){
            for (int x = 0; x < bufferedImageColorToggle.getWidth(); x++) {

                for (int y = 0; y < bufferedImageColorToggle.getHeight(); y++) {

                    Color imageColor = new Color(bufferedImageColorToggle.getRGB(x, y), true);

                    if(imageColor.getTransparency() != Transparency.BITMASK){
                        bufferedImageColorToggle.setRGB(x, y, HPGui.getIntFromColor(color, imageColor.getAlpha()));
                    }
                }
            }
            colorChanged = true;
        }

        setIcon(null);
        setIcon(new ImageIcon(colorToggle ? bufferedImage : bufferedImageColorToggle));
    }



    private BufferedImage getBufferedImageColorToggle() throws IOException {
        if(bufferedImageColorToggle == null){
            bufferedImageColorToggle = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource(resourcePath)));
            bufferedImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource(resourcePath)));

        }
        return bufferedImageColorToggle;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getAlphaBgColor() {
        return alphaBgColor;
    }

    public void setAlphaBgColor(Color alphaBgColor) {
        this.alphaBgColor = alphaBgColor;
    }

    private ImageIcon getImage(){
       ImageIcon icon = new ImageIcon(Objects.requireNonNull(
               getClass().getClassLoader().getResource(HPGui.removeSlashFromImage(resourcePath))));
        return getImage(icon);
    }

    private ImageIcon getImage(ImageIcon icon){
        imageHeight = icon.getIconHeight();
        imageWidth = icon.getIconWidth();
        return icon;
    }

    public void setMargin(int value){
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(value, value, value, value)
                , getBorder()));
    }

    public void setMarginTop(int value){
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(value, 0, 0, 0)
                , getBorder()));
    }

    public void setMarginLeft(int value){
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(0, value, 0, 0)
                , getBorder()));
    }

    public void setMarginBottom(int value){
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(0, 0, value, 0)
                , getBorder()));
    }

    public void setMarginRight(int value){
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(0, 0, 0, value)
                , getBorder()));
    }

    public void onClick(Consumer<MouseEvent> consumer){
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                consumer.accept(e);
            }
        });
    }

    private enum ImageSource{
        BufferedImage, Image, Path
    }

}

