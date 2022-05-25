package app;

import component.Bird;

import component.GameBackground;
import component.GameForeground;
import component.WelcomeAnimation;
import component.GameElementLayer;

import static util.Constant.FRAME_HEIGHT;
import static util.Constant.FRAME_WIDTH;
import static util.Constant.FRAME_X;
import static util.Constant.FRAME_Y;
import static util.Constant.FPS;
import static util.Constant.GAME_TITLE;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.image.BufferedImage;



public class Game extends Frame {
    private static final long serialVersionUID = 1L; 
    // Maintain game state compatible with version 
    private static int gameState; 
    public static final int GAME_READY = 0; // Game ready
    public static final int GAME_START = 1; // Game start
    public static final int GAME_OVER = 2; // Game over

    private GameBackground background; // Background
    private GameForeground foreground; // Foreground
    private Bird bird; // Bird object
    private GameElementLayer gameElement; // Game element object
    private WelcomeAnimation welcomeAnimation; // Welcome object

    // Constructor to initialize game
    public Game() {
        initFrame(); // initialize game frame
        setVisible(true); 
        initGame(); // initialize game object
    }

    // Method initialize frame
    private void initFrame() {
        setSize(FRAME_WIDTH, FRAME_HEIGHT); // Set size for frame
        setTitle(GAME_TITLE); // Frame title
        setLocation(FRAME_X, FRAME_Y); // Frame location
        setResizable(false); 
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0); // Exit window
            }
        });
        addKeyListener(new BirdKeyListener()); // Add main game window 
    }

    // Allows the bird object to receive events
    class BirdKeyListener implements KeyListener{
        // Press the button to call different methods based on the game status
        public void keyPressed(KeyEvent e) {
            int keycode = e.getKeyCode();
            switch (gameState) {
                case GAME_READY:
                    if (keycode == KeyEvent.VK_SPACE || keycode == KeyEvent.VK_UP) {
                        // Press space and the bird will clap wings once and start effected the gravity
                        bird.birdFlap();
                        bird.birdFall();
                        setGameState(GAME_START); // Game state change 
                    }
                    break;
                case GAME_START:
                    if (keycode == KeyEvent.VK_SPACE || keycode == KeyEvent.VK_UP) {
        /* Pressing space during the game 
         * will make the wings vibrate once 
         * and continue to be affected by gravity */                     
                        bird.birdFlap();
                        bird.birdFall();
                        //audio.sound_fly();
                    }
                    break;
                case GAME_OVER:
                    if (keycode == KeyEvent.VK_SPACE || keycode == KeyEvent.VK_UP) {
  // Press space at the end of the game to restart the game
                          resetGame();
                    }
                    break;
            }
        }

       	
		// Restart game
        private void resetGame() {
            setGameState(GAME_READY);
            gameElement.reset();
            bird.reset();
        }

        // Key release, change key status flag
         public void keyReleased(KeyEvent e) {
            int keycode = e.getKeyCode();
            if (keycode == KeyEvent.VK_SPACE || keycode == KeyEvent.VK_UP) {
                bird.keyReleased();
            }
        }

        public void keyTyped(KeyEvent e) {
        }
    }

    // Initialize each object in the game
    private void initGame() {
        background = new GameBackground();
        gameElement = new GameElementLayer();
        foreground = new GameForeground();
        welcomeAnimation = new WelcomeAnimation();
        bird = new Bird();
        setGameState(GAME_READY);

        // Start the thread for refreshing the window
        new Thread(() ->{
            while (true) {
                repaint(); // Let the JVM call update() by calling repaint()
                try {
                    Thread.sleep(FPS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    // There are two threads in the project: the system thread, and a custom thread: the call to repaint().
    // System thread: drawing of screen content, listening and handling of window events
    // The two threads will grab system resources, and it may happen that the content drawn in one refresh cycle is not finished in one refresh cycle
    // (double buffering) define a separate image, draw the content to this image, and then draw the image to the window in one go

    private final BufferedImage bufImg = new BufferedImage(FRAME_WIDTH, FRAME_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);

    /**
     * Draw the game content When the repaint() method is called, the JVM calls update() with the parameter g, which is a system-supplied brush, instantiated by the system
     * Start a separate thread and keep calling repaint() quickly to let the system repaint the whole window
     */
    public void update(Graphics g) {
        Graphics bufG = bufImg.getGraphics(); //Get the picture
        // Use the picture brush to draw what needs to be drawn to the picture
        background.draw(bufG, bird); // Background layer
        foreground.draw(bufG, bird); // Foreground layer
        if (gameState == GAME_READY) { // Game not started
            welcomeAnimation.draw(bufG);
        } else { // Game over
            gameElement.draw(bufG, bird); // Game element layer
        }
        bird.draw(bufG);
        g.drawImage(bufImg, 0, 0, null); 
        // Draw pictures to the screen in one go 

    }

    public static void setGameState(int gameState) {
        Game.gameState = gameState;
    }

}
