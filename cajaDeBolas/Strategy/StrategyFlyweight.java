package cajaDeBolas.Strategy;

import cajaDeBolas.Flyweight.FlyweightBallWorld;
import javax.swing.JFrame;

public class StrategyFlyweight implements Strategy {

    @Override
    public void start(int balls) {
        // Run UI in the Event Dispatcher Thread (EDT), instead of Main thread

        JFrame frame;
        frame = new JFrame("A World of Balls: Flyweight");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(new FlyweightBallWorld(800, 550, balls)); // BallWorld is a JPanel
        frame.pack();            // Preferred size of BallWorld
        frame.setVisible(true);  // Show 
        while (frame.isVisible()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
