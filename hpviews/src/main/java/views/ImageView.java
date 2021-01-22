package views;

import services.HPGui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class ImageView extends JLabel {

    private String resourcePath;
    private boolean resized = false;
    private int imageWidth = 0;
    private int imageHeight = 0;

    public ImageView(String resourcePath) {
        this.resourcePath = resourcePath;
        setIcon(getImage());
        common();
    }

    public ImageView(String resourcePath,int imageWidth, int imageHeight) {
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
        ImageIcon imageIcon = getImage();
        graphics2D.drawImage(imageIcon.getImage(), 0, 0, width, height, null);
        graphics2D.dispose();

        setIcon(new ImageIcon(bufferedImage));
        resized = true;
        imageWidth = width;
        imageHeight = height;
    }

    public void swap(String resourcePath){
       this.resourcePath = resourcePath;
       if(resized){
           resizeImage(imageWidth, imageHeight);
       }else{
           setIcon(getImage());
       }
    }

    public void changeColor(Color color, Color replace) throws IOException {

        BufferedImage bufferedImage = ImageIO.read(getClass().getClassLoader().getResource(resourcePath));

        for (int x = 0; x < bufferedImage.getWidth(); x++) {

            for (int y = 0; y < bufferedImage.getHeight(); y++) {

                Color imageColor = new Color(bufferedImage.getRGB(x, y), true);

                    if(HPGui.isSameColor(imageColor, color)){
                        bufferedImage.setRGB(x, y, HPGui.getIntFromColor(replace));
                    }
            }
        }

        setIcon(new ImageIcon(bufferedImage));
    }

    private ImageIcon getImage(){
       ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource(resourcePath)));
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
}

