/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tilemap7.Crafting;

import java.awt.Point;
import tilemap7.Buildings.Tools.Stock;
import tilemap7.LittleMan;

/**
 *
 * @author Steve
 */
public interface craftable {
    /**
     * Crafts at this craftable object. If success a new carryobject will be added to outstock.
     * @param man 
     */
    public abstract boolean craft(LittleMan man);
    
    /**
     * Returns the stock of this craftable were all crafted elements will be put.
     * @return
     */
    public abstract Stock getOutStock();
    
    /**
     * Tells if this object is craftable or if it needs to grow first.
     * @return 
     */
    public boolean isCraftable();
    
    /**
     * Returns the position of this craftable
     * @return 
     */
    public Point getPosition();
}
