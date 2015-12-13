/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tilemap7;

import tilemap7.Mission.Mission;


/**
 * Write a description of class PathMission here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PathMission extends Mission
{
    
    /**
     * Constructor for objects of class PathMission
     */
    public PathMission(missionMaster master)
    {
        super(master);
    }

    public boolean doMission(LittleMan actor){
        return true;
    }
}
