/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;


import services.HPGui;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import javax.swing.*;
import javax.swing.border.Border;

/**
 *
 * @author Peter A. Akinlolu
 */
public class HPDialog extends JDialog{
           
       HPDialog context = this;
       int mX = 0;
       int mY = 0;
       int mW = 400;
       int mH = 150;
       HPGui hp = new HPGui();
       private Rectangle rectangle = null;
       private final Card panel = new Card(new SpringLayout());
       private JFrame frame = null;
       
        public HPDialog (JFrame frame) {
            setLayout(new BoxLayout(super.getContentPane(), BoxLayout.PAGE_AXIS));
            this.frame = frame;
            this.rectangle = frame.getBounds();
            setSizeLocation();
            setUndecorated(true);
            setBackground(new Color(0.0f, 0.0f, 0.0f, 0.4f));
            setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            setHolder();
        }
       
       private void init(String title, String message){
           if(!frame.getBounds().equals(rectangle))setSizeLocation();
           
           panel.setPadding(15);
           Border outside = BorderFactory.createLineBorder(Color.GRAY, 1, true);
           panel.setBorder(outside);
           panel.setBoxShadow(new Color(0,0,0,10));
           panel.setBorderRadius(10);
           
           if(panel.getComponentCount() > 0){
               panel.removeAll();
           }
           JLabel titleLabel = new JLabel(title);
           titleLabel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 30));
           titleLabel.setFont(new Font(HPGui.FontStandard, Font.BOLD, 14));
           
           JTextArea msgArea = new JTextArea(message);
           msgArea.setBorder(BorderFactory.createEmptyBorder());
           msgArea.setEditable(false);
           msgArea.setFocusable(false);
           msgArea.setLineWrap(true);
           msgArea.setMaximumSize(new Dimension(mW, 50));
           msgArea.setPreferredSize(new Dimension(mW, 50));
           msgArea.setMaximumSize(new Dimension(mW, 50));
           
           panel.add(titleLabel);
           panel.add(msgArea);
           
           
       }
       
       private void setSizeLocation(){
           setSize(frame.getBounds().width, frame.getBounds().height-30);
           setLocation(frame.getLocation().x, frame.getLocation().y+30); 
       }
       public void alert(String title, String message){
           
           init(title, message);
           panel.add(hp.setAlignRight(addClose(false)));
           
           HPGui.Springer.makeCompactGrid(panel, 3, 1, 0, 0, 0, 10);
           add(panel);
           setVisible(true);
       }
       
       public void confirm(String title, String message){
           
           init(title, message);
           Card buttons = new Card(new SpringLayout());
           buttons.add(addConfirm());
           buttons.add(addClose(true));
           buttons.setOpacity(.0f);

           HPGui.Springer.makeCompactGrid(buttons, 1, 2, 0, 0, 5, 0);
           
           panel.add(hp.setAlignRight(buttons));
          
           HPGui.Springer.makeCompactGrid(panel, 3, 1, 0, 0, 0, 10);
           
           add(panel);
           SwingUtilities.invokeLater(()->{
               setVisible(true);
           });
       }
       
       private JLabel addClose(boolean isForConfirm){
           JLabel label = new JLabel("close");
           label.setFont(new Font(HPGui.FontStandard, Font.PLAIN, 14));
           label.addMouseListener(new MouseAdapter() {
               @Override
               public void mouseClicked(MouseEvent e) {
                   WindowEvent close = new WindowEvent(context, WindowEvent.WINDOW_CLOSING);
                   context.dispatchEvent(close);
                  if(isForConfirm)onConfirmDialogue.confirmed(false);
               }
               
           });
           Border outside = BorderFactory.createLineBorder(new Color(0,0,0,0.1f), 1, true);
           Border inside = BorderFactory.createEmptyBorder(5, 10, 5, 10);
           label.setBorder(BorderFactory.createCompoundBorder(outside, inside));
           return label;
       }
        
       private JLabel addConfirm(){
           JLabel label = new JLabel("confirm");
           label.setFont(new Font(HPGui.FontStandard, Font.PLAIN, 14));
           label.setFocusable(true);
           label.addMouseListener(new MouseAdapter() {
               @Override
               public void mouseClicked(MouseEvent e) {
                   WindowEvent close = new WindowEvent(context, WindowEvent.WINDOW_CLOSING);
                   context.dispatchEvent(close);
                   onConfirmDialogue.confirmed(true);
               }
               
           });
           Border outside = BorderFactory.createLineBorder(new Color(0,0,0,0.1f), 1, true);
           Border inside = BorderFactory.createEmptyBorder(5, 10, 5, 10);
           label.setBorder(BorderFactory.createCompoundBorder(outside, inside));
           return label;
       }
        
       private void setHolder(){
           /*
           frame width and height
           */
           int fW = context.getWidth();
           int fH = context.getHeight();
          
           
//           mH = fH / 2;
//           
//           if(fW > (mW * 2)){
//               mW = fW/2;
//           }else if(fW < mW){
//               mW = fW-10;
//           }
           
           mX = (int)((double)(fW - mW) / 2);
           mY = (int)((double)(fH - mH) / 2);
           
          
           
           panel.setLocation(mX, 0);
           panel.setMinimumSize(new Dimension(mW, mH));
           panel.setPreferredSize(new Dimension(mW, mH));
           
           add(Box.createRigidArea(new Dimension(0, mY)));
           
       }

       @FunctionalInterface
       public interface OnConfirmDialogue{


           void confirmed(boolean state);
       }
       
      OnConfirmDialogue onConfirmDialogue; 
       public void setOnConfirmDialogue(OnConfirmDialogue onConfirmDialogue){
           this.onConfirmDialogue = onConfirmDialogue;
       }
       
       
}
