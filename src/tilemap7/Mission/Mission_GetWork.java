/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tilemap7.Mission;

import tilemap7.LittleMan;
import tilemap7.missionMaster;
import tilemap7.workable;



public class Mission_GetWork extends Mission{
    workable workplace;
    public Mission_GetWork(workable workplace, missionMaster master){
        super(master);
        this.workplace = workplace;
        missionList = new Mission[3];
        missionList[0] = new Mission_GoTo(workplace.getWorkPos());
        missionList[1] = null;
        missionList[2] = new Mission_GoTo(workplace.getWorkPos());
        //System.out.println("new getwork mission created");
    }
    
    
    @Override
    public boolean doMission(LittleMan actor){
        if(missionList[actMission].doMission(actor)){ //wenn act mission abgeschlossen ist
            //System.out.println("do work mission at step " + actMission);
            actMission++; //wird act mission erhoeht
            //configurationen fuer die naechste mission
            if(actMission == 1){ //config fuer mission nr1
                missionList[1] = workplace.getWork();
                if(missionList[1] == null)
                    actMission--;
            }
            
        }
        //abbruchbedingung
        if(actMission == missionList.length){
            return true;
        }else{
            return false;
        }
    }
    
}