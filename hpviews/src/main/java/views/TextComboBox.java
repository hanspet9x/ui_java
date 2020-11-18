/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.SpringLayout;
import services.HPGui;

/**
 *
 * @author Peter A. Akinlolu
 */
public class TextComboBox extends JPanel implements ItemListener{
 
    private int comboSize;
    private String label;
    private HPGui hp = new HPGui();
    private JComboBox<Object> comboBox;
    private Object[] list;
    private Object result;
    
    public TextComboBox(Object[] list, String label, int comboBoxSize) {
       setBackground(hp.getColTranslucent());
setOpaque(true);
        this.list = list;
        this.label = label;
        this.comboSize = comboBoxSize;
        form4();
    }

    private void form4(){
        JLabel labelName = new JLabel(this.label);
        comboBox = new JComboBox(list);
        comboBox.addItemListener(this);
        hp.setAllSizes(labelName, comboSize, 20);
        hp.setAllSizes(comboBox, comboSize, 40);
        labelName.setFont(new Font(HPGui.FontStandard, Font.BOLD, 14));
        
        
        setLayout(new SpringLayout());
        add(labelName);
        add(comboBox);
        HPGui.Springer.makeCompactGrid(this, 2, 1, 0, 0, 0, 2);  
        
    }
    
    public JComboBox getJComboBox(){
        return this.comboBox;
    }

    public Object getValue(){
        return result;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        result = e.getItem();
    }
    
    

}

