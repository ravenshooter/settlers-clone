/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tilemap7;


/**
 * Parent for all moving entities
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

public abstract class movingEntity extends Entity
{

    public movingEntity(Tile tile)
    {
        super(tile);
        checkCollision = false;
    }

    public void setCollision(boolean b)
    {
        checkCollision = b;
    }

    boolean checkCollision;
}
