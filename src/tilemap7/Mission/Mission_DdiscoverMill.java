/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tilemap7.Mission;

import tilemap7.Buildings.Mill;
import tilemap7.Tile;
import tilemap7.missionMaster;

/**
 *
 * @author Steve
 */

public class Mission_DdiscoverMill extends Mission_Ddiscover{

    public Mission_DdiscoverMill(missionMaster home,Tile tile){
        super(home,tile, Mill.class.getName());
        
    }
    
    public Mill getMill(){
        return (Mill) discovery;
    }
    
    @Override
    public String getType(){
        return "DdiscoverMill";
    }



}
