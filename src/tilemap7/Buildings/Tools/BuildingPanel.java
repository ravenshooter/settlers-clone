/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tilemap7.Buildings.Tools;

import GUI.EntityPanel;
import GUI.SouthPanel;
import Tools.Sprite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import tilemap7.Buildings.Building;
import tilemap7.GV;
import tilemap7.LittleMan;

/**
 *
 * @author Steve
 */
public class BuildingPanel extends EntityPanel{
    
    
    
    WorkersPanel workersPanel;
    
    /**
     * Erstellt ein blankes Panel für ein Building mit Layout null und Höhe 80
     */
    public BuildingPanel(Building building){
        super(building);
        workersPanel = new WorkersPanel(building,100,40,140,20);
        this.add(workersPanel);
    }

    /**
     * Erstellt ein Panel mit Building Name und Bild auf der linken Seite des Panels
     * @param name Name des Buildings
     * @param sprite Bild des Buildings
     */
    public BuildingPanel(String name, Sprite sprite) {
        super(name,sprite);
    }
    

    
    
    public void addWorkerToButton(LittleMan littleMan){
        workersPanel.addWorkerToButton(littleMan);
    }
    
    public LittleMan removeWorkerFromButton(LittleMan littleMan){
        int i = 0;
        while(!workersPanel.getWorkerButtons()[i].getWorker().equals(littleMan)){
            i++;
        }
        LittleMan returner = workersPanel.getWorkerButtons()[i].getWorker();
        workersPanel.getWorkerButtons()[i].setWorker(null);
        workersPanel.getWorkerButtons()[i].setBackground(new JButton().getBackground());
        return returner;
    }
    
    public void setBoundsWorkersPanel(int x, int y, int width, int height){
        workersPanel.setBounds(x, y, width, height);
    }
    
    public void setSizeWorkersPanel(int width, int height){
        workersPanel.setBounds(workersPanel.getBounds().x,workersPanel.getBounds().y,width+workersPanel.getWorkerLabelWidth(),height);
    }
    
    public void setLayoutWorkersPanel(int rows, int collums){
        workersPanel.setLayoutWorkerButtons(rows, collums);
    }
    
    public void workerArrived(LittleMan littleMan){
        workersPanel.colorButton(littleMan, Color.CYAN);
    }
    
    public void workerLeft(LittleMan littleMan){
        workersPanel.colorButton(littleMan, new JButton().getBackground());
    }
    
    
    

}
