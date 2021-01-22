/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import containers.Card;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Peter A. Akinlolu
 */
public class SwitchView extends Card {
    
private final int w = 38;
private final int h = 20;
private final int cSize = 10;
private final int cY = 8;
private final int arc = 10;
private final int offset = 2;
private int start = 7;
private final int end = 26;
private boolean checked = false;


private Color bgActive = Color.BLUE;
private Color bgInActive = getBackground();
private Color stoneActive = Color.WHITE;
private Color stoneInActive = Color.BLACK;

private Color stoneColor = stoneInActive;
private Color backgroundBorderColor = stoneInActive;
private Color backgroundColor = bgInActive;
private String backUpStr = "no";
private String activeString = "yes";
private String inActiveString = "no";
private int stringWidth = 0;
private JLabel label;
private JPanel stone;

private SwitchView switchView;
   
    public SwitchView() {
        super.setLayout(null);
        stone();
        base();
        setStringSize();
        addString();
        setOpacity(0.0f);
    }
    
    public SwitchView setStoneDefColor(Color color){
        stoneInActive = color;
        return this;
    }
    public SwitchView setStoneActvColor(Color color){
        stoneActive = color;
        return this;
    }
    public SwitchView setBgDefColor(Color color){
        bgInActive = color;
        return this;
    }
    public SwitchView setBgActvColor(Color color){
        bgActive = color;
        return this;
    }
    public SwitchView setStoneColors(Color activeColor, Color defColor){
        stoneActive = activeColor;
        stoneInActive = defColor;
        return this;
    }
    
    public SwitchView setBgColors(Color activeColor, Color defColor){
        bgActive = activeColor;
        bgInActive = defColor;
        return this;
    }
    
    public SwitchView setActValue(String value){
        this.activeString = value;
        return this;
    }   
    public SwitchView setInActValue(String value){
        this.inActiveString = value;
        return this;
    }
    
    public SwitchView setValues(String active, String inActive){
        this.activeString = active;
        this.inActiveString = inActive;
        return this;
    }
    
    public SwitchView build(){
        stoneColor = stoneInActive;
        backgroundBorderColor = stoneInActive;
        backgroundColor = bgInActive;
        setStringSize();
        label.setBounds(w+8, 2, stringWidth+10, h);
        label.setText(inActiveString);
        switchView = this;
        return this;
    }
        
    private void setStringSize(){
         
        BufferedImage text = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
        int activeW = text.createGraphics().getFontMetrics().stringWidth(activeString);
        int inActiveW = text.createGraphics().getFontMetrics().stringWidth(inActiveString);
        stringWidth = Math.max(activeW, inActiveW);        
        
    }
    
    private void addString(){
        label = new JLabel(inActiveString);
        label.setBounds(w+8, 2, stringWidth+10, h);
        add(label);
//        if(inActiveString.equals(backUpStr)){
//            label.revalidate();
//            label.repaint();
//            System.out.println("not equal");
//        }
    }
    
    private void switchString(){
        label.setBounds(w+8, 2, stringWidth+10, h);
        if(checked){
            label.setText(activeString);
        }else{
            label.setText(inActiveString);
        }
    }
    
    public boolean getChecked(){
        return checked;
    }
    
    public void setChecked(boolean state){
        this.checked = state;
        new Selected().dispatchClicked();
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(w+10+stringWidth, h+5);
        
    }


    
    
   
    private void base(){
        Card body = new Card(){
                @Override
                protected void paintComponent(Graphics g) {        
                    super.paintComponent(g);
                    Graphics2D gd = (Graphics2D)g;
                    gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    gd.setColor(backgroundBorderColor);
                    gd.drawRoundRect(0, 0, w-offset, h-offset, arc, arc);
                    gd.setColor(backgroundColor);
                    gd.fillRoundRect(1, 1, w-offset-1, h-offset-1, arc, arc);
                    gd.dispose();

                }
                
                @Override
                public Dimension getPreferredSize() {
                    base();
                    return new Dimension(w, h);
                }
        };
        body.setBounds(3, offset+1, w, h);
        body.setOpacity(0.0f);
        add(body);
    }
    
    private void stone(){
       stone = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D gd = (Graphics2D)g;
                gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                gd.setColor(stoneColor);
                gd.fillRoundRect(0, 0, cSize, cSize, cSize,cSize);
                gd.dispose();
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(cSize, cSize);
            }
            
                       
        };
        stone.setOpaque(false);
     
        stone.setBounds(start, cY, cSize, cSize);
        stone.addMouseListener(new Selected());
        add(stone);
    }
     
   public interface OnClickedSwitch{
       void selected(boolean state, SwitchView switchView);
   }
   
   OnClickedSwitch onClickedSwitch = null;
   
   public SwitchView setOnClickedSwitch(OnClickedSwitch onClickedSwitch){
       this.onClickedSwitch = onClickedSwitch;
       return this;
   }

    private class Selected extends MouseAdapter implements ActionListener{
        
        private final int cOffset = 4;

        Timer timer = new Timer(1, this);
        
        @Override
        public void mouseClicked(MouseEvent e) {             
            timer.start();
            onClickedSwitch.selected(!checked, switchView);
        }

        @Override
        public void mouseEntered(MouseEvent e) {
           ((JPanel) e.getSource()).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }


        public void actionPerformed(ActionEvent e) {
            clicked();
           
        }
        
        public void dispatchClicked(){
            timer.start();
        }

        public void clicked(){
            if(checked){
                if(start > 8){
                    stone.setBounds(start-=cOffset, cY, cSize, cSize);
                    
                }else{
                    
                    timer.stop();
                    backgroundBorderColor = stoneInActive;
                    backgroundColor = bgInActive;
                    stoneColor = stoneInActive;
                    
                    stone.getParent().repaint();
                    checked = !checked;
                }
            }else{
                if(start < end){
                    stone.setBounds(start+=cOffset, cY, cSize, cSize);
                    
                }else{
                   
                    timer.stop();                   
                    backgroundBorderColor = bgActive;
                    backgroundColor = bgActive;
                    stoneColor = stoneActive;
                    
                    stone.getParent().repaint();
                     checked = !checked;
                }
            }
            switchString();
        }
        
    }

}
