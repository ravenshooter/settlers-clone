/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;
/**
 * Write a description of class SpriteStore here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SpriteStore
{
    private static SpriteStore instance = new SpriteStore();
    
    public static SpriteStore get(){
        return instance;
    }
    
    private HashMap sprites;
    /**
     * Constructor for objects of class SpriteStore
     */
    private SpriteStore()
    {
        sprites = new HashMap();
    }

   
    public boolean loadSprite(String ref)
    {
        BufferedImage sourceImage = null;
        try{
            URL url = this.getClass().getClassLoader().getResource(ref);
            if(url == null){
                url = this.getClass().getClassLoader().getResource("cross.png");
            }
            sourceImage = ImageIO.read(url);
        }catch(IOException e){
            System.out.println("Failed to load image " + ref);
            return false;
        }
        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        Image image = gc.createCompatibleImage(sourceImage.getWidth(),sourceImage.getHeight(),Transparency.TRANSLUCENT);
        
        image.getGraphics().drawImage(sourceImage,0,0,null);
        
        Sprite sprite = new Sprite(image);
        sprites.put(ref,sprite);
        return true;
    }
    
    public Sprite getSprite(String ref){
        Sprite sprite = (Sprite) sprites.get(ref);
        if(sprite==null){
            if(loadSprite(ref)){
                return (Sprite) sprites.get(ref);
            }
        }
        return sprite;
    }
    
}