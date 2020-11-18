/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Peter A. Akinlolu
 */
public class CenterContainer extends CardImage{

    public CenterContainer() {
        super("/bg/1.jpg", .4f);
        setBoxShadow(new Color(0,0,0,70));
        setBorderRadius(40);
    }
     
     public CenterContainer(int width, int height) {
         setPreferredSize(new Dimension(width, height));
    }
      
    
    
    @Override
    public Component add(Component comp) {
        
        this.addContainerListener(new ContainerAdapter() {
          @Override
          public void componentAdded(ContainerEvent e) {
              Container container = e.getContainer();
              container.setLayout(null);
              if(container.getComponents().length > 1)try {
                  container.remove(0);
                  container.validate();
              } catch (Exception ex) {
                  Logger.getLogger(CenterContainer.class.getName()).log(Level.SEVERE, null, ex);
              }
              
              try {
                  Rectangle rect = getCenterBound(container, comp);
                  comp.setBounds(rect);
                  validate();
              } catch (Exception ex) {
                  Logger.getLogger(CenterContainer.class.getName()).log(Level.SEVERE, null, ex);
              }
          }
        });
        
        return super.add(comp);
    }
    
    private Rectangle getCenterBound(Container container, Component component) throws Exception{
        Dimension size = container.getPreferredSize();
        Dimension cSize = component.getPreferredSize();
        if(size.height > cSize.height && size.width > cSize.width){
                int x = (size.width - cSize.width)/2;
                int y = (size.height - cSize.height)/2;
                return new Rectangle(x, y, cSize.width, cSize.height);
        }else{
            throw new Exception("Component cannot be greater than container.");
        }
    }

   
   
   
}
