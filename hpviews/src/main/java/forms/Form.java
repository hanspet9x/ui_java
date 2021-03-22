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

    private JTextField field;

    private Card2<Object> fieldWrapper;

    private boolean isHasLeftComp = false, isHasRightComp = false;

    private Color backgroundColor = HPGui.getColor("#f6f6f7");

    private int boxShadowSize = 0;

    private Color boxShadowColor = HPGui.getColor(Color.BLACK, .09f);

    public Form() {
        formBuild();
    }

    public Form(int width, int height){
        this.width = width;
        this.height = height;
        formBuild();
    }

    public Form(int width){
        this.width = width;
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

    public JTextField getField() {
        return field;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setBoxShadow(int size, Color color){
        boxShadowSize = size;
        boxShadowColor = color;
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

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }


    public int getFormWidth() {
        return width;
    }

    public void setFormWidth(int width) {
        this.width = width;
    }


    public int getFormHeight() {
        return height;
    }

    public void setFormHeight(int height) {
        this.height = height;
    }

    public int getPadding() {
        return padding;
    }

    public boolean isHasLeftComp() {
        return isHasLeftComp;
    }

    public void setHasLeftComp(boolean hasLeftComp) {
        isHasLeftComp = hasLeftComp;
    }

    public boolean isHasRightComp() {
        return isHasRightComp;
    }

    public void setHasRightComp(boolean hasRightComp) {
        isHasRightComp = hasRightComp;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public int getBoxShadowSize() {
        return boxShadowSize;
    }

    public void setBoxShadowSize(int boxShadowSize) {
        this.boxShadowSize = boxShadowSize;
    }

    public Color getBoxShadowColor() {
        return boxShadowColor;
    }

    public void setBoxShadowColor(Color boxShadowColor) {
        this.boxShadowColor = boxShadowColor;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getFieldWrapperWidth() {
        return width;
    }

    public int getFieldWrapperHeight() {
        return height;
    }

    public void formBuild(){
        removeAll();
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
        fieldWrapper.setBoxShadow(boxShadowSize, boxShadowColor);
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
        add(fieldWrapper);
    }

}
