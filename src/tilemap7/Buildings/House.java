/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tilemap7.Buildings;
/**
 * Write a description of class Farm here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */


import GUI.EntityPanel;
import GUI.SouthPanel;
import Tools.MouseObject;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import tilemap7.Entity;
import tilemap7.GV;
import tilemap7.LittleMan;
import tilemap7.Mission.Mission;
import tilemap7.Mission.Mission_DdiscoverFarm;
import tilemap7.Mission.Mission_DdiscoverMill;
import tilemap7.Mission.Mission_DeliverFood;
import Tools.SpriteStore;
import java.awt.event.MouseEvent;
import tilemap7.Buildings.Tools.Stock;
import tilemap7.Crafting.craftable;
import tilemap7.Crafting.crafthead;
import tilemap7.Tile;
import tilemap7.collideable;
import tilemap7.missionMaster;

public class House extends Building
    implements missionMaster, collideable, crafthead
{
    


    
    public int expectedStock;
    int hungercounter;
    int missionTimer;
    int manInHouse;
    int discoverTileX;
    int discoverTileY;
    int distance;
    String missionBlog[];
    LittleMan littleMen[];
    Farm farm;
    Mill mill;
    ArrayList missionlist;
    Rectangle collisionBox[];
    Point collisionBoxCorners[];
    private Stock stock;

    
    public House(Tile tile) throws UnBuildableException
    {
        super(tile);
        discoverTileX = 0;
        discoverTileY = 0;
        distance = 0;
        sprite = SpriteStore.get().getSprite("house.png");
        this.tile = tile;
        tile.setUnpassable();
        collisionBox = new Rectangle[1];
        collisionBox[0] = new Rectangle(tile.getXPos() + 3, tile.getYPos() + 3, 28, 28);
        collisionBoxCorners = new Point[4];
        collisionBoxCorners[0] = new Point(collisionBox[0].getLocation());
        collisionBoxCorners[1] = new Point((int)(collisionBox[0].getX() + collisionBox[0].getWidth()), (int)collisionBox[0].getY());
        collisionBoxCorners[2] = new Point((int)(collisionBox[0].getX() + collisionBox[0].getWidth()), (int)(collisionBox[0].getY() + collisionBox[0].getHeight()));
        collisionBoxCorners[3] = new Point((int)collisionBox[0].getX(), (int)(collisionBox[0].getY() + collisionBox[0].getHeight()));
        isClickable = true;
        manInHouse = 4;
        missionlist = new ArrayList();
        discoverTileX = tile.getXNr();
        discoverTileY = tile.getYNr();
        littleMen = new LittleMan[manInHouse];
        for(int i = 0; i < littleMen.length; i++){
            littleMen[i] = new LittleMan(i, new Point(tile.getPos()), this);
        }
        
        stock = new Stock(tile.getCenter(),4);
        panel = new HousePanel(this);

    }
    

    
    /**
     * Creates a house with no tile, no position etc. 
     * add House can be called to place the house at a specific tile.
     */
    public House(){
        sprite = SpriteStore.get().getSprite("house.png");        
    }
    
    @Override
    public Building addBuilding(Tile tile) throws UnBuildableException{
        if(this.tile == null){
                House house = new House(tile);
                tile.addEntity(house);
                return house;
        }
        return null;
    }

    @Override
    public void draw(Graphics g, int x, int y)
    {
        g.drawImage(sprite.getImage(), x, y, null);
        stock.draw(g, x+25, y+25);
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        if(isClickable){
            GV.get().getUI().mouseClicked(this);
            GV.get().getGameWindow().setSouthPanel(panel);
        }
    }

    @Override
    public void doLogic(int time)
    {
 
    }

    public void addMission(Mission mission)
    {
        missionlist.add(mission);
    }

    @Override
    public void missionCompleted(Mission mission)
    {
        if(mission.getType().compareTo("DdiscoverFarm") == 0) {
            handleDiscoverFarm(mission);
        }
        else
        if(mission.getType().compareTo("PickUp") == 0) {
            handleDeliverFoodMission((Mission_DeliverFood)mission);
        }
        else
        if(mission.getType().compareTo("DdiscoverMill") == 0) {
            handleDiscoverMill(mission);
        }
    }

    void handleDiscoverFarm(Mission mission)
    {
        Mission_DdiscoverFarm m = (Mission_DdiscoverFarm)mission;
        farm = m.getFarm();
        if(farm == null)
        {
            if(discoverTileY - distance == tile.getYNr() || discoverTileY + distance == tile.getYNr()) {
                discoverTileX++;
            }
            else {
                discoverTileX = tile.getXNr() + distance;
            }
            if(discoverTileX > tile.getXNr() + distance)
            {
                discoverTileY++;
                discoverTileX = tile.getXNr() - distance;
            }
            if(discoverTileY > tile.getYNr() + distance)
            {
                distance++;
                discoverTileX = tile.getXNr() - distance;
                discoverTileY = tile.getYNr() - distance;
            }
        } else
        {
            System.out.println("House has Farm");
        }
    }
    
    void handleDiscoverMill(Mission mission){
        Mission_DdiscoverMill m = (Mission_DdiscoverMill)mission;
        mill = m.getMill();
        if(mill == null)
        {
            if(discoverTileY - distance == tile.getYNr() || discoverTileY + distance == tile.getYNr()) {
                discoverTileX++;
            }
            else {
                discoverTileX = tile.getXNr() + distance;
            }
            if(discoverTileX > tile.getXNr() + distance)
            {
                discoverTileY++;
                discoverTileX = tile.getXNr() - distance;
            }
            if(discoverTileY > tile.getYNr() + distance)
            {
                distance++;
                discoverTileX = tile.getXNr() - distance;
                discoverTileY = tile.getYNr() - distance;
            }
        } else
        {
            System.out.println("House has Mill");
        }
    }

    private void handleDeliverFoodMission(Mission_DeliverFood mission)
    {
        for(int i = 0; i < mission.getPickUp(); i++){
            //incStock();----------------------------------------------------------------
        }
    }

    public void setLittleMan(int ID, LittleMan littleMan)
    {
        littleMen[ID] = littleMan;
    }

    public synchronized Mission[] getMissionList()
    {
        Mission missionList[] = new Mission[missionlist.size()];
        for(int i = 0; i < missionList.length; i++) {
            missionList[i] = (Mission)missionlist.get(i);
        }

        return missionList;
    }

    public void reportHome()
    {
        incInhabits();
    }

    public void incInhabits()
    {
        manInHouse++;
    }

    public void decInhabits()
    {
        manInHouse--;
    }
    
    @Override
    public void reportAtWork(LittleMan littleMan){
        littleMan.setHome();
    }



    @Override
    public boolean isColliding(Point p)
    {
        for(int i = 0; i < collisionBox.length; i++) {
            if(collisionBox[i].contains(p)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean isColliding(Rectangle r)
    {
        for(int i = 0; i < collisionBox.length; i++) {
            if(collisionBox[i].contains(r)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Rectangle[] getBoundBox()
    {
        return collisionBox;
    }
    
    
    @Override
    public MouseObject getMouseObject(){
        return new HouseMouseObject(this);
    }

    @Override
    public Point getClosestCorner(Point location, double direction[])
    {
        if(collisionBoxCorners[2].getY() - 1.0D == location.getY())
        {
            System.out.println("wenn er an der unteren wand steht");
            if(direction[0] > 0.0D) {
                return collisionBoxCorners[2];
            }
            else {
                return collisionBoxCorners[3];
            }
        }
        if(collisionBoxCorners[0].getY() == location.getY())
        {
            System.out.println("wenn er an der oberen wand steht");
            if(direction[0] > 0.0D) {
                return collisionBoxCorners[1];
            }
            else {
                return collisionBoxCorners[0];
            }
        }
        if(collisionBoxCorners[0].getX() == location.getX())
        {
            System.out.println("wenn er an der linken wand steht");
            if(direction[1] < 0.0D) {
                return collisionBoxCorners[0];
            }
            else {
                return collisionBoxCorners[3];
            }
        }
        if(collisionBoxCorners[1].getX() - 1.0D == location.getX())
        {
            System.out.println("wenn er an der rechten wand steht");
            if(direction[1] < 0.0D) {
                return collisionBoxCorners[1];
            }
            else {
                return collisionBoxCorners[2];
            }
        } else
        {
            return new Point(0, 0);
        }
    }

    @Override
    public void addAllowance(Entity entity)
    {
    }

    @Override
    public void removeAllowance(Entity entity)
    {
    }

    /**
     * Returns the stock of this house
     * @return 
     */
    public Stock getStock() {
        return stock;
    }
    
    
    
    /**
     * Returns an array with all littleMan living in this Building
     * @return 
     */
    public LittleMan[] getLittleMan(){
        return littleMen;
    }

    @Override
    public craftable getCraft() {
        return null;
    }

    @Override
    public Stock getDropStock() {
        return stock;
    }
    
    
    class HousePanel extends EntityPanel{
        House house;
        JPanel inhabitPanel;
        
        public HousePanel(House house){
            super("House", house.getSprite());
            this.house = house;
            //Initialize GUI for house
            //this.setPreferredSize(new Dimension(GV.get().getXRes(),200));
            //this.setBackground(Color.DARK_GRAY);
            //this.setLayout(null);
            
            //JLabel label = new JLabel("HOUSE:");
            //label.setForeground(Color.WHITE);
            //label.setBounds(10, 2, 100, 20);
            //this.add(label);
            
            //JLabel icon = new JLabel();
            //icon.setIcon(new ImageIcon(SpriteStore.get().getSprite("house.png").getImage()));
            //icon.setBounds(10, 20, 50, 50);
            //this.add(icon);
            
            
            inhabitPanel = new JPanel(new FlowLayout());
            inhabitPanel.setBounds(100, 20, 200, 28);
            inhabitPanel.setBackground(Color.DARK_GRAY);
            
            JLabel littleManLabel = new JLabel("Inhabitants: ");
            littleManLabel.setForeground(Color.WHITE);
            inhabitPanel.add(littleManLabel);
            JButton littleMan;
            for(int i = 0; i < 4;i++){
                littleMan = new JButton();
                littleMan.setIcon(new ImageIcon(SpriteStore.get().getSprite("littleman00.png").getImage()));
                littleMan.setPreferredSize(new Dimension(20,20));
                littleMan.addActionListener(new LittleManButtonListener(house.littleMen[i],littleMan));
                house.littleMen[i].setButton(littleMan);
                inhabitPanel.add(littleMan);
            }
            this.add(inhabitPanel);
            
            
        }
    }
    
    
    class LittleManButtonListener implements ActionListener{

        LittleMan littleMan;
        JButton button;
        Color standardBackground;
        LittleManButtonListener(LittleMan littleMan, JButton button){
            this.littleMan = littleMan;
            this.button = button;
            standardBackground = button.getBackground();
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            GV.get().getMouse().setMouseObject(littleMan.getMouseObject());
        }


        
    }
    
    
    
    class HouseMouseObject extends BuildingMouseObject{

        public HouseMouseObject(Building building) {
            super(building);
        }

        public HouseMouseObject(Building building, int radius) {
            super(building, radius);
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
