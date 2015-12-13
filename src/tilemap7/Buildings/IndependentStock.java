/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tilemap7.Buildings;

import Tools.SpriteStore;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import tilemap7.Buildings.Tools.Stock;
import tilemap7.Entity;
import tilemap7.GV;

/**
 *
 * @author Steve
 */
public class IndependentStock extends Entity{

    
    /**
     * Distance between topleft corner of tile and stock.
     */
    private int xOffset;
    /**
     * Distance between topleft corner of tile and stock.
     */
    private int yOffset;
    
    Stock stock;
    
    public IndependentStock(Point position, int maxSize, int rows) {
        super(GV.get().getTileMap().getTileByPos(position.x, position.y));
        sprite = SpriteStore.get().getSprite("stock.png");
        
        stock = new Stock(position, maxSize, rows);
        
        xOffset = position.x - tile.getXPos();
        yOffset = position.y - tile.getYPos();
    }
    
    /**
     * Draws this stock.
     * @param g
     * @param x the x cood of the top left corner of the current tile
     * @param y the y cood of the top left corner of the current tile
     */
    @Override
    public void draw(Graphics g, int x, int y){
        g.drawImage(sprite.getImage(), x+xOffset, y+yOffset, null);
        stock.draw(g, x+xOffset, y+yOffset);
    }
    
    
    /**
     * Returns true if this mouseEvent clicks this stock.
     * @param e
     * @return 
     */
    @Override
    public boolean isClicked(MouseEvent e){
        if(Math.abs(tile.getXPos()+xOffset-GV.get().getCamera().getXPos()-e.getX()) < 9 
                && Math.abs( tile.getYPos()+yOffset-GV.get().getCamera().getYPos()-e.getY())<9){
            return true;
        }
        return false;
    }
    

    @Override
    public void mouseClicked(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
