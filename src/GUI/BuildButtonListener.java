/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Tools.MouseObject;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import tilemap7.Buildings.Building;
import tilemap7.Buildings.ConstructionSite;
import tilemap7.GV;
import tilemap7.Tile;

/**
 *
 * @author Steve
 */

    public class BuildButtonListener implements ActionListener {

        Building building;
        public BuildButtonListener(Building building){
            this.building=building;
        }
        
    @Override
    public void actionPerformed(ActionEvent e) {
        if (building.getMouseObject() != null) {
            GV.get().getMouse().setMouseObject(building.getMouseObject());
        } else {
            GV.get().getMouse().setMouseObject(new MouseObject() {
                @Override
                protected void initialize() {
                    setShade(building.getSprite());
                }

                @Override
                public void doLeftClick(MouseEvent e) {
                    Tile tile = GV.get().getTileMap().getTileByPosWithCamera(e.getX(), e.getY());
                    //building.addBuilding(GV.get().getTileMap().getTileByPosWithCamera(e.getX(), e.getY()));
                    tile.addEntity(new ConstructionSite(tile, building));

                    GV.get().getMouse().setMouseObject(null);
                }
            });
            //MouseObject m = new MouseObject();
            //m.setShade(building.getSprite());
            //GV.get().getMouse().setMouseObject(m);
        }
    }

    public Building getBuilding() {
        return building;
    }
}
