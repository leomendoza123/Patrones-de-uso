package cajaDeBolas.Strategy;

import cajaDeBolas.Flyweight.FlyweightBallWorld;
import cajaDeBolas.None.NoneBallWorld;
import javax.swing.JFrame;

public class StrategyNone implements Strategy {

    @Override
    public void start(int balls) {

        JFrame frame = new JFrame("A World of Balls: No pattern");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(new NoneBallWorld(800, 550, balls)); // BallWorld is a JPanel
        frame.pack();            // Preferred size of BallWorld
        frame.setVisible(true);  // Show it
        while (frame.isVisible()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
