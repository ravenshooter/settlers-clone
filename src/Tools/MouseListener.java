/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools;

import java.awt.event.MouseEvent;
import tilemap7.GV;

/**
 *
 * @author Steve
 */
public class MouseListener implements java.awt.event.MouseListener{

    @Override
    public void mouseClicked(MouseEvent e) {
        if(GV.get().getGameWindow().isSetSouthPanel()){
            GV.get().getGameWindow().resetSouthPanel();
            GV.get().getGameWindow().resetSouthPanel();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
