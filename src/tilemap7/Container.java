/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tilemap7;
import Tools.Sprite;
import Tools.SpriteStore;
import java.awt.Graphics2D;
import java.awt.Point;
import javax.swing.JPanel;
    /**
     * In das UI eingelagertes Element (Bauleiste, Gebaudeuebersicht,usw)
     */
    
    abstract class Container{
        public Sprite icon_background;
        public int xOffset;
        public int yOffset;
        
        Sprite entity_type;
        
        public boolean drawShade;
        Point mousePosition;
        public Camera camera;
        Sprite shade;
        
        
        Container(){
            //this.yOffset = yOffset+GV.get().getYCameraSize();
            icon_background = SpriteStore.get().getSprite("icon_background1.png");
            camera = GV.get().getCamera();
            mousePosition = GV.get().getMousePosition();
            shade = SpriteStore.get().getSprite("shade.png");
            
        }
        
        abstract void draw(Graphics2D g,int xOffset,int yOffset);
        abstract void mouseClicked(int x, int y);
        private void drawComp(Graphics2D g,Sprite sprite,int x,int y){
            g.drawImage(sprite.getImage(),x+xOffset,y+yOffset,null);
        }
    
        public void drawIcon(Graphics2D g,Sprite sprite,int x,int y){
            drawComp(g,icon_background,x,y);
            drawComp(g,sprite,x+2,y+2);
        }
        
        public int getXOffset(){
            return xOffset;
        }
        public int getYOffset(){
            return yOffset;
        }
        
        
        
        public void setShade(Sprite shade){
            entity_type = shade;
            drawShade = true;
        }
        
        public void removeShade(Sprite shade){
            if(entity_type.equals(shade))
                drawShade = false;
        }
    


        
        protected void drawShade(Graphics2D g){
            if (entity_type != null) {
                int y = (camera.getYPos() + (int) mousePosition.getY()) / GV.get().getYTileSize();
                int x = (camera.getXPos() + (int) mousePosition.getX()) / GV.get().getXTileSize();
                y = y * GV.get().getYTileSize();
                x = x * GV.get().getXTileSize();
                g.drawImage(entity_type.getImage(), x - camera.getXPos(), y - camera.getYPos(), null);
                g.drawImage(shade.getImage(), x - camera.getXPos(), y - camera.getYPos(), null);
            }
        }
    
    
    
}

