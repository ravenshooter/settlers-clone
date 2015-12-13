/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tilemap7.Mission;

import tilemap7.LittleMan;
import tilemap7.missionMaster;

/**
 * Write a description of class Mission here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Mission
{
    public missionMaster home;
    public Mission[] missionList;
    
    public int actMission;
    /**
     * Constructor for objects of class Mission
     */
    public Mission(missionMaster home)
    {
        this.home = home;
        actMission = 0;
    }

    public void missionCompleted(missionMaster master){}
    public abstract boolean doMission(LittleMan actor);
    public String getType()
    {
        return this.getClass().getSimpleName();
    }
}


    
















