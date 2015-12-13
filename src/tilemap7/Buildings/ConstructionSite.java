/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tilemap7.Buildings;

import Tools.Sprite;
import Tools.SpriteStore;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import tilemap7.Buildings.Building.UnBuildableException;
import tilemap7.Buildings.Tools.BuildingPanel;
import tilemap7.Buildings.Tools.Stock;
import tilemap7.CarryObjects.CarryObject;
import tilemap7.Crafting.craftable;
import tilemap7.Entity;
import tilemap7.GV;
import tilemap7.LittleMan;
import tilemap7.Tile;

/**
 *
 * @author Steve
 */
public class ConstructionSite extends Entity implements craftable{

    private int state;
    private boolean craftable;
    private Stock stock;
    private Stock requierments;
    
    private Building building;
    private Sprite[] sprites;
    
    
    public ConstructionSite(Tile tile, Building building) {
        super(tile);
        this.building = building;

        requierments = building.getRequirements();
        craftable = true;
        state = 0;
        stock = new Stock(new Point(tile.getXPos()+25,tile.getYPos()+25), 10);
        sprite = SpriteStore.get().getSprite("constructionsite0.png");
        sprites = new Sprite[3];
        for(int i = 0 ; i < 3; i++){
            sprites[i] = SpriteStore.get().getSprite("constructionsite"+i+".png");
        }
        
        panel = new BuildingPanel("Construction Site", sprites[1]);
        
        
        JTextArea explanation = new JTextArea("Send workers from a house here to build up the place or build a Construction Center nearby." +System.lineSeparator()+ 
                "Requiered: " + requierments.getTypes());
        explanation.setLineWrap(true);
        explanation.setWrapStyleWord(true);
        explanation.setForeground(Color.red);
        explanation.setBackground(Color.DARK_GRAY);
        explanation.setBounds(10,10,300,100);
        panel.add(explanation);
    }



    


    @Override
    public boolean craft(LittleMan man) {
        state++;
        if(state == 299){
            craftable = false;
            tile.remove(this);
            try {
                building.addBuilding(tile);
            } catch (UnBuildableException ex) {
                Logger.getLogger(ConstructionSite.class.getName()).log(Level.SEVERE, null, ex);
            }
            return true;
        }else{
            return false;
        }
    }
    
    
    @Override
    public void draw(Graphics g, int x, int y){
        g.drawImage(sprites[state/100].getImage(), x, y, null);
        stock.draw(g, x+25, y+25);
    }

    @Override
    public Stock getOutStock() {
        return stock;
    }
    
    public Stock getRequierments(){
        Stock returnStock = new Stock(requierments.getSize());
        for(String type : requierments.getTypes()){
            for(int i = 0; i < Math.max(0 ,requierments.howMany(type) - stock.howMany(type));i++){
                returnStock.add(requierments.get(type));
            }
        }
        return returnStock;
    }
    

    @Override
    public boolean isCraftable() {
        for(String type : requierments.getTypes()){
            if(requierments.howMany(type) > stock.howMany(type)){
                System.err.println("Not enough " + type);
                return false;
            }
        }
        return true;
    }

    @Override
    public Point getPosition() {
        return tile.getCenter();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        GV.get().getGameWindow().setSouthPanel(panel);
    }
    
    
}
