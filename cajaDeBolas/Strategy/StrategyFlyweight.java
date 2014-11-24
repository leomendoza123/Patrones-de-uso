package cajaDeBolas.Strategy;

import cajaDeBolas.Flyweight.FlyweightBallWorld;
import javax.swing.JFrame;

public class StrategyFlyweight implements Strategy {

    @Override
    public void start(int balls) {
        // Run UI in the Event Dispatcher Thread (EDT), instead of Main thread
      javax.swing.SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            JFrame frame = new JFrame("A World of Balls");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(new FlyweightBallWorld(800, 550, balls)); // BallWorld is a JPanel
            frame.pack();            // Preferred size of BallWorld
            frame.setVisible(true);  // Show it
         }
      });
    }
}
