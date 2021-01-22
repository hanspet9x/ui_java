package animations;

import containers.TransparentContainer;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Transform extends TransparentContainer {

    public Component component;

    public Transform() {
        setOpaque(false);
    }

    @Override
    public Component add(Component comp) {
        if(getComponents().length > 0){
            removeAll();
        }
        component = comp;
        return super.add(comp);
    }

}
