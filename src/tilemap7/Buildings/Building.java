/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tilemap7.Buildings;

import Tools.MouseObject;
import Tools.Sprite;
import Tools.SpriteStore;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import tilemap7.Entity;
import tilemap7.GV;
import tilemap7.LittleMan;
import tilemap7.Tile;
import tilemap7.TileMap;


/**
 * Write a description of class Building here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

public abstract class Building extends Entity
{

    private ArrayList<LittleMan> workers;
    private ArrayList<LittleMan> currentWorkers;
    private int maxWorkers;
    private boolean selected;
    
    public Building(Tile tile) throws UnBuildableException
    {
        super(tile);
        if(!tile.isBuildable()){
            throw new UnBuildableException();
        }
        workers = new ArrayList<LittleMan>();
        currentWorkers = new ArrayList<LittleMan>();
        tile.setUnpassable();
        sprite = SpriteStore.get().getSprite("cross.png");
    }
    
    /**
     * Creates a new building with no position and tile.
     * MUST LOAD THE SPRITE!
     */
    public Building(){
        super(null);
        sprite = SpriteStore.get().getSprite("cross.png");
        
    }
    
    
    /**
     * Adds the building to the given tile, if it is not added to another tile already.
     * @param tile 
     */
    public abstract Building addBuilding(Tile tile)throws UnBuildableException;
    
    

    
    public boolean addWorker(LittleMan littleMan) {
        if (workers != null && workers.size() < maxWorkers) {
            workers.add(littleMan);
            littleMan.setWorkPlace(this);
            System.out.println("worker added");
            return true;
        }
        return false;
    }
    
    public LittleMan removeWorker(LittleMan worker){
        for(int i = 0; i < workers.size();i++){
            if(workers.get(i).equals(worker)){
                return workers.remove(i);
            }
        }
        return null;
    }

    
    public LittleMan getWorker(){
        for(LittleMan l : workers){
            if(!l.hasMission()){
                return l;
            }
        }
        return null;
    }
    
    /**
     * Called by the littleMan when he arrives at his workplace
     * @param littleMan 
     */
    public synchronized void reportAtWork(LittleMan littleMan){
        System.out.println("worker " + littleMan.toString() +"reports");
        currentWorkers.add(littleMan);
    }
    
    /**
     * Called by the littleMan when he leaves his workplace.
     * @param littleMan 
     */
    public synchronized void reportLeaveWork(LittleMan littleMan){
        System.out.println("worker " + littleMan.toString() +"left");
        currentWorkers.remove(littleMan);
    }
    
    /**
     * Sends all workers back to there home
     */
    public void sendAllWorkerHome(){
        for(LittleMan littleMan: currentWorkers){
            littleMan.leaveWork();
        }
    }
    
    
    /**
     * Draws a shaded circle round the building
     * @param g
     * @param radius radius of the circle
     * @param shade sprite for the shade
     */
    public void drawShadedCircle(Graphics g, int radius, Sprite shade) {
        TileMap tileMap = GV.get().getTileMap();


        for (int i = 0; i < radius * 2; i += GV.get().getXTileSize()) {
            for (int j = 0; j < radius * 2; j += GV.get().getYTileSize()) {
                int x = xPos - radius + j;
                int y = yPos - radius + i;
                Tile tileToShade = tileMap.getTileByPos(x, y);
                if (tileToShade.getType().compareTo("black") != 0
                        && tileToShade.getPos().distance(this.tile.getPos()) < radius) {
                    g.drawImage(shade.getImage(), tileToShade.getXPos() - GV.get().getCamera().getXPos(), tileToShade.getYPos() - GV.get().getCamera().getYPos(), null);
                }
            }
        }
    }
    
    public void drawShadedCircle(Graphics g, int radius){
        drawShadedCircle(g,radius,SpriteStore.get().getSprite("shade.png"));
    }
    
    public class UnBuildableException extends Exception{
    }
    
    /**
     * Creates the workers array with specified size.
     * @param size 
     */
    protected void setWorkersSize(int size){
        maxWorkers = size;
    }
    public ArrayList<LittleMan> getWorkers(){
        return workers;
    }
    
    public Point getWorkPos(){
        return tile.getPos();
    }

    /**
     * Returns the amount of people currently at work.
     * @return 
     */
    public int getCurrentWorkersAmount() {
        return currentWorkers.size();
    }
    
    
    public ArrayList<LittleMan> getCurrentWorkers(){
        return currentWorkers;
    }
    
    
    /**
     * Returns all registered workers of this builing, not the once currently ar work!
     * @return 
     */
    public int getRegisteredWorkers(){
        return workers.size();
    }
    
    /**
     * Returns the maximum of workers in this building
     * @return 
     */
    public int getMaxWorkers(){
        return maxWorkers;
    }
    
    public MouseObject getMouseObject(){
        return new BuildingMouseObject(this);
    }

    @Override
    public void mouseClicked() {
        selected = true;
    }
    
    public void setUnseleceted(){
        selected = false;
    }
    
    public boolean isSelected(){
        return selected;
    }
    
    public void setSelected(){
        selected = true;
    }
    
    
    
    class BuildingMouseObject extends MouseObject {

        Building building;
        int radius;

        public BuildingMouseObject(Building building) {
            this.building = building;
            setShade(building.getSprite());
        }

        public BuildingMouseObject(Building building, int radius) {
            this.building = building;
            setShade(building.getSprite());
            this.radius = radius;
        }
        
        @Override
        protected void initialize() {
            
        }
        
        @Override
        public void drawMouse(Graphics2D g){
            super.drawMouse(g);
            if(radius != 0){
                drawShadedCircle(g, radius);
            }
        }

        @Override
        public void doLeftClick(MouseEvent e) {
            try {
                building.addBuilding(GV.get().getTileMap().getTileByPosWithCamera(e.getX(), e.getY()));
            } catch (Building.UnBuildableException g) {
                System.err.println("Can't build here");
            }
            GV.get().getMouse().setMouseObject(null);
        }
    }
}
