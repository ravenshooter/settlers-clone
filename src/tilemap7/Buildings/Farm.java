/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tilemap7.Buildings;

import GUI.BuildButtonListener;
import Tools.MouseObject;
import Tools.SpriteStore;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import tilemap7.CarryObjects.Grain;
import tilemap7.GV;
import tilemap7.LittleMan;
import tilemap7.Mission.Mission;
import tilemap7.Mission.Mission_PickUp;
import tilemap7.Tile;
import tilemap7.missionMaster;
import tilemap7.workable;
/**
 * Write a description of class Farm here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */


public class Farm extends Factory
    implements missionMaster, workable
{



    int productivity;
    int stock;
    Field fields[];
    int actFields;
    FarmPanel farmPanel;
    
    public Farm(Tile tile) throws UnBuildableException
    {
        super(tile,"Bakery",SpriteStore.get().getSprite("farm.png"),8);
        panel.setBoundsWorkersPanel(100, 20, 240, 20);
        sprite = SpriteStore.get().getSprite("farm.png");
        fields = new Field[6];
        
        outStock = inStock = new Stock(new Point(tile.getXPos()+40,tile.getYPos()+40),10,2);
        farmPanel = new FarmPanel();
    }
    
    public Farm(){
        sprite = SpriteStore.get().getSprite("farm.png");
    }
    
    @Override
    public Building addBuilding(Tile tile){
        if(this.tile == null){
            try{
                Farm farm = new Farm(tile);
                tile.addEntity(farm);
                return farm;
            }catch(UnBuildableException e){
                System.err.println("Cannot build here");
            }
        }
        return null;
    }

    @Override
    public void draw(Graphics g, int x, int y)
    {
        g.drawImage(sprite.getImage(), x, y, null);
        outStock.draw(g, x+40, y+40);
    }

    @Override
    public void mouseClicked()
    {
        if(isClickable){
            GV.get().getUI().mouseClicked(this);
            GV.get().getGameWindow().setSouthPanel(panel);
        }
        
    }

    @Override
    public void doLogic(int time)
    {
        if (super.getCurrentWorkersAmount() < super.getMaxWorkers()) {
            super.callWorker();
        }
        
        //calc productivity
        
            for(Field field : fields){
                if(field != null && field.outStock.getExpectedStock()>0){
                    LittleMan worker = getWorker();
                    if(worker != null){
                        worker.addMission(new Mission_PickUp(field.getOutStock(), outStock, Grain.class.getSimpleName(), worker));
                    }
                }
            }
        
        
        
        farmPanel.updateStock();
        
        
        //System.out.println(outStock.getExpectedStock() + "  " + outStock.getSize() + "  " + outStock.getLimit());
    }
    
    

    @Override
    public String getMainAttribute()
    {
        return String.valueOf(stock);
    }

    @Override
    public String getMainAttributeName()
    {
        return "Food";
    }
    
    @Override
    public String getProduct(){
        return Grain.class.getSimpleName();
    }

    @Override
    public int getProductionStep(){
        return 0;
    }
    
    /**
     * Returns an array of String containing all ingredients for this factory
     * @return 
     */
    @Override
    public String[] getIngridients() {
        String[] ingridients = {};
        return ingridients;
    }
    
    /**
     * OUTDATED; CALL REMOVE AT STOCK
     * @return 
     */
    public synchronized boolean getFood()
    {
        if(stock > 0)
        {
            stock--;
            outStock.remove(Grain.class.getSimpleName());
            return true;
        } else{
            return false;
        }
    }
    
    

    public boolean addField(Field field)
    {
        if(actFields < fields.length)
        {
            fields[actFields] = field;
            actFields++;
            farmPanel.updateFields();
            return true;
        } else
        {
            GV.get().getUI().showString("This farm already has " + fields.length + " Fields");
            return false;
        }
    }


    @Override
    public void missionCompleted(Mission mission1)
    {
    }

    @Override
    public Point getWorkPos()
    {
        return new Point(tile.getCenter());
    }

    
    public int getStock() {
        return outStock.getSize();
    }

    @Override
    public Mission getWork() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
    


    /**
     * Class that contains all the GU-interface elements of the farm
     */
    class FarmPanel{
        
        Label stockLabel;
        Label fieldsLabel;
        
        
        
        private FarmPanel(){
            JLabel addFieldLabel = new JLabel("Build a field: ");
            addFieldLabel.setBounds(100, 50, 80, 40);
            addFieldLabel.setForeground(Color.WHITE);
            panel.add(addFieldLabel);
            JButton addFieldButton = new JButton(new ImageIcon(SpriteStore.get().getSprite("field.png").getImage()));
            addFieldButton.setToolTipText("Build a new field for this farm!");
            addFieldButton.setBounds(170, 60, 20, 20);
            addFieldButton.addActionListener(new FieldBuildButtonListener(new Field()));
            panel.add(addFieldButton);
            
            panel.setLayoutWorkersPanel(2, 4);
            panel.setBoundsWorkersPanel(100, 20, 140, 30);
            
            
            JPanel infoPanel = new JPanel(new GridLayout(2,2));
            infoPanel.setBounds(280, 20, 100, 50);
            infoPanel.setBackground(Color.DARK_GRAY);
            infoPanel.setForeground(Color.WHITE);
            infoPanel.add(new Label("Stock: "));
            stockLabel = new Label("0");
            stockLabel.setBounds(280, 20, 100, 20);
            infoPanel.add(stockLabel);
            infoPanel.add(new Label("Fields: "));
            fieldsLabel = new Label("0");
            fieldsLabel.setBounds(280, 50, 20, 20);
            infoPanel.add(fieldsLabel);
            
            panel.add(infoPanel);
            

            

            
        }
        
        
        public void updateStock(){
            stockLabel.setText(String.valueOf(stock));
        }
        
        public void updateFields(){
            fieldsLabel.setText(String.valueOf(actFields));
        }
        


        
        private class FieldBuildButtonListener extends BuildButtonListener{
            public FieldBuildButtonListener(Building building){
                super(building);
            }

        @Override
        public void actionPerformed(ActionEvent e) {
            GV.get().getMouse().setMouseObject(new MouseObject(){

                @Override
                protected void initialize(){
                    setShade(getBuilding().getSprite());
                }
                
                @Override
                public void doLeftClick(MouseEvent e) {
                    try{
                        addField((Field)getBuilding().addBuilding(GV.get().getTileMap().getTileByPosWithCamera(e.getX(), e.getY())));
                    }catch(Building.UnBuildableException g){
                        System.err.println("Can't build here");
                    }
                    GV.get().getMouse().setMouseObject(null);
                }
            });
        }
            
            
        }
    }



}
