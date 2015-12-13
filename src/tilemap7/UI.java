/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tilemap7;
import Tools.Sprite;
import Tools.SpriteStore;
import Tools.clickHandler;
import tilemap7.Buildings.Farm;
import tilemap7.Buildings.Mill;
import tilemap7.Buildings.House;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import tilemap7.Buildings.Building.UnBuildableException;
import tilemap7.Buildings.Bakery;

/**
 * Contains and draws elements like menu,radar, toolbar usw
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */





public class UI
{
    
    
    class BuildContainer extends Container implements clickHandler
    {
        Sprite farm;
        Sprite house;
        Sprite mill;
        Sprite bakery;
        Marker marker;
        

        
        BuildContainer()
        {
            super();
            farm = SpriteStore.get().getSprite("farm.png");
            house = SpriteStore.get().getSprite("house.png");
            mill = SpriteStore.get().getSprite("mühle0.png");
            shade = SpriteStore.get().getSprite("shade.png");
            bakery = SpriteStore.get().getSprite("bakery.png");
            marker = GV.get().getMarker();
            mousePosition = GV.get().getMousePosition();
            camera = GV.get().getCamera();
        }
        
        @Override
        void draw(Graphics2D g, int xOffset, int yOffset)
        {
            this.xOffset = xOffset;
            this.yOffset = yOffset;
            g.setColor(Color.WHITE);
            g.setFont(new Font(null, 2, 20));
            g.drawString("Farm", 40 + xOffset, 35 + yOffset);
            g.drawString("House", 104 + xOffset, 35 + yOffset);
            g.drawString("Mill", 168 + xOffset, 35 + yOffset);
            g.drawString("Bakery", 232 + xOffset, 35 + yOffset);
            drawIcon(g, farm, 40, 40);
            drawIcon(g, house, 104, 40);
            drawIcon(g, mill, 168, 40);
            drawIcon(g, bakery, 232,40);
            if(drawShade){
                drawShade(g);
            }
        }

        @Override
        void mouseClicked(int x, int y)
        {
            if(y > 40 && y < 80){
                if(x > 40 && x < 94){
                    GV.get().getUI().reserveNextClick(this);
                    drawShade = true;
                    entity_type = farm;
                }
                else{
                    if(x > 104 && x < 158){
                        GV.get().getUI().reserveNextClick(this);
                        drawShade = true;
                        entity_type = house;
                    }else{
                        if(x > 168 && x < 168+icon_background.getWidth()){
                            GV.get().getUI().reserveNextClick(this);
                            drawShade = true;
                            entity_type = mill;
                        }else{
                            if(x > 232 && x < 232+icon_background.getWidth()){
                                GV.get().getUI().reserveNextClick(this);
                                drawShade = true;
                                entity_type = bakery;
                            }
                        }   
                    }
                }
            }
        }






        @Override
        public void reservedClick(int x, int y) {
            Tile tile = GV.get().getTileMap().getTileByPosWithCamera(x, y);
            drawShade = false;
            try{
            if(entity_type.equals(farm)){
                tile.addEntity(new Farm(tile));
            }else
                if(entity_type.equals(house)){
                    tile.addEntity(new House(tile));
                }else{
                    if(entity_type.equals(mill)){
                        tile.addEntity(new Mill(tile));
                    }else{
                        if(entity_type.equals(bakery)){
                            tile.addEntity(new Bakery(tile));
                        }
                    }
                }
            }catch(UnBuildableException e){
                GV.get().getUI().showString("Cannot build here");
            }
        }
        
    }

    String printString;
    int printStringCounter;
    Font printStringFont;
    
    Sprite background;
    Graphics2D drawsheet;
    boolean getNextClick;
    clickHandler nextClickMaster;
    Marker marker;
    int yOffset;
    Container container;
    BuildContainer buildContainer;
    
    public UI(Graphics2D drawsheet, Marker marker)
    {
        GV.get().setUI(this);
        background = SpriteStore.get().getSprite("menu_background2.png");
        this.drawsheet = drawsheet;
        yOffset = GV.get().getYCameraSize();
        this.marker = marker;
        buildContainer = new BuildContainer();
        container = buildContainer;
        printStringFont = new Font(null, 2, 20);

    }

    public void draw()
    {
        drawsheet.drawImage(background.getImage(), 0, GV.get().getYCameraSize(), null);
        if(GV.get().getXRes() > background.getWidth()){
            drawsheet.drawImage(background.getImage(),background.getWidth() , GV.get().getYCameraSize(), null);
        }
        if(printString != null){
            drawsheet.setColor(Color.RED);
            drawsheet.setFont(printStringFont);
            int xOffset = GV.get().getStringLength(printString, printStringFont);
            drawsheet.drawString(printString, (GV.get().getXRes()-xOffset)/2, 40);
            
            printStringCounter--;
            if(printStringCounter == 0 )
                printString = null;
        }
        drawContainer();
    }

    public void drawContainer()
    {
        if(container != null)
            container.draw(drawsheet, 10, GV.get().getYCameraSize() + 10);
        else
            container = buildContainer;
    }

    private void drawComp(Sprite sprite, int x, int y)
    {
        drawsheet.drawImage(sprite.getImage(), x, GV.get().getYCameraSize() + y, null);
    }

    /**
     * Prints a string in the middle of the screen, for 100 loops (30ms each)
     * @param print printString
     */
    public void showString(String print){
        this.printString = print;
        printStringCounter = 100;
    }
    
    /**
     * Draw a string for a specific time at the middle of the screen
     * @param print print String
     * @param time number of loops that the string is visible
     */
    public void showString(String print, int time){
        this.printString = print;
        printStringCounter = time;
    }
    
    public void mouseClicked(MouseEvent e)
    {
        int x = e.getX();
        int y = e.getY();
        if(getNextClick)
        {
            nextClickMaster.reservedClick(x, y);
            nextClickMaster = null;
            getNextClick = false;
        } else
        if(y > yOffset && y < GV.get().getYRes())
        {
            container.mouseClicked(x - container.getXOffset(), y - container.getYOffset());
        } else
        {
            container = buildContainer;
            GV.get().getTileMap().mouseClicked(e);
        }
    }

    public void reserveNextClick(clickHandler c)
    {
        getNextClick = true;
        nextClickMaster = c;
    }

    public void mouseClicked(Entity entity)
    {
        container = entity.getContainer();
    }


}
