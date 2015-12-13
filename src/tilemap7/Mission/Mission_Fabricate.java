/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tilemap7.Mission;

import java.awt.Point;
import tilemap7.Buildings.Factory;
import tilemap7.Buildings.Tools.Stock;
import tilemap7.CarryObjects.CarryObject;
import tilemap7.LittleMan;

/**
 *
 * @author Steve
 */
public class Mission_Fabricate extends Mission{
    Point workPos;
    int length;
    Factory factory;
    Stock pickUp;
    Stock dropDown;
    String ingredient;
    CarryObject product;
    boolean sendToPickUp;
    boolean pickedUp;
    boolean sendToWorkPos;
    boolean atWorkPos;
    boolean fabricated;
    boolean sendToDrop;
    boolean droppedDown;

    public Mission_Fabricate(Point workPos, int length, Factory factory, String ingredient, CarryObject product) {
        super(null);
        this.workPos = workPos;
        this.length = length;
        this.factory = factory;
        this.pickUp = factory.getInStock();
        this.dropDown = factory.getOutStock();
        this.ingredient = ingredient;
        this.product = product;
        pickUp.reportPickUp();
        dropDown.reportDropDown();
    }

    
    
    
    /**
     * Fabricate mision in following steps:
     * 1. Send to inStock
     * 2. Pick up ingridient at inStock; return if not available
     * 3. Send to WorkPos
     * 4. Fabricate
     * 5. Send to dropPos
     * 6. return true;
     * @param actor
     * @return 
     */
    @Override
    public boolean doMission(LittleMan actor) {
        
        //1.
        if(!sendToPickUp){
            actor.setTarget(pickUp.getPosition());
            sendToPickUp = true;
        //2
        }else if(!pickedUp){
            if(actor.atPosition(pickUp.getPosition())){
                CarryObject c = pickUp.remove(ingredient);
                if(c == null){ //if the ingredient is not in stock, cancel mission
                    return true;
                }else{
                    actor.setCarryObject(c);
                }
                pickedUp = true;
            }
        //3
        }else if(!sendToWorkPos){
            actor.setTarget(workPos);
            sendToWorkPos = true;
        //4
        }else if(!atWorkPos){
            if(actor.atPosition(workPos)){
                atWorkPos = true;
                actor.finishCarryObject();
                factory.setActive(true);
            }
        }else if(!fabricated){
            length--;
            if(length == 0){
                fabricated = true;
                factory.setActive(false);
                actor.setCarryObject(product);
            }
        //5
        }else if(!sendToDrop){
            actor.setTarget(dropDown.getPosition());
            sendToDrop = true;
        //6
        }else if(!droppedDown){
            if(!dropDown.isFull()){
                dropDown.add(actor.finishCarryObject());
                droppedDown = true;
                return true;
            }else{
                return false;
            }
        }
        return false;
    }
    
}
