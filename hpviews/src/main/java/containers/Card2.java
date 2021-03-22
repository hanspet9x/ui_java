
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package containers;

import controllers.AncestorAdapter;
import controllers.OnClick;
import services.HPGui;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author Peter A. Akinlolu
 */


public class Card2<T> extends JPanel{


    private int boxShadowSize = 0;
    private Color boxShadowColor = null;

    private int padding = 0;
    private int paddingLeft = 0;
    private int paddingTop = 0;
    private int paddingBottom = 0;
    private int paddingRight = 0;

    private int margin = 0, marginTop = 0, marginLeft = 0, marginRight = 0, marginBottom = 0;

    int borderRadius = 0;
    private boolean isFilledContainer = false;

    String bgImagePath = null;
    float opacity = 1f;

    /*Colors and Gradients*/
    Color backGroundColor = Color.WHITE;
    RadialGradientPaint radialGradientPaint = null;
    GradientPaint gradientPaint = null;
    LinearGradientPaint linearGradientPaint = null;
    Paint finalGradient = null;
    private Color parentColor;

    private CardAlignment alignment = CardAlignment.LEFT_TOP;
    private CardType cardType = CardType.SINGLE;
    private CardAnimation animation = CardAnimation.NONE;
    private ShadowPosition shadowPosition = ShadowPosition.BOTTOM;

    private int animType;

    Timer timer = null;
    private boolean initTemp = false;

    private Color tmpBgColor;
    private Color tmpBoxShadow;
    private int width = 0;
    private int height = 0;
    private int rotateAngle = 0;
    private T object;
    private double scaleX;
    private double scaleY;
    private double translateX;
    private double translateY;
    private double shearX;
    private double shearY;

    private HPGui hp = new HPGui();
    private Color borderColor;
    private int borderWidth = 0;
    private boolean resized = false;

    public Card2() {
        super(new FlowLayout(FlowLayout.LEADING, 0, 0));
        setOpaque(false);
        //init();
    }

    public Card2(LayoutManager manager) {
        super(manager);
        setOpaque(false);
    }

    private void init() {
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if(!resized){
                    //HPGui.log(Card.super.getPreferredSize(), e.getComponent().getPreferredSize());
                    width = isFilledContainer?getParent().getPreferredSize().width : getPreferredSize().width;
                    height = isFilledContainer?getParent().getPreferredSize().height : getPreferredSize().height;
                    setEstimatedSize(width, height);
                    resized = true;
                }
            }
        });
    }

    public boolean isFilledContainer() {
        return isFilledContainer;
    }

    public void setFilledContainer(boolean filledContainer) {
        isFilledContainer = filledContainer;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public T getObject() {
        return object;
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

    /*Padding*/
    public int getPadding() {
        return padding;
    }

    public void setPadding(int padding) {
        this.padding = padding;
        this.paddingTop = padding;
        this.paddingBottom = padding;
        this.paddingLeft = padding;
        this.paddingRight = padding;
    }

    public void setPadding(int paddingTop, int paddingRight, int paddingBottom, int paddingLeft) {

        this.paddingTop = paddingTop;
        this.paddingBottom = paddingBottom;
        this.paddingLeft = paddingLeft;
        this.paddingRight = paddingRight;
    }

    public void setPadding(int paddingX, int paddingY){
        this.paddingTop = paddingY;
        this.paddingBottom = paddingY;
        this.paddingLeft = paddingX;
        this.paddingRight = paddingX;

    }

    public int getPaddingLeft() {
        return paddingLeft;
    }

    public void setPaddingLeft(int paddingLeft) {
        this.paddingLeft = paddingLeft;
    }

    public void setPaddingHorizontal(int padding) {
        this.paddingLeft = padding;
        this.paddingRight = padding;
    }

    public void setPaddingVertical(int padding) {
        this.paddingTop = padding;
        this.paddingBottom = padding;
    }

    public int getPaddingTop() {
        return paddingTop;
    }

    public void setPaddingTop(int paddingTop) {
        this.paddingTop = paddingTop;

    }

    public int getPaddingBottom() {
        return paddingBottom;
    }

    public void setPaddingBottom(int paddingBottom) {
        this.paddingBottom = paddingBottom;

    }

    public int getPaddingRight() {
        return paddingRight;
    }

    public void setPaddingRight(int paddingRight) {
        this.paddingRight = paddingRight;

    }

    /*Margin*/

    public int getMargin() {
        return margin;
    }

    public void setMargin(int margin) {
        this.margin = margin;
        this.marginLeft = margin;
        this.marginRight = margin;
        this.marginBottom = margin;
        this.marginTop = margin;
    }

    public void setMargin(int marginX, int marginY){
        this.marginLeft = marginX;
        this.marginRight = marginX;
        this.marginBottom = marginY;
        this.marginTop = marginY;
    }

    public int getMarginTop() {
        return marginTop;
    }

    public void setMarginTop(int marginTop) {
        this.marginTop = marginTop;
    }

    public int getMarginLeft() {
        return marginLeft;
    }

    public void setMarginLeft(int marginLeft) {
        this.marginLeft = marginLeft;
    }

    public int getMarginRight() {
        return marginRight;
    }

    public void setMarginRight(int marginRight) {
        this.marginRight = marginRight;
    }

    public int getMarginBottom() {
        return marginBottom;

    }

    public void setMarginBottom(int marginBottom) {
        this.marginBottom = marginBottom;
    }



    /*Border Radius*/

    public int getBorderRadius() {
        return borderRadius;
    }

    public void setBorderRadius(int borderRadius) {
        this.borderRadius = borderRadius;
    }

    public Color getBoxShadowColor() {
        return boxShadowColor;
    }

    public void setBoxShadowColor(Color boxShadowColor) {
        this.boxShadowColor = boxShadowColor;
        if(!initTemp)tmpBoxShadow = boxShadowColor;
    }

    public int getBoxShadowSize() {
        return boxShadowSize;
    }

    public void setBoxShadowSize(int boxShadowSize) {
        this.boxShadowSize = boxShadowSize;
    }

    public void setBoxShadow(int boxShadowSize, Color boxShadowColor) {
        this.boxShadowSize = boxShadowSize;
        this.boxShadowColor = boxShadowColor;
        if(marginBottom == 0) marginBottom = boxShadowSize;
    }

    public void setBoxShadow(int boxShadowSize, Color boxShadowColor, ShadowPosition shadowPosition){
        this.boxShadowSize = boxShadowSize;
        this.boxShadowColor = boxShadowColor;
        this.shadowPosition = shadowPosition;
        switch (shadowPosition){
            case RIGHT -> {
                if(marginRight == 0) marginRight = boxShadowSize;
            }
            case LEFT -> {
                if(marginLeft == 0) marginLeft = boxShadowSize;
            }
            case TOP -> {
                if(marginTop == 0) marginTop = boxShadowSize;
            }
            case BOTTOM -> {
                if(marginBottom == 0) marginBottom = boxShadowSize;
            }
        }
    }



/*
    public void setWidth(int width) {
        setEstimatedSize(width, this.getPreferredSize().width);
    }

    public void setHeight(int height) {
        setEstimatedSize(height, this.getPreferredSize().height);
    }
*/


    public void setCardSize(int width, int height) {

        setEstimatedSize(width, height);
    }


    public void setCardSize(Dimension d) {

        setEstimatedSize(d.width, d.height);
    }

    public void setCardSizeAsync(Component referenceComponent) {

        this.addAncestorListener(new AncestorAdapter() {
            @Override
            public void ancestorAdded(AncestorEvent event) {
                setEstimatedSize(referenceComponent.getWidth(), referenceComponent.getHeight());
            }
        });
    }

    public void setCardSizeAsync() {

        this.addAncestorListener(new AncestorAdapter() {
            @Override
            public void ancestorAdded(AncestorEvent event) {
                setEstimatedSize(event.getAncestor().getWidth(), event.getAncestor().getHeight());
            }
        });
    }

    private void setEstimatedSize(int width, int height){
        // HPGui.log("estimated set");
        int w = width+marginLeft+marginRight+paddingLeft+paddingRight+(borderWidth * 2);
        int h = height+marginTop+marginBottom+paddingTop+paddingBottom+(borderWidth * 2);

        HPGui.setAllSizes(this, w, h);
    }

    public String getBgImagePath() {
        return bgImagePath;
    }

    public void setBgImagePath(String bgImagePath) {
        this.bgImagePath = bgImagePath;
    }

    public int getRotateAngle() {
        return rotateAngle;
    }

    public void setRotateAngle(int rotateAngle) {
        this.rotateAngle = rotateAngle;
    }

    public double getScaleX() {
        return scaleX;
    }

    public void setScaleX(double scaleX) {
        this.scaleX = scaleX;
    }

    public double getScaleY() {
        return scaleY;
    }

    public void setScaleY(double scaleY) {
        this.scaleY = scaleY;
    }

    public double getTranslateX() {
        return translateX;
    }

    public void setTranslateX(double translateX) {
        this.translateX = translateX;
    }

    public double getTranslateY() {
        return translateY;
    }

    public void setTranslateY(double translateY) {
        this.translateY = translateY;
    }

    public double getShearX() {
        return shearX;
    }

    public void setShearX(double shearX) {
        this.shearX = shearX;
    }

    public double getShearY() {
        return shearY;
    }

    public void setShearY(double shearY) {
        this.shearY = shearY;
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

    public Color getParentColor() {
        return parentColor;
    }

    public void setParentColor(Color parentColor) {
        this.parentColor = parentColor;
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
    public Insets getInsets() {
        return new Insets(paddingTop+marginTop, paddingLeft+marginLeft,
                paddingBottom+marginBottom, paddingRight+marginRight);
    }

    public void setBorder(int borderWidth, Color borderColor) {
        this.borderColor = borderColor;
        this.borderWidth = borderWidth;
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





    private void boxShadow(Color shadow) {

        setBoxShadowColor(shadow);
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

    private void setPaint(){

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

    private void setGraphicsBackground(Graphics2D graphics2D){
        if(finalGradient != null){
            graphics2D.setPaint(finalGradient);
        }else{
            graphics2D.setColor(backGroundColor);
        }
    }


/*    private class HoverAdapter extends MouseAdapter{


        @Override
        public void mouseEntered(MouseEvent e) {
            switch(animType){
                case 1:
                    boxShadow(getBoxShadowColor().darker());
                    break;
                case 2:
                    boxShadow(getBoxShadowColor().brighter());
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


    }*/

    private void setGraphicShadow(Graphics2D imageGraphics2D, int bw, int bh){

        int x = 0;
        int y = 0;
        int shd = HPGui.getPercent(70, boxShadowSize);
        int yx = 0, xy = 0;
        int bbh = bh, bbw = bw;

        switch (shadowPosition){
            case BOTTOM -> {
                bw = bw - boxShadowSize * 2;
                bh = bh - boxShadowSize;
                x = boxShadowSize;
                y = boxShadowSize;

                bbw = bw;
                bbh = bh -boxShadowSize;
                xy = x;
                yx = shd+boxShadowSize;

            }

            case TOP -> {
                bw = bw - boxShadowSize * 2;
                x = boxShadowSize;
                bh = bh - boxShadowSize;

                bbw = bw;
                bbh = bh - shd;
                xy = x;
                yx = shd;
            }

            case LEFT -> {
                bw = bw - boxShadowSize;
                bh = bh - boxShadowSize * 2;
                y = boxShadowSize;

                bbw = bw - boxShadowSize - shd;
                bbh = bh;
                xy = shd;
                yx = y;
            }
            case RIGHT -> {
                bw = bw - boxShadowSize;
                bh = bh - boxShadowSize * 2;
                y = boxShadowSize;
                x = boxShadowSize;

                bbw = bw - boxShadowSize;
                bbh = bh;
                xy = boxShadowSize+shd;
                yx = y;
            }
            default -> {
                bbw -= (shd * 2);
                bbh -= (shd * 2);
                xy = shd;
                yx = shd;
            }
        }
        /*if box shadow size is greater than 0 then draw lighter version*/
        imageGraphics2D.setColor(boxShadowColor);
        imageGraphics2D.fillRoundRect(x, y, bw , bh, borderRadius, borderRadius);

        /*Inner box shadow*/
        if(boxShadowSize > 1){
            imageGraphics2D.setColor(hp.getDarkerColor(boxShadowColor, 10));
            imageGraphics2D.fillRoundRect(xy, yx, bbw, bbh, borderRadius, borderRadius);

        }

    }

    private void setGraphicOpacity(Graphics2D imageGraphics2D,int ctW, int ctH){
        if(opacity < 1f && opacity >= 0f && bgImagePath == null){
            imageGraphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_ATOP, opacity));
            imageGraphics2D.fillRoundRect(0, 0, ctW, ctH, borderRadius, borderRadius);
        }
    }

    @Override
    protected void paintComponent(Graphics g){


        initTemp = true;
        Graphics2D paintGraphics2D = (Graphics2D)g;
        paintGraphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        /*set new width and height*/


        int bw = getWidth() - (marginLeft + marginRight);
        int bh = getHeight() - (marginTop + marginBottom);

        // HPGui.log("painted", bw, bh);
        /*set new image size for box shadow*/
        int baseShadowSize = boxShadowSize * 2; //shadowPosition == ShadowPosition.CENTER ? boxShadowSize * 2 : boxShadowSize;
        bw += baseShadowSize;
        bh += baseShadowSize;

        BufferedImage shapeImage = new BufferedImage(bw, bh, BufferedImage.TYPE_4BYTE_ABGR);

        Graphics2D imageGraphics2D = shapeImage.createGraphics();

        imageGraphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        /*box shadow*/
        setGraphicShadow(imageGraphics2D, bw, bh);

        /*set border layout size*/
        int brW = bw - baseShadowSize;
        int brH= bh - baseShadowSize;

        /*border*/
        BasicStroke stroke = new BasicStroke(borderWidth);
        imageGraphics2D.setColor(borderColor);
        imageGraphics2D.setStroke(stroke);
        imageGraphics2D.draw(new RoundRectangle2D.Float(boxShadowSize, boxShadowSize, brW, brH, borderRadius, borderRadius));

        /*set content area size*/
        int ctW = brW - (borderWidth * 2);
        int ctH = brH - (borderWidth * 2);

        int cx = boxShadowSize + borderWidth, cy = boxShadowSize + borderWidth;


        /*Opacity*/
        setGraphicOpacity(imageGraphics2D, ctW, ctH);
        /*Base*/
        setGraphicsBackground(imageGraphics2D);
        imageGraphics2D.fillRoundRect(cx, cy, ctW, ctH, borderRadius, borderRadius);

        /**
         * transform values
         */

        /*AffineTransform transform = imageGraphics2D.getTransform();
        transform.rotate(Math.toRadians(rotateAngle));
        transform.scale(scaleX, scaleY);
        transform.translate(translateX, translateY);
        transform.shear(shearX, shearY);

        imageGraphics2D.setTransform(transform);*/

        paintGraphics2D.drawImage(shapeImage, marginLeft - boxShadowSize, marginTop - boxShadowSize,  null);

    }

    public enum ShadowPosition {
        TOP, BOTTOM, LEFT, RIGHT, CENTER
    }

    public void onClick(OnClick<MouseEvent> clicked){
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clicked.clicked(e);
            }
        });
    }
}
