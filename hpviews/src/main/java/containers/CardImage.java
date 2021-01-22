/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package containers;


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

        BufferedImage shapeImage = new BufferedImage(getPreferredSize().width, getPreferredSize().height, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D mainGraphic = shapeImage.createGraphics();
        mainGraphic.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        try {
          /*
           Make image take shape
           */
           mainGraphic.setColor(backGroundColor);
           mainGraphic.fillRoundRect(0, 0, getWidth(), getHeight(), borderRadius, borderRadius);
           mainGraphic.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN, 1f));

           BufferedImage bgImage = ImageIO.read(getClass().getResource(bgImagePath));
           Graphics2D imageGraphic = bgImage.createGraphics();

           /*
           backgroundImage screen
           */
            if(finalGradient != null){

                imageGraphic.setPaint(finalGradient);

             }else{
                int rc = backGroundColor.getRed();
                int gc = backGroundColor.getGreen();
                int bc = backGroundColor.getBlue();

                int alp = Math.round(opacity * 255);
                imageGraphic.setColor(new Color(rc, gc, bc, alp));
             }
           imageGraphic.fillRoundRect(0, 0, bgImage.getWidth(), bgImage.getHeight(), borderRadius, borderRadius);

           /*
           bufferImage
           */
           mainGraphic.drawImage(bgImage, 0, 0, getPreferredSize().width, getPreferredSize().height, this);
//                 imageGraphic.dispose();
           /*
           set Opacity with colors
           */

       } catch (IOException ex) {
           ex.printStackTrace();
       }
        
         Graphics2D graphics2D = (Graphics2D)g;
         graphics2D.drawImage(shapeImage, 0, 0, null);
         
    }
    
    
}
