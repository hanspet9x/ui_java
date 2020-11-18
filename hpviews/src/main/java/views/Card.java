/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.Border;

/**
 *
 * @author Peter A. Akinlolu
 */
 public class Card<T> extends JPanel implements ActionListener{
   
    int width = 0;
    int height = 0;
    private final int shadowSize = 3;
    private int padding = 5;
    private int paddingLeft = 5;
    private int paddingTop = 5;
    private int paddingBottom = 0;
    private int paddingRight = 0;
    int borderRadius = 0;
    private Color boxShadow = null;
    private Border inner = BorderFactory.createEmptyBorder();
    private Border border = BorderFactory.createEmptyBorder();
    String bgImagePath = null;
    float opacity = 1f;
    Color backGroundColor = Color.WHITE;
    RadialGradientPaint radialGradientPaint = null;
    GradientPaint gradientPaint = null;
    LinearGradientPaint linearGradientPaint = null;
    Paint finalGradient = null;
    private CardAlignment alignment = CardAlignment.LEFT_TOP;
    private CardType cardType = CardType.SINGLE;
    private CardAnimation animation = CardAnimation.NONE;
    private int animType;
    Timer timer = null;
    private boolean initTemp = false;
    
    private Color tmpBgColor;
    private Color tmpBoxShadow;

    private T object;


    public Card() {
        super(new FlowLayout(FlowLayout.LEADING, 0, 0));
        inner = BorderFactory.createEmptyBorder(padding, padding, padding, padding);
        super.setBorder(BorderFactory.createCompoundBorder(border, inner));
        setOpaque(false);
        this.addMouseListener(new HoverAdapter());
      
    }

    public void setObject(T object) {
        this.object = object;
    }

    public T getObject() {
        return object;
    }

    public Card(LayoutManager manager) {
         super(manager);
        inner = BorderFactory.createEmptyBorder(padding, padding, padding, padding);
        super.setBorder(BorderFactory.createCompoundBorder(border, inner));
        setOpaque(false);
        this.addMouseListener(new HoverAdapter());
      
    }


    @Override
    public Color getBackground() {
        return backGroundColor;
    }

    @Override
    public void setBackground(Color bg) {
        this.backGroundColor = bg;
        if(!initTemp)tmpBgColor = bg;
    }

    public void setBackground(String bg) {
        this.backGroundColor = Color.decode(bg);
        if(!initTemp)tmpBgColor = Color.decode(bg);
    }
    
    public int getPadding() {
        return padding;
    }

    public void setPadding(int padding) {
        this.padding = padding;
        this.paddingTop = padding;
        this.paddingBottom = padding;
        this.paddingLeft = padding;
        this.paddingRight = padding;
        inner = BorderFactory.createEmptyBorder(padding, padding, padding, padding);
        super.setBorder(BorderFactory.createCompoundBorder(border, inner));
    }

    public void setPadding(int paddingTop, int paddingRight, int paddingBottom, int paddingLeft) {

        this.paddingTop = paddingTop;
        this.paddingBottom = paddingBottom;
        this.paddingLeft = paddingLeft;
        this.paddingRight = paddingRight;
        inner = BorderFactory.createEmptyBorder(paddingTop, paddingLeft, paddingBottom, paddingRight);
        super.setBorder(BorderFactory.createCompoundBorder(border, inner));
    }
    
    public int getPaddingLeft() {
        return paddingLeft;
    }

    public void setPaddingLeft(int paddingLeft) {
        this.paddingLeft = paddingLeft;
        inner = BorderFactory.createEmptyBorder(paddingTop, paddingLeft, paddingBottom, paddingRight);
        super.setBorder(BorderFactory.createCompoundBorder(border, inner));
    }

    public int getPaddingTop() {
        return paddingTop;
    }

    public void setPaddingTop(int paddingTop) {
        this.paddingTop = paddingTop;
        inner = BorderFactory.createEmptyBorder(paddingTop, paddingLeft, paddingBottom, paddingRight);
        super.setBorder(BorderFactory.createCompoundBorder(border, inner));
    }

    public int getPaddingBottom() {
        return paddingBottom;
    }

    public void setPaddingBottom(int paddingBottom) {
        this.paddingBottom = paddingBottom;
        inner = BorderFactory.createEmptyBorder(paddingTop, paddingLeft, paddingBottom, paddingRight);
        super.setBorder(BorderFactory.createCompoundBorder(border, inner));
    }

    public int getPaddingRight() {
        return paddingRight;
    }

    public void setPaddingRight(int paddingRight) {
        this.paddingRight = paddingRight;
        inner = BorderFactory.createEmptyBorder(paddingTop, paddingLeft, paddingBottom, paddingRight);
        super.setBorder(BorderFactory.createCompoundBorder(border, inner));
    }
    
    public int getBorderRadius() {
        return borderRadius;
    }

    public void setBorderRadius(int borderRadius) {
        this.borderRadius = borderRadius;
    }

    public Color getBoxShadow() {
        return boxShadow;
    }

    public void setBoxShadow(Color boxShadow) {
        this.boxShadow = boxShadow;
        if(!initTemp)tmpBoxShadow = boxShadow;
    }

    @Override
    public Border getBorder() {
        return border;
    }

    @Override
    public void setBorder(Border border) {
        this.border = border;
        super.setBorder(BorderFactory.createCompoundBorder(border, inner));
    }

    public String getBgImagePath() {
        return bgImagePath;
    }

    public void setBgImagePath(String bgImagePath) {
        this.bgImagePath = bgImagePath;
    }

    

    public float getOpacity() {
        return opacity;
    }

    public void setOpacity(float opacity) {
        this.opacity = opacity;
    }

    public RadialGradientPaint getRadialGradientPaint() {
        return radialGradientPaint;
    }

    public void setRadialGradientPaint(RadialGradientPaint radialGradientPaint) {
        this.radialGradientPaint = radialGradientPaint;
        this.finalGradient = radialGradientPaint;
    }

    public GradientPaint getGradientPaint() {
        return gradientPaint;
    }

    public void setGradientPaint(GradientPaint gradientPaint) {
        this.gradientPaint = gradientPaint;
        this.finalGradient = gradientPaint;
    }

    public LinearGradientPaint getLinearGradientPaint() {
        return linearGradientPaint;
    }

    public void setLinearGradientPaint(LinearGradientPaint linearGradientPaint) {
        
        this.linearGradientPaint = linearGradientPaint;
        this.finalGradient = linearGradientPaint;
    }

    
    public CardAlignment getAlignment() {
        return alignment;
    }

    public void setAlignment(CardAlignment alignment) {
        this.alignment = alignment;
        
        
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public CardAnimation getAnimation() {
        return animation;
    }

    public void setAnimation(CardAnimation animation) {
        this.animation = animation;
         switch(animation){
            case BOX_SHADOW_DARKER:
                animType = 1;
                break;
            case BOX_SHADOW_LIGHTER:
                animType = 2;
                break;
            case OPACITY:
                animType = 3;
                break;
            case GRADIENT:
                animType = 4;
                break;
            case BG_COLOR:
                animType = 5;
                break;
            default:
                animType = 0;
                break;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }


    
    
    
  /*
    ENUMS
    */  
    public enum CardAlignment{
        LEFT_TOP, LEFT, LEFT_BOTTOM, BOTTOM, 
        RIGHT_BOTTOM, RIGHT, RIGHT_TOP, TOP, CENTER;
    }
    
    public enum CardType{
        SINGLE, DOUBLE, TRIPLET, QUADRUPLET, 
    }
    
    public enum CardAnimation{
        BOX_SHADOW_DARKER, BOX_SHADOW_LIGHTER, BG_COLOR, OPACITY, GRADIENT, NONE;
    } 
    
    /*
    ENUMS ENDED
    */
    
    
    /*
    PAINT
    */
    
    
    @Override
    protected void paintComponent(Graphics g){
        initTemp = true;
        Graphics2D paintGraphics2D = (Graphics2D)g;
        paintGraphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        BufferedImage shapeImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D imageGraphics2D = shapeImage.createGraphics();
        imageGraphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        /*
        Gradient settings g overides r
        */

        /*
        If Opacity is adjusted
        */
        if(opacity < 1f && opacity >= 0f && bgImagePath == null){
            
           /*
            add -1 to add bound color
            */
            imageGraphics2D.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, borderRadius, borderRadius);
            imageGraphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_ATOP, opacity));
          
            /*
            gradient overides color
            */
            if(finalGradient != null){
               imageGraphics2D.setPaint(finalGradient); 
            }else{
               imageGraphics2D.setColor(backGroundColor); 
            }         
            imageGraphics2D.fillRoundRect(0, 0, getWidth(), getHeight(), borderRadius, borderRadius);
            
         /*
            Opacity not adjusted
            */   
        }else{
         /*
            set panel size using box shadow existence
        */
//         int width = boxShadow != null?getWidth()-shadowSize: getWidth();
            width = getWidth();
            height = boxShadow != null?getHeight()-shadowSize: getHeight();
             if(finalGradient != null){
                
               imageGraphics2D.setPaint(finalGradient); 
             }else{
               imageGraphics2D.setColor(backGroundColor); 
             }
             imageGraphics2D.fillRoundRect(0, 0, width, height, borderRadius, borderRadius);
           
           
            if(boxShadow != null){
                
                RoundRectangle2D.Float roundRect = new RoundRectangle2D.Float(0, shadowSize-1, width, height, borderRadius, borderRadius);         
                paintGraphics2D.setColor(boxShadow);
                paintGraphics2D.fill(roundRect);
            }
        }
        
        
        /*
        rednder and dispose all graphics
        */
        paintGraphics2D.drawImage(shapeImage, 0, 0, null);
        
        
    }

   
    private void boxShadow(Color shadow) {

        setBoxShadow(shadow);
        repaint();
    }

    private void animBgColor(Color color){
        setBackground(color);
        repaint();
    }
    
    private void opacity() {
       
    }

    private void gradient() {
        
    }

    private void noAnimation() {
        
    }

    public void setPaint(){
     
        if(linearGradientPaint != null){
            finalGradient = linearGradientPaint;
            return;
        }
        if(gradientPaint != null){
            finalGradient = gradientPaint;
            return;
        }
        if(radialGradientPaint != null){
            finalGradient = radialGradientPaint;

        }
 
    }
    
    
    private class HoverAdapter extends MouseAdapter{
 

        @Override
        public void mouseEntered(MouseEvent e) {
            switch(animType){
                case 1:
                    boxShadow(getBoxShadow().darker());
                    break;
                case 2:
                    boxShadow(getBoxShadow().brighter());
                    break;
                case 3:
                    opacity();
                    break;
                case 4:
                    gradient();
                    break;
                case 5:
                    animBgColor(Color.BLUE);
                    break;
                default:
                    break;
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            switch(animType){
                case 1:
                    boxShadow(tmpBoxShadow);
                    break;
                case 2:
                    boxShadow(tmpBoxShadow);
                    break;
                case 3:
                    opacity();
                    break;
                case 4:
                    gradient();
                    break;
                case 5:
                    animBgColor(tmpBgColor);
                    break;
                default:
                    break;
            }
        }
        
        
    }
    
}
