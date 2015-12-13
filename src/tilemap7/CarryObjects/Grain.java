/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tilemap7.CarryObjects;

import Tools.SpriteStore;

/**
 *
 * @author Steve
 */
public class Grain extends CarryObject{
    public Grain(){
        super(SpriteStore.get().getSprite("grain.png"));
    }
    
    
}
