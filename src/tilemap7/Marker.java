/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tilemap7;

import Tools.SpriteStore;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
/**
 * Write a description of class Marker here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Marker extends Entity
{
    
    TileMap tileMap;
    /**
     * Constructor for objects of class Marker
     */
    public Marker()
    {
        super(null);
        GV.get().setMarker(this);
        if(GV.get().getXTileSize() == 100){
            sprite = SpriteStore.get().getSprite("marker100.png");
        }else{
            sprite = SpriteStore.get().getSprite("marker.png");
        }
        tileMap = GV.get().getTileMap();
        tile = tileMap.getTile(1,1);
        tile.addEntity(this);
        isClickable = false;
    }
    
    @Override
    public void draw(Graphics g, int x, int y){
        g.drawImage(sprite.getImage(),x,y,GV.get().getXTileSize(),GV.get().getYTileSize(),null);
    }
    
    @Override
    public void mouseClicked(MouseEvent e){}
    
    public void move(Tile tile){
        if(tile!=null && tile.isMarkable()){
            this.tile.remove(this);
            tile.addEntity(this);
            this.tile = tile;
        }
        Camera camera = GV.get().getCamera();
        byte b = -1;
        int xDistance =  this.tile.getXNr()*GV.get().getXTileSize() - camera.getXPos();
        if(xDistance < 41)
        {
            b = 6;
        }else{
            if(xDistance > GV.get().getXCameraSize() - 81) {
                b = 2;
            }
        }
        if(b == -1){
            int yDistance =  this.tile.getYNr()*GV.get().getYTileSize() - camera.getYPos();
            if(yDistance < 41)
            {
                b = 0;
            }else{
                if(yDistance > GV.get().getYCameraSize() - 81) {
                    b = 4;
                }
            }
        }
        if(b != -1) {
            camera.addMovement(b);
        }
        xPos = this.tile.getXPos();
        yPos = this.tile.getYPos();
    }
    
    public void move(int dir){
        move(this.tile.getNeighbour(dir*2-2));
        
    }
    
    public void addLetter(Letter letter){
        this.tile.addEntity(letter);
    }
    
    public void addEntity(Entity entity){
        this.tile.addEntity(entity);
        System.out.println("entity added");
    }
    
    Tile getMarkedTile(){
        return this.tile;
    }
        
}