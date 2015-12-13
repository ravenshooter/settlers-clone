/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tilemap7.Buildings;

import Tools.MouseObject;
import Tools.Sprite;
import Tools.SpriteStore;
import java.awt.Graphics;
import java.util.ArrayList;
import tilemap7.Buildings.Tools.FactoryPanel;
import tilemap7.GV;
import tilemap7.LittleMan;
import tilemap7.Mission.Mission;
import tilemap7.Mission.Mission_GoToWork;
import tilemap7.Tile;

/**
 *
 * @author Steve
 */
public abstract class Factory extends Building{

    protected FactoryPanel panel;
    protected int radius;
    protected Stock inStock;
    protected Stock outStock;
    private boolean active;
    
    /**
     * Creates a new factory building with factory panel, and methods such as: add worker, remove worker...
     * @param tile tile on which the building is placed
     * @param name type of the factory, e.g bakery
     * @param sprite image displayed under the name
     * @param workers numer of workers that can max work in the factory
     * @throws tilemap7.Buildings.Building.UnBuildableException if the tile is full or unbuildable
     */
    Factory(Tile tile, String name, Sprite sprite, int workers) throws UnBuildableException{
        super(tile);
        this.sprite = sprite;
        this.setWorkersSize(workers);
        panel = new FactoryPanel(name, sprite, this);
        isClickable = true;
        sprite = SpriteStore.get().getSprite("cross.png");
        radius = 0;
    }
    
    Factory(){
        sprite = SpriteStore.get().getSprite("cross.png");
        radius = 0;
    }
    

    @Override
    public abstract Building addBuilding(Tile tile)throws UnBuildableException;
    
    
    @Override
    public void draw(Graphics g, int x, int y)
    {
        g.drawImage(sprite.getImage(), x, y, null);
    }
    
    @Override
    public LittleMan removeWorker(LittleMan littleMan){
        panel.removeWorkerFromButton(littleMan);
        return super.removeWorker(littleMan);
    }
    
    
    @Override
    public boolean addWorker(LittleMan littleMan){
        if(super.addWorker(littleMan)){
            panel.addWorkerToButton(littleMan);
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * Calls all workers to work.
     */
    protected void callWorker(){
        ArrayList<LittleMan> workers = getWorkers();
        for(int i = 0; i < workers.size();i++){
            if(!super.getCurrentWorkers().contains(workers.get(i))&&!workers.get(i).calledToWork()){
                workers.get(i).callToWork();
            }
        }
    }
    
    
    /**
     * Called by the littleMan when he arrives at his workplace
     * @param littleMan 
     */
    @Override
    public void reportAtWork(LittleMan littleMan){
        super.reportAtWork(littleMan);
        panel.workerArrived(littleMan);
    }
    
    
    @Override
    public void reportLeaveWork(LittleMan littleMan){
        super.reportLeaveWork(littleMan);
        panel.workerLeft(littleMan);
    }
    

    
    /**
     * Sends all workers back home
     */
    @Override
    public void sendAllWorkerHome(){
        super.sendAllWorkerHome();
        for(int i = 0; i < getRegisteredWorkers();i++){
            panel.workerLeft(getWorkers().get(i));
        }
    }

    @Override
    public void setUnseleceted() {
        super.setUnseleceted();
    }

    
    /**
     * Returns the Stock for incoming goods
     * @return 
     */
    public Stock getInStock() {
        return inStock;
    }

    
    /**
     * Returns the Stock for outgoing goods
     * @return 
     */
    public Stock getOutStock() {
        return outStock;
    }

    
    /**
     * Sets this building to active mode.
     * @param active 
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Returns the active mode of this building.
     * @return 
     */
    public boolean isActive() {
        return active;
    }
    
    /**
     * Returns the name of the produced good of this factory
     * @return 
     */
    public abstract String getProduct();
    
    /**
     * Returns the step in production chain, 0 is crafting, 1,2... is manufacturing
     * @return 
     */
    public abstract int getProductionStep();
        
    /**
     * Returns an array of String with ingridients needet by this factory
     * @return 
     */
    public abstract String[] getIngridients();

    
    /**
     * Returns a mouseObject for a factory with shaded radius
     * @return 
     */
    @Override
    public MouseObject getMouseObject(){
        return new BuildingMouseObject(this,radius);
    }
    
    
    
    @Override
    public void mouseClicked() {
        if (isClickable) {
            GV.get().getUI().mouseClicked(this);
            GV.get().getGameWindow().setSouthPanel(panel);
            setSelected();
        }

    }
}
