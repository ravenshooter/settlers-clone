/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tilemap7.Mission;

import tilemap7.Buildings.Farm;
import tilemap7.Tile;
import tilemap7.missionMaster;

public class Mission_DdiscoverFarm extends Mission_Ddiscover{

    public Mission_DdiscoverFarm(missionMaster home,Tile tile){
        super(home,tile, Farm.class.getName());
        
    }
    
    @Override
    public String getType(){
        return "DdiscoverFarm";
    }

    public Farm getFarm(){
        return (Farm) discovery;
    }


}

