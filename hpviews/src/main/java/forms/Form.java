package forms;

import containers.TransparentContainer;
import services.HPGui;

import javax.swing.*;
import java.awt.*;
import containers.*;
public class Form extends TransparentContainer {

    private String label = null;
    private String placeholder = null;

    private int width = 250;
    private int height = 25;
    private final int padding = 10;

    private Color inputColor = Color.GRAY;

    private boolean enabled;

    public HPGui hp = new HPGui();

    private JTextField field;

    private Card2<Object> fieldWrapper;

    private boolean isHasLeftComp = false, isHasRightComp = false;
    private Color backgroundColor = HPGui.getColor("#f6f6f7");

    public Form() {
        formBuild();
    }

    public Form(int width, int height){
        this.width = width;
        this.height = height;
        formBuild();
    }

    public Form(String label, int width, int height) {
        this.label = label;
        this.width = width;
        this.height = height;
        formBuild();
    }

    public Form(String label) {
        this.label = label;
        formBuild();
    }

    private void formBuild(){

        setLayout(new BorderLayout());

        field = new JTextField();
        field.setFont(new Font(HPGui.FontText, Font.PLAIN, 18));
        field.setOpaque(false);
        field.setBorder(null);

        fieldWrapper =  new Card2<>();
        fieldWrapper.setLayout(new BorderLayout());

        fieldWrapper.setBorderRadius(10);
        fieldWrapper.setPadding(padding);
        fieldWrapper.setBackground(backgroundColor);
        fieldWrapper.setBoxShadow(2, hp.getColor(Color.BLACK, .09f));
        fieldWrapper.add(field);

        /*set label and placeholder*/

        int margin  = 0;

        if(label != null){
            margin = 2;
            fieldWrapper.setMarginTop(margin);
            JLabel formTitle = new JLabel(label);
            formTitle.setFont(new Font(HPGui.FontText, Font.PLAIN, 15));
            add(formTitle, BorderLayout.PAGE_START);
        }

        fieldWrapper.setCardSize(width, height);
//        HPGui.setAllSizes(this, width, height+margin);
        add(fieldWrapper);
    }

    public String getLabel() {
        return label;
    }

    public Card2<Object> getFieldWrapper() {
        return fieldWrapper;
    }

    public void setLeftComp(Component component){
        fieldWrapper.add(component, BorderLayout.LINE_START);
        field.setBorder(BorderFactory.createEmptyBorder(0, padding, 0, 0));
        isHasLeftComp = true;
        setBothBorder();
    }

    public void setRightComp(Component component){
        fieldWrapper.add(component, BorderLayout.LINE_END);
        field.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, padding));
        isHasRightComp = true;
        setBothBorder();
    }

    private void setBothBorder(){
        if(isHasRightComp && isHasLeftComp){
            field.setBorder(BorderFactory.createEmptyBorder(0, padding, 0, padding));
        }
    }


    public void setLabel(String label) {
        this.label = label;

        if(label != null){

            fieldWrapper.setMarginTop(2);
            JLabel formTitle = new JLabel(label);
            formTitle.setFont(new Font(HPGui.FontText, Font.PLAIN, 15));
            add(formTitle, BorderLayout.PAGE_START);

        }

    }

/*    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }*/

    public JTextField getField() {
        return field;
    }


    public void setBackgroundColor(Color backgroundColor) {
        fieldWrapper.setBackground(backgroundColor);
    }

    public void setBoxShadow(int size, Color color){
        fieldWrapper.setBoxShadow(size, color);
    }

    public Color getInputColor() {
        return inputColor;
    }

    public void setInputColor(Color inputColor) {
        this.inputColor = inputColor;
        field.setForeground(inputColor);
    }


    public void formUpdate(){
        fieldWrapper.revalidate();
        fieldWrapper.repaint();
    }

    public void setMarginTop(int value){
        setBorder(BorderFactory.createEmptyBorder(value, 0, 0, 0));
    }

    public void setMarginBottom(int value){
        setBorder(BorderFactory.createEmptyBorder(0, 0, value, 0));
    }

    public void setMarginLeft(int value){
        setBorder(BorderFactory.createEmptyBorder(0, value, 0, 0));
    }

    public void setMarginRight(int value){
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, value));
    }
    public String getValue(){
        return field.getText();
    }

    public void setValue(String value){
        field.setText(value);
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }


}
