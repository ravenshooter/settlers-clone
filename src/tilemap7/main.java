/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tilemap7;
import GUI.Drawsheet;
import java.awt.Graphics2D;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Steve
 */
public class main implements Runnable{
    GUI.GameWindow gameWindow;
    UI ui;
    TileMap tileMap;
    int time;
    Graphics2D canvas;
    Drawsheet drawsheet;
    boolean running = true;
    
    public main()
    {
        tileMap = new TileMap(GV.get().getXTiles(),GV.get().getYTiles(),GV.get().getXTileSize(),GV.get().getYTileSize());
        for(int i = 0; i < 80; i++){
            for(int j = 0;j<12;j++){
                tileMap.add(i,j,"water");
            }
            tileMap.add(i,12,"grass");
            tileMap.add(i,13,"grass");
            tileMap.add(i,14,"grass");
            //tileMap.add(i,36,"water",0.0);
            //tileMap.add(i,37,"water",0.0);
            
            tileMap.add(i,38,"water");
            tileMap.add(i,39,"water");
        }
        for(int i = 0; i < 40; i++){
            tileMap.add(0,i,"water");
            tileMap.add(1,i,"water");
            //tileMap.add(2,i,"water",0.0);
            //tileMap.add(3,i,"water",0.0);
        }
        for(int i = 0; i < 40; i++){
            //tileMap.add(76,i,"water",0.0);
            //tileMap.add(77,i,"water",0.0);
            tileMap.add(78,i,"water");
            tileMap.add(79,i,"water");
        }
        
        for(int i = 20; i < 30; i++){
            //tileMap.add(76,i,"water",0.0);
            //tileMap.add(77,i,"water",0.0);
            tileMap.add(20,i,"rocks2");
            tileMap.add(21,i,"rocks2");
            tileMap.add(22,i,"rocks2");
            tileMap.add(23,i,"rocks2");
            tileMap.add(24,i,"rocks2");
            tileMap.add(25,i,"rocks2");
            
            
        }
        
        for(int i = 20; i < 30; i++){
            //tileMap.add(76,i,"water",0.0);
            //tileMap.add(77,i,"water",0.0);
            tileMap.add(30,i,"forest");
            tileMap.add(31,i,"forest");
            tileMap.add(32,i,"forest");
            tileMap.add(33,i,"forest");
            tileMap.add(34,i,"forest");
            tileMap.add(35,i,"forest");
            
            
        }
        
        tileMap.finishMap();
        //tileMap.addGroundType("desert", 50, 3);
        
        
        gameWindow = new GUI.GameWindow();
        canvas = gameWindow.getDrawsheet().getDrawsheet();
        Camera.get().setUpCamera(tileMap,canvas,0,0);
        drawsheet = gameWindow.getDrawsheet();
        Marker marker = new Marker();
        ui = new UI(canvas,marker);
        long timen = 0;
        time = 0;
        Thread t = new Thread(this);
        t.start();
        while(true){
            long start = System.currentTimeMillis();
            long startn = System.nanoTime();
            time++;
            doLogic();
            long end = System.currentTimeMillis();
            long endn = System.nanoTime();
            timen = (endn-startn)/100000;
            //canvas.drawString("Looptime: " +String.valueOf(timen),40,40);
            long loopTime = (end-start);
            //System.out.println(endn-startn);
            if(loopTime<30)
                synchronized(this){try{this.wait(30-(loopTime));}catch(InterruptedException e){}}
            else{
                System.out.println("TimeLimit exceeded");
            }
        }
        
            
        }
        void doLogic(){
            for(int yNr = 0; yNr <GV.get().getYTiles();yNr++)
            {
                for(int xNr = 0; xNr < GV.get().getXTiles();xNr++){
                    tileMap.getTile(xNr,yNr).doLogic(time);
                }
            }
            
        
    }

    @Override
    public void run(){
        long start;
        long end = System.currentTimeMillis();
        int wait = 0;
        while(running){
            start = end + wait;
            canvas = drawsheet.getDrawsheet();
            Camera.get().cameraLoop();
            //ui.draw();
            GV.get().getMouse().draw(canvas);
            canvas.dispose();
            drawsheet.flipBuffer();
            end = System.currentTimeMillis();
            wait = 33 - (int)(end - start);
            if(wait < 1){
                wait = 1;
            }
            synchronized (this) {
                try {
                    this.wait(wait);
                } catch (InterruptedException ex) {
                    Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static void main(String[]args){
        new main();
    } 
}
