/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tilemap7.Buildings;

import Tools.Sprite;
import Tools.SpriteStore;
import java.awt.Graphics;
import java.awt.Point;
import tilemap7.LittleMan;
import tilemap7.Tile;

/**
 *
 * @author Steve
 */
public class WatchTower extends Factory{

    Sprite soldat;
    Sprite shadow;
    
    
    public WatchTower(Tile tile) throws UnBuildableException{
        super(tile,"Tower",SpriteStore.get().getSprite("watchtower.png"),1);
        soldat = SpriteStore.get().getSprite("soldat.png");
        shadow = SpriteStore.get().getSprite("littlemanshadow.png");
        inStock = outStock = new Stock(tile.getCenter(),4);
    }
    
    public WatchTower(){
        sprite = SpriteStore.get().getSprite("watchtower.png");
    }
    
    
    @Override
    public void doLogic(int step){
        if(getCurrentWorkersAmount() < getRegisteredWorkers()){
            callWorker();
        }
    }

    @Override
    public void reportAtWork(LittleMan littleMan) {
        super.reportAtWork(littleMan);
        littleMan.setVisible(false);
    }
    

    
    
    
    @Override
    public void draw(Graphics g, int x, int y){
        g.drawImage(sprite.getImage(), x, y, null);
        if(getCurrentWorkersAmount() > 0){
            g.drawImage(shadow.getImage(), x+12, y+13, null);
            g.drawImage(soldat.getImage(),x+12,y+13,null);
        }
    }
    
    @Override
    public Building addBuilding(Tile tile)throws UnBuildableException {
        if(this.tile == null){
            WatchTower watchTower = new WatchTower(tile);
            tile.addEntity(watchTower);
            return watchTower;
        }
        return null;
    }
    
    
    @Override
    public Point getWorkPos()
    {
        return new Point(tile.getCenter());
    }
    
    
    @Override
    public String getProduct(){
        return "";
    }
    
    @Override
    public int getProductionStep(){
        return -1;
    }
    
    /**
     * Returns an array of String containing all ingredients for this factory
     * @return 
     */
    @Override
    public String[] getIngridients() {
        String[] ingridients = {};
        return ingridients;
    }
}
