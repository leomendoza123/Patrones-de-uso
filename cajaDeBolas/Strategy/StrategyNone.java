package cajaDeBolas.Strategy;

import cajaDeBolas.Flyweight.FlyweightBallWorld;
import cajaDeBolas.None.NoneBallWorld;
import javax.swing.JFrame;

public class StrategyNone implements Strategy {

    @Override
    public void start(int balls) {
        System.out.println("Estrategia None");
        // Run UI in the Event Dispatcher Thread (EDT), instead of Main thread
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("A World of Balls");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setContentPane(new NoneBallWorld(800, 550, balls)); // BallWorld is a JPanel
                frame.pack();            // Preferred size of BallWorld
                frame.setVisible(true);  // Show it
            }
        });
    }
}
