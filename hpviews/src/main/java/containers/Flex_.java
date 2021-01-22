package containers;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Flex_ extends TransparentContainer{

    private final GridBagLayout gridBagLayout;
    private boolean column = false;
    private boolean row = true;


    public Flex_(){
        gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);
        addListeners();
    }

    private void addListeners() {
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
            }
        });
    }



    @Override
    public Component add(Component comp) {

        setUps(comp);
        return super.add(comp);
    }

    private void setUps(Component comp) {
        if(row){
            gridBagLayout.setConstraints(comp, getRowConstraint());
        }else{
            gridBagLayout.setConstraints(comp, getColumnConstraint());
        }
    }


    private GridBagConstraints getRowConstraint(){
        GridBagConstraints g = new GridBagConstraints();

        g.gridx = GridBagConstraints.RELATIVE;
        g.gridy = 0;
        g.anchor = GridBagConstraints.BASELINE;
        g.gridwidth = 1;
        g.gridheight = 1;
        g.weighty = 1;
        g.fill = GridBagConstraints.BOTH;
        return g;
    }

    private GridBagConstraints getColumnConstraint(){
        GridBagConstraints g = new GridBagConstraints();

        g.gridx = 0;
        g.gridy = GridBagConstraints.RELATIVE;
        g.gridwidth = 1;
        g.gridheight = 1;
        g.weightx = 1;
        g.weighty = 1;
        g.fill = GridBagConstraints.BOTH;
        return g;
    }

    public boolean isColumn() {
        return column;
    }

    public void setColumn(boolean column) {
        this.column = column;
        this.row = !column;
    }

    public boolean isRow() {
        return row;
    }

    public void setRow(boolean row) {
        this.row = row;
        this.column = !row;
    }
}
