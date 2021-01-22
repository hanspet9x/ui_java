package containers;

import model.FlexAlignment;
import model.FlexDirection;
import services.HPGui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.util.Objects;

public class Flex extends TransparentContainer{

    private FlexDirection flexDirection;
    private FlexAlignment flexAlignmentX;
    private FlexAlignment flexAlignmentY;
    private int xSpace = 0;
    private int ySpace = 0;
    private boolean isAddedSpace = false;
    private final String boxName = "flexBoxRigidArea";
    private TransparentContainer childContainer;
    private boolean isAlignComponents = false;
    private boolean isTrimSpace = true;

    public Flex() {
        flexDirection = FlexDirection.ROW;
        flexAlignmentX = FlexAlignment.LEFT;
        flexAlignmentY = FlexAlignment.TOP;
        common();

    }

    public Flex(FlexDirection flexDirection, FlexAlignment flexAlignmentX) {
        this.flexAlignmentX = flexAlignmentX;
        this.flexAlignmentY = FlexAlignment.TOP;
        this.flexDirection = flexDirection;
        common();
    }

    public Flex(FlexDirection flexDirection, FlexAlignment flexAlignmentX, FlexAlignment flexAlignmentY) {
        this.flexAlignmentX = flexAlignmentX;
        this.flexAlignmentY = flexAlignmentY;
        this.flexDirection = flexDirection;
        common();
    }

    public Flex(FlexDirection flexDirection) {
        this.flexAlignmentX = FlexAlignment.LEFT;
        this.flexAlignmentY = FlexAlignment.TOP;
        this.flexDirection = flexDirection;
        common();
    }


    private void common(){

        childContainer = new TransparentContainer();
        if(flexDirection == FlexDirection.ROW){

            childContainer.setLayout(new BoxLayout(childContainer, BoxLayout.LINE_AXIS));

        }else{
            childContainer.setLayout(new BoxLayout(childContainer, BoxLayout.PAGE_AXIS));
        }
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        childContainer.setAlignmentX(getXAlignment());
        childContainer.setAlignmentY(getYAlignment());
        childContainer.addContainerListener(compAddedListener());
    }

    public  Flex addComponent(Component component){
        addComponent((JComponent)component);
        return this;
    }

    public Flex addComponent(JComponent component){
        HPGui.setAllSizes(component, component.getPreferredSize());
        if(isAlignComponents){
            component.setAlignmentX(getXAlignment());
            component.setAlignmentY(getYAlignment());
        }else{
            component.setAlignmentX(Component.LEFT_ALIGNMENT);
            component.setAlignmentY(Component.TOP_ALIGNMENT);
        }

        if(Objects.isNull(component.getName())){
            isAddedSpace = false;
        }else{
            isAddedSpace = component.getName().equals(boxName);
        }
        childContainer.add(component);
        return this;
    }


    public Flex build(){

        if(xSpace > 0 || ySpace > 0 && isTrimSpace){
            childContainer.remove(childContainer.getComponentCount()-1);
        }
        add(childContainer);
        return this;
    }

    private ContainerAdapter compAddedListener(){
        return new ContainerAdapter() {
            @Override
            public void componentAdded(ContainerEvent e) {
                if(!isAddedSpace){
                    if(xSpace > 0 || ySpace > 0){
                        Component box = Box.createRigidArea(new Dimension(xSpace, ySpace));
                        box.setName(boxName);
                        addComponent((JComponent) box);
                    }
                }
            }
        };
    }

    public boolean isAlignComponents() {
        return isAlignComponents;
    }

    public void setAlignComponents(boolean alignComponents) {
        isAlignComponents = alignComponents;
    }

    public boolean isTrimSpace() {
        return isTrimSpace;
    }

    public void setTrimSpace(boolean trimSpace) {
        isTrimSpace = trimSpace;
    }

    /*    @Override
    public Component add(Component comp) {

        JComponent component = (JComponent)comp;
        HPGui.setAllSizes(component, component.getPreferredSize());
        component.setAlignmentX(getXAlignment());
        component.setAlignmentY(getYAlignment());

        if(Objects.isNull(comp.getName())){
            isAddedSpace = false;
        }else{
            isAddedSpace = comp.getName().equals(boxName);
        }


        return super.add(component);
    }*/




    public int getXSpace() {
        return xSpace;
    }

    public void setXSpace(int xSpace) {
        this.xSpace = xSpace;
    }

    public int getYSpace() {
        return ySpace;
    }

    public void setYSpace(int ySpace) {
        this.ySpace = ySpace;
    }

    private float getXAlignment(){
        float alignment = 0;
        switch (flexAlignmentX){
            case LEFT -> alignment = Component.LEFT_ALIGNMENT;
            case RIGHT -> alignment = Component.RIGHT_ALIGNMENT;
            case CENTER -> alignment = Component.CENTER_ALIGNMENT;
        }

        return alignment;
    }

    private float getYAlignment(){
        float alignment = 0;
        switch (flexAlignmentY){
            case CENTER -> alignment = Component.CENTER_ALIGNMENT;
            case TOP -> alignment = Component.TOP_ALIGNMENT;
            case BOTTOM -> alignment = Component.BOTTOM_ALIGNMENT;
        }

        return alignment;
    }


    public FlexDirection getFlexDirection() {
        return flexDirection;
    }

    public void setFlexDirection(FlexDirection flexDirection) {
        this.flexDirection = flexDirection;
    }

    public FlexAlignment getFlexAlignmentX() {
        return flexAlignmentX;
    }

    public void setFlexAlignmentX(FlexAlignment flexAlignmentX) {
        this.flexAlignmentX = flexAlignmentX;
    }
}
