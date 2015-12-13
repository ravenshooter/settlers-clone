/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import tilemap7.Buildings.House;
import tilemap7.Camera;
import tilemap7.GV;
import tilemap7.LittleMan;
import tilemap7.Tile;
import tilemap7.TileMap;

/**
 *
 * @author Steve
 */
public class Map extends JPanel{
    
    boolean isRunning;
    Drawsheet drawSheet;
    final Painter painter;
    
    public Map(){

        this.setBounds(100, 100, GV.get().getXRes()-200,GV.get().getYRes()-200);
        this.setLocation(100, 100);
        this.setLayout(null);
        this.setBackground(Color.LIGHT_GRAY);
        this.setFocusable(false);
        
        drawSheet = new Drawsheet(0,0, this.getWidth(),this.getHeight());
        this.add(drawSheet);
        if(drawSheet == null){
            System.out.println("drawsheet null");
        }
        painter = new Painter(drawSheet);
        painter.start();
        
        
        isRunning = true;
    }
    

    @Override
    public void setVisible(boolean visible){
        super.setVisible(visible);
        if(painter != null){
            painter.setVisible(visible);
            if(visible){
                synchronized(painter){
                    painter.notify();
                }
            }
        }
    }
    
    
    public void createBuffer(){
        this.setVisible(true);
        drawSheet.createBuffer();
        this.setVisible(false);
    }

    
    class Painter extends Thread{
        
        BufferedImage map;
        Drawsheet drawSheet;
        boolean visible;

        public Painter(Drawsheet drawSheet) {
            this.drawSheet = drawSheet;
            map = new BufferedImage(drawSheet.xSize, drawSheet.ySize, BufferedImage.TYPE_INT_RGB);
        }
        
        
        
        
        @Override
        public void run() {
            while(isRunning){
                if(visible){
                    draw();
                }else{
                    synchronized(this){
                        try{
                            wait();
                        }catch(InterruptedException e){
                            
                        }
                    }
                }
            }
        }
        
        
       public void drawMap(Graphics g){
            TileMap tileMap = GV.get().getTileMap();
            while(tileMap == null ){
                try{
                    Thread.sleep(10);
                    tileMap = GV.get().getTileMap();
                }catch(InterruptedException e){}
            }
            while(!tileMap.isInitialized()){
                try{
                    Thread.sleep(10);
                }catch(InterruptedException e){}
            }
            int tileSizeX = GV.get().getXTileSize();
            int tileSizeY = GV.get().getYTileSize();
            for (int i = 0; i < GV.get().getYTiles(); i++) {
                for (int j = 0; j < GV.get().getXTiles(); j++) {
                    Tile tile = tileMap.getTile(j, i);
                    if(tile.getType().equals("grass")){
                        g.setColor(new Color(53, 150, 5));
                    }else if(tile.getType().equals("water")){
                        g.setColor(Color.BLUE);
                    }else if(tile.getType().equals("desert")){
                        g.setColor(Color.YELLOW);    
                    }else{
                        g.setColor(Color.BLACK);
                    }
                    if (tile.getBuilding() != null) {
                        g.setColor(Color.WHITE);
                        if (tile.getBuilding() instanceof House) {
                            House h = (House) tile.getBuilding();
                            for (LittleMan l : h.getLittleMan()) {
                                g.fillOval((int) scaleXPoint(l.getXPos()), (int) scaleYPoint(l.getYPos()), 4, 4);
                            }
                        }
                    }
                    g.fillRect((int) scaleXPoint(tile.getXPos()), (int) scaleYPoint(tile.getYPos()), (int) scaleXPoint(tileSizeX)+1, (int) scaleYPoint(tileSizeY)+1);
                    
                    
                }
            }
            
            for (int i = 0; i < GV.get().getYTiles(); i++) {
                for (int j = 0; j < GV.get().getXTiles(); j++) {
                    Tile tile = tileMap.getTile(j, i);
                    
                    if (tile.getBuilding() != null) {
                        g.setColor(Color.WHITE);
                        g.fillRect((int) scaleXPoint(tile.getXPos()), (int) scaleYPoint(tile.getYPos()), (int) scaleXPoint(tileSizeX)+1, (int) scaleYPoint(tileSizeY)+1);
                        if (tile.getBuilding() instanceof House) {
                            House h = (House) tile.getBuilding();
                            for (LittleMan l : h.getLittleMan()) {
                                g.setColor(Color.red);
                                g.fillOval((int) scaleXPoint(l.getLocation().x), (int) scaleYPoint(l.getLocation().y), 6, 6);
                            }
                        }
                    }
                    
                    
                    
                }
            }
        }
        
        
        public void draw() {
            
            
            if(drawSheet.getDrawsheet() == null){
                return;
            }
            Graphics g = drawSheet.getDrawsheet();
            g.setColor(Color.BLACK);
            g.drawRect(0, 0, map.getWidth(), map.getHeight());
            
            
            drawMap(g);
            
            //draw marker
            if(GV.get().getMarker() != null){
                double xMarker = (double)GV.get().getMarker().getXPos();
                double yMarker = (double)GV.get().getMarker().getYPos();
                xMarker = scaleXPoint(xMarker);
                yMarker = scaleYPoint(yMarker);
                g.setColor(Color.red);
                g.drawRect((int)xMarker-1, (int)yMarker-1,(int) scaleXPoint(GV.get().getXTileSize())+1, (int) scaleYPoint(GV.get().getYTileSize()+1));
            }
            
            //draw camera frame
            Camera camera = GV.get().getCamera();
            if(camera != null){
                double xCamera = (double)camera.getXPos();
                double yCamera = (double)camera.getYPos();
                xCamera = scaleXPoint(xCamera);
                yCamera = scaleYPoint(yCamera);
                g.setColor(Color.WHITE);
                g.drawRect((int)xCamera, (int)yCamera, (int)scaleXPoint(GV.get().getXCameraSize()), (int)scaleYPoint(GV.get().getYCameraSize()));
            }
            drawSheet.flipBuffer();
        }
        
        
        /**
         * Scales a point from xRes to miniMap res
         * @param x
         * @return 
         */
        private double scaleXPoint(double x){
            return (x*map.getWidth())/(GV.get().getXTiles()*GV.get().getXTileSize());   
        }
        
        /**
         * Scales a point from yRes to miniMap res
         * @param y
         * @return 
         */
        private double scaleYPoint(double y){
            return (y*map.getHeight())/(GV.get().getYTiles()*GV.get().getYTileSize());   
        }

        
        public void setVisible(boolean visible) {
            this.visible = visible;
        }
        
        
        
    }
}

