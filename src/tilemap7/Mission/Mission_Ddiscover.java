/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tilemap7.Mission;

import tilemap7.Entity;
import tilemap7.LittleMan;
import tilemap7.Tile;
import tilemap7.missionMaster;

/**
 *
 * @author Steve
 */

public class Mission_Ddiscover extends Mission{
    String target;
    Entity discovery;
    Tile tile;
    int distance = 2;
    int counter = -1;
    int direction = 2;
    int counter2 = 0;
    public Mission_Ddiscover(missionMaster home,Tile tile, String target){
        super(home);
        this.target = target;
        this.tile = tile.getNeighbour(7);
        missionList = new Mission[2];
        missionList[0] = new Mission_Discover(null,this.tile);
        missionList[1] = new Mission_GoTo(tile.getPos());
    }
    public boolean missionCompleted(){
        home.missionCompleted(this);
        return true;
    }
    
    @Override
    public boolean doMission(LittleMan actor){
        if(actMission == missionList.length){
            return true;
        }
        if(missionList[actMission].doMission(actor)){
            if(missionList[actMission].getType().compareTo("DiscoverMission")==0){
                Mission_Discover m = (Mission_Discover) missionList[actMission];
                Entity discoveredEntity = m.discoverdEntity[0];
                if(discoveredEntity != null){
                    boolean alreadySet =false;
                    if(discoveredEntity.getClass().getName().compareTo(target) ==0){
                        this.discovery = discoveredEntity;
                        actMission++;
                        
                    }else{
                        counter++;
                        if(counter == distance){
                            direction = direction + 2;
                            if(direction == 2){
                                distance = distance + 2;
                                tile = tile.getNeighbour(7);
                                counter = -1;
                                alreadySet =true;
                            }
                            if(direction == 8){
                                direction = 0;
                            }
                            if(!alreadySet)
                                counter = 0;
                        }
                        if(!alreadySet){
                            tile = tile.getNeighbour(direction);
                        }
                        missionList[0] = new Mission_Discover(null,tile);
                        
                    }
                }
            }else{
                actMission++;
            }
        }
        if(actMission == missionList.length){
            return true;
        }else{
            return false;
        }
    }
    

    
    
    @Override
    public String getType(){
        return "Ddiscover";
    }



}

    

