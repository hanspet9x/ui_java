package containers;

import controllers.AncestorAdapter;
import services.HPGui;

import javax.swing.event.AncestorEvent;
import java.awt.*;

public class CenterOld extends TransparentContainer{

    private final GridBagLayout gridBagLayout;

    public CenterOld(Component component) {
        this.gridBagLayout = new GridBagLayout();
        this.setLayout(gridBagLayout);
        common();
        add(component);
    }

    public CenterOld() {
        this.gridBagLayout = new GridBagLayout();
        this.setLayout(gridBagLayout);
        common();
    }

    public CenterOld(int opacity) {
        super(opacity);
        this.gridBagLayout = new GridBagLayout();
        this.setLayout(gridBagLayout);
        common();
    }

    private void common(){

        this.addAncestorListener(new AncestorAdapter() {
            @Override
            public void ancestorAdded(AncestorEvent e) {

                HPGui.setAllSizes(CenterOld.this, e.getAncestor().getWidth(), e.getAncestor().getHeight());
            }
        });
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


/*    private ComponentAdapter resizing(){
        return new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Container container = getParent();
                if(container != null){
                    HPGui.setAllSizes(Center.this, container.getPreferredSize());
                   *//* if(container.getName() != null && container.getName().contains("contentPane")){
                        int w = container.getWidth();
                        int h = container.getHeight();
                        HPGui.setAllSizes(Center.this, w, h);
                    }else{
                        HPGui.setAllSizes(Center.this, container.getPreferredSize());
                    }*//*
                }
            }
        };
    }*/



}
