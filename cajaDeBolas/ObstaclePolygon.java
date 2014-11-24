package cajaDeBolas;

import java.awt.Color;
import java.awt.Graphics;

/**
 * An Polygon Obstacle.
 * 
 * @author Hock-Chuan Chua
 * @version October 2010
 */
public class ObstaclePolygon {
   public int[] xPoints;  // x-coordinate of the points 
   public int[] yPoints;  // y-coordinate of the points
   public Color color;    // Polygon's filled color
   
   /** Constructors */
   public ObstaclePolygon(int[] xPoints, int[] yPoints, Color color) {
      this.xPoints = xPoints;
      this.yPoints = yPoints;
      this.color = color;
   }
   
   /** Constructor with the default color */
   public ObstaclePolygon(int[] xPoints, int[] yPoints, int numPoints) {
      this(xPoints, yPoints, Color.YELLOW);
   }

   /** Draw itself using the given graphic context. */
   public void draw(Graphics g) {
      g.setColor(color);
      g.fillPolygon(xPoints, yPoints, xPoints.length);
   }
}
