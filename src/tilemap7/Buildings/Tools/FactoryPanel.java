/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tilemap7.Buildings.Tools;

import Tools.Sprite;
import Tools.SpriteStore;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import tilemap7.Buildings.Building;

/**
 * Ein Panel für ein Building mit integrieretem workerPanel
 * @author Steve
 */
public class FactoryPanel extends BuildingPanel{
    
    JButton sendHomeButton;
    
    public FactoryPanel(String name, Sprite sprite, Building buildingn){
        super(name+":",sprite);
        entity = buildingn;
        workersPanel = new WorkersPanel((Building)entity,100,40,140,20);
        this.add(workersPanel);
        
        sendHomeButton = new JButton(new ImageIcon(SpriteStore.get().getSprite("cross.png").getImage()));
        sendHomeButton.setBounds(760, 80, 20, 20);
        sendHomeButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                ((Building)entity).sendAllWorkerHome();
            }
        });
        this.add(sendHomeButton);
    }
    
    public FactoryPanel(String name, Sprite sprite, WorkersPanel workersPanel){
        super(name+":",sprite);
        this.workersPanel = workersPanel;
        this.add(workersPanel);
    }
    
    
    
    
}
