/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tilemap7;
import tilemap7.Mission.Mission;
import tilemap7.Mission.Mission_GoTo;
import Tools.Sprite;
import Tools.SpriteStore;
import Tools.MouseObject;
import java.awt.Color;
import tilemap7.Buildings.House;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Label;
import java.awt.geom.Point2D.Double;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import tilemap7.Buildings.Building;
import tilemap7.CarryObjects.CarryObject;
import tilemap7.Mission.Mission_GoToWork;
/**
 * Write a description of class LittleMan here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LittleMan extends movingEntity
{
    

    Double location;
    House house;
    double speed;
    double direction[];
    int ID;
    
    Sprite[] sprites;
    int spriteState;
    int spriteTimer;
    Sprite shadow;
    
    boolean hasMission;
    boolean setCheckCollision;

    PathMission path;
    ArrayList<Mission> missionList;
    
    
    boolean initialized;
    boolean visible;
    
    CarryObject carryObject;
    boolean carrying;
    
    
    private ArrayList<String> jobs;
    
    private boolean moving;
    private Point target;
    
    private Building workPlace;
    private boolean atWork;
    
    private boolean atHome;
    
    private LittleManMouseObject mouseObject; 
    private JButton button;
    
    
    public LittleMan(int ID, Point start, House house) {
        super(house.getTile());
        house.getTile().addEntity(this);


        missionList = new ArrayList();
        hasMission = false;
        jobs = new ArrayList();
        //jobs.add("home");

        sprites = new Sprite[4];
        sprites[0] = SpriteStore.get().getSprite("littleman00.png");
        sprites[1] = SpriteStore.get().getSprite("littleman01.png");
        sprites[2] = SpriteStore.get().getSprite("littleman02.png");
        sprites[3] = SpriteStore.get().getSprite("littleman03.png");
        sprite = sprites[0];
        spriteState = 0;
        shadow = SpriteStore.get().getSprite("littlemanshadow.png");

        this.location = new Double(start.getX(), start.getY());
        this.house = house;
        direction = new double[2];
        direction[0] = 1;
        direction[1] = 1;
        speed = 0.8;
        this.ID = ID;

        mouseObject = new LittleManMouseObject(this);
        initialized = true;
    }

    
    @Override
    public void mouseClicked(){
        
    }
  
    int yOffset = (int)(Math.random()*40.0);
    @Override
    public synchronized void draw(Graphics g, int x, int y) {
        if (initialized && visible) {

            Graphics2D g2 = (Graphics2D) g;
            Camera camera = GV.get().getCamera();
            double rotate = Math.atan(direction[1] / direction[0]) + Math.PI / 2;
            if (direction[0] < 0) {
                rotate = rotate + Math.PI;
            }
            int xPos = (int) location.getX() - camera.getXPos();
            int yPos = (int) location.getY() - camera.getYPos() - sprite.getHeight() / 2;
            g2.drawImage(shadow.getImage(), (int) xPos - sprite.getWidth() / 2, (int) yPos - sprite.getHeight() / 2, null);
            g2.rotate(rotate, xPos, yPos);
            g2.drawImage(sprites[spriteState].getImage(), (int) xPos - sprite.getWidth() / 2, (int) yPos - sprite.getHeight() / 2, null);
            if (carryObject != null) {
                g2.drawImage(carryObject.getImage(), (int) xPos - sprite.getWidth() / 2, (int) yPos - sprite.getHeight() / 2, null);
            }
            g2.rotate(-rotate, xPos, yPos);
            
            
            if(GV.get().drawLittleManMissionList){
                synchronized(missionList){
                    if(!missionList.isEmpty()){
                        if(target!=null){
                            g2.drawString("target: "+target.x+ "|" +target.y, xPos, yPos+20+yOffset);
                        }
                        g2.drawString(this.toString()+" "+missionList.get(0).getType(), xPos, yPos+yOffset);
                    }
                }
            }
           
        }
    }

    @Override
    public void doLogic(int time) {
        if (initialized) {
            
            //set feet of actor
            if(moving){
                spriteTimer++;
                if (spriteTimer == 25) {
                    spriteTimer = 0;
                    spriteState++;
                    if (spriteState == 4) {
                        spriteState = 0;
                    }
                }
            }
            
            doMission();
            
            //bewegt den mann (move ist true wenn er angekommen ist)
            if (target != null) {
                if(move(target)){
                    target = null;
                }
            }
            

            checkTile();

            checkCollision();
        }
    }


    /**
     * Checks if the actor is registered at the tile on which he is standing
     * Also checks if the tile is the home or job tile of the littleman.
     */
    private void checkTile(){
        if((location.getX()<tile.getXPos())){
            tile.remove(this);
            tile = tile.getNeighbour(6);
            checkCollision = false;
            tile.addEntity(this);
            
        
            //System.out.println("Actor " + ID + " at Tile: " + tile.getXNr() + "/" + tile.getYNr());
        }
        if((location.getX()-tile.getXPos())>GV.get().getXTileSize()){
            tile.remove(this);
            tile = tile.getNeighbour(2);
            checkCollision = false;
            tile.addEntity(this);
            //System.out.println("Actor " + ID + " at Tile: " + tile.getXNr() + "/" + tile.getYNr());
        }
        if((location.getY()<tile.getYPos())){
            tile.remove(this);
            tile = tile.getNeighbour(0);
            checkCollision = false;
            tile.addEntity(this);            
            //System.out.println("Actor " + ID + " at Tile: " + tile.getXNr() + "/" + tile.getYNr());
        }
        if((location.getY()-tile.getYPos())>GV.get().getYTileSize()){
            tile.remove(this);
            tile = tile.getNeighbour(4);
            checkCollision = false;
            tile.addEntity(this);  
            
            //System.out.println("Actor " + ID + " at Tile: " + tile.getXNr() + "/" + tile.getYNr());
        }
        
        

        
    }
    
    
    /**
     * Calls the doMission Method of the current Mission. Called everytime in logic loop.
     */
    private void doMission() {
        if (!missionList.isEmpty()) {
            boolean done = missionList.get(0).doMission(this);
            if (done) {
                missionList.remove(0);
            }
        }

    }

    /**
     * Checks if the actor is colliding with anything at the place where he's standing now
     */
    private void checkCollision(){
        /*
        if(checkCollision){
            collideable c = tile.isColliding(new Point ((int)location.getX(),(int)location.getY()));
            if(c != null){
                System.out.println("collision");
                Point wayPoint = c.getClosestCorner(new Point ((int)location.getX(),(int)location.getY()),direction);
                if(mission instanceof Mission_GoTo){
                    Mission_GoTo m = (Mission_GoTo) mission;
                    m.setDirSet(false);
                }
                missionStack = mission;
                mission = new Mission_GoTo(wayPoint);
                checkCollision = false;
                System.out.println("new waypoint to: " + wayPoint.getX()+" " + wayPoint.getY());
            }
        }
        * */
    }
    
    
    /**
     * Moves the little Man one step in direction of the target
     * @param goTo target where the man should go to
     * @return true if the little Man reached the target with this step, otherwise returns false
     */
    private boolean move(Point goTo){
        if(getLocation().distance(goTo) < getSpeed() && getLocation().distance(goTo) > -getSpeed()){
            setLocation(new java.awt.geom.Point2D.Double(goTo.getX(),goTo.getY()));
            return true;
        }else{
            double newDirection[] = new double[2];
            newDirection[0]=(goTo.getX()-getLocation().getX())/getLocation().distance(goTo);
            newDirection[1]=(goTo.getY()-getLocation().getY())/getLocation().distance(goTo);
            double newX = newDirection[0]*getSpeed()+getLocation().getX();
            double newY = newDirection[1]*getSpeed()+getLocation().getY();
            
            setDirection(newDirection);
            getLocation().setLocation(newX,newY);
            return false;
        }
    }
    
    
    public void addMission(Mission mission){
        //System.err.println(this.toString() + " mission added " + mission.getClass().getSimpleName());
        missionList.add(mission);
        hasMission = true;
    }

    
    
    public void callToWork(){
        if(!calledToWork()){
            addMission(new Mission_GoToWork(this));
        }
        System.out.println("Worker " + this.toString() + " called to work by " + workPlace.getClass());
    }
    
    public void leaveWork(){
        if(!jobs.isEmpty() && jobs.get(0).compareTo("work")==0) {
            jobs.remove(0);
            workPlace.reportLeaveWork(this);
            atWork = false;
            System.out.println("Worker " + this.toString() + " left work " + workPlace.getClass());
        }
        
    }
    
    public void goHome(){
        jobs.add("home");
        target = house.getWorkPos();
    }
    
    public boolean calledToWork(){
        for(Mission m : missionList){
            if(m.getType().equals(Mission_GoToWork.class.getSimpleName())){
                return true;
            }
        }
        return false;
    }


    /**
     * Should only be called by the actors house from reportAtWork
     */
    public void setHome(){
        if(atHome){
            visible = false;
            moving = false;
            if(!jobs.isEmpty() && jobs.get(0).compareTo("home")==0) {
                jobs.remove(0);
            }
        }
    }
    
    public void setWorkPlace(Building workPlace) {
        if(this.workPlace != null){
            leaveWork();
            this.workPlace.removeWorker(this);
            for(int i = 0; i < jobs.size();i++){
                if(jobs.get(i).compareTo("work")== 0){
                    jobs.remove(i);
                }
            }
            
        }
        this.workPlace = workPlace;
    }
    
    
    public Double getLocation(){
        return location;
    }
    
    public void setCarryObject(CarryObject object){
        carryObject = object;
        carrying = true;
    }
    public CarryObject finishCarryObject(){
        carrying = false;
        CarryObject c = carryObject;
        carryObject = null;
        return c;
    }

    
    /**
     * Should only be called by mission.
     * Use addMission(new GotoMission instead!)
     * @param target 
     */
    public void setTarget(Point target) {
        this.target = target;
    }
    
    public boolean atPosition(Point p){
        return getLocation().distance(p)==0;
    }
    
    public synchronized void setDirection(double direction[]){
        this.direction = direction;
    }

    public void setLocation(Double location) {
        this.location = location;
    }
    
    
    public double[] getDirection(){
        return direction;
    }
    public boolean hasMission(){
        return !missionList.isEmpty();
    }
    
    
    public Mission getMission(){
        if(!missionList.isEmpty()){
            return missionList.get(0);
        }else{
            return null;
        }
    }
    
    
    @Override
    public Tile getTile(){
        //System.out.println(Math.round(getLocation().getX()/GV.get().getXTileSize()-0.5) + " " + Math.round(this.getLocation().getY()/GV.get().getXTileSize()-0.5));
        
        return GV.get().getTileMap().getTile((int)Math.round(getLocation().getX()/GV.get().getXTileSize()-0.5),(int)Math.round(getLocation().getY()/GV.get().getXTileSize()-0.5));
        
    }

    public boolean isCarrying() {
        return carrying;
    }
    
    

    public Building getWorkPlace() {
        return workPlace;
    }
    
    
    
    public LittleManMouseObject getMouseObject(){
        return mouseObject;
    }

    public double getSpeed() {
        return speed;
    }

    public void setButton(JButton button) {
        this.button = button;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    
    
    
    
    class LittleManMouseObject extends MouseObject{
        LittleMan littleMan;

        public LittleManMouseObject(LittleMan littleMan) {
            super();
            this.littleMan = littleMan;
        }

        @Override
        public void drawMouse(Graphics2D g) {
            g.drawImage(sprite.getImage(), (int)getMousePosition().getX(), (int)getMousePosition().getY(), null);
        }
        
        
        
        
        /**
         * Prüft ob das geklickte Tile ein building enthält, wenn ja wird der littleMan dem Building als Arbeiter hinzugefügt
         * Sonst neue GoTo Mission
         * @param e 
         */
        @Override
        public void doRightClick(MouseEvent e) {
            Tile clickedTile = GV.get().getTileMap().getTileByPosWithCamera(e.getX(), e.getY());
            if(clickedTile.getBuilding()!=null){
                if(clickedTile.getBuilding().addWorker(littleMan)){
                    button.setBackground(Color.lightGray);
                    GV.get().getUI().showString("Worker added.");
                }else{
                    GV.get().getUI().showString("No worklace available.");
                }
            }else{
                addMission(new Mission_GoTo(new Point(e.getX()+GV.get().getCamera().getXPos(), e.getY()+GV.get().getCamera().getYPos())));
            }
            GV.get().getMouse().setMouseObject(null);
            GV.get().getMouse().setClickHandler(null);
        }

        @Override
        public void doLeftClick(MouseEvent e) {
            GV.get().getMouse().setMouseObject(null);
        }
        
        
        
        
    }

    
    class WorkerPanel extends JPanel{
        WorkerPanel(){
            this.setLayout(null);
            Label name = new Label("Worker");
            name.setBounds(20,20,70,20);
            this.add(name);
            
            JLabel image = new JLabel(new ImageIcon(sprite.getImage()));
            image.setBounds(40,40,50,50);
            this.add(image);
        }
    }
}
