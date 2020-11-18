/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Peter A. Akinlolu
 */
public class RoundedImage extends JLabel{

    Dimension size;
    String path;
    int padding = 10;
    float alpha = .3f;
    int shadowX = 0;
    int shadowY = 2;
    int sx1 = 0;
    int sx2 = 0;
    int sy1 = 0;
    int sy2 = 0;
    byte [] imageData;
    boolean useImageData = false;
    private boolean centralize = true;
    
    public RoundedImage(String path, Dimension size) {
       this.size = size;
       this.path = path; 
       useImageData = false;
    }
    
    public RoundedImage(String path, Dimension size, boolean centralize) {
       this.size = size;
       this.path = path;
       this.centralize = centralize;
       useImageData = false;
    }
    
    public RoundedImage(String path, Dimension size, int padding, int shadowX, int shadowY, float alpha){
       this.size = size;
       this.path = path;
      this.padding = padding;
      this.alpha = alpha;
      this.shadowX = shadowX;
      this.shadowY = shadowY;
      useImageData = false;
    }
            
    public RoundedImage(String path, Dimension size, int padding, int shadowX, int shadowY, float alpha, boolean centralize) {
       this.size = size;
       this.path = path;
      this.padding = padding;
      this.alpha = alpha;
      this.shadowX = shadowX;
      this.shadowY = shadowY;
      this.centralize = centralize;
      useImageData = false;
    }
    
    public RoundedImage(String path, Dimension size, int padding, int shadow, float alpha, boolean centralize) {
       this.size = size;
       this.path = path;
      this.padding = padding;
      this.alpha = alpha;
      this.shadowX = shadow;
      this.shadowY = shadow;
      this.centralize = centralize;
      useImageData = false;
    }
    
    public RoundedImage(byte [] imageData, Dimension size) {
       this.size = size;
       this.imageData = imageData;
       useImageData = true;
    }
    
    public RoundedImage(byte [] imageData, Dimension size, boolean centralize) {
       this.size = size;
       this.imageData = imageData;
       this.centralize = centralize;
       useImageData = true;
    }
    
    public RoundedImage(byte [] imageData, Dimension size, int padding, int shadowX, int shadowY, float alpha){
       this.size = size;
       this.imageData = imageData;
      this.padding = padding;
      this.alpha = alpha;
      this.shadowX = shadowX;
      this.shadowY = shadowY;
      useImageData = true;
    }
            
    public RoundedImage(byte [] imageData, Dimension size, int padding, int shadowX, int shadowY, float alpha, boolean centralize) {
       this.size = size;
       this.imageData = imageData;
      this.padding = padding;
      this.alpha = alpha;
      this.shadowX = shadowX;
      this.shadowY = shadowY;
      this.centralize = centralize;
      useImageData = true;
    }
    
    public RoundedImage(byte [] imageData, Dimension size, int padding, int shadow, float alpha, boolean centralize) {
       this.size = size;
       this.imageData = imageData;
      this.padding = padding;
      this.alpha = alpha;
      this.shadowX = shadow;
      this.shadowY = shadow;
      this.centralize = centralize;
      useImageData = true;
    }
    
    
    private Image getImage(){
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        if(centralize){
           setCentralSizeBasedOnShape(icon); 
        } else{
            setSizeBasedOnShape(icon);
        }
       
        return icon.getImage();
        
    }
    
    private void setCentralSizeBasedOnShape(ImageIcon icon){
        int width = icon.getIconWidth();
        int height = icon.getIconHeight();
        
        if(width > height){
            sx1 = (int)((double)(width-height) / (double)2);
            sx2 = height;
            sy2 = height;
        }else if(width < height){
            sy1 = (int)((double)(height-width) / (double)2);
            sx2 = width;
            sy2 = width;
        }else{
            sx1 = 0;
            sx2 = width;
            sy1 = 0;
            sy2 = height;
        }
    }

    private void setSizeBasedOnShape(ImageIcon icon){
        int width = icon.getIconWidth();
        int height = icon.getIconHeight();
        
        if(width > height){
            sx1 = 0;
            sx2 = height;
            sy2 = height;
        }else if(width < height){
            sy1 = 0;
            sx2 = width;
            sy2 = width;
        }else{
            sx1 = 0;
            sx2 = width;
            sy1 = 0;
            sy2 = height;
        }
    }
    
    @Override
    public Border getBorder() {
       return new EmptyBorder(padding, padding, padding, padding); 
    }

    @Override
    public Dimension getPreferredSize() {
        return size; 
    }

    @Override
    public Dimension getMaximumSize() {
        return size;
    }
    
    @Override
    public Dimension getMinimumSize() {
        return size;
    }
    
   
    @Override
    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
        int width = size.width;
        int height = size.height;
        
        Graphics2D gd = (Graphics2D)g;
        gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Ellipse2D elliseD = new Ellipse2D.Float(0, 0, width-padding, height-padding);
        
        BufferedImage image = new BufferedImage(width, height, BufferedImage.BITMASK);
        Graphics2D gImage = image.createGraphics();
        gImage.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            gImage.fill(elliseD);            
            gImage.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN, alpha));
            gImage.setColor(Color.BLACK);
            gImage.fillRect(0, 0, width, height);   
            gImage.dispose();

            /*
            shadow
             */
            gd.drawImage(image, shadowX, shadowY, null);
            
            BufferedImage picImage = new BufferedImage(width, height, BufferedImage.BITMASK);
            Graphics2D picD = picImage.createGraphics();
            picD.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            picD.fill(elliseD);
            picD.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN, 1f));
            //picD.drawImage(getImage(), 0, 0, width, height, null);
            if(useImageData){
            try {

                Image nufImage = ImageIO.read(new ByteArrayInputStream(imageData)).getScaledInstance(width, height, Image.SCALE_DEFAULT); 
                picD.drawImage(nufImage, 0, 0, null); 
            } catch (IOException ex) {
                Logger.getLogger(RoundedImage.class.getName()).log(Level.SEVERE, null, ex);
            }
           
               
            }else{
                picD.drawImage(getImage(), 0, 0, width, height, sx1, sy1, sx2, sy2, null);
            }
            
            gd.drawImage(picImage, 0, 0, null);
            gd.dispose();
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
        useImageData = true;
        repaint();
    }
}
