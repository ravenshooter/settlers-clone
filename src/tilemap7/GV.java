/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tilemap7;

import Tools.Mouse;
import Tools.MouseObject;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Point;
import java.awt.Toolkit;

/**
 * Write a description of class GameVariables here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GV
{
    private static GV instance = new GV();
    public static GV get(){
        return instance;
    }
    
    public int XRes = 1024;
    public int YRes = 786;
    public int XTiles = 80;
    public int YTiles = 40;
    public int XCameraSize = XRes;
    public int YCameraSize = YRes - 200;
    int xTileSize = 50;
    int yTileSize = 50;
    private TileMap tileMap;
    private Camera camera;
    private Marker marker;
    private UI ui;
    private GUI.GameWindow gameWindow;
    
    public static boolean drawLittleManMissionList = false;
    
    private Mouse mouse;
    
    private Point mousePosition =  new Point(0,0);
    /**
     * Constructor for objects of class GameVariables
     */
    private GV()
    {
        mouse = new Mouse();
    }
    public int getXRes(){
        return XRes;
    }
    public int getYRes(){
        return YRes;
    }
    public int getXTiles(){
        return XTiles;
    }
    public int getYTiles(){
        return YTiles;
    }
    public int getXTileSize(){
        return xTileSize;
    }
    public int getYTileSize(){
        return yTileSize;
    }
    public void setCamera(Camera c){
        camera = c;
    }
    public Camera getCamera(){
        return camera;
    }
    public int getXCameraSize(){
        return XCameraSize;
    }
    public int getYCameraSize(){
        return YCameraSize;
    }
    public void setTileMap(TileMap t){
        tileMap = t;
    }
    public TileMap getTileMap(){
        return tileMap;
    }
    public void setMarker(Marker m){
        marker = m;
    }
    public Marker getMarker(){
        return marker;
    }
    public void setUI(UI ui){
        this.ui = ui;
    }
    public UI getUI(){
        return ui;
    }

    public GUI.GameWindow getGameWindow() {
        return gameWindow;
    }

    public void setGameWindow(GUI.GameWindow gameWindow) {
        this.gameWindow = gameWindow;
    }
    
    
    public Point getMousePosition(){
        return mousePosition;
    }


    public Mouse getMouse() {
        return mouse;
    }

    
    
    
    
    public int getStringLength(String str, Font font){
        FontMetrics metrics = Toolkit.getDefaultToolkit().getFontMetrics(font);
        return metrics.stringWidth(str);
    }
    
    static double getAngle(int actX1, int actY1, int goToX1, int goToY1){
        double goToX = (double)goToX1;
        double goToY = (double)goToY1;
        double actX = (double)actX1;
        double actY = (double)actY1;
        actY = actY * (-1);
        goToY = goToY * (-1);
        goToX = goToX - actX;
        goToY = goToY - actY;
        actX = 0;
        actY = 0;
        double dir;
        Math.atan(2);
        if (goToX - actX > 0) {
            dir = Math.atan((goToY - actY)/ (goToX - actX));
            if(dir < 0){
                dir = dir + 2*Math.PI;
            }
        } else {
            if (goToX - actX < 0) {
                dir = Math.atan((goToY - actY) /( goToX - actX)) + Math.PI;
            } else {
                if (goToY - actY < 0) {  //falls x = 0 ist atan nicht definiert
                    dir = 3 * Math.PI / 2;
                } else {
                    dir = Math.PI / 2;
                }
            }
        }
        return dir;
    }
    
    static double getAngleDeg(int actX1, int actY1, int goToX1, int goToY1){
        return getAngle(actX1, actY1, goToX1, goToY1)/Math.PI*180;
    }
}
