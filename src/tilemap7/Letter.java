/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tilemap7;

import Tools.SpriteStore;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
/**
 * Write a description of class Letter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Letter extends Entity
{

    
    public Letter(String type)
    {
        super(null);
        sprite = SpriteStore.get().getSprite(type+".png");
    }

    @Override
    public void draw(Graphics g, int x, int y){
        g.drawImage(sprite.getImage(),x,y,null);
    }
    
    @Override
    public void mouseClicked(MouseEvent e){}
    
    
}
