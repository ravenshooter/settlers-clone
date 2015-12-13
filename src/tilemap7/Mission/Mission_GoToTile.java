/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tilemap7.Mission;

import tilemap7.LittleMan;
import tilemap7.Tile;

public class Mission_GoToTile extends Mission{
    Tile tile;
    public Mission_GoToTile(Tile tile){
         super(null);
         this.tile = tile;
         missionList = new Mission[1];
         missionList[0] = new Mission_GoTo(tile.getCenter());
    }
    @Override
    public boolean doMission(LittleMan actor){
        if(actor.getTile() != tile){
            missionList[0].doMission(actor);
            return false;
        }else{
            return true;
        }
    }
    @Override
    public String getType(){
        return "GoToTileMission";
    }
}