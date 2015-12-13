/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Tools.Sprite;
import Tools.SpriteStore;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import tilemap7.Buildings.Bakery;
import tilemap7.Buildings.Farm;
import tilemap7.Buildings.House;
import tilemap7.Buildings.Lumberjack;
import tilemap7.Buildings.Mill;
import tilemap7.Buildings.Storage;
import tilemap7.Buildings.WatchTower;
import tilemap7.GV;


/**
 * Panel for the list of buildings. Contains a button for every available building.
 * @author Steve
 */
public class BuildPanel extends SouthPanel {
    
    boolean drawShade;
    Sprite entity_type;
    
    public BuildPanel(){
        this.setLayout(new GridLayout(2,10));
        //this.setPreferredSize(new Dimension(GV.get().getXRes()-100,100));
        this.setBackground(Color.DARK_GRAY);
        
        addHouseButton();
        addFarmButton();
        addMillButton();
        addBakeryButton();
        addWatchTowerButton();
        addStorageButton();
        addLumberjackButton();
        
        for(int i = 0; i < 20; i++){
            this.add(new JLabel());
        }
    }
    
    public final void addHouseButton(){
        JButton button = new JButton();
        //button.setBorderPainted(false);
        button.addActionListener(new BuildButtonListener(new House()));
        button.setToolTipText("Build a House with 4 people living in it.");
        button.setIcon(new ImageIcon(SpriteStore.get().getSprite("house.png").getImage()));        
        this.add(button);
    }
    
    public final void addFarmButton(){
        JButton button = new JButton();
        //button.setBorderPainted(false);
        button.addActionListener(new BuildButtonListener(new Farm()));
        button.setToolTipText("Build a Farm to create wheat.");
        button.setIcon(new ImageIcon(SpriteStore.get().getSprite("farm.png").getImage()));        
        this.add(button);
    }
    
    public final void addMillButton(){
        JButton button = new JButton();
        //button.setBorderPainted(false);
        button.addActionListener(new BuildButtonListener(new Mill()));
        button.setToolTipText("Build a Mill to produce flour from wheat.");
        button.setIcon(new ImageIcon(SpriteStore.get().getSprite("mühle0.png").getImage()));        
        this.add(button);
    }
    
    public final void addBakeryButton(){
        JButton button = new JButton();
        //button.setBorderPainted(false);
        button.addActionListener(new BuildButtonListener(new Bakery()));
        button.setToolTipText("Build a Bakery to produce bread from flour.");
        button.setIcon(new ImageIcon(SpriteStore.get().getSprite("bakery.png").getImage()));
        this.add(button);
    }
    
     public final void addWatchTowerButton(){
        JButton button = new JButton();
        //button.setBorderPainted(false);
        button.addActionListener(new BuildButtonListener(new WatchTower()));
        button.setToolTipText("Build a watchtower to protect your buildings.");
        button.setIcon(new ImageIcon(SpriteStore.get().getSprite("watchtower.png").getImage()));
        this.add(button);
     }  
     
    public final void addStorageButton(){
        JButton button = new JButton();
        //button.setBorderPainted(false);
        button.addActionListener(new BuildButtonListener(new Storage()));
        button.setToolTipText("Build a storage room.");
        button.setIcon(new ImageIcon(SpriteStore.get().getSprite("storage.png").getImage()));
        this.add(button);
    }
     
        
    public final void addLumberjackButton(){
        JButton button = new JButton();
        //button.setBorderPainted(false);
        button.addActionListener(new BuildButtonListener(new Lumberjack()));
        button.setToolTipText("Build a Lumberjack.");
        button.setIcon(new ImageIcon(SpriteStore.get().getSprite("lumberjack.png").getImage()));
        this.add(button);
    }  
        

    
}
