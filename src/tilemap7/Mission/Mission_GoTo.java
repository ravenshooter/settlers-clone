/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tilemap7.Mission;

import java.awt.Point;
import tilemap7.LittleMan;

public class Mission_GoTo extends Mission{
    Point goTo;
    boolean dirSet =false;
    public Mission_GoTo(Point goTo){
        super(null);
        this.goTo = goTo;
    }
    @Override
    public boolean doMission(LittleMan actor){
        
        if(actor.getLocation().distance(goTo) < actor.getSpeed() && actor.getLocation().distance(goTo) > -actor.getSpeed()){
            actor.setLocation(new java.awt.geom.Point2D.Double(goTo.getX(),goTo.getY()));
            return true;
        }else{
            double direction[] = new double[2];
            direction[0]=(goTo.getX()-actor.getLocation().getX())/actor.getLocation().distance(goTo);
            direction[1]=(goTo.getY()-actor.getLocation().getY())/actor.getLocation().distance(goTo);
            double newX = direction[0]*actor.getSpeed()+actor.getLocation().getX();
            double newY = direction[1]*actor.getSpeed()+actor.getLocation().getY();
            
            actor.setDirection(direction);
            actor.getLocation().setLocation(newX,newY);
            //if(!dirSet){
            //    double anKat = goTo.getX()-actor.getLocation().getX();
            //    double gegKat = goTo.getY()-actor.getLocation().getY();
            //    actor.setDirection(Math.atan(gegKat/anKat)+Math.PI/2);
            //Math.atan(direction[1]/direction[0]);
            //    dirSet =true;
            //}
            return false;
        }
    }
    public void setDirSet(boolean b){
        dirSet = b;
    }
    @Override
    public String getType(){
        return "GoToMission";
    }
}