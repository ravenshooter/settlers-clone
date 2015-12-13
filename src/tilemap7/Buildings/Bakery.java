/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tilemap7.Buildings;

import tilemap7.Buildings.Tools.Stock;
import Tools.MouseObject;
import Tools.SpriteStore;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import tilemap7.CarryObjects.Bread;
import tilemap7.CarryObjects.Flour;
import tilemap7.CarryObjects.Grain;
import tilemap7.GV;
import tilemap7.LittleMan;
import tilemap7.Mission.Mission_Fabricate;
import tilemap7.Mission.Mission_GoTo;
import tilemap7.Mission.Mission_PickUp;
import tilemap7.Tile;
import tilemap7.TileMap;

/**
 *
 * @author Steve
 */
public class Bakery extends Factory{
    int counter, state;
    
    
    private int flourAmount, breadAmount;
    
    private boolean hasAllMills;
    private ArrayList<Mill> mills;
    private int maxMills;
    
    BakeryPanel bakeryPanel;
    
    /**
     * Creates a new Bakery with 4 workspaces.
     * @param tile
     * @throws tilemap7.Buildings.Building.UnBuildableException 
     */
    public Bakery(Tile tile)throws UnBuildableException{
        super(tile,"Bakery",SpriteStore.get().getSprite("bakery.png"),4);
        sprite = SpriteStore.get().getSprite("bakery.png");
        isClickable = true;
        bakeryPanel = new BakeryPanel(this);
        radius = 200;
        mills = new ArrayList<Mill>();
        maxMills = 4;
        inStock = new Stock(new Point(tile.getXPos()+22,tile.getYPos()+40),6);
        outStock = new Stock(new Point(tile.getXPos()+28,tile.getYPos()+40),6);
    }
    
    public Bakery(){
        sprite = SpriteStore.get().getSprite("bakery.png");    
        radius = 200;
    }
    
    @Override
    public Building addBuilding(Tile tile){
        if(this.tile == null){
            try{
                Building bakery = new Bakery(tile);
                tile.addEntity(bakery);
                return bakery;
            }catch(UnBuildableException e){
                System.err.println("Cannot build here");
            }
        }
        return null;
    }
    
    
    /*
    public void doJob(){
        if(getWorkers() != null){
            if(getWorkers().size() > 0){
                getWorkers().get(0).addMission(new Mission_GoTo(new Point(this.xPos,yPos)));
            }
        }
    }
    * */
    
    
    @Override
    public void draw(Graphics g, int x, int y){
        if(isSelected()){
            drawShadedCircle(g, radius);
        }
        g.drawImage(sprite.getImage(),x,y,null);
        inStock.draw(g, x+22, y+40);
        outStock.draw(g, x+28, y+40);
    }
    
    
    @Override
    public void mouseClicked(MouseEvent e)
    {
        super.mouseClicked(e);
        if(isClickable){
            bakeryPanel.updateAll();
        }
        
    }
    
    
    /**
     * Called by logic loop
     * @param i loopNr
     */
    @Override
    public void doLogic(int i){
        super.doLogic(i);
        
        if(!hasAllMills && i%10 == 0){
            lookForMills();
        }

        if (super.getCurrentWorkersAmount() < super.getMaxWorkers()) {
            super.callWorker();
        } 
        
        if(super.getCurrentWorkersAmount() > 0) {
            //fabricate asked first, highest priority
             if(!outStock.isFull() && inStock.getExpectedStock()>0){
                if(getWorker()!= null){
                    getWorker().addMission(new Mission_Fabricate(getWorkPos(), 30, this, Flour.class.getSimpleName(), new Bread()));
                }
            }
            //check farms for grain and collect it 
            for (Mill m : mills) {
                while (getWorker() != null && m.getOutStock().getExpectedStock() > 0) {
                    getWorker().addMission(new Mission_PickUp(m.getOutStock(), inStock, Flour.class.getSimpleName(), getWorkers().get(0)));
                }
            }



            
        }
        
        
    }
    
    
    private void lookForMills(){
        TileMap tileMap = GV.get().getTileMap();
        for (int i = 0; i < radius * 2; i += GV.get().getXTileSize()) {
            for (int j = 0; j < radius * 2; j += GV.get().getYTileSize()) {
                int x = xPos - radius + j;
                int y = yPos - radius + i;
                Tile tile = tileMap.getTileByPos(x, y);
                if (tile.getType().compareTo("black") != 0
                        && tile.getPos().distance(this.tile.getPos()) < radius) {
                    if(tile.getBuilding() instanceof Mill){
                        addMill((Mill)tile.getBuilding());
                    }
                }
            }
        }
    }
    
    private void addMill(Mill mill){
        for(Mill m : mills){
            if(mill.equals(m)){
                return;
            }
        }
        if(mills.size() < maxMills){
            mills.add(mill);
            System.out.println("found mill");            
        }
        if(mills.size() == maxMills){
            hasAllMills = true;
        }
    }


    
    
    
    /**
     * Called by littleMan when he arrives at his workplace
     * @param littleMan 
     */
    @Override
    public void reportAtWork(LittleMan littleMan){
        super.reportAtWork(littleMan);
    }
    
    
    
    public void incFlour(){
        flourAmount++;
        bakeryPanel.updateFlour();
    }
    private void decFlour(){
        flourAmount--;
        bakeryPanel.updateFlour();
    }
    private void incBread(){
        breadAmount++;
        bakeryPanel.updateBread();
    }
    public void decBread(){
        breadAmount--;
        bakeryPanel.updateBread();
    }

    
    /**
     * Returns the curren amount of flour in this bakery.
     * @return 
     */
    public int getFlourAmount() {
        return flourAmount;
    }

    /**
     * Returns the curren amount of Bread in this bakery
     * @return 
     */
    public int getBreadAmount() {
        return breadAmount;
    }
    
    
    @Override
    public String getProduct(){
        return Bread.class.getSimpleName();
    }
    
    @Override
    public int getProductionStep(){
        return 2;
    }
    
    @Override
    public String[] getIngridients() {
        String[] ingridients = {Flour.class.getSimpleName()};
        return ingridients;
    }

    
    @Override
    public LittleMan removeWorker(LittleMan littleMan){
        LittleMan temp = super.removeWorker(littleMan);
        bakeryPanel.updateAll();
        return temp;
    }
    
    /**
     * Returns the mouseObject for this building, the element that will be added to the cursor
     * @return 
     */
    @Override
    public MouseObject getMouseObject(){
        return new BuildingMouseObject(this,radius);
    }

    
    @Override
    public Point getWorkPos(){
        return new Point(tile.getXPos()+15,tile.getYPos()+40);
    }

    
    
    
    class BakeryPanel{
        private Bakery bakery;
        
        private Label flour;
        private Label bread;
        private Label workers;
        
        
        public BakeryPanel(Bakery bakery){
            this.bakery = bakery;
            
            
            
            
            
            addInfoPanel(400,20);
            
            

            
        }
        
        private void addInfoPanel(int x, int y){
            JPanel infoPanel = new JPanel(new GridLayout(3,2));
            infoPanel.setBounds(x,y,160,50);
            infoPanel.setBackground(Color.DARK_GRAY);
            
            JLabel workersLabel = new JLabel("Workers:");
            workersLabel.setForeground(Color.white);
            infoPanel.add(workersLabel);
            workers = new Label("0");
            workers.setForeground(Color.white);
            infoPanel.add(workers);
            
            JLabel flourLabel = new JLabel("Flour:");
            flourLabel.setForeground(Color.white);
            infoPanel.add(flourLabel);
            flour = new Label("0");
            flour.setForeground(Color.white);
            infoPanel.add(flour);
            
            JLabel breadLabel = new JLabel("Bread:");
            breadLabel.setForeground(Color.white);
            infoPanel.add(breadLabel);
            bread = new Label("0");
            bread.setForeground(Color.white);
            infoPanel.add(bread);
            
            panel.add(infoPanel);
        }
        
        
        public void updateFlour(){
            flour.setText(String.valueOf(bakery.getFlourAmount()));
        }
        
        public void updateBread(){
            bread.setText(String.valueOf(bakery.getBreadAmount()));
        }
        
        public void updateWorkers(){
            workers.setText(String.valueOf(bakery.getRegisteredWorkers()));
        }
        
        public void updateAll(){
            updateBread();
            updateFlour();
            updateWorkers();
        }


        
        
        

        
        
        
        
    }
    
    
    
    

    
}
