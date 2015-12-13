/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tilemap7.Buildings;

import Tools.MouseObject;
import Tools.SpriteStore;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import tilemap7.Buildings.Tools.BuildingPanel;
import tilemap7.Buildings.Tools.Stock;
import tilemap7.Camera;
import tilemap7.Crafting.craftable;
import tilemap7.Crafting.crafthead;
import tilemap7.GV;
import tilemap7.LittleMan;
import tilemap7.Mission.Mission_Craft;
import tilemap7.Mission.Mission_PickUp;
import tilemap7.Tile;
import tilemap7.TileMap;

/**
 *
 * @author Steve
 */
public class ConstructionCenter extends Building implements crafthead{

    int radius;
    Stock stock;
    
    public ConstructionCenter(Tile tile) throws UnBuildableException {
        super(tile);
        sprite = SpriteStore.get().getSprite("constructioncenter.png");
        super.setWorkersSize(8);
        panel = new BuildingPanel(this);
        
        radius = 400;
        stock = new Stock(tile.getCenter(),10);
    }

    public ConstructionCenter() {
        sprite = SpriteStore.get().getSprite("constructioncenter.png");
    }
    
    
    
    
    @Override
    public void doLogic(int time){
        super.doLogic(time);
        
        if (super.getCurrentWorkersAmount() < super.getMaxWorkers()) {
            callWorker();
        } 
        
        ConstructionSite  site = findConstructionSites();
        LittleMan worker = getWorker();

        if(site != null && worker != null){
            if(site.isCraftable()){
                worker.addMission(new Mission_Craft(site,this));
            }else{
                Stock requirements = site.getRequierments();
                for(int i = 0; i < requirements.getSize(); i++){
                    Stock target = findCarryObject(requirements.get(i).getType());
                    if(target != null){
                        worker.addMission(new Mission_PickUp(target, site.getOutStock(), requirements.get(i).getType(), worker));
                    }
                }
            }
        }
    }
    
    
    
    
    
    public ConstructionSite findConstructionSites(){
        TileMap tileMap = GV.get().getTileMap();
        for (int i = 0; i < radius * 2; i += GV.get().getXTileSize()) {
            for (int j = 0; j < radius * 2; j += GV.get().getYTileSize()) {
                int x = xPos - radius + j;
                int y = yPos - radius + i;
                Tile tile = tileMap.getTileByPos(x, y);
                if (tile.getType().compareTo("black") != 0
                        && tile.getPos().distance(this.tile.getPos()) < radius) {
                    if(tile.hasEntity(ConstructionSite.class.getSimpleName())!=null){
                        return (ConstructionSite)tile.getEntity(ConstructionSite.class.getSimpleName());
                    }
                }
            }
        }
        return null;
    }
    
    
    public Stock findCarryObject(String type){
        TileMap tileMap = GV.get().getTileMap();
        for (int i = 0; i < radius * 2; i += GV.get().getXTileSize()) {
            for (int j = 0; j < radius * 2; j += GV.get().getYTileSize()) {
                int x = xPos - radius + j;
                int y = yPos - radius + i;
                Tile tile = tileMap.getTileByPos(x, y);
                if (tile.getType().compareTo("black") != 0
                        && tile.getPos().distance(this.tile.getPos()) < radius) {
                    if(tile.getBuilding() != null && tile.getBuilding() instanceof Factory){
                        Factory factory = (Factory)tile.getBuilding();
                        if(factory.getOutStock().hasElement(type)){
                            return factory.getOutStock();
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Calls all workers to go to work
     */
    protected void callWorker() {
        ArrayList<LittleMan> workers = getWorkers();
        for (int i = 0; i < workers.size(); i++) {
            if (!super.getCurrentWorkers().contains(workers.get(i)) && !workers.get(i).calledToWork()) {
                workers.get(i).callToWork();
            }
        }
    }
    
    /**
     * Checks if the draw method is called by the original tile (top left) of this building, or maybe by another tile.
     * Draws the building at the specified position.
     * @param g
     * @param x
     * @param y 
     */
    @Override
    public void draw(Graphics g, int x, int y){
            super.draw(g, x, y);
            if(isSelected()){
                super.drawShadedCircle(g, radius);
            }
    }
    
    
    
    
    @Override
    public Building addBuilding(Tile tile) throws UnBuildableException {
        if(this.tile == null){
           
            try{
                ConstructionCenter center = new ConstructionCenter(tile);
                tile.addEntity(center);
                if(!tile.getNeighbour(2).addEntity(new ConstructionCenterExtension(center))){
                    tile.remove(center);
                }
                return center;
            }catch(UnBuildableException e){
                System.err.println("Cannot build here");
            }
        }
        return null;
    }
    
    
    
    @Override
    public MouseObject getMouseObject(){
        return new ConstructionCenterMouseObject(this);
    }

    
    @Override
    public craftable getCraft() {
        return findConstructionSites();
    }

    @Override
    public Stock getDropStock() {
        return stock;
    }
    
    
    
    
    
    
    
    
    
    class ConstructionCenterMouseObject extends BuildingMouseObject{

        public ConstructionCenterMouseObject(Building building) {
            super(building);
        }

        public ConstructionCenterMouseObject(Building building, int radius) {
            super(building, radius);
        }
        
        @Override
        public void drawMouse(Graphics2D g){
            Camera camera = GV.get().getCamera();       
            int y = (camera.getYPos() + (int) getMousePosition().getY()) / GV.get().getYTileSize();
            int x = (camera.getXPos() + (int) getMousePosition().getX()) / GV.get().getXTileSize();
            y = y * GV.get().getYTileSize();
            x = x * GV.get().getXTileSize(); 
            g.drawImage(shadedObject.getImage(), x - camera.getXPos(), y - camera.getYPos(), null);
            g.drawImage(shade.getImage(), x - camera.getXPos(), y - camera.getYPos(), null); 
            g.drawImage(shade.getImage(), x - camera.getXPos()+GV.get().getXTileSize(), y - camera.getYPos(), null);  
            drawShadedCircle(g, 400);
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
    
    
    
    class ConstructionCenterExtension extends ConstructionCenter{

        ConstructionCenter main;
        public ConstructionCenterExtension(ConstructionCenter main){
            this.main = main;
        }
        
        @Override
        public void mouseClicked(MouseEvent e) {
            main.mouseClicked(e);
        }
        
        @Override
        public void draw(Graphics g, int x, int y){
            
        }

        @Override
        public Stock getDropStock() {
            return main.getDropStock();
        }
        
        @Override
        public void doLogic(int t){}
        
        @Override
        public boolean addWorker(LittleMan worker){
            return main.addWorker(worker);
        }
        
        
        
        @Override
        public Building addBuilding(Tile tile) throws UnBuildableException {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
    }
}
