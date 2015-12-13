/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.LayoutManager;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 *
 * @author Steve
 */
public class SouthPanel extends JPanel{
    
    
    public SouthPanel(LayoutManager m){
        super(m);
        this.setBorder(BorderFactory.createEtchedBorder());
    }
    
    public SouthPanel(){
        super();
        this.setBorder(BorderFactory.createEtchedBorder());
    }
    
    public void setUnseleceted(){
        
    }
}
