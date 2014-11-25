package cajaDeBolas.Prototype;

import cajaDeBolas.*;
import cajaDeBolas.Prototype.PrototypeFactory.ApirienceType;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import javax.swing.event.*;

/**
 * The control logic and main display panel for game.
 *
 * @author Hock-Chuan Chua
 * @version October 2010
 */
public class PrototypeBallWorld extends JPanel {

    private final int UPDATE_RATE = 30;    // Frames per second (fps)
    private final float EPSILON_TIME = 1e-2f;  // Threshold for zero time

    // Balls
    private int MAX_BALLS = 20; // Max number allowed 
    private int currentNumBalls;             // Number currently active
    private PrototypeBall[] balls = new PrototypeBall[MAX_BALLS];

    // The obstacles
    private ContainerBox box;               // The container box
    private ObstacleLineSegment lanuchTube; // The launching tube for new balls
    private ObstacleLine cornerTopLeft;     // Line at the top-left corner 
    private ObstacleLine cornerTopRight;    // Line at the top-right corner
    private ObstacleLineSegment line;       // The line obstacle
    private ObstaclePolygon polygon1;       // A polygon obstacle
    private ObstaclePolygon polygon2;       // A polygon obstacle
    private ObstacleCircle circle;          // A circle obstacle

    private DrawCanvas canvas;    // The Custom canvas for drawing the box/ball
    private int canvasWidth;
    private int canvasHeight;

    private ControlPanel control; // The control panel of buttons and sliders.
    private boolean paused = false;  // Flag for pause/resume control

    /**
     * Constructor to create the UI components and init the game objects. Set
     * the drawing canvas to fill the screen (given its width and height).
     *
     * @param width : screen width
     * @param height : screen height
     */
    public PrototypeBallWorld(int width, int height, int numBalls) {
        final int controlHeight = 30;
        canvasWidth = width;
        canvasHeight = height - controlHeight;  // Leave space for the control panel
        MAX_BALLS = numBalls;

        // Init the Container Box to fill the screen
        box = new ContainerBox(0, 0, canvasWidth, canvasHeight, Color.BLACK, Color.WHITE);

        // Init the obstacle blocks
        lanuchTube = new ObstacleLineSegment(32, canvasHeight - 160, 32, canvasHeight, Color.WHITE);
        cornerTopLeft = new ObstacleLine(0, 50, 100, 0, Color.WHITE);
        cornerTopRight = new ObstacleLine(canvasWidth, 200, canvasWidth - 90, 0, Color.WHITE);
        line = new ObstacleLineSegment(36, 80, 100, 50, Color.WHITE);
        int[] polygon1Xs = {500, 630, 450, 380};
        int[] polygon1Ys = {280, 350, 420, 360};
        polygon1 = new ObstaclePolygon(polygon1Xs, polygon1Ys, Color.WHITE);
        int[] polygon2Xs = {150, 250, 350};
        int[] polygon2Ys = {550, 400, 550};
        polygon2 = new ObstaclePolygon(polygon2Xs, polygon2Ys, Color.WHITE);
        circle = new ObstacleCircle(400, -30, 100, Color.WHITE);

        PrototypeFactory Factory = new PrototypeFactory();
        String[] Apirience = {ApirienceType.BLUE, ApirienceType.YELLOW, ApirienceType.BLUE, ApirienceType.GREEN, ApirienceType.ORANGE};
        ArrayList<PrototypeBall> ballsArray = new ArrayList<>();

        Random ram = new Random();

        PrototypeAperiences apirience;
        String color; 
        for (int x = 0; x < MAX_BALLS; x++) {
           
            color = Apirience[ram.nextInt(5)];
            apirience = Factory.lookup(color);
            
            ballsArray.add(new PrototypeBall(50 + ram.nextInt(200), 50 + ram.nextInt(200), ram.nextInt(6), -114 + ram.nextInt(174),
                    apirience));

        }
        currentNumBalls = ballsArray.size();
        balls = ballsArray.toArray(balls);

        // Init the custom drawing panel for the box/ball
        canvas = new DrawCanvas();

        // Init the control panel
        control = new ControlPanel();

        // Layout the drawing panel and control panel
        this.setLayout(new BorderLayout());
        this.add(canvas, BorderLayout.CENTER);
        this.add(control, BorderLayout.SOUTH);

        // Handling window resize. Adjust container box to fill the screen.
        this.addComponentListener(new ComponentAdapter() {
            // Called back for first display and subsequent window resize.
            @Override
            public void componentResized(ComponentEvent e) {
                Component c = (Component) e.getSource();
                Dimension dim = c.getSize();
                canvasWidth = dim.width;
                canvasHeight = dim.height - controlHeight; // Leave space for control panel
                // Need to resize all components that is sensitive to the screen size.
                box.set(0, 0, canvasWidth, canvasHeight);
                lanuchTube.set(32, canvasHeight - 160, 32, canvasHeight);
                cornerTopRight.set(canvasWidth, 200, canvasWidth - 90, 0);
            }
        });

        // Start the ball bouncing
        gameStart();
    }

    /**
     * Start the ball bouncing.
     */
    public void gameStart() {
        // Run the game logic in its own thread.
        Thread gameThread = new Thread() {
            public void run() {
                while (true) {
                    long beginTimeMillis, timeTakenMillis, timeLeftMillis;
                    beginTimeMillis = System.currentTimeMillis();

                    if (!paused) {
                        // Execute one game step
                        gameUpdate();
                        // Refresh the display
                        repaint();
                    }

                    // Provide the necessary delay to meet the target rate
                    timeTakenMillis = System.currentTimeMillis() - beginTimeMillis;
                    timeLeftMillis = 1000L / UPDATE_RATE - timeTakenMillis;
                    if (timeLeftMillis < 5) {
                        timeLeftMillis = 5; // Set a minimum
                    }
                    // Delay and give other thread a chance
                    try {
                        Thread.sleep(timeLeftMillis);
                    } catch (InterruptedException ex) {
                    }
                }
            }
        };
        gameThread.start();  // Invoke GaemThread.run()
    }

    /**
     * One game time-step. Update the game objects, with proper collision
     * detection and response.
     */
    public void gameUpdate() {
        float timeLeft = 1.0f;  // One time-step to begin with

        // Repeat until the one time-step is up 
        do {
            // Find the earliest collision up to timeLeft among all objects
            float tMin = timeLeft;

            // Check collision between two balls
            for (int i = 0; i < currentNumBalls; i++) {
                for (int j = 0; j < currentNumBalls; j++) {
                    if (i < j) {
                        balls[i].intersect(balls[j], tMin);
                        if (balls[i].earliestCollisionResponse.t < tMin) {
                            tMin = balls[i].earliestCollisionResponse.t;
                        }
                    }
                }
            }
            for (int i = 0; i < currentNumBalls; i++) {
                // Check collision between the balls and the other obstacles
                balls[i].intersect(box, tMin);
                if (balls[i].earliestCollisionResponse.t < tMin) {
                    tMin = balls[i].earliestCollisionResponse.t;
                }
                balls[i].intersect(cornerTopLeft, tMin);
                if (balls[i].earliestCollisionResponse.t < tMin) {
                    tMin = balls[i].earliestCollisionResponse.t;
                }
                balls[i].intersect(cornerTopRight, tMin);
                if (balls[i].earliestCollisionResponse.t < tMin) {
                    tMin = balls[i].earliestCollisionResponse.t;
                }
                balls[i].intersect(lanuchTube, tMin);
                if (balls[i].earliestCollisionResponse.t < tMin) {
                    tMin = balls[i].earliestCollisionResponse.t;
                }
                balls[i].intersect(polygon1, tMin);
                if (balls[i].earliestCollisionResponse.t < tMin) {
                    tMin = balls[i].earliestCollisionResponse.t;
                }
                balls[i].intersect(polygon2, tMin);
                if (balls[i].earliestCollisionResponse.t < tMin) {
                    tMin = balls[i].earliestCollisionResponse.t;
                }
                balls[i].intersect(line, tMin);
                if (balls[i].earliestCollisionResponse.t < tMin) {
                    tMin = balls[i].earliestCollisionResponse.t;
                }
                balls[i].intersect(circle, tMin);
                if (balls[i].earliestCollisionResponse.t < tMin) {
                    tMin = balls[i].earliestCollisionResponse.t;
                }
            }

            // Update all the balls up to the detected earliest collision time tMin,
            // or timeLeft if there is no collision.
            for (int i = 0; i < currentNumBalls; i++) {
                balls[i].update(tMin);
            }

            timeLeft -= tMin;                // Subtract the time consumed and repeat
        } while (timeLeft > EPSILON_TIME);  // Ignore remaining time less than threshold
    }

    /**
     * The custom drawing panel for the bouncing ball (inner class).
     */
    class DrawCanvas extends JPanel {

        /**
         * Custom drawing codes
         */
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);    // Paint background

            // Draw the box, obstacles and balls
            box.draw(g);
            cornerTopLeft.draw(g);
            cornerTopRight.draw(g);
            lanuchTube.draw(g);
            polygon1.draw(g);
            polygon2.draw(g);
            line.draw(g);
            circle.draw(g);

            int totalEnergy = 0;
            for (int i = 0; i < MAX_BALLS; i++) {
                totalEnergy += balls[i].getKineticEnergy();
                balls[i].draw(g);
            }
            // Display balls' information
            g.setColor(Color.BLUE);
            g.setFont(new Font("Courier New", Font.PLAIN, 12));
            int line;
            for (line = 0; line < currentNumBalls; line++) {
                g.drawString("Ball " + (line + 1) + " " + balls[line].toString(), 42, 20 + line * 20);
            }
            g.drawString("Total Energy: " + (int) totalEnergy, 42, 20 + line * 20);
        }

        /**
         * Called back to get the preferred size of the component.
         */
        @Override
        public Dimension getPreferredSize() {
            return (new Dimension(canvasWidth, canvasHeight));
        }
    }

    /**
     * The control panel (inner class).
     */
    class ControlPanel extends JPanel {

        /**
         * Constructor to initialize UI components
         */
        public ControlPanel() {
            // A checkbox to toggle pause/resume movement
            JCheckBox pauseControl = new JCheckBox();
            this.add(new JLabel("Pause"));
            this.add(pauseControl);
            pauseControl.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    paused = !paused;  // Toggle pause/resume flag
                    transferFocusUpCycle();  // To handle key events
                }
            });

            // A slider for adjusting the speed of all the balls by a factor
            final float[] ballSavedSpeedXs = new float[MAX_BALLS];
            final float[] ballSavedSpeedYs = new float[MAX_BALLS];
            for (int i = 0; i < currentNumBalls; i++) {
                ballSavedSpeedXs[i] = balls[i].speedX;
                ballSavedSpeedYs[i] = balls[i].speedY;
            }
            int minFactor = 5;    // percent
            int maxFactor = 200;  // percent
            JSlider speedControl = new JSlider(JSlider.HORIZONTAL, minFactor, maxFactor, 100);
            this.add(new JLabel("Speed"));
            this.add(speedControl);
            speedControl.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    JSlider source = (JSlider) e.getSource();
                    if (!source.getValueIsAdjusting()) {
                        int percentage = (int) source.getValue();
                        for (int i = 0; i < currentNumBalls; i++) {
                            balls[i].speedX = ballSavedSpeedXs[i] * percentage / 100.0f;
                            balls[i].speedY = ballSavedSpeedYs[i] * percentage / 100.0f;
                        }
                    }
                    transferFocusUpCycle();  // To handle key events
                }
            });

        }
    }
}
