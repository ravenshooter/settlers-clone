/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tilemap7.Buildings;

import tilemap7.Buildings.Tools.Stock;
import Tools.MouseObject;
import Tools.SpriteStore;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import tilemap7.CarryObjects.Wood;
import tilemap7.GV;
import tilemap7.LittleMan;
import tilemap7.Mission.Mission_PickUp;
import tilemap7.Tile;
import tilemap7.TileMap;

/**
 *
 * @author Steve
 */
public class Storage extends Factory{
    
    private ArrayList<Factory> factories;
    
    public Storage(Tile tile) throws UnBuildableException {
        super(tile,"Storage",SpriteStore.get().getSprite("storage.png"),6);
        panel.setLayoutWorkersPanel(2, 3);
        panel.setSizeWorkersPanel(110,35);
        sprite = SpriteStore.get().getSprite("storage.png");
        inStock = new Stock(new Point(tile.getXPos()+8,tile.getYPos()+10),16,4);
        outStock = inStock;
        factories = new ArrayList<Factory>();
        radius = 400;
    }

    public Storage() {
        sprite = SpriteStore.get().getSprite("storage.png");
        radius = 400;
    }

    

    
    @Override
    public Building addBuilding(Tile tile) throws UnBuildableException {
        if (this.tile == null) {
            Storage storage = new Storage(tile);
            tile.addEntity(storage);
            return storage;
        }
        return null;
    }
    
    
    @Override
    public void draw(Graphics g, int x, int y)
    {
        g.drawImage(sprite.getImage(), x, y, null);
        outStock.draw(g, x+8, y+10);
        if(isSelected()){
            drawShadedCircle(g, radius);
        }
    }
    
    @Override
    public void doLogic(int time){
        super.doLogic(time);
        
        if(getCurrentWorkersAmount()<getMaxWorkers()){
            callWorker();
        }
        
        if(time%10 == 0){
            getBuildings();
        }
        
        
        for(Factory f: factories){
            if (f != null && f.getInStock().getExpectedStock() == 0) {
                for (String s : f.getIngridients()) {
                    if (inStock.hasElement(s)) {
                        LittleMan l = getWorker();
                        if (l != null) {
                            l.addMission(new Mission_PickUp(inStock,f.getInStock(), s, l));
                        }
                    }
                }

            }
            if (f != null && f.getOutStock().isFull()) {
                LittleMan l = getWorker();
                if (l != null) {
                    l.addMission(new Mission_PickUp(f.getOutStock(), inStock, f.getProduct(), l));
                }
            }
        }

    }
    
    
    private void getBuildings(){
        TileMap tileMap = GV.get().getTileMap();
        for (int i = 0; i < radius * 2; i += GV.get().getXTileSize()) {
            for (int j = 0; j < radius * 2; j += GV.get().getYTileSize()) {
                int x = xPos - radius + j;
                int y = yPos - radius + i;
                Tile tile = tileMap.getTileByPos(x, y);
                if (tile.getType().compareTo("black") != 0
                        && tile.getPos().distance(this.tile.getPos()) < radius) {
                    if(tile.getBuilding() instanceof Factory){
                        Factory factory = (Factory) tile.getBuilding();
                        int k = 0;
                        while(k < factories.size() && factories.get(k).getProductionStep()>=factory.getProductionStep()){
                            k++;
                        }
                        factories.add(k,factory);
                    }
                }
            }
        }
    }
    
    
    @Override
    public Point getWorkPos() {
        return new Point(this.xPos+25,yPos+25);
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

    
    /**
     * This building requiers 2 wood. {{"Wood","2"}}
     * @return 
     */
    @Override
    public Stock getRequirements() {
        Stock requierements = new Stock(2);
        requierements.add(new Wood());
        requierements.add(new Wood());
        return requierements;
    }
}
