/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import tilemap7.Camera;
import tilemap7.GV;
import tilemap7.Tile;
import tilemap7.TileMap;

/**
 *
 * @author Steve
 */
public class MouseObject {
    protected Sprite shade;
    protected Sprite shadedObject;
    private clickHandler leftClick;
    private clickHandler rightClick;
    private Point mousePosition = new Point(0,0);
    
    
    public MouseObject(){
        shade = SpriteStore.get().getSprite("shade.png");
        initialize();
    }
    
    protected void initialize(){
        
    }
    
    public void drawMouse(Graphics2D g){
        if(shadedObject != null){
            drawShade(g);
        }
    }
    
    public void doLeftClick(MouseEvent e){
        if(leftClick != null){
            leftClick.reservedClick(e.getX(), e.getY());
        }else{
            GV.get().getMouse().setMouseObject(null);
        }
    }
    
    public void doRightClick(MouseEvent e){
        if(rightClick != null){
            rightClick.reservedClick(e.getX(), e.getY());
        }else{
            GV.get().getMouse().setMouseObject(null);
        }
    }
    
    
    public void drawShade(Graphics2D g){
        Camera camera = GV.get().getCamera();       
        int y = (camera.getYPos() + (int) mousePosition.getY()) / GV.get().getYTileSize();
        int x = (camera.getXPos() + (int) mousePosition.getX()) / GV.get().getXTileSize();
        y = y * GV.get().getYTileSize();
        x = x * GV.get().getXTileSize(); 
        g.drawImage(shadedObject.getImage(), x - camera.getXPos(), y - camera.getYPos(), null);
        g.drawImage(shade.getImage(), x - camera.getXPos(), y - camera.getYPos(), null);   
    }

    public void drawShadedCircle(Graphics g, int radius) {
        Camera camera = GV.get().getCamera();
        TileMap tileMap = GV.get().getTileMap();

        int yShade = (camera.getYPos() + (int) getMousePosition().getY()) / GV.get().getYTileSize();
        int xShade = (camera.getXPos() + (int) getMousePosition().getX()) / GV.get().getXTileSize();
        yShade = yShade * GV.get().getYTileSize();
        xShade = xShade * GV.get().getXTileSize();
        for (int i = 0; i < radius * 2; i += GV.get().getXTileSize()) {
            for (int j = 0; j < radius * 2; j += GV.get().getYTileSize()) {
                int x = xShade - radius + j;
                int y = yShade - radius + i;
                Tile tile = tileMap.getTileByPos(x, y);
                if (tile.getType().compareTo("black") != 0
                        && tile.getPos().distance(new Point(xShade, yShade)) < radius) {
                    g.drawImage(shade.getImage(), tile.getXPos() - camera.getXPos(), tile.getYPos() - camera.getYPos(), null);
                }
            }
        }
    }

    public void setShade(Sprite shadedSprite){
        this.shadedObject = shadedSprite;
    }
    
    public void updateMousePosition(Point p){
        this.mousePosition = p;
    }

    public void setLeftClick(clickHandler leftClick) {
        this.leftClick = leftClick;
    }

    public void setRightClick(clickHandler rightClick) {
        this.rightClick = rightClick;
    }

    public Point getMousePosition() {
        return mousePosition;
    }
    
    
    
    
}
