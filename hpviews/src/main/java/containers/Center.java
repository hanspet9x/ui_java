package containers;

import services.HPGui;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Center extends TransparentContainer{

    private final GridBagLayout gridBagLayout;

    public Center(Component component) {
        this.gridBagLayout = new GridBagLayout();
        this.setLayout(gridBagLayout);
        common();
        add(component);
    }

    public Center() {
        this.gridBagLayout = new GridBagLayout();
        this.setLayout(gridBagLayout);
        common();
    }

    public Center(int opacity) {
        super(opacity);
        this.gridBagLayout = new GridBagLayout();
        this.setLayout(gridBagLayout);
        common();
    }

    private void common(){
        addComponentListener(resizing());
    }

    @Override
    public Component add(Component comp) {

        if(getComponentCount() > 0)removeAll();
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = GridBagConstraints.RELATIVE;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.gridheight = GridBagConstraints.REMAINDER;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.CENTER;

        gridBagLayout.setConstraints(comp, constraints);
        return super.add(comp);
    }


    private ComponentAdapter resizing(){
        return new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Container container = getParent();
                if(container != null){
                    HPGui.setAllSizes(Center.this, container.getPreferredSize());
                   /* if(container.getName() != null && container.getName().contains("contentPane")){
                        int w = container.getWidth();
                        int h = container.getHeight();
                        HPGui.setAllSizes(Center.this, w, h);
                    }else{
                        HPGui.setAllSizes(Center.this, container.getPreferredSize());
                    }*/
                }
            }
        };
    }



}
