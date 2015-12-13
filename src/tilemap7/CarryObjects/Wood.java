/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tilemap7.CarryObjects;

import Tools.Sprite;
import Tools.SpriteStore;

/**
 *
 * @author Steve
 */
public class Wood extends CarryObject{

    public Wood() {
        super(SpriteStore.get().getSprite("wood.png"));
    }
    
}
