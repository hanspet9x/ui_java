package containers;

import model.FlexDirection;
import services.HPGui;

import java.awt.*;

@SuppressWarnings("rawtypes")
public class ExpandedView {

    private FlexDirection direction = FlexDirection.ROW;
    private final Card wrapper = new Card(new GridBagLayout());
    private final  GridBagConstraints constraints = new GridBagConstraints();
    private int x = 0;
    private int y = 0;

    public ExpandedView(FlexDirection direction) {
        this.direction = direction;
        common();
    }

    public ExpandedView() {
        common();
    }

    private void common(){
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
    }

    public void add(Component component){
        resetConfiguration();
        wrapper.add(component, constraints);
        incrementPositions();
    }

    public void addExpandedView(Component component){
        configureExpandedView();
        paddingReset();
        wrapper.add(component, constraints);
        incrementPositions();
    }

    public void addExpandedView(){
        configureExpandedView();
        paddingReset();
        wrapper.add(HPGui.getCard(), constraints);
        incrementPositions();
    }

    public void addSpace(int space){
        addPadding(space);

        wrapper.add(HPGui.getCard(), constraints);
        incrementPositions();
    }

    private void resetConfiguration(){
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 0;
        constraints.weighty = 0;
        paddingReset();
    }

    private void configureExpandedView(){
        if(direction == FlexDirection.ROW){
            constraints.fill = GridBagConstraints.HORIZONTAL;
            constraints.weightx = 1;
        }else{
            constraints.fill = GridBagConstraints.VERTICAL;
            constraints.weighty = 1;
        }
    }

    private void incrementPositions(){
        if(direction == FlexDirection.ROW){
            constraints.gridx = ++x;
        }else{
            constraints.gridy = ++y;
        }
    }

    private void addPadding(int space){
        if(direction == FlexDirection.ROW){
            constraints.ipadx = space;
        }else{
            constraints.ipady = space;
        }
    }

    private void paddingReset(){
        constraints.ipadx = 0;
        constraints.ipady = 0;
    }
    public Card build(){
        return wrapper;
    }

    public Card getWrapper() {
        return wrapper;
    }
}
