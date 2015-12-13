/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tilemap7;

import GUI.EntityPanel;
import GUI.SouthPanel;
import Tools.Sprite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
/**
 * Write a description of class Entitiy here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.awt.*;
import java.awt.event.MouseEvent;

public abstract class Entity
{
    public class EntityContainer extends Container
    {

        @Override
        public void draw(Graphics2D g, int xOffset, int yOffset)
        {
            this.xOffset = xOffset;
            this.yOffset = yOffset;
            g.setColor(Color.BLACK);
            g.fillRect(xOffset, yOffset, 500, 100);
            drawIcon(g, entity.getSprite(), 10, 30);
            g.setColor(Color.WHITE);
            g.setFont(new Font(null, 2, 20));
            g.drawString(entity.getClass().getName(), 20, yOffset + 22);
            g.setFont(new Font(null, 2, 10));
            for(int i = 0; i < attributes.length; i++)
                if(attributeNames[i] != null)
                    g.drawString((new StringBuilder()).append(attributeNames[i]).append(": ").append(attributes[i]).toString(), 100, yOffset + 22 + i * 20);

            g.setColor(Color.BLACK);
        }

        @Override
        public void mouseClicked(int i, int j)
        {
        }

        public void addAttribute(String name, String attribute)
        {
            int i = 0;
            for(boolean success = false; i < attributes.length && !success; i++)
                if(attributes[i] == null)
                {
                    attributes[i] = attribute;
                    attributeNames[i] = name;
                    success = true;
                }

        }

        public boolean editAttribute(String name, String newValue)
        {
            for(int i = 0; i < attributes.length; i++)
                if(attributeNames[i] != null && attributeNames[i].compareTo(name) == 0)
                {
                    attributes[i] = newValue;
                    return true;
                }

            return false;
        }

        public String attributes[];
        public String attributeNames[];
        public Entity entity;

        public EntityContainer(Entity entity)
        {
            super();
            
            attributes = new String[6];
            attributeNames = new String[6];
            this.entity = entity;
        }
    }


    protected EntityPanel panel;
    public int xPos;
    public int yPos;
    public boolean isClickable;
    public EntityContainer entityContainer;
    public Sprite sprite;
    public Tile tile;
    private boolean selected;
    
    
    public Entity(Tile tile)
    {
        isClickable = true;
        this.tile = tile;
        if(tile != null)
        {
            xPos = tile.getXNr() * GV.get().getXTileSize();
            yPos = tile.getYNr() * GV.get().getYTileSize();
        } else
        {
            xPos = 0;
            yPos = 0;
        }
    }

    public void draw(Graphics g1, int i, int j)
    {
    }

    public void doLogic(int i)
    {
    }

    public abstract void mouseClicked(MouseEvent e);

    
    /**
     * Returns if this mouseEvent clicks the entity. Returns isCLickable value if not overwritten
     * 
     * @param e the current mouseEvent
     * @return
     */
    public boolean isClicked(MouseEvent e)
    {
        return isClickable;
    }

    public Sprite getSprite()
    {
        return sprite;
    }

    public int getXPos()
    {
        return xPos;
    }

    public int getYPos()
    {
        return yPos;
    }

    public Point getPos()
    {
        return new Point(xPos, yPos);
    }

    Container getContainer()
    {
        return entityContainer;
    }
    
    public EntityPanel getPanel(){
        return panel;
    }

    public Tile getTile()
    {
        return tile;
    }

    public String getMainAttribute()
    {
        return null;
    }

    public String getMainAttributeName()
    {
        return null;
    }
    
    public void setUnselected(){
        selected = false;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    

    


}
