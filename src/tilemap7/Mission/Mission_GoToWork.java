/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tilemap7.Mission;

import tilemap7.LittleMan;
import tilemap7.missionMaster;

/**
 *
 * @author Steve
 */
public class Mission_GoToWork extends Mission{

    LittleMan littleMan;
    private boolean targetSet;
    private boolean arrived;
    

    public Mission_GoToWork(LittleMan littleMan) {
        super(null);
        this.littleMan = littleMan;
    }
    
   
    
    @Override
    public boolean doMission(LittleMan actor) {
        if(!targetSet){
            littleMan.setVisible(true);
            littleMan.setTarget(littleMan.getWorkPlace().getWorkPos());
            targetSet = true;
        }
        if(!arrived){
            if(littleMan.atPosition(littleMan.getWorkPlace().getWorkPos())){
                arrived = true;
            }
        }
        else{
            littleMan.getWorkPlace().reportAtWork(littleMan);
            return true;
        }
        return false;

        
    }
    
}
