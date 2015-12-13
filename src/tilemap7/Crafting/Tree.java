/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tilemap7.Crafting;

import Tools.SpriteStore;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import tilemap7.Buildings.Stock;
import tilemap7.CarryObjects.Wood;
import tilemap7.Entity;
import tilemap7.LittleMan;
import tilemap7.Tile;

/**
 *
 * @author Steve
 */
public class Tree extends Entity implements craftable{

    int x, y;
    Stock outStock;
    private int timer;
    private boolean craftable;
    
    public Tree(Tile tile, int x, int y) {
        super(tile);
        sprite = SpriteStore.get().getSprite("tree.png");
        this. x = x-tile.getXPos();
        this. y = y-tile.getYPos();
        outStock = new Stock(new Point(x,y), 1);
        timer = 3900;
    }


    @Override
    public void doLogic(int time) {
        if (timer < 4000) {
            timer++;
        }
        if(timer == 4000){
            craftable = true;
        }
    }

    @Override
    public void draw(Graphics g, int x ,int y){
        g.setColor(new Color(139,69,19));
        g.fillOval(x+this.x+2, y+this.y+2, 5, 5);
        g.setColor(Color.BLACK);
        g.drawOval(x+this.x, y+this.y, timer/400+1, timer/400+1);
        g.setColor(new Color(53, 100, 5));
        g.fillOval(x+this.x, y+this.y, timer/400, timer/400);
        //g.drawString(timer+"", x+this.x, y+this.y);
    }
    
    
    @Override
    public void mouseClicked() {
        //
    }

    
    @Override
    public Stock getOutStock() {
        return outStock;
    }

    @Override
    public boolean craft(LittleMan man) {
        if(isCraftable()){
            timer -= 20;
            if(timer < 0){
                outStock.reportDropDown();
                outStock.add(new Wood());
                craftable = false;
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean isCraftable(){
        return craftable;
    }

    @Override
    public Point getPosition() {
        return new Point(tile.getXPos()+x,tile.getYPos()+y);
    }
    
    
    
}
