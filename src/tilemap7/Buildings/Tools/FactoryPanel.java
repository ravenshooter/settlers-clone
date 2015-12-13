/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tilemap7.Buildings.Tools;

import tilemap7.Buildings.Building;
import tilemap7.LittleMan;
import Tools.Sprite;
import Tools.SpriteStore;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * Ein Panel für ein Building mit integrieretem workerPanel
 * @author Steve
 */
public class FactoryPanel extends BuildingPanel{
    
    WorkersPanel workersPanel;
    JButton sendHomeButton;
    
    public FactoryPanel(String name, Sprite sprite, Building buildingn){
        super(name+":",sprite);
        building = buildingn;
        workersPanel = new WorkersPanel(building,100,40,140,20);
        this.add(workersPanel);
        
        sendHomeButton = new JButton(new ImageIcon(SpriteStore.get().getSprite("cross.png").getImage()));
        sendHomeButton.setBounds(760, 80, 20, 20);
        sendHomeButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                building.sendAllWorkerHome();
            }
        });
        this.add(sendHomeButton);
    }
    
    public FactoryPanel(String name, Sprite sprite, WorkersPanel workersPanel){
        super(name+":",sprite);
        this.workersPanel = workersPanel;
        this.add(workersPanel);
    }
    
    public void addWorkerToButton(LittleMan littleMan){
        int i = 0;
        while(!workersPanel.addWorkerToButton(littleMan, i)){
            i++;
        }
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
