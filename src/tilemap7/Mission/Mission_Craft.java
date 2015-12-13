/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tilemap7.Mission;

import tilemap7.Buildings.Tools.Stock;
import tilemap7.Crafting.craftable;
import tilemap7.Crafting.crafthead;
import tilemap7.LittleMan;
import tilemap7.missionMaster;

/**
 *
 * @author Steve
 */
public class Mission_Craft extends Mission{

    private craftable craft;
    private Stock dropStock;
    private crafthead head;
    
    private boolean sendToCraft;
    private boolean crafted;
    private boolean droppedDown;

    /**
     * 
     * @param craft
     * @param dropStock
     * @param craftTarget 
     */
    public Mission_Craft(craftable craft, crafthead head) {
        super(null);
        this.craft = craft;
        this.head = head;
        this.dropStock = head.getDropStock();
    }
    
    

    
    
    @Override
    public boolean doMission(LittleMan actor) {
        if(!sendToCraft){
            actor.setTarget(craft.getPosition());
            sendToCraft = true;
        }else if(!crafted){
            if(actor.atPosition(craft.getPosition())){
                if(!craft.isCraftable()){
                    setNewTarget(actor);
                }
                if(craft.craft(actor)){
                    actor.setCarryObject(craft.getOutStock().remove());
                    actor.setTarget(dropStock.getPosition());
                    crafted = true;
                }
            }
        }else if(!droppedDown){
            if(actor.atPosition(dropStock.getPosition())){
                if(!dropStock.isFull()){
                    dropStock.add(actor.finishCarryObject());
                    droppedDown = true;
                    return true;
                }else{
                    return false;
                }
            }
        }else{
            return true;
        }
        return false;
    }
    
    
    private void setNewTarget(LittleMan actor){
        if(head.getCraft()!= null && !head.getCraft().equals(craft)){
            sendToCraft = false;
            crafted = false;
            droppedDown = false;
            craft = head.getCraft();
        }else{
            actor.setTarget(dropStock.getPosition());
            crafted = true;
        }
    }
}
