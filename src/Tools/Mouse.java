/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import tilemap7.GV;

/**
 *
 * @author Steve
 */
public class Mouse implements java.awt.event.MouseListener, MouseMotionListener, MouseWheelListener{

    private MouseObject mouseObject;
    private clickHandler clickHandler;
    private Point mousePosition;
    
    public Mouse(){
    }
    
    
    public void draw(Graphics2D g){
        if(mouseObject != null){
            mouseObject.drawMouse(g);
        }
    }
    
    /**
     * Handles a mouseclick by the user. Checks first if there is a registered mouseObject waiting for a click.
     * If not it checks if there is an clickHandler waiting for a click
     * if none of the above, it will call a regualr tilemap mouseclick
     * @param e 
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (mouseObject != null) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                mouseObject.doLeftClick(e);
            } else if (e.getButton() == MouseEvent.BUTTON3) {
                mouseObject.doRightClick(e);
            }
        } else {
            if (clickHandler != null) {
                clickHandler.reservedClick(e.getX(), e.getY());
            } else {
                if(GV.get().getGameWindow().isSetSouthPanel()){
                    GV.get().getGameWindow().resetSouthPanel();
                }
                GV.get().getTileMap().mouseClicked(e);
            }
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

    @Override
    public void mouseDragged(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(mouseObject != null){
            mouseObject.updateMousePosition(e.getPoint());
        }
        mousePosition = e.getPoint();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
    public void setMouseObject(MouseObject mouseObject){
        this.mouseObject = mouseObject;
        if(mouseObject != null){
            mouseObject.updateMousePosition(mousePosition);
        }
    }

    public void setClickHandler(Tools.clickHandler clickHandler) {
        this.clickHandler = clickHandler;
    }
    
    
    
}
