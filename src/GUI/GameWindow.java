/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;


import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import tilemap7.GV;
import Tools.KeyInputHandler;
import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultDesktopManager;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;


/**
 *
 * @author Steve
 */
public class GameWindow extends JFrame{
    
    JDesktopPane contentPane;
    Drawsheet drawsheet;
    JPanel southPanelCenter;
    SouthPanel southPanelAddon;
    BuildPanel buildPanel;
    MiniMap miniMap;
    Map map;
    private boolean setSouthPanel;
    
    public GameWindow(){
        GV.get().setGameWindow(this);
        this.setSize(GV.get().getXRes(), GV.get().getYRes());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        
        contentPane = new JDesktopPane();
        
        addMap();
        
        
        setContentPane(contentPane);
        getContentPane().setLayout(new BorderLayout());
        drawsheet = new Drawsheet(0,0,GV.get().getXRes(),GV.get().getYRes());
        contentPane.add(drawsheet,BorderLayout.CENTER);
        
        addMouseListener();
        drawsheet.addKeyListener(new KeyInputHandler());
        drawsheet.requestFocus();
     
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.setBackground(Color.DARK_GRAY);
        southPanel.setPreferredSize(new Dimension(GV.get().getXRes(),160));
        
        //southPanelCenter containing southpanel addon
        southPanelCenter = new JPanel(new BorderLayout());
        southPanelCenter.setBackground(Color.DARK_GRAY);
        southPanel.add(southPanelCenter,BorderLayout.CENTER);

        buildPanel = new BuildPanel();
        southPanelCenter.add(buildPanel,BorderLayout.CENTER);
        
        //minimap on raw southpanel
        miniMap = new MiniMap();
        southPanel.add(miniMap,BorderLayout.EAST);
        
        contentPane.add(southPanel,BorderLayout.SOUTH);
        

        
        this.setVisible(true);

        drawsheet.createBuffer();
        miniMap.createBuffer();
        map.createBuffer();
    }
    
    
    /**
     * Adds the map window invisible to the contentpane
     */
    private void addMap(){
        contentPane.setDesktopManager(new DefaultDesktopManager() {
            @Override
            public void beginDraggingFrame(JComponent f) {
                if (!"fixed".equals(f.getClientProperty("dragMode"))) {
                    super.beginDraggingFrame(f);
                }
            }

            @Override
            public void dragFrame(JComponent f, int newX, int newY) {
                if (!"fixed".equals(f.getClientProperty("dragMode"))) {
                    super.dragFrame(f, newX, newY);
                }
            }

            @Override
            public void endDraggingFrame(JComponent f) {
                if (!"fixed".equals(f.getClientProperty("dragMode"))) {
                    super.endDraggingFrame(f);
                }
            }
        });
        map = new Map();
        contentPane.add(map);
    }
    
    public Drawsheet getDrawsheet(){
        return drawsheet;
    }
    

    
    private void addMouseListener(){
        drawsheet.addMouseListener(GV.get().getMouse());
        drawsheet.addMouseMotionListener(GV.get().getMouse());
        drawsheet.addMouseWheelListener(GV.get().getMouse());
    }
    
    /**
     * Sets the current southPanel
     * @param panel 
     */
    public void setSouthPanel(SouthPanel panel) {
        if(southPanelAddon != null){
            southPanelAddon.setUnseleceted();
        }
        southPanelAddon = panel;
        southPanelCenter.removeAll();
        southPanelCenter.add(panel,BorderLayout.CENTER);
        southPanelCenter.validate();
        southPanelCenter.repaint();
        setSouthPanel = true;
    }
    
    
    public void resetSouthPanel() {
        if (southPanelAddon != null) {
            southPanelAddon.setUnseleceted();
        }
        southPanelAddon = buildPanel;
        southPanelCenter.removeAll();
        southPanelCenter.validate();
        southPanelCenter.add(buildPanel, BorderLayout.CENTER);
        southPanelCenter.validate();
        southPanelCenter.repaint();
        setSouthPanel = false;
    }

    /**
     * True if a specific southpanel is set, false if only buildingpanel is set
     * @return 
     */
    public boolean isSetSouthPanel() {
        return setSouthPanel;
    }
    
    /**
     * Shows or hide the internal frame map
     * @param b 
     */
    public void showMap(boolean b){
        map.setVisible(b);
    }
    
    public void toggleShowMap(){
        map.setVisible(!map.isVisible());
    }

    
    
}
