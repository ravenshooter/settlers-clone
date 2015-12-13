/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tilemap7.Buildings;

import tilemap7.Buildings.Tools.Stock;
import Tools.MouseObject;
import Tools.Sprite;
import Tools.SpriteStore;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import tilemap7.CarryObjects.Flour;
import tilemap7.CarryObjects.Grain;
import tilemap7.GV;
import tilemap7.LittleMan;
import tilemap7.Mission.Mission;
import tilemap7.Mission.Mission_Fabricate;
import tilemap7.Mission.Mission_GoTo;
import tilemap7.Mission.Mission_PickUp;
import tilemap7.Tile;
import tilemap7.TileMap;
import tilemap7.missionMaster;
import tilemap7.workable;
//import tilemap7.CarryObjects.Grain;


/**
 *
 * @author Steve
 */
public class Mill extends Factory implements missionMaster, workable{

    Sprite[] sprites;
    int counter;
    
    private ArrayList<Farm> farms;
    private final int maxFarms = 4;
    private boolean hasAllFarms;
    
    
    int activeCounter;
    
    public Mill(Tile tile) throws UnBuildableException{
        super(tile,"Mill",SpriteStore.get().getSprite("mühle1.png"),2);
        panel.setBoundsWorkersPanel(100, 20, 100, 20);
        sprite = SpriteStore.get().getSprite("mühle1.png");
        sprites = new Sprite[5];
        for(int i = 0; i < 5; i++ ){
            sprites[i] = SpriteStore.get().getSprite("mühle"+i+".png");
        }
        farms = new ArrayList(maxFarms);
        radius = 200;
        inStock = new Stock(new Point(tile.getXPos()+22, tile.getYPos()+40),4);
        outStock = new Stock(new Point(tile.getXPos()+28, tile.getYPos()+40),4);
    }
    
    
    public Mill(){  
        sprite = SpriteStore.get().getSprite("mühle0.png");
        radius = 200;
    }
    
    
    @Override
    public Building addBuilding(Tile tile)throws UnBuildableException{
        if(this.tile == null){
            Mill mill = new Mill(); 
            tile.addEntity(new Mill(tile));
            return mill;
        }
        return null;
    }


    
    
    
    @Override
    public void draw(Graphics g, int x, int y){
        g.drawImage(sprites[Math.round(counter/2)].getImage(),x,y,null);
        if(isActive()) {
            counter++;
        }
        if(counter == 10){
            counter = 0;
        }
        if(isSelected()){
            drawShadedCircle(g,radius);
        }
        inStock.draw(g, x+22, y+40);
        outStock.draw(g, x+28, y+40);
    }
    
    
    

    @Override
    public void doLogic(int i) {
        super.doLogic(i);
        
        
        if(!hasAllFarms && i%10 == 0){
            lookForFarms();
        }

        if (super.getCurrentWorkersAmount() < super.getMaxWorkers()) {
            super.callWorker();
        }
        
        for(LittleMan worker : getCurrentWorkers()) {
            if(!worker.hasMission()){
                //fabricate asked first, highest priority
                if (!outStock.isFull() && inStock.getExpectedStock() > 0) {
                    worker.addMission(new Mission_Fabricate(getWorkPos(), 30, this, Grain.class.getSimpleName(), new Flour()));
                } else {
                    //check farms for grain and collect it 
                    for (Farm f : farms) {
                        //System.out.println("farm outstock: "+ f.getOutStock().getExpectedStock() +"  mill instock: "+ inStock.getExpectedStock() +"   limit: "+ inStock.getLimit());
                        if (f.getOutStock().getExpectedStock() > 0 && inStock.getExpectedStock() < inStock.getLimit()) {
                            worker.addMission(new Mission_PickUp(f.getOutStock(), inStock, Grain.class.getSimpleName(), getWorkers().get(0)));
                            break;
                        }
                    }
                }   
            }
        }  
    }


    
    @Override
    public Mission getWork() {

        class Millwork extends Mission {

            Mill mill;
            int workrounds = 0;
            Millwork(Mill mill) {
                super(mill);
                this.mill = mill;
                missionList = new Mission[1];
                missionList[0] = new Mission_GoTo(mill.getWorkPos());

            }

            @Override
            public boolean doMission(LittleMan actor) {

                if (actMission == missionList.length) {
                    actor.setCarryObject(null);
                    if (workrounds == 0) {
                        if (!mill.isActive()) {
                            mill.setActive(true);
                        } else {
                            return false;
                        }
                    }
                    if (workrounds < 60) {
                        workrounds++;
                        actMission--;
                        return false;
                    } else {
                        mill.setActive(false);
                        actor.setCarryObject(new Flour());
                        home.missionCompleted(this);
                        return true;
                    }

                } else {
                    if (missionList[actMission].doMission(actor)) {
                        actMission++;
                        return false;
                    }
                    return false;
                }
            }
        }
        return new Millwork(this);
    }

    @Override
    public void missionCompleted(Mission mission1)
    {
    }
    
    
    
    private void lookForFarms(){
        TileMap tileMap = GV.get().getTileMap();
        for (int i = 0; i < radius * 2; i += GV.get().getXTileSize()) {
            for (int j = 0; j < radius * 2; j += GV.get().getYTileSize()) {
                int x = xPos - radius + j;
                int y = yPos - radius + i;
                Tile tileToShade = tileMap.getTileByPos(x, y);
                if (tileToShade.getType().compareTo("black") != 0
                        && tileToShade.getPos().distance(this.tile.getPos()) < radius) {
                    if(tileToShade.getBuilding() instanceof Farm){
                        addFarm((Farm)tileToShade.getBuilding());
                    }
                }
            }
        }
    }
    
    private void addFarm(Farm farm){
        for(Farm f : farms){
            if(farm.equals(f)){
                return;
            }
        }
        if(farms.size() < maxFarms){
            farms.add(farm);
            System.out.println("found farm");            
        }
        if(farms.size() == maxFarms){
            hasAllFarms = true;
        }
    }

    @Override
    public Point getWorkPos() {
        return new Point(this.xPos+40,yPos+40);
    }
    
    
    @Override
    public MouseObject getMouseObject(){
        return new BuildingMouseObject(this,radius);
    }
    
    
    

    @Override
    public String getProduct(){
        return Flour.class.getSimpleName();
    }
    
    @Override
    public int getProductionStep(){
        return 1;
    }

    
    /**
     * Returns an array of String containing all ingredients for this factory
     * @return 
     */
    @Override
    public String[] getIngridients() {
        String[] ingridients = {Grain.class.getSimpleName()};
        return ingridients;
    }

    
}
