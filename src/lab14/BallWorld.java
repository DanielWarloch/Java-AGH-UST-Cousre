package lab14;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;

public class BallWorld extends JPanel {
    private static final int UPDATE_RATE = 60;
    private static final float EPSILON_TIME = 1e-2f;


    private static final int MAX_BALLS = 25;
    private int currentNumBalls;
    private Ball[] balls = new Ball[MAX_BALLS];


    private ContainerBox box;
    private ObstacleLineSegment lanuchTube;
    private ObstacleLine cornerTopLeft;
    private ObstacleLine cornerTopRight;
    private ObstacleLineSegment line;
    private ObstaclePolygon polygon1;
    private ObstaclePolygon polygon2;
    private ObstacleCircle circle;

    private DrawCanvas canvas;
    private int canvasWidth;
    private int canvasHeight;

    private ControlPanel control;
    private boolean paused = false;


    public BallWorld(int width, int height) {
        final int controlHeight = 30;
        canvasWidth = width;
        canvasHeight = height - controlHeight;


        box = new ContainerBox(0, 0, canvasWidth, canvasHeight, Color.BLACK, Color.WHITE);


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


        currentNumBalls = 11;
        balls[0] = new Ball(100, 410, 25, 3, 34, Color.YELLOW);
        balls[1] = new Ball(80, 350, 25, 2, -114, Color.YELLOW);
        balls[2] = new Ball(530, 200, 30, 3, 14, Color.GREEN);
        balls[3] = new Ball(400, 200, 30, 3, 14, Color.GREEN);
        balls[4] = new Ball(400, 150, 35, 1, -47, Color.PINK);
        balls[5] = new Ball(480, 220, 35, 4, 47, Color.PINK);
        balls[6] = new Ball(80, 150, 40, 1, -114, Color.ORANGE);
        balls[7] = new Ball(100, 240, 40, 2, 60, Color.ORANGE);
        balls[8] = new Ball(600, 450, 50, 3, -42, new Color(0, 196, 128));
        balls[9] = new Ball(200, 80, 70, 6, -84, Color.CYAN);
        balls[10] = new Ball(500, 170, 90, 6, -42, Color.MAGENTA);


        for (int i = currentNumBalls; i < MAX_BALLS; i++) {
            balls[i] = new Ball(16, canvasHeight - 20, 15, 0, 90, Color.RED);
        }


        canvas = new DrawCanvas();


        control = new ControlPanel();


        this.setLayout(new BorderLayout());
        this.add(canvas, BorderLayout.CENTER);
        this.add(control, BorderLayout.SOUTH);


        this.addComponentListener(new ComponentAdapter() {
            // Called back for first display and subsequent window resize.
            @Override
            public void componentResized(ComponentEvent e) {
                Component c = (Component) e.getSource();
                Dimension dim = c.getSize();
                canvasWidth = dim.width;
                canvasHeight = dim.height - controlHeight;

                box.set(0, 0, canvasWidth, canvasHeight);
                lanuchTube.set(32, canvasHeight - 160, 32, canvasHeight);
                cornerTopRight.set(canvasWidth, 200, canvasWidth - 90, 0);
            }
        });


        gameStart();
    }


    public void gameStart() {

        Thread gameThread = new Thread() {
            public void run() {
                while (true) {
                    long beginTimeMillis, timeTakenMillis, timeLeftMillis;
                    beginTimeMillis = System.currentTimeMillis();

                    if (!paused) {

                        gameUpdate();

                        repaint();
                    }


                    timeTakenMillis = System.currentTimeMillis() - beginTimeMillis;
                    timeLeftMillis = 1000L / UPDATE_RATE - timeTakenMillis;
                    if (timeLeftMillis < 5) timeLeftMillis = 5;


                    try {
                        Thread.sleep(timeLeftMillis);
                    } catch (InterruptedException ex) {
                    }
                }
            }
        };
        gameThread.start();
    }


    public void gameUpdate() {
        float timeLeft = 1.0f;


        do {

            float tMin = timeLeft;


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


            for (int i = 0; i < currentNumBalls; i++) {
                balls[i].update(tMin);
            }

            timeLeft -= tMin;
        } while (timeLeft > EPSILON_TIME);
    }


    class DrawCanvas extends JPanel {

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);


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
            g.setColor(Color.BLUE);
            g.setFont(new Font("Courier New", Font.PLAIN, 12));
            int line;
            for (line = 0; line < currentNumBalls; line++) {
                g.drawString("Ball " + (line + 1) + " " + balls[line].toString(), 42, 20 + line * 20);
            }
            g.drawString("Total Energy: " + (int) totalEnergy, 42, 20 + line * 20);
        }

        @Override
        public Dimension getPreferredSize() {
            return (new Dimension(canvasWidth, canvasHeight));
        }
    }


    class ControlPanel extends JPanel {

        public ControlPanel() {

            JCheckBox pauseControl = new JCheckBox();
            this.add(new JLabel("Pause"));
            this.add(pauseControl);
            pauseControl.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    paused = !paused;
                    transferFocusUpCycle();
                }
            });


            final float[] ballSavedSpeedXs = new float[MAX_BALLS];
            final float[] ballSavedSpeedYs = new float[MAX_BALLS];
            for (int i = 0; i < currentNumBalls; i++) {
                ballSavedSpeedXs[i] = balls[i].speedX;
                ballSavedSpeedYs[i] = balls[i].speedY;
            }
            int minFactor = 5;
            int maxFactor = 200;
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
                    transferFocusUpCycle();
                }
            });


            final JButton launchControl = new JButton("Launch New Ball");
            this.add(launchControl);
            launchControl.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (currentNumBalls < MAX_BALLS) {
                        balls[currentNumBalls].x = 16;
                        balls[currentNumBalls].y = canvasHeight - 20;
                        balls[currentNumBalls].speedY = 10f;
                        currentNumBalls++;
                        if (currentNumBalls == MAX_BALLS) {
                            launchControl.setEnabled(false);
                        }
                    }
                    transferFocusUpCycle();
                }
            });
        }
    }
}
