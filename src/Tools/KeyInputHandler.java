/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import tilemap7.GV;
import tilemap7.Letter;
import tilemap7.Marker;
/**
 * Write a description of class KeyInputHandler here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class KeyInputHandler extends KeyAdapter
{
    boolean leftPressed;
    boolean rightPressed;
    boolean upPressed;
    boolean downPressed;
    Marker marker;
    /**
     * Constructor for objects of class KeyInputHandler
     */
    public KeyInputHandler()
    {
        leftPressed = false;
        rightPressed = false;
        upPressed = false;
        downPressed = false;
        marker = GV.get().getMarker();
    }
    
    @Override
    public void keyPressed(KeyEvent e){
        if(marker == null){
            marker = GV.get().getMarker();
        }
        boolean cameramove;
        switch(e.getKeyCode()){
            case KeyEvent.VK_LEFT:  marker.move(4);break;
            case KeyEvent.VK_UP:    marker.move(1);break;
            case KeyEvent.VK_RIGHT: marker.move(2);break;
            case KeyEvent.VK_DOWN:  marker.move(3);break;
            case KeyEvent.VK_T:     GV.get().drawLittleManMissionList = !GV.get().drawLittleManMissionList; break;
            case KeyEvent.VK_A:     marker.addLetter(new Letter("A"));break;
            case KeyEvent.VK_M:     GV.get().getGameWindow().toggleShowMap();break;
            case KeyEvent.VK_Y:     marker.addLetter(new Letter("Y"));break;
            case KeyEvent.VK_R:     marker.addLetter(new Letter("menu"));break;
            case KeyEvent.VK_O:     marker.addLetter(new Letter("farm"));break;
                       
        }
    }
        
}

/*
    public void keyPressed(KeyEvent e){
        System.out.println("suc");
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            if(!leftPressed){
                int[] vek = {-1,0};
                Camera.get().newMovement(vek);
                leftPressed = true;
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            if(!rightPressed){
                int[] vek = {1,0};
                Camera.get().newMovement(vek);
                rightPressed = true;
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_UP){
            if(!upPressed){
                int[] vek = {0,-1};
                Camera.get().newMovement(vek);
                upPressed = true;
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN){
            if(!downPressed){
                int[] vek = {0,1};
                Camera.get().newMovement(vek);
                downPressed = true;
            }
        }
    }
    
    public void keyReleased(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            if(leftPressed){
                int[] vek = {1,0};
                Camera.get().newMovement(vek);
                leftPressed = false;
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            if(rightPressed){
                int[] vek = {-1,0};
                Camera.get().newMovement(vek);
                rightPressed = false;
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_UP){
            if(upPressed){
                int[] vek = {0,1};
                Camera.get().newMovement(vek);
                upPressed = false;
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN){
            if(downPressed){
                int[] vek = {0,-1};
                Camera.get().newMovement(vek);
                downPressed = false;
            }
        }
    } 
    */