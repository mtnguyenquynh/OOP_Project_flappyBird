package app;

import component.Bird;


import component.GameBackground;
import component.GameForeground;
import component.WelcomeAnimation;
import component.GameElementLayer;
import util.Constant;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import static util.Constant.*;

/**
 * Class contains frame and objects in game
 *
 * @author BUILD SUCCESSFULL
 *
 */

public class Game extends Frame {
    private static final long serialVersionUID = 1L;
    private static int gameState; 
    public static final int GAME_READY = 0;
    public static final int GAME_START = 1;
    public static final int GAME_OVER = 2;

    private GameBackground background;
    private GameForeground foreground;
    private Bird bird;
    private GameElementLayer gameElement;
    private WelcomeAnimation welcomeAnimation;

    public Game() {
        initFrame();
        setVisible(true); 
        initGame();
    }

    private void initFrame() {
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setTitle(GAME_TITLE);
        setLocation(FRAME_X, FRAME_Y);
        setResizable(false); 
        setIconImage(Toolkit.getDefaultToolkit().getImage(Constant.ICON_IMG_PATH));
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        addKeyListener(new BirdKeyListener());
    }

    class BirdKeyListener implements KeyListener{
        // Press the button to call different methods based on the game status
        public void keyPressed(KeyEvent e) {
            int keycode = e.getKeyCode();
            switch (gameState) {
                case GAME_READY:
                    if (keycode == KeyEvent.VK_SPACE || keycode == KeyEvent.VK_UP) {
                        bird.birdFlap();
                        bird.birdFall();
                        setGameState(GAME_START);
                    }
                    break;
                case GAME_START:
                    if (keycode == KeyEvent.VK_SPACE || keycode == KeyEvent.VK_UP) {
                        /* Pressing space or up button during the game
                         * will make the wings vibrate once
                         * and continue to be affected by gravity */
                        bird.birdFlap();
                        bird.birdFall();
                    }
                    break;
                case GAME_OVER:
                    if (keycode == KeyEvent.VK_SPACE || keycode == KeyEvent.VK_UP) {
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
        while(true){
        Thread thread = new Thread();
        repaint();
        try {
            Thread.sleep(FPS);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        thread.start();}
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
    @Override
    public void update(Graphics g) {
        Graphics bufG = bufImg.getGraphics();
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
