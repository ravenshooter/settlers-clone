/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Tools.Sprite;
import Tools.SpriteStore;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import tilemap7.Buildings.Tools.WorkersPanel;
import tilemap7.Entity;
import tilemap7.GV;

/**
 *
 * @author Steve
 */
public class EntityPanel extends SouthPanel{
    
    protected Entity entity;
    
    /**
     * Panel with all content on
     */
    private JPanel centerPanel;
    
    /**
     * Panel for the icon, in west of Building Panel
     */
    JPanel iconPanel;
    
    
    /**
     * Panel with buttons for each worker.
     */
    WorkersPanel workersPanel;
    
    
    
    /**
     * Erstellt ein blankes Panel für ein Building mit Layout null und Höhe 80
     */
    public EntityPanel(Entity entity){
        this.entity = entity;
        initialize();
        setIcon(entity.getClass().getSimpleName(), entity.getSprite());
    }

    /**
     * Erstellt ein Panel mit Building Name und Bild auf der linken Seite des Panels
     * @param name Name des Buildings
     * @param sprite Bild des Buildings
     */
    public EntityPanel(String name, Sprite sprite) {
        initialize();
        setIcon(name, sprite);
    }
    
    
    private void initialize(){
        this.setLayout(new BorderLayout(20,20));
        this.setPreferredSize(new Dimension(GV.get().getXRes(), 80));
        this.setBackground(Color.DARK_GRAY);
        this.setForeground(Color.WHITE); 
        
        iconPanel = new JPanel(new BorderLayout());
        iconPanel.setBounds(10, 2, 70, 80);
        iconPanel.setPreferredSize(new Dimension(120,200));
        this.add(iconPanel,BorderLayout.WEST);
        
        centerPanel = new JPanel(new BorderLayout());
        centerPanel.setLayout(null);
        centerPanel.setBackground(Color.DARK_GRAY);
        centerPanel.setForeground(Color.WHITE);
        centerPanel.setBorder(BorderFactory.createLineBorder(Color.white));
        this.add(centerPanel,BorderLayout.CENTER);
    }
    
    
    private void setIcon(String name, Sprite sprite){
        
        if(sprite == null || sprite.getImage() == null){
            sprite = SpriteStore.get().getSprite("cross.png");
        }
        
        iconPanel.setBorder(BorderFactory.createLineBorder(Color.white));
        iconPanel.setOpaque(false);
        
        JLabel label = new JLabel(name);
        label.setPreferredSize(new Dimension(120,40));
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.ITALIC, 16));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        iconPanel.add(label,BorderLayout.NORTH);

        JLabel icon = new JLabel();
        icon.setPreferredSize(new Dimension(120,120));
        icon.setBorder(BorderFactory.createBevelBorder(1, Color.LIGHT_GRAY, Color.BLACK));
        icon.setIcon(new ImageIcon(sprite.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH)));
        icon.setHorizontalAlignment(SwingConstants.CENTER);
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
        if(entity != null){
            entity.setUnselected();
        }
    }
    
    
}
