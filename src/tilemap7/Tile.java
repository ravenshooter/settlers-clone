/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tilemap7;
import tilemap7.Crafting.Tree;
import Tools.Sprite;
import Tools.SpriteStore;
import tilemap7.Buildings.Building;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.awt.Point;
import java.awt.Rectangle;
/**
 * Write a description of class Tile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Tile
{
    String type;
    Sprite sprite;
    Point center;
    ArrayList <Entity> entities = new ArrayList<Entity>();
    boolean hasEntities;
    public double height;
    boolean showHeight;
    int xNr;
    int yNr;
    int xPos;
    int yPos;
    Rectangle tileRect;
    boolean markable = true; //true wenn diese Zelle markiert werden kann;
    boolean passable = true;
    boolean buildable = true;
    private Building building;
    
    /**
     * Referenzen auf die Umgebung des Tiles, 0 ist oben, dann im Uhrzeigersinn
     */
    Tile neighbours[];
    
    /**
     * Constructor for objects of class Tile
     */
    public Tile(TileMap tileMap, String type, int xNr, int yNr)
    {
        neighbours = new Tile[8];
        neighbours[0] = tileMap.getTile(xNr,yNr-1);
        neighbours[1] = tileMap.getTile(xNr+1,yNr-1);
        neighbours[2] = tileMap.getTile(xNr+1,yNr);
        neighbours[3] = tileMap.getTile(xNr+1,yNr+1);
        neighbours[4] = tileMap.getTile(xNr,yNr+1);
        neighbours[5] = tileMap.getTile(xNr-1,yNr+1);
        neighbours[6] = tileMap.getTile(xNr-1,yNr);
        neighbours[7] = tileMap.getTile(xNr-1,yNr-1);
        this.xNr = xNr;
        this.yNr = yNr;
        xPos = xNr*GV.get().getXTileSize();
        yPos = yNr*GV.get().getYTileSize();
        tileRect= new Rectangle(xPos,yPos,GV.get().getXTileSize(),GV.get().getYTileSize());
        center = new Point(xPos+GV.get().getXTileSize()/2,yPos+GV.get().getYTileSize()/2);
        
        
        setType(type);
        

        hasEntities =false;
        if(type.compareTo("water")==0){
            passable = false;
            buildable = false;
        }
        if(type.compareTo("forest")==0){
            buildable = false;
            addEntity(new Tree(this,xPos+10,yPos+10));
            addEntity(new Tree(this,xPos+30,yPos+10));
            addEntity(new Tree(this,xPos+10,yPos+30));
            addEntity(new Tree(this,xPos+30,yPos+30));
        }
    }
    
    /**
     * Konstuktor fuer ein black Tile ohne jeglichen Wert
     */
    public Tile(){
        this.type = "black";
        sprite = SpriteStore.get().getSprite(type+".png");
        hasEntities =false;
        markable = false;
        passable = false;
    }
    
    public final void setType(String type){
        this.type = type;
        
        //neighbours = new Tile[8];
        //setNeighbours();
        sprite = SpriteStore.get().getSprite(type+".png");
        if(type.compareTo("water")==0){
                    passable = false;
                    buildable = false;
        }
    }
    
    


    public synchronized void draw(Graphics2D g, int x, int y){
        int dstx1 = x; 
        int dstx2 = x+GV.get().getXTileSize();
        int srcx1 = (xNr*GV.get().getXTileSize())%sprite.getWidth(); 
        int srcx2 = srcx1+GV.get().getXTileSize(); 
        int dsty1;
        int dsty2;
        int srcy1;
        int srcy2;
        if(type.compareTo("water")==0 && neighbours[4].getType().compareTo("grass")==0){
             sprite = SpriteStore.get().getSprite("beach.png");
             dsty1 = y-50;
             dsty2 = y+50;
             srcy1 = 500;
             srcy2 = 600;
        }else{
             dsty1 = y;
             dsty2 = y+GV.get().getYTileSize();              
             srcy1 = (yNr*GV.get().getYTileSize())%sprite.getHeight(); 
             srcy2 = srcy1+GV.get().getYTileSize();          
        }
        g.drawImage(sprite.getImage(),
                    dstx1,dsty1,dstx2,dsty2,
                    srcx1,srcy1,srcx2,srcy2,
                    null);
        
        if(showHeight){
            g.drawString(String.valueOf(height),x,y+10);
        }
    }
    
    public void drawEntities(Graphics2D g, int x, int y){
        
    if(hasEntities){
        synchronized(entities){        
            for(int i = 0; i < entities.size();i++){
                entities.get(i).draw(g,x,y);
            }
        }
    }
    }
    
    public synchronized void mouseClicked(){
        System.out.println(type);
        for(int i = 0; i < entities.size();i++){
            if(entities.get(i).isClickable()){
                entities.get(i).mouseClicked();
            }
        }
    }
    
    public boolean addEntity(Entity e){
        if(!buildable){
            if(e instanceof Building)
                return false;
        }
        if(!markable){
            if(e instanceof Marker)
                return false;
        }
        if(!passable){
            if(e instanceof movingEntity)
            {
                movingEntity me = (movingEntity) e;
                me.setCollision(true);
                entities.add(e);
                hasEntities = true;
                return false;
            }
        }
        entities.add(e);
        if(e instanceof Building){
            setUnbuildable();
            building = (Building)e;
        }
        hasEntities = true;
        return true;
    }
    
    /**
     * Returns the first listed entity of this tile, except the marker.
     * @return 
     */
    public Entity getEntity(){
        if(!entities.isEmpty()){
            if(entities.get(0).getClass().getName().compareTo("Marker")!=0){
                return entities.get(0);
            }else{
                if(entities.size()>0){
                    return entities.get(1);
                }else{
                    return null;
                }
            }
        }else{
            return null;
        }
    }
    
    /**
     * Returns first occurence of an entity of this type at the tile, null if not there
     * @param type
     * @return 
     */
    public synchronized Entity getEntity(String type){
        if(!entities.isEmpty()){
            for(Entity e: entities){
                if(e.getClass().getSimpleName().equals(type)){
                    return e;
                }
            }
        }
        return null;
    }
    
    public synchronized ArrayList<Entity> getEntities(String type){
        ArrayList<Entity> returnList = new ArrayList<Entity>();
        if(!entities.isEmpty()){
            for(Entity e: entities){
                if(e.getClass().getSimpleName().equals(type)){
                    returnList.add(e);
                }
            }
        }
        return returnList;
    }
    
    /**
     * Removes the specified entity from the tile
     * @param e 
     */
    public void remove(Entity e){
        int entityID = entities.indexOf(e);
        if(entityID == -1){}else{
            entities.remove(entityID);
            if(entities.isEmpty()){
                hasEntities=false;
            }
        }
    }
    public collideable isColliding(Point p){
        Entity e;
        for(int i = 0; i < entities.size();i++){
            e = entities.get(i);
            if(e instanceof collideable){
                collideable c = (collideable) e;
                if(c.isColliding(p))
                    return c; 
            }
        }
        return null;
    }
    public void doLogic(int time){
        for(int i = 0; i < entities.size();i++){
            entities.get(i).doLogic(time);
        }
    }
    
    public void setNeighbours(TileMap tileMap, int xNr,int yNr){
        neighbours[0] = tileMap.getTile(xNr,yNr-1);
        neighbours[1] = tileMap.getTile(xNr+1,yNr-1);
        neighbours[2] = tileMap.getTile(xNr+1,yNr);
        neighbours[3] = tileMap.getTile(xNr+1,yNr+1);
        neighbours[4] = tileMap.getTile(xNr,yNr+1);
        neighbours[5] = tileMap.getTile(xNr-1,yNr+1);
        neighbours[6] = tileMap.getTile(xNr-1,yNr);
        neighbours[7] = tileMap.getTile(xNr-1,yNr-1);
    }
    
    private void setNeighbours(){
        TileMap tileMap = GV.get().getTileMap();
        neighbours[0] = tileMap.getTile(xNr,yNr-1);
        neighbours[1] = tileMap.getTile(xNr+1,yNr-1);
        neighbours[2] = tileMap.getTile(xNr+1,yNr);
        neighbours[3] = tileMap.getTile(xNr+1,yNr+1);
        neighbours[4] = tileMap.getTile(xNr,yNr+1);
        neighbours[5] = tileMap.getTile(xNr-1,yNr+1);
        neighbours[6] = tileMap.getTile(xNr-1,yNr);
        neighbours[7] = tileMap.getTile(xNr-1,yNr-1);
    }
    
    /**
     * Returns the neighbour at the position nr, 0 is above, than clockwise, maximum 7
     * @param nr Position of the neighbour
     * @return returns null if nr > 7
     */
    public Tile getNeighbour(int nr){
        if(nr < 8){
            return neighbours[nr];
        }else{
            return null;
        }
    }
    public void setUnpassable(){
        passable = false;
    }
    public void setUnbuildable(){
        buildable = false;
    }
    
    public void setShowHeight(boolean set){
        showHeight = set;
    }
    public int getXNr(){
        return xNr;
    }
    public int getYNr(){
        return yNr;
    }
    public int getXPos(){
        return xPos;
    }
    public int getYPos(){
        return yPos;
    }
    public Point getCenter(){
        return center;
    }
    public int getXSize(){
        return sprite.getWidth();
    }
    public int getYSize(){
        return sprite.getHeight();
    }
    public String getType(){
        return type;
    }
    public double getHeight(){
        return height;
    }
    public boolean isMarkable(){
        return markable;
    }

    public boolean isBuildable() {
        return buildable;
    }

    public Building getBuilding() {
        return building;
    }
    
    
    public synchronized Entity hasEntity(String entity){
        for(int i = 0; i < entities.size();i++){
            if(entities.get(i).getClass().getName().compareTo(entity) == 0){
                return entities.get(i);
            }
        }
        return null;
    }
    public Point getPos(){
        return new Point(xNr*GV.get().getXTileSize(),yNr*GV.get().getYTileSize());
    }
    
    public int hasNeighbour(String type){
        int amount = 0;
        for(int i = 0;i < 8; i++ ){
            if(neighbours[i].getType().compareTo(type)==0)
                amount++;
        }
        return amount;
    }
        public int hasNeighbourAtBorder(String type){
        int amount = 0;
        for(int i = 0;i < 8; i = i + 2 ){
            if(neighbours[i].getType().compareTo(type)==0)
                amount++;
        }
        return amount;
    }
    
}
