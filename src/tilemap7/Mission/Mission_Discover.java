/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tilemap7.Mission;

import tilemap7.Entity;
import tilemap7.LittleMan;
import tilemap7.Tile;
import tilemap7.missionMaster;


public class Mission_Discover extends Mission{
    Tile tile;
    Entity discoverdEntity[] = new Entity[1];
    public Mission_Discover(missionMaster home,Tile tile){
        super(home);
        this.tile = tile;
        missionList = new Mission[2];
        missionList[0] = (new Mission_GoToTile(tile));
        missionList[1] = (new Mission_GetEntity(discoverdEntity));
        //missionList[2] = (new GoToMission(home.getPos()));
        actMission = 0;
    }
    
    @Override
    public boolean doMission(LittleMan actor){
        if(missionList[actMission].doMission(actor)){
            actMission++;
        }
        if(actMission == missionList.length){
            return true;
        }else{
            return false;
        }
    }
    @Override
    public String getType(){
        return "DiscoverMission";
    }
}