package cajaDeBolas;

import java.awt.Color;
import java.awt.Graphics;

/**
 * An obstacle Circle.
 * 
 * @author Hock-Chuan Chua
 * @version October 2010
 */
public class ObstacleCircle {
   public int centerX, centerY, radius;
   Color color;  // Line's color
   
   /** Constructors */
   public ObstacleCircle(int centerX, int centerY, int radius, Color color) {
      this.centerX = centerX;
      this.centerY = centerY;
      this.radius = radius;
      this.color = color;
   }
   /** Constructor with the default color */
   public ObstacleCircle(int centerX, int centerY, int radius) {
      this(centerX, centerY, radius, Color.YELLOW);
   }

   /** Draw itself using the given graphic context. */
   public void draw(Graphics g) {
      g.setColor(color);
      g.fillOval(centerX-radius, centerY-radius, 2*radius-1, 2*radius-1);
   }
}
