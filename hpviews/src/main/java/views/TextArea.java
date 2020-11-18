/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;
import services.HPGui;

/**
 *
 * @author Peter A. Akinlolu
 */
public class TextArea extends JPanel{
    private final int fieldSize;
    private final Color color;
    private final String label;
    private final HPGui hp = new HPGui();
    private JTextArea field;
    
    public TextArea(int textFieldSize, String label, Color borderColor) {
        setBackground(hp.getColTranslucent());
        this.label = label;
        this.color = borderColor;
        this.fieldSize = textFieldSize;
        form4();
    }

    private void form4(){
        JLabel labelName = new JLabel(this.label);
        
        field = new JTextArea(); 
        
        field.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(field);
        scrollPane.setAlignmentY(Component.TOP_ALIGNMENT);

        hp.setAllSizes(labelName, fieldSize, 20);
        hp.setAllSizes(scrollPane, fieldSize, 100);
        
        field.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        field.setBackground(hp.getColor(0, 0, 0, .1f));
        labelName.setFont(new Font(HPGui.FontStandard, Font.BOLD, 14));
        
        setLayout(new SpringLayout());
        add(labelName);
        add(scrollPane);
        HPGui.Springer.makeCompactGrid(this, 2, 1, 0, 0, 0, 2);
        hp.setBorder(scrollPane, color, 1, false);
        
        
        
    }
    
    public JTextArea getJTextArea(){
        return this.field;
    }

    public String getValue(){
        return this.field.getText();
    }
    

}

