/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools;

import java.awt.Image;
/**
 * Write a description of class Sprite here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Sprite
{
    Image image;

    /**
     * Constructor for objects of class Sprite
     */
    public Sprite(Image image)
    {
        this.image = image;
    }

    public int getWidth(){
        return image.getWidth(null);
    }
    
    public int getHeight(){
        return image.getHeight(null);
    }
    
    public Image getImage(){
        return image;
    }
    
}

