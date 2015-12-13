/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tilemap7.Mission;

import tilemap7.Buildings.Stock;
import tilemap7.CarryObjects.CarryObject;
import tilemap7.CarryObjects.Grain;
import tilemap7.LittleMan;

/**
 *
 * @author Steve
 */
public class Mission_PickUp extends Mission{
    Stock pickupStock;
    Stock dropStock;
    String carryObject;
    boolean sendToStock;
    boolean pickedUp;
    boolean droppedDown;

    public Mission_PickUp(Stock pickupStock, Stock dropStock, String carryObject, LittleMan actor) {
        super(null);
        this.pickupStock = pickupStock;
        this.dropStock = dropStock;
        this.carryObject = carryObject;
        pickupStock.reportPickUp();
        dropStock.reportDropDown();
    }

    @Override
    public boolean doMission(LittleMan l) {
        
        if(!sendToStock){
            l.setTarget(pickupStock.getPosition());
            sendToStock = true;
        }else if(!pickedUp){
            if(l.atPosition(pickupStock.getPosition())){
                l.setCarryObject(pickupStock.remove(carryObject));
                pickedUp = true;
                l.setTarget(dropStock.getPosition());
            }
        }else if(!droppedDown){
            if(l.atPosition(dropStock.getPosition())){
                if(!dropStock.isFull()){
                    dropStock.add(l.finishCarryObject());
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
    
    
}
