package services;

import views.ExplorerIcon;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class ExplorerIconService extends MouseAdapter {

    private final Color bgOnEnter, bgOnOut;

    public ExplorerIconService(ExplorerIcon icon) {
        icon.addMouseListener(this);
        bgOnEnter = icon.getBgOnEnter();
        bgOnOut = icon.getBgOnOut();
    }
/*
    public void mouseEntered(MouseEvent e) {
        super.mouseEntered(e);
        ExplorerIcon icon = (ExplorerIcon)e.getSource();
            setIconBg(true, icon);

    }

    @Override
    public void mouseExited(MouseEvent e) {
        super.mouseExited(e);
        ExplorerIcon icon = (ExplorerIcon)e.getSource();
            setIconBg(false, icon);
    }*/

    private void setIconBg(boolean active, ExplorerIcon icon){
        if(active){
            icon.setBackground(bgOnEnter);
        }else{
            icon.setBackground(bgOnOut);
        }

        icon.repaint();
    }
}
