/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tilemap7.Buildings.Tools;

import GUI.SouthPanel;
import Tools.Sprite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import tilemap7.Buildings.Building;
import tilemap7.GV;

/**
 *
 * @author Steve
 */
public class BuildingPanel extends SouthPanel{
    
    Building building;
    
    /**
     * Panel with all content on
     */
    private JPanel centerPanel;
    
    /**
     * Panel for the icon, in west of Building Panel
     */
    JPanel iconPanel;
    
    /**
     * Erstellt ein blankes Panel für ein Building mit Layout null und Höhe 80
     */
    public BuildingPanel(Building building){
        this.building = building;
        initialize();
    }

    /**
     * Erstellt ein Panel mit Building Name und Bild auf der linken Seite des Panels
     * @param name Name des Buildings
     * @param sprite Bild des Buildings
     */
    public BuildingPanel(String name, Sprite sprite) {
        initialize();
        setIcon(name, sprite);
    }
    
    
    private void initialize(){
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(GV.get().getXRes(), 80));
        this.setBackground(Color.DARK_GRAY);
        this.setForeground(Color.WHITE); 
        
        iconPanel = new JPanel(new BorderLayout());
        iconPanel.setBounds(10, 2, 70, 80);
        this.add(iconPanel,BorderLayout.WEST);
        
        centerPanel = new JPanel(new BorderLayout());
        centerPanel.setLayout(null);
        centerPanel.setBackground(Color.DARK_GRAY);
        centerPanel.setForeground(Color.WHITE);
        centerPanel.setBorder(BorderFactory.createLineBorder(Color.white));
        this.add(centerPanel,BorderLayout.CENTER);
    }
    
    private void setIcon(String name, Sprite sprite){
        
        
        iconPanel.setBorder(BorderFactory.createLineBorder(Color.white));
        iconPanel.setOpaque(false);
        
        JLabel label = new JLabel(name);
        label.setForeground(Color.WHITE);
        label.setAlignmentX(CENTER_ALIGNMENT);
        iconPanel.add(label,BorderLayout.NORTH);

        JLabel icon = new JLabel();
        icon.setIcon(new ImageIcon(sprite.getImage()));
        iconPanel.add(icon,BorderLayout.CENTER);
    }
    
    /**
     * Adds the component to the centerPanel of the BuildingPanel
     * @param c
     * @return 
     */
    @Override
    public Component add(Component c){
        return centerPanel.add(c);
    }
    
    
    

    @Override
    public void setUnseleceted() {
        building.setUnseleceted();
    }
    

}
