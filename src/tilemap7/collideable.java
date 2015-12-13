/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tilemap7;
import java.awt.Point;
import java.awt.Rectangle;

public interface collideable
{

    public abstract boolean isColliding(Point point);

    public abstract boolean isColliding(Rectangle rectangle);

    public abstract Rectangle[] getBoundBox();

    public abstract void addAllowance(Entity entity);

    public abstract void removeAllowance(Entity entity);

    public abstract Point getClosestCorner(Point point, double ad[]);
}
