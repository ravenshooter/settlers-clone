/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tilemap7.CarryObjects;

import Tools.Sprite;
import java.awt.Graphics;
import java.awt.Image;

/**
 *
 * @author Steve
 */
public class CarryObject {
    
    Sprite sprite;
    
    public CarryObject(Sprite sprite){
        this.sprite = sprite;
    }
    
    public Image getImage(){
        return sprite.getImage();
    }
    
    public String getType(){
        return this.getClass().getSimpleName();
    }
    
    public void draw(Graphics g, int x, int y){
        if(sprite != null){
            g.drawImage(sprite.getImage(),x,y,null);
        }
    }
}
