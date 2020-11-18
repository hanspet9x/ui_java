/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;


import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Peter A. Akinlolu
 */
public class CardImage extends Card{
    
    public CardImage() {
        
    }
   
    public CardImage(String bgImagePath, float alpha) {
        super.setBgImagePath(bgImagePath);
        super.setOpacity(alpha);
    }
    
    public CardImage(String bgImagePath, Color screenColor, float alpha) {
        super.setBgImagePath(bgImagePath);
        super.setBackground(screenColor);
        super.setOpacity(alpha);
    }
    public CardImage(String bgImagePath, GradientPaint screenGradient) {
        super.setBgImagePath(bgImagePath);
        super.setGradientPaint(screenGradient);
       
    }
    
   public CardImage(String bgImagePath, LinearGradientPaint screenGradient) {
        super.setBgImagePath(bgImagePath);
        super.setLinearGradientPaint(linearGradientPaint);
         finalGradient = screenGradient;
    }
    public CardImage(String bgImagePath, RadialGradientPaint screenGradient) {
        super.setBgImagePath(bgImagePath);
        super.setRadialGradientPaint(screenGradient);
           }
    
    private Color getColor(Color color, float alpha){
        int red = color.getRed();
        int blue = color.getBlue();
        int green = color.getGreen();
        
        int alp = Math.round(alpha * 255);
        
        return new Color(red, green, blue, alp);
    }
    
    private Color getColor(Color color, int alpha){
        int red = color.getRed();
        int blue = color.getBlue();
        int green = color.getGreen();
        
        return new Color(red, green, blue, alpha);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        BufferedImage shapeImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D imageGraphics2D = shapeImage.createGraphics();
        imageGraphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        try {
            /*
                Gradient settings g overides r
            */
            
            
            
     /*
                   Make image take shape
                   */
                   imageGraphics2D.setColor(backGroundColor);              
                   imageGraphics2D.fillRoundRect(0, 0, width, height, borderRadius, borderRadius);
                   imageGraphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN, 1f));
                   
                   BufferedImage bgImage = ImageIO.read(getClass().getResource(bgImagePath));
                   System.err.println(bgImage.getWidth());
                   Graphics2D biD = bgImage.createGraphics();
                   /*
                   backgroundImage screen
                   */
                   
                     if(finalGradient != null){
                         
                        biD.setPaint(finalGradient);
                        
                     }else{
                        int rc = backGroundColor.getRed();
                        int gc = backGroundColor.getGreen();
                        int bc = backGroundColor.getBlue();

                        int alp = Math.round(opacity * 255);
                        biD.setColor(new Color(rc, gc, bc, alp));
                     }
//                   RoundRectangle2D.Float roundRect = new RoundRectangle2D.Float(0, 0, bgImage.getWidth(), bgImage.getHeight(), borderRadius, borderRadius);
                   biD.fillRoundRect(0, 0, bgImage.getWidth(), bgImage.getHeight(), borderRadius, borderRadius);
//                   biD.fill(roundRect);
                   /*
                   bufferImage
                   */
                   imageGraphics2D.drawImage(bgImage, 0, 0, width, height, this);
//                   biD.dispose();
                   /*
                   set Opacity with colors
                   */
                   
                   
               } catch (IOException ex) {
                   ex.printStackTrace();
               }
        
         Graphics2D graphics2D = (Graphics2D)g;
         graphics2D.drawImage(shapeImage, 0, 0, null);
//         graphics2D.dispose();
         
    }
    
    
}
