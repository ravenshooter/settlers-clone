/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tilemap7.Buildings;

import tilemap7.Buildings.Tools.Stock;
import Tools.Sprite;
import Tools.SpriteStore;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import tilemap7.CarryObjects.Bread;
import tilemap7.CarryObjects.Flour;
import tilemap7.CarryObjects.Wood;
import tilemap7.Entity;
import tilemap7.GV;
import tilemap7.LittleMan;
import tilemap7.Mission.Mission_Fabricate;
import tilemap7.Mission.Mission_PickUp;
import tilemap7.Tile;
import tilemap7.TileMap;
import tilemap7.Crafting.Tree;
import tilemap7.Crafting.craftable;
import tilemap7.Crafting.crafthead;
import tilemap7.Mission.Mission_Craft;

/**
 *
 * @author Steve
 */
public class Lumberjack extends Factory implements crafthead{

    public Lumberjack(Tile tile, String name, int workers) throws UnBuildableException {
        super(tile, name, SpriteStore.get().getSprite("lumberjack.png"), workers);
        
        radius = 200;
        inStock = new Stock(new Point(tile.getXPos()+22,tile.getYPos()+40),6);
        outStock = new Stock(new Point(tile.getXPos(),tile.getYPos()),6);
    }

    public Lumberjack() {
        super();
        sprite = SpriteStore.get().getSprite("lumberjack.png");
        radius = 200;
    }

    @Override
    public void doLogic(int time){
        super.doLogic(time);


        if (super.getCurrentWorkersAmount() < super.getMaxWorkers()) {
            super.callWorker();
        } 
        
        if(super.getCurrentWorkersAmount() > 0) {
            //fabricate asked first, highest priority
             if(!outStock.isFull()){
                LittleMan worker = getWorker();
                Tree tree= findTree();
                if(worker!= null && tree != null && tree.isCraftable()){
                    worker.addMission(new Mission_Craft(tree, this));
                }
            } 
        }
    }
    
    
    private Tree findTree(){
        TileMap tileMap = GV.get().getTileMap();
        for (int i = 0; i < radius * 2; i += GV.get().getXTileSize()) {
            for (int j = 0; j < radius * 2; j += GV.get().getYTileSize()) {
                int x = xPos - radius + j;
                int y = yPos - radius + i;
                Tile tile = tileMap.getTileByPos(x, y);
                if (tile.getType().compareTo("black") != 0
                        && tile.getPos().distance(this.tile.getPos()) < radius) {
                    //if(tile.getEntity() instanceof Tree){
                        for(Entity e : tile.getEntities(Tree.class.getSimpleName())) {
                            if(((Tree)e).isCraftable()){
                                return (Tree)e;
                            }
                        }
                    //}
                }
            }
        }
        return null;
    }
    
    @Override
    public void draw(Graphics g, int x, int y){
        super.draw(g,x,y);
        if(isSelected()){
            drawShadedCircle(g, radius);
        }
        outStock.draw(g, x, y);
    }
    
    @Override
    public Building addBuilding(Tile tile) throws UnBuildableException {
        if(this.tile == null){
            Lumberjack lumberjack = new Lumberjack(tile, "Lumberjack", 2); 
            tile.addEntity(lumberjack);
            return lumberjack;
        }
        return null;
    }

    @Override
    public String getProduct() {
        return Wood.class.getSimpleName();
    }

    @Override
    public int getProductionStep() {
        return 0;
    }

    @Override
    public String[] getIngridients() {
        return new String[0];
    }

    @Override
    public craftable getCraft() {
        return findTree();
    }
    
    @Override
    public Stock getDropStock(){
        return outStock;
    }
    
}
