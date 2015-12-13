/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tilemap7.Mission;

import tilemap7.Buildings.Farm;
import tilemap7.Tile;


public class Mission_DiscoverFarm extends Mission_Discover{
    public Mission_DiscoverFarm(Tile tile){
        super(null,tile);
    }
    public boolean missionCompleted(){
        if(discoverdEntity[0] != null){
            if(discoverdEntity[0].getClass().getName().compareTo("Farm") ==0){
                return true;
            }
        }
        return false;
    }
    public Farm getFarm(){
        //System.out.println(discoverdEntity);
        if(discoverdEntity[0] != null){
            if(discoverdEntity[0].getClass().getName().compareTo("Farm") ==0){
                return (Farm) discoverdEntity[0];
            }else{
                return null;
            }
        }else{
            return null;
        }
    }
    
    
    @Override
    public String getType(){
        return "DiscoverFarm";
    }
    
}
