package javagg.util;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class swaPnl extends MouseAdapter {

    private final JPanel panelToHidden;
    private final JPanel panelToShow;

    public swaPnl(JPanel pPanelToHidden, JPanel pPanelToShow) {
        this.panelToHidden = pPanelToHidden;
        this.panelToShow = pPanelToShow;
    }   
    
    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        panelToShow.setVisible(true);
        panelToHidden.setVisible(false);
    }
}


