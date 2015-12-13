/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;
import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferStrategy;
/**
 * Write a description of class Drawsheet here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Drawsheet extends Canvas
{
    BufferStrategy strategy;
    int xSize;
    int ySize;
    
    public Drawsheet(int xPos, int yPos, int xSize, int ySize)
    {
        setBounds(xPos,yPos,xSize,ySize);
        this.xSize = xSize;
        this.ySize = ySize;
        requestFocus();
        setIgnoreRepaint(true);
    }
    
    public static Drawsheet createDrawsheet(Rectangle r)
    {
        return new Drawsheet(r.x,r.y,r.width,r.height);
    }

    public void createBuffer(){
        createBufferStrategy(2);
        strategy = getBufferStrategy();
    }
    
    public Graphics2D getDrawsheet(){
        if(strategy != null){
            return (Graphics2D) strategy.getDrawGraphics();
        } 
        return null;
    }
    
    public void flipBuffer(){
        strategy.show();
    }
    
    public int getXSize(){
        return xSize;
    }
    
    public int getYSize(){
        return ySize;
    }
}
