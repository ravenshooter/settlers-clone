/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tilemap7.Crafting;

import tilemap7.Buildings.Stock;

/**
 *
 * @author Steve
 */
public interface crafthead {
    abstract public craftable getCraft();
    abstract public Stock getDropStock();
}
