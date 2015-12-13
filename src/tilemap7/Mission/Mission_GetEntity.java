/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tilemap7.Mission;

import tilemap7.Entity;
import tilemap7.LittleMan;


public class Mission_GetEntity extends Mission{
    Entity discoveredEntity[];
    public Mission_GetEntity(Entity discoveredEntity[]){
        super(null);
        this.discoveredEntity = discoveredEntity;
    }
    @Override
    public boolean doMission(LittleMan actor){
        discoveredEntity[0] = actor.getTile().getEntity();
        if(discoveredEntity[0] == null){
            //System.out.println("Actor" + actor.ID + " has found nothing");
        }else{
            //System.out.println("Actor" + actor.ID + " has found " +discoveredEntity[0].getClass().getName());
        }
        return true;
    }
}
