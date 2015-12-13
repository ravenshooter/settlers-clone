/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import tilemap7.Camera;
import tilemap7.GV;
import tilemap7.Tile;
import tilemap7.TileMap;

/**
 *
 * @author Steve
 */
public class MiniMap extends JPanel implements Runnable{

    
    DrawField drawField;
    
    public MiniMap(){
        super();
        this.setLayout(new BorderLayout());
        this.setBackground(Color.DARK_GRAY);
        this.setPreferredSize(new Dimension(164,164));
        this.setBorder(BorderFactory.createEtchedBorder());
        drawField = new DrawField();
        this.add(drawField,BorderLayout.CENTER);

        new Thread(this).start();
    }

    @Override
    public void run() {
        drawField.drawMap();
        int i = 0;
        while(true){
            drawField.draw();
            i++;
            if(i%10==0){
                drawField.drawMap();
            }
            try{
                Thread.sleep(30);
            }catch(InterruptedException e){}
        }
                
    }
    
    public void createBuffer(){
        drawField.createBuffer();
    }
    
    
    class DrawField extends Canvas{
        BufferedImage map;
        BufferStrategy strategy;
        
        public DrawField(){
            this.setPreferredSize(new Dimension(160,160));
            map = new BufferedImage(160, 160, BufferedImage.TYPE_INT_RGB);
            setIgnoreRepaint(true);
        }
        
        public void createBuffer(){
            createBufferStrategy(2);
            strategy = getBufferStrategy();
        }
        
        public void drawMap(){
            Graphics g = map.getGraphics();
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
                    }else if(tile.getType().equals("forest")){
                        g.setColor(new Color(53, 100, 5)); 
                    }else{
                        g.setColor(Color.BLACK);
                    }
                    if(tile.getBuilding()!= null){
                        g.setColor(Color.WHITE);
                    }
                    g.fillRect((int) scaleXPoint(tile.getXPos()), (int) scaleYPoint(tile.getYPos()), (int) scaleXPoint(tileSizeX), (int) scaleYPoint(tileSizeY));
                }
            }
        }
        
        
        public void draw() {
            if(strategy == null){
                return;
            }
            Graphics g = strategy.getDrawGraphics();
            g.setColor(Color.BLACK);
            g.drawRect(0, 0, map.getWidth(), map.getHeight());
            g.drawImage(map, 0, 0, null);
            
            //draw marker
            if(GV.get().getMarker() != null){
                double xMarker = (double)GV.get().getMarker().getXPos();
                double yMarker = (double)GV.get().getMarker().getYPos();
                xMarker = scaleXPoint(xMarker);
                yMarker = scaleYPoint(yMarker);
                g.setColor(Color.red);
                g.drawOval((int)xMarker-1, (int)yMarker-1,(int) scaleXPoint(GV.get().getXTileSize())+1, (int) scaleYPoint(GV.get().getYTileSize()+1));
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
            strategy.show();
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
        
    }
    
    
    
    
    
    
}
