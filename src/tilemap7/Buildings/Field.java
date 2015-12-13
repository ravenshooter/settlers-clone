/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tilemap7.Buildings;
import Tools.Sprite;
import Tools.SpriteStore;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import tilemap7.CarryObjects.Grain;
import tilemap7.Entity;
import tilemap7.GV;
import tilemap7.Mission.Mission;
import tilemap7.Tile;
import tilemap7.collideable;
import tilemap7.missionMaster;

public class Field extends Building
    implements missionMaster, collideable
{

    Sprite[] sprites;
    int state;
    private int initTime;
    
    Stock outStock;
    
    int productivity;
    boolean hasWorker;
    Rectangle collisionBox[];
    Point collisionBoxCorners[];
    
    
    public Field(Tile tile) throws UnBuildableException
    {
        super(tile);
        tile.setUnpassable();
        sprite = SpriteStore.get().getSprite("field0.png");
        if(tile.getType().compareTo("grass") == 0) {
            productivity = 1;
        }
        hasWorker = false;
        entityContainer = new Entity.EntityContainer(this);
        entityContainer.addAttribute("Productivity", String.valueOf(productivity));
        tile.setUnpassable();
        collisionBox = new Rectangle[1];
        collisionBox[0] = new Rectangle(tile.getXPos() + 3, tile.getYPos() + 3, 46, 46);
        collisionBoxCorners = new Point[4];
        collisionBoxCorners[0] = new Point(collisionBox[0].getLocation());
        collisionBoxCorners[1] = new Point((int)(collisionBox[0].getX() + collisionBox[0].getWidth()), (int)collisionBox[0].getY());
        collisionBoxCorners[2] = new Point((int)(collisionBox[0].getX() + collisionBox[0].getWidth()), (int)(collisionBox[0].getY() + collisionBox[0].getHeight()));
        collisionBoxCorners[3] = new Point((int)collisionBox[0].getX(), (int)(collisionBox[0].getY() + collisionBox[0].getHeight()));
        outStock = new Stock(new Point(tile.getPos().x+25,tile.getPos().y+25),1);
        sprites = new Sprite[4];
        for(int i = 0; i < 4; i++){
            sprites[i] = SpriteStore.get().getSprite("field"+i+".png");
        }
        initTime = (int)(System.currentTimeMillis()%100);
    }
    
    public Field(){
        sprite = SpriteStore.get().getSprite("field0.png");
    }

    
    @Override
    public void mouseClicked()
    {
        if(isClickable) {
            GV.get().getUI().mouseClicked(this);
        }
    }

    
    @Override
    public void doLogic(int time) {
        synchronized (this) {
            if (time % 100 == initTime && !outStock.isFull()) {
                state++;
                if(state > 3){
                    outStock.reportDropDown();
                    outStock.add(new Grain());
                    state = 0;
                }
            }
        }

    }
    
    public int getProductivity()
    {
        return productivity;
    }

    @Override
    public void missionCompleted(Mission mission)
    {
        hasWorker = false;
    }

    @Override
    public void draw(Graphics g, int x, int y)
    {
        synchronized(this){
            g.drawImage(sprites[state].getImage(), x, y, null);
            outStock.draw(g, x+25, y+25);
            //g.drawString("outstock: " + outStock.getExpectedStock() + "/"+ outStock.getSize(), x, y);
        }
    }

    public boolean needWorker()
    {
        return !hasWorker;
    }

    public void sendWorker()
    {
        hasWorker = true;
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
            if(collisionBox[i].contains(r))
                return true;
        }

        return false;
    }

    @Override
    public Rectangle[] getBoundBox()
    {
        return collisionBox;
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
     * Returns the outStock of this field
     * @return 
     */
    public Stock getOutStock() {
        return outStock;
    }
    
    
    

    

    @Override
    public Building addBuilding(Tile tile) throws UnBuildableException {
        if(!tile.getType().equals("rocks2")){
            Field field = null;
            if(this.tile == null){
                field = new Field(tile);
                tile.addEntity(field);
            }
            return field;
        }else{
            System.err.println("Cannot build on rocks");
        }
        return null;
    }
}
