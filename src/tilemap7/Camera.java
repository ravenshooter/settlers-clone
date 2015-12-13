/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tilemap7;
import java.awt.Graphics2D;
import java.util.ArrayList;
/**
 * Write a description of class Camera here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Camera
{
    private int x;
    private int y;
    private int xSize;
    private int ySize;
    private TileMap tileMap;
    private int xTileNr;
    private int yTileNr;
    private int xLastTileNr;
    private int yLastTileNr;   
    /**
     * Nicht angezeigte Pixel der Tiles an der linken Kante
     */
    private int xOffset;
    private int yOffset;
    private double moveSpeed;
    private int moveDirection[];
    private TileMap partTileMap;
    
    private ArrayList<Byte> movements;
    
    Graphics2D drawsheet;
    
    public static Camera get(){
        return camera;
    }
    public static Camera camera = new Camera();  
    /**
     * Constructor for objects of class Camera
     */
    private Camera(){}
    
    public void setUpCamera(TileMap tileMap, Graphics2D drawsheet, int x, int y)
    {
        GV.get().setCamera(this);
        this.tileMap =tileMap;
        this.x = x;
        this.y =y;
        this.xSize=GV.get().getXCameraSize();
        this.ySize=GV.get().getYCameraSize();
        moveSpeed = 4;
        moveDirection = new int[2]; 
        moveDirection[0] = 0;
        moveDirection[1] = 0;
        this.drawsheet = drawsheet;
        movements = new ArrayList();
    }
    
    public void cameraLoop(){
        move();
        synchronized(tileMap){
            capture();
            draw();
        }
    }
    
    private synchronized void move(){
        int i = 0;
        while(movements.size() != 0){
            byte dir = movements.remove(0);
            switch(dir){
                case 0: y = y-GV.get().getYTileSize();break;
                //case 1: //moveDirection[0]++;moveDirection[1]--;break;
                case 2: x = x+GV.get().getXTileSize();break;
                //case 3: moveDIrection[0]++;moveDirection[1]++;break;
                case 4: y = y+GV.get().getYTileSize();break;
                case 6: x = x-GV.get().getXTileSize();break;
            }
        }
        /*
        if(x + xSize + moveDirection[0]*(int)moveSpeed<tileMap.getWidth()*tileMap.getXTileSize()){
            if(x + moveDirection[0]*(int)moveSpeed>=0){
                x = x + moveDirection[0]*(int)moveSpeed;
            }else{
                x = 0;
            }
        }else{
            x = tileMap.getWidth()*tileMap.getXTileSize()-xSize-1;
        }
        
        if(y + ySize + moveDirection[1]*(int)moveSpeed<tileMap.getHeight()*tileMap.getYTileSize()){
            if(y + moveDirection[1]*(int)moveSpeed>=0){
                y = y + moveDirection[1]*(int)moveSpeed;
            }else{
                y = 0;
            }
        }else{
            y = tileMap.getHeight()*tileMap.getYTileSize()-ySize-1;
        }
        //System.out.println(moveDirection[0]+"/"+moveDirection[1]);
        //System.out.println(x+"/"+y);
        */
    }
    
    private void capture(){
        xTileNr = (int) Math.round((double)x/(double)tileMap.getXTileSize()-0.5);
        yTileNr = (int) Math.round((double)y/(double)tileMap.getYTileSize()-0.5);
        xLastTileNr = xTileNr + (int) Math.round((double)xSize/(double)tileMap.getXTileSize()+0.5);
        yLastTileNr = yTileNr + (int) Math.round((double)ySize/(double)tileMap.getYTileSize()+0.5);
        if(xLastTileNr < tileMap.getWidth())
            xLastTileNr++;
        if(yLastTileNr < tileMap.getHeight())
            yLastTileNr++;
        xOffset = x-xTileNr*tileMap.getXTileSize();
        yOffset = y-yTileNr*tileMap.getYTileSize();
    }
    
    private void draw(){
        long start = System.nanoTime();
        boolean hasWater= false;
        for(int yNr = yTileNr; yNr < yLastTileNr; yNr++){
            for(int xNr = xTileNr; xNr < xLastTileNr; xNr++){
                if(hasWater)
                    xNr = xLastTileNr;
                if(tileMap.getTile(xNr,yNr).getType().compareTo("water") == 0){
                    hasWater = true;
                }
            }
            if(hasWater)
                    yNr = yLastTileNr;
        }
        if(hasWater)
            tileMap.draw(drawsheet,x,y);
        
            
        
        int xTileSize = tileMap.getXTileSize();
        int yTileSize = tileMap.getYTileSize();
        Tile tile;
        for(int yNr = yTileNr; yNr < yLastTileNr; yNr++){
            for(int xNr = xTileNr; xNr < xLastTileNr; xNr++){
                tile=tileMap.getTile(xNr,yNr);
                tile.draw(drawsheet, 0 - xOffset+(xNr-xTileNr)*xTileSize, 0 - yOffset+ (yNr-yTileNr)*yTileSize );
                //tile.drawEntities(drawsheet, 0 - xOffset+(xNr-xTileNr)*xSize, 0 - yOffset+ (yNr-yTileNr)*ySize );

            }
        }
        for(int yNr = yTileNr; yNr < yLastTileNr; yNr++){
            for(int xNr = xTileNr; xNr < xLastTileNr; xNr++){
                tile=tileMap.getTile(xNr,yNr);
                //tile.draw(drawsheet, 0 - xOffset+(xNr-xTileNr)*xSize, 0 - yOffset+ (yNr-yTileNr)*ySize );
                tile.drawEntities(drawsheet, 0 - xOffset+(xNr-xTileNr)*xTileSize, 0 - yOffset+ (yNr-yTileNr)*yTileSize );

            }
        }
        long result = System.nanoTime()-start;
        /*for(int yNr = yTileNr; yNr < yLastTileNr; yNr++){
            for(int xNr = xTileNr; xNr < xLastTileNr; xNr++){
                tile = tileMap.getTile(xNr,yNr);
                if(tile.hasEntities){
                    tile.drawEntities(drawsheet, 0 - xOffset+(xNr-xTileNr)*xSize, 0 - yOffset+ (yNr-yTileNr)*ySize );
                }
            }
        }*/
        drawsheet.drawString("drawing: "+String.valueOf(result/100000),40,60);
    }
    
    
    /**
     * Ueber Vektorenaddition
     */
    public synchronized void newMovement(int moveDirectionn[])
    {
        moveDirection[0] = moveDirection[0] + moveDirectionn[0];
        moveDirection[1] = moveDirection[1] + moveDirectionn[1];
    }
    
    public synchronized void addMovement(byte b){
        movements.add(b);
    }
    
    public int getXPos(){
        return x;
    }
    public int getYPos(){
        return y;
    }
    public int getXOffset(){
        return xOffset;
    }
    public int getYOffset(){
        return yOffset;
    }
    
    
}
