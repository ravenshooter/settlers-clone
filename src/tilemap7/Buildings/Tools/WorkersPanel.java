/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tilemap7.Buildings.Tools;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import tilemap7.Buildings.Bakery;
import tilemap7.Buildings.Building;
import tilemap7.GV;
import tilemap7.LittleMan;
import Tools.SpriteStore;

/**
 *
 * @author Steve
 */
public class WorkersPanel extends JPanel {

    Building building;
    private WorkerButton[] workerButtons;
    private JPanel workerButtonPanel;
    private JLabel workersLabel; 
    
    public WorkersPanel(Building building, int x, int y, int width, int height) {
        this.building = building;
    
        this.setLayout(new BorderLayout());
        this.setBounds(x, y, width, height);
        this.setBackground(Color.DARK_GRAY);

        workersLabel = new JLabel("Workers: ");
        workersLabel.setBackground(Color.DARK_GRAY);
        workersLabel.setForeground(Color.WHITE);
        this.add(workersLabel, BorderLayout.WEST);

        workerButtonPanel = new JPanel(new GridLayout(1, building.getMaxWorkers()));
        workerButtonPanel.setBackground(Color.DARK_GRAY);
        this.workerButtons = new WorkerButton[building.getMaxWorkers()];
        for (int i = 0; i < this.workerButtons.length; i++) {
            this.workerButtons[i] = new WorkerButton();
            workerButtonPanel.add(this.workerButtons[i]);
        }
        this.add(workerButtonPanel, BorderLayout.CENTER);
    }

    public WorkerButton[] getWorkerButtons() {
        return workerButtons;
    }
    
    public void colorButton(LittleMan littleMan, Color c){
        for(int i = 0; i < workerButtons.length; i++){
            if(workerButtons[i] != null && workerButtons[i].getWorker() != null && workerButtons[i].getWorker().equals(littleMan)){
                workerButtons[i].setBackground(c);
                break;
            }
        }
    }

    /*
    public boolean addWorkerToButton(LittleMan littleMan, int i) {
        if (workerButtons[i].getWorker() == null) {
            workerButtons[i].setWorker(littleMan);
            return true;
        } else {
            return false;
        }
    }
    */
    
    /**
     * Adds a worker to the buttonlist.
     * Returns false if no free space is available.
     * @param littleMan
     * @return 
     */
   public boolean addWorkerToButton(LittleMan littleMan){
        for(WorkerButton button : workerButtons){
            if(button.getWorker() == null){
                button.setWorker(littleMan);
                return true;
            }
        }
        return false;
    }
    
    /**
     * Edits the rows and collums of the workerButtons
     * @param rows
     * @param collums 
     */
    public void setLayoutWorkerButtons(int rows, int collums){
        workerButtonPanel.setLayout(new GridLayout(rows,collums));
        for(int i = 0; i < getWorkerButtons().length;i++){
            workerButtonPanel.add(getWorkerButtons()[i]);
        }
    }

    
    public int getWorkerLabelWidth(){
        return workersLabel.getSize().width;
    }
    class WorkerButton extends JButton {

        private LittleMan worker;

        public void setWorker(LittleMan worker) {
            this.worker = worker;
            if(worker != null){
                this.setIcon(new ImageIcon(SpriteStore.get().getSprite("littleman00.png").getImage()));
            }else{
                this.setIcon(null);
            }
        }

        public LittleMan getWorker() {
            return worker;
        }
    }
}

