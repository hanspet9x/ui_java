/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import containers.Card;
import org.json.JSONArray;
import org.json.JSONObject;
import services.HPGui;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.lang.reflect.Array;
import java.util.*;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.Border;
import javax.swing.event.MouseInputAdapter;

/**
 *
 * @author Peter A. Akinlolu
 */
public class FormField extends Card {
    
    FormField context = this;
    HPGui hp = new HPGui();
    
    private JLabel label = null;
    private JTextField textField = null;
    private JTextArea textArea = null;
    private JPasswordField passwordField = null;
    private JComboBox<Object> comboBox = null;
    
    private Object [] comBoxData = null;
     Object comboItem = null;
    private String labelText;


    private Color labelTextColor = new Color(102, 102, 102);
    private Color fieldColor = new Color(51, 51, 51);  
    private Color borderColor = new Color(102, 102, 102);

    private Color fieldBgColor = new Color(244, 246, 248);
    private String fieldIcon = null;
    private ImageIcon imageIcon = null;

    Font labelTextFont = new Font(HPGui.FontStandard, Font.BOLD, 16);
    Font fieldFont = new Font(HPGui.FontStandard, Font.PLAIN, 18);


    FormType formType = FormType.TEXTFIELD;
    CaseType caseType = CaseType.NONE;

    public int fieldLength = 300;
    private int fieldHeight = 40;
    private int textAreaHeight = 100;
    private int fieldBorderRadius = 0;
    private Color fieldBoxShadow = null;
    private final int innerBorderPad = 5;


    public FormField() {
        conditions();
    }
    
    
    
    public FormField(String labelText) {
        this.labelText = labelText;
        conditions();
    }
    
    public FormField(String labelText, Object [] comboBoxData) {
        this.labelText = labelText;
        this.comBoxData = comboBoxData;
        this.formType = FormType.COMBOBOX;
        conditions();
    }
    
    public FormField(String labelText, FormType formType) {
        this.labelText = labelText;
        this.formType = formType;
        conditions();
        
    }

    
    public FormField(String labelText, String iconPath, FormType formType) {
        this.labelText = labelText;
        this.formType = formType;
        this.fieldIcon = iconPath;
        conditions();
        
    }
    
    public FormField(String labelText, String iconPath, Object [] comboBoxData) {
        this.labelText = labelText;
        this.formType = FormType.COMBOBOX;
        this.fieldIcon = iconPath;
        this.comBoxData = comboBoxData;
        conditions();
        
    }

    public FormField(String labelText, ImageIcon icon, FormType formType) {
        this.labelText = labelText;
        this.formType = formType;
        this.imageIcon = icon;
        conditions();
        
    }
    
    public FormField(String labelText, ImageIcon icon, Object [] comboBoxData) {
        this.labelText = labelText;
        this.formType = FormType.COMBOBOX;
        this.imageIcon = icon;
        this.comBoxData = comboBoxData;
        conditions();
        
    }

    public FormField(String labelText, int fieldLength) {
        this.labelText = labelText;
        this.fieldLength = fieldLength;
        conditions();
    }
    
    public FormField(String labelText, Object [] comboBoxData, int fieldLength) {
        this.labelText = labelText;
        this.comBoxData = comboBoxData;
        this.formType = FormType.COMBOBOX;
        this.fieldLength = fieldLength;
        conditions();
    }
    
    public FormField(String labelText, FormType formType, int fieldLength) {
        this.labelText = labelText;
        this.formType = formType;
        this.fieldLength = fieldLength;
        conditions();
        
    }

    
    public FormField(String labelText, String iconPath, FormType formType, int fieldLength) {
        this.labelText = labelText;
        this.formType = formType;
        this.fieldIcon = iconPath;
        this.fieldLength = fieldLength;
        conditions();
        
    }
    
    public FormField(String labelText, String iconPath, Object [] comboBoxData, int fieldLength) {
        this.labelText = labelText;
        this.formType = FormType.COMBOBOX;
        this.fieldIcon = iconPath;
        this.comBoxData = comboBoxData;
        this.fieldLength = fieldLength;
        conditions();
        
    }

    public FormField(String labelText, ImageIcon icon, FormType formType, int fieldLength) {
        this.labelText = labelText;
        this.formType = formType;
        this.imageIcon = icon;
        this.fieldLength = fieldLength;
        conditions();
        
    }
    
    public FormField(String labelText, ImageIcon icon, Object [] comboBoxData, int fieldLength) {
        this.labelText = labelText;
        this.formType = FormType.COMBOBOX;
        this.imageIcon = icon;
        this.comBoxData = comboBoxData;
        this.fieldLength = fieldLength;
        conditions();
        
    }

    public enum FormType{
        TEXTFIELD, TEXTAREA, PASSWORD, COMBOBOX;
    }
    
    public enum CaseType{        
        FULL, BOTTOM, NONE;
    }

    /*
    FieldHolder
    */
    
    private Card getFieldHolder(){
        Card join = new Card(new FlowLayout(FlowLayout.LEADING, 0, 0));
        join.setPadding(1);
        join.setBackground(fieldBgColor);
        join.setBorder(getFieldBorder());
        join.setBorderRadius(fieldBorderRadius);
        if(fieldBoxShadow != null){
            join.setBoxShadow(fieldBoxShadow);
        }
        if(formType == FormType.TEXTAREA){
          hp.setAllSizes(join, fieldLength, textAreaHeight);  
        }else{
            hp.setAllSizes(join, fieldLength, fieldHeight);
        }
        
        return join;
    }
    
    private Card getFieldIconHolder(){
        Card join = new Card(new SpringLayout());
        join.setPadding(1);
        join.setBackground(fieldBgColor);    
        join.setBorder(getFieldBorder());
        join.setBorderRadius(fieldBorderRadius);
        if(fieldBoxShadow != null){
            join.setBoxShadow(fieldBoxShadow);
        }
        /*
        icon
        */
        JLabel iconLabel = new JLabel(imageIcon);
        hp.setAllSizes(iconLabel, fieldHeight, fieldHeight);
        
        Card iconCard = new Card(new FlowLayout(FlowLayout.LEADING, 0, 0));
        iconCard.setPadding(0);
        iconCard.setOpacity(0.f);
        iconCard.add(iconLabel);
        hp.setAllSizes(iconCard, fieldHeight, fieldHeight);
        
        if(formType == FormType.TEXTAREA){
          hp.setAllSizes(join,fieldLength, textAreaHeight);  
        }else{
            hp.setAllSizes(join, fieldLength, fieldHeight);
        }
        join.add(iconCard);
        return join;
    }
    /*
    JTEXTFIELD
    */
    private JTextField getJTextField(){
        textField = new JTextField();
        /*
        set label text and color
        */
        label.setForeground(labelTextColor);
        label.setFont(labelTextFont);
        

        textField.setFont(fieldFont);
        textField.setForeground(fieldColor);
        textField.setOpaque(false);
        textField.setBackground(hp.getColTranslucent());
        textField.setBorder(BorderFactory.createEmptyBorder(innerBorderPad, innerBorderPad, innerBorderPad, innerBorderPad));
        textField.addMouseListener(new OnFieldMouseListener());
        
        return textField;
    }
    
    private void formTF(){
           
        textField = getJTextField();
        hp.setAllSizes(textField, fieldLength, fieldHeight);
        
        Card join = getFieldHolder();
        join.add(textField);
        
        add(label);
        add(join);
        
        HPGui.Springer.makeCompactGrid(this, 2, 1, 0, 0, 0, 0);
        
    }
    
    private void formTFIcon(){
           
        /*
        field
        */
        
        textField = getJTextField();
        hp.setAllSizes(textField, fieldLength-fieldHeight-2, fieldHeight);
        
        /*
        join icon and field
        */
        Card join = getFieldIconHolder();
        join.add(textField);
        
        HPGui.Springer.makeCompactGrid(join, 1, 2, 0, 0, 2, 0);
        
        add(label);
        add(join);
        
        HPGui.Springer.makeCompactGrid(this, 2, 1, 0, 0, 0, 0);
    }

    /*
    Password Field
    */
    private JPasswordField getJPasswordField(){
        passwordField = new JPasswordField();
        /*
        set label text and color
        */
        label.setForeground(labelTextColor);
        label.setFont(labelTextFont);
        
        
        passwordField.setBorder(BorderFactory.createEmptyBorder(innerBorderPad,innerBorderPad,innerBorderPad,innerBorderPad));
        passwordField.setFont(fieldFont);
        passwordField.setForeground(fieldColor);
        passwordField.setOpaque(false);
        passwordField.setBackground(hp.getColTranslucent());
        passwordField.addMouseListener(new OnFieldMouseListener());
        return passwordField;
    }
    
    private void formPF(){
        
        passwordField = getJPasswordField();
        hp.setAllSizes(passwordField, fieldLength, fieldHeight);
        
        Card join = getFieldHolder();
        join.add(passwordField);
       
        add(label);
        add(join);
        
        HPGui.Springer.makeCompactGrid(this, 2, 1, 0, 0, 0, 0);
        
    }
    
    private void formPFIcon(){

        /*
        field
        */

        passwordField = getJPasswordField();
        hp.setAllSizes(passwordField, fieldLength-fieldHeight-2, fieldHeight);
        
        /*
        join icon and field
        */
        Card join = getFieldIconHolder();
        join.add(passwordField);   
        HPGui.Springer.makeCompactGrid(join, 1, 2, 0, 0, 2, 0);
        
        add(label);
        add(join);
        
        HPGui.Springer.makeCompactGrid(this, 2, 1, 0, 0, 0, 0);
    }
    
    /*
    ComboBox
    */
    
    private JComboBox<Object> getJComboBox(){
         comboBox = new JComboBox<>(comBoxData);
         comboBox.addItemListener(new OnComboItemSelected());
         comboItem = comBoxData[0];
        /*
        set label text and color
        */
        label.setForeground(labelTextColor);
        label.setFont(labelTextFont);
        

        comboBox.setFont(fieldFont);
        comboBox.setForeground(fieldColor);
        comboBox.setOpaque(false);
        comboBox.setBackground(new Color(0,0,0,0));
        comboBox.setBorder(BorderFactory.createEmptyBorder(innerBorderPad, innerBorderPad, innerBorderPad, innerBorderPad));
        comboBox.addMouseListener(new OnFieldMouseListener());
        return comboBox;
    }
    
    private void formCB(){
        
        comboBox = getJComboBox();
        hp.setAllSizes(comboBox, fieldLength, fieldHeight);
        
        Card join = getFieldHolder();
        join.add(comboBox);
        
        add(label);
        add(join);
        
        HPGui.Springer.makeCompactGrid(this, 2, 1, 0, 0, 0, 0);
        
    }
    
    private void formCBIcon(){
    
    
        /*
        set label text and color
        */
        
        comboBox = getJComboBox();
        
        hp.setAllSizes(comboBox, fieldLength-fieldHeight-2, fieldHeight);
        
        /*
        join icon and field
        */
        Card join = getFieldIconHolder();
        join.add(comboBox);
        
        HPGui.Springer.makeCompactGrid(join, 1, 2, 0, 0, 2, 0);       
        add(label);
        add(join);
        
        HPGui.Springer.makeCompactGrid(this, 2, 1, 0, 0, 0, 0);
    }
    
    /*
    TextArea
    */
    
    private JScrollPane getScrollPane(){
        textArea = new JTextArea();
        /*
        set label text and color
        */
        label.setForeground(labelTextColor);
        label.setFont(labelTextFont);
        
        
        textArea.setBorder(BorderFactory.createEmptyBorder(innerBorderPad,innerBorderPad,innerBorderPad,innerBorderPad));
        textArea.setFont(fieldFont);
        textArea.setOpaque(false);
        textArea.setBackground(new Color(0,0,0,0));
        textArea.setForeground(fieldColor);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);   
        textArea.addMouseListener(new OnFieldMouseListener());
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getViewport().setBackground(new Color(0,0,0,0));
        scrollPane.setViewportBorder(BorderFactory.createEmptyBorder());
        
        return scrollPane;
    }
    
    private void formTA(){
        
        JScrollPane scrollPane = getScrollPane();
        
        hp.setAllSizes(scrollPane, fieldLength, textAreaHeight);
        
        Card join = getFieldHolder();
        join.add(scrollPane);
        
        add(label);
        add(join);
        
        HPGui.Springer.makeCompactGrid(this, 2, 1, 0, 0, 0, 0);
        
    }
    
    private void formTAIcon(){
           
        JScrollPane scrollPane = getScrollPane();
        /*
        join icon and field
        */
        hp.setAllSizes(scrollPane, fieldLength-fieldHeight-2, 100);
        Card join = getFieldIconHolder();
        join.add(scrollPane);
        
        HPGui.Springer.makeCompactGrid(join, 1, 2, 0, 0, 2, 0);
        
        add(label);
        add(join);
        
        HPGui.Springer.makeCompactGrid(this, 2, 1, 0, 0, 0, 0);
    }
    
    
    private void conditions(){

        setLayout(new SpringLayout());
        setOpacity(.0f);
        
        if(label == null){
            label = new JLabel(labelText);
        }
        
        if(imageIcon == null && fieldIcon == null){
            
            switch(formType){
                
                case TEXTFIELD:
                  formTF();
                  break;
                case PASSWORD:
                  formPF();
                break;
                case COMBOBOX:
                  formCB();
                break;
                  default:
                    formTA();
                      break;
            }
            
            
        }else{ 
            if(fieldIcon != null){
             imageIcon = hp.getImageIcon(fieldIcon);
            }
         
                switch(formType){
                
                case TEXTFIELD:
                  formTFIcon();
                  break;
                case PASSWORD:
                  formPFIcon();
                break;
                case COMBOBOX:
                  formCBIcon();
                break;
                  default:
                    formTAIcon();
                  break;
            }  
        
        }
    }
    /*
    get full or bottom border using case typw
    */

    private Border getFieldBorder(){
       
        Border outside;
        
      switch (caseType) {
            case FULL:
                outside = BorderFactory.createLineBorder(borderColor, 1);
                break;
            case BOTTOM:
                outside = BorderFactory.createMatteBorder(0, 0, 1, 0, borderColor);
                break;
            default:
                return null;
        }
        return outside;
    }  

    

    public JTextArea getTextArea() {
        return textArea;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JComboBox<Object> getComboBox() {
        return comboBox;
    }

    public JTextField getTextField() {
        return textField;
    }

    public void setTextField(JTextField textField) {
        this.textField = textField;
    }
    
    
    
    /*
    RETRIEVE VALES
    */
    
    public String getValue(){
        
        return valueConditions();
    }
    
    public void setValue(String value){
        setConditions(value);
    }
    
    public void setObject(Object[] objects){
        if(objects.length == 0)return;
        if(formType == FormType.COMBOBOX){
            comboBox.setModel(new DefaultComboBoxModel<>(objects));
            comBoxData = objects;
            comboItem = objects[0];
        }
    }


    public void setObject(JSONArray objects, String field){
        if(objects.length() == 0)return;

        Object [] object = new Object[objects.length()];
        if(formType == FormType.COMBOBOX){
            for (int i = 0; i < objects.length(); i++) {
                JSONObject data = objects.getJSONObject(i);
                Array.set(object, i, data.get(field));
            }
            comBoxData = object;
            comboBox.setModel(new DefaultComboBoxModel<>(object));
            comboItem = object[0];
        }
    }

    public void setObject(Object object){
        if(formType == FormType.COMBOBOX){
            comboBox.setSelectedItem(object);
            comboItem = object;
        }
    }

    public void addObjects(Object... objects){
        if(formType == FormType.COMBOBOX){
            List<Object> list = new ArrayList<>();
            Collections.addAll(list, comBoxData);
            Collections.addAll(list, objects);

            Object [] data = list.toArray();
            comboBox.setModel(new DefaultComboBoxModel<>(data));
            comBoxData = data;
        }
    }


    public Object getObject(){

        if(formType == FormType.COMBOBOX){
            return comboItem;
        }
        return null;
    }

    public Object [] getObjects(){
        if(formType == FormType.COMBOBOX){
            return comBoxData;
        }
        return new Object[]{};
    }
    
    /*
    conditions
    */
    
    private String valueConditions(){
        String text;
        switch(formType){
            case TEXTFIELD:
               text = textField.getText();
               break;
            case TEXTAREA:
               text = textArea.getText();
               break;
            case PASSWORD:
               char [] passwords = passwordField.getPassword();
                StringBuilder sb = new StringBuilder();
                for (char password : passwords) {
                        sb.append(password);
                }      
                text = sb.toString();
               break;
            default:
                text = String.valueOf(comboItem);
        }
        
        return text;
    }
      
    private void setConditions(String value){
        
        switch(formType){
            case TEXTFIELD:
               textField.setText(value);
               break;
            case TEXTAREA:
               textArea.setText(value);
               break;
            case PASSWORD:
               passwordField.setText(value);
               break;
        }
    }
    
    public void setEditable(boolean state){
        switch(formType){
            case TEXTFIELD:
               textField.setEditable(state);
               break;
            case TEXTAREA:
               textArea.setEditable(state);
               break;
            case PASSWORD:
               passwordField.setEditable(state);
               break;
            default:
                comboBox.setEditable(state);
                break;
        }
    }
    
    @Override
    public void setEnabled(boolean state){
        switch(formType){
            case TEXTFIELD:
               textField.setEnabled(state);
               break;
            case TEXTAREA:
               textArea.setEnabled(state);
               break;
            case PASSWORD:
               passwordField.setEnabled(state);
               break;
            default:
                comboBox.setEnabled(state);
                break;
        }
    }
    
    /*
    Builder Class
    */

   public static class Builder{

     FormField formField;
        public Builder() {
            formField = new FormField();
        }

        public Builder setFieldBorderRadius(int fieldBorderRadius) {
            this.formField.fieldBorderRadius = fieldBorderRadius;
            return this;
        }

        public Builder setFieldBoxShadow(Color fieldBoxShadow) {
            formField.fieldBoxShadow = fieldBoxShadow;
            return this;
        }
        public Builder setLabel(JLabel label) {
            formField.label = label;
            return this;
        }

        public Builder setComboData(Object[] comboData) {
            formField.comBoxData = comboData;
            return this;
        }

        public Builder setLabelText(String labelText) {
            formField.labelText = labelText;
            return this;
        }

        public Builder setLabelTextColor(Color labelTextColor) {
            formField.labelTextColor = labelTextColor;
            return this;
        }

        public Builder setFieldColor(Color fieldColor) {
            formField.fieldColor = fieldColor;
            return this;
        }

        public Builder setLabelTextFont(Font labelTextFont) {
            formField.labelTextFont = labelTextFont;
            return this;
        }

        public Builder setFieldFont(Font fieldFont) {
            formField.fieldFont = fieldFont;
            return this;
        }

        public Builder setFormType(FormType formType) {
            formField.formType = formType;
            return this;
        }

        public Builder setFieldIcon(String fieldIcon) {
            formField.fieldIcon = fieldIcon;
            return this;
        }

        public Builder setImageIcon(ImageIcon imageIcon) {
            formField.imageIcon = imageIcon;
            return this;
        }

        public Builder setBorderColor(Color borderColor) {
            formField.borderColor = borderColor;
            return this;
        }

        public Builder setCaseType(CaseType caseType) {
            formField.caseType = caseType;
            return this;
        }

        public Builder setFieldBgColor(Color fieldBgColor) {
            formField.fieldBgColor = fieldBgColor;
            return this;
        }       

        public Builder setFieldLength(int fieldLength) {
            formField.fieldLength = fieldLength;
            return this;
        }
        public Builder setFieldHeight(int fieldHeight) {
            formField.fieldHeight = fieldHeight;
            return this;
        }
        public Builder setTextAreaHeight(int textAreaHeight) {
            formField.textAreaHeight = textAreaHeight;
            return this;
        }
        public FormField build(){
            formField.conditions();
            return formField;
        }
    
 }  
   
   /*
   ComboBox Data
   */
    
    public static Object[] Hours(){
        Integer [] hours = {1, 2, 3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24};
        return hours;
    } 
    
    public static Object[] Minutes(){
        List<Object> mins = new ArrayList<>();
        int day = 61;
        
        for (int i = 1; i < day; i++) {
            mins.add(i);
        }
        
        return mins.toArray();
    }
    
    public static Object[] Days(){
        List<Object> days = new ArrayList<>();
        int day = 32;
        
        for (int i = 1; i < day; i++) {
            days.add(i);
        }
        
        return days.toArray();
    }
    
    public static Object[] MonthsI(){
        List<Integer> days = new ArrayList<>();
        int day = 13;
        
        for (int i = 1; i < day; i++) {
            days.add(i);
        }
        
        return days.toArray();
    }
    
    public static Object[] Months(){
      
        Object []months = {"January", "February", "March", "April",
            "May", "June", "July", "August", "September", "October", "November", "December"}; 
        return months;
    }
    
    public static Object[] Weeks(){
        
        String []week = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"}; 
        return  week;
    }
     
    public static Object[] Years(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR) -10;
        /*
        10 backwards, 10 forward;
        */
        
        List<Integer> yearList = new ArrayList<>();
        
        for (int i = 1; i < 21; i++) {
            yearList.add(year+i);
        }
        
        return yearList.toArray();
    } 


    
    /*
    Combox itemslelction
    */

    public class OnComboItemSelected implements ItemListener{

        @Override
        public void itemStateChanged(ItemEvent e) {
           comboItem = e.getItem();
        }


    }

    /*
    Animation on mouse :listener
    */
   private class OnFieldMouseListener extends MouseInputAdapter{

        @Override
        public void mouseEntered(MouseEvent e) {
            JComponent comp = (JComponent)e.getSource();
            
            if(formType == FormType.TEXTAREA){
                Card card = (Card)(comp.getParent().getParent().getParent());
                card.setBackground(hp.getColor(fieldBgColor, .6f));
                card.repaint(); 
            }else{
               Card card = (Card)comp.getParent();
                card.setBackground(hp.getColor(fieldBgColor, .6f));
                card.repaint(); 
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
           JComponent comp = (JComponent)e.getSource();
            if(formType == FormType.TEXTAREA){
                Card card = (Card)(comp.getParent().getParent().getParent());
                card.setBackground(fieldBgColor);
                card.repaint(); 
            }else{
               Card card = (Card)comp.getParent();
                card.setBackground(fieldBgColor);
                card.repaint(); 
            }
        }

        
    }



}
