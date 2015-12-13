/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tilemap7.Mission;

import tilemap7.Buildings.Farm;
import tilemap7.Buildings.Mill;
import tilemap7.Buildings.House;
import tilemap7.Entity;
import tilemap7.LittleMan;
import tilemap7.Tile;
import tilemap7.missionMaster;


public class Mission_DeliverFood extends Mission{
    Tile tile;
    House house;
    Farm farm;
    Entity discoverdEntity[] = new Entity[1];
    //Mission[] missionList;
    //int actMission;
    public Mission_DeliverFood(missionMaster home,House house, Farm farm, Mill mill){
        super(home);
        this.house = house;
        this.tile = farm.tile;
        this.farm = farm;
        missionList = new Mission[4];
        missionList[0] = new Mission_GoTo(tile.getCenter());
        //missionList[1] = new GetEntityMission(discoverdEntity);
        missionList[1] = new Mission_GetWork(farm,farm);
        missionList[2] = new Mission_GetWork(mill,mill);
        missionList[3] = new Mission_GoTo(house.getTile().getCenter());
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
    public boolean missionCompleted(){
        home.missionCompleted(this);
        return true;
    }
    @Override
    public String getType(){
        return "PickUp";
    }
    public int getPickUp(){
        return 1;
    }
}
