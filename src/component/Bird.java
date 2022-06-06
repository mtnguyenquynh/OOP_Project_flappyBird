package component;

import java.awt.Color;



import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import app.Game;
import util.Constant;
import util.GameUtil;
import util.Sound;


/**
 * Small bird class, implement 
 * the logic of drawing and flying of small birds
 * 
 * @author BUILD SUCCESSFUL
 */
public class Bird {
    public static final int IMG_TOTAL_NUMBER = 8;
    public static final int STATE_NUMBER = 4;
    private final BufferedImage[][] birdImages;
    private final int birdX;
    private int birdY;
    private int wingState;

    // Image resources
    private BufferedImage image;

    // Status of the bird
    private int birdState;
    public static final int BIRD_NORMAL = 0;
    public static final int BIRD_UP = 1;
    public static final int BIRD_FALL = 2;
    public static final int BIRD_DEAD_FALL = 3;
    public static final int BIRD_DEAD = 4;

    private final Rectangle birdCollisionRect;
    public static final int RECT_DESCALE = 2;
    private final component.ScoreCounter scoreCounter;
    private final component.GameOverAnimation gameOverAnimation;

    public static int BIRD_WIDTH;
    public static int BIRD_HEIGHT;

    // Initialization of resources in the constructor
    public Bird() {
        scoreCounter = ScoreCounter.getInstance();
        gameOverAnimation = new GameOverAnimation();
        // Read birdie image resources
        birdImages = new BufferedImage[STATE_NUMBER][IMG_TOTAL_NUMBER];
        for (int i = 0; i < STATE_NUMBER; i++) {
            for (int j = 0; j < IMG_TOTAL_NUMBER; j++) {
                birdImages[i][j] = GameUtil.loadBufferedImage(Constant.BIRDS_IMG_PATH[i][j]);
            }
        }

        assert birdImages[0][0] != null;
        BIRD_WIDTH = birdImages[0][0].getWidth();
        BIRD_HEIGHT = birdImages[0][0].getHeight();

        // Initialize the coordinates of the bird
        birdX = Constant.FRAME_WIDTH/4;
        birdY = Constant.FRAME_HEIGHT/2;

        // Initialize collision rectangle
        int rectX = birdX - BIRD_WIDTH/2;
        int rectY = birdY - BIRD_HEIGHT/2;
        birdCollisionRect = new Rectangle(rectX + RECT_DESCALE, rectY + RECT_DESCALE * 2, BIRD_WIDTH - RECT_DESCALE * 3,
                BIRD_WIDTH - RECT_DESCALE * 4);
    }

    // Drawing method
    public void draw(Graphics g) {
        movement();
        int state_index = Math.min(birdState, BIRD_DEAD_FALL);

        // Birdie center point calculation
        int halfImgWidth = birdImages[state_index][0].getWidth()/2;
        int halfImgHeight = birdImages[state_index][0].getHeight()/2;

        // Movement when bird goes up
        if (birdVelocity > 0)
            image = birdImages[BIRD_UP][0];
        
        g.drawImage(image, birdX - halfImgWidth, birdY - halfImgHeight, null); //Draw the bird at pipeX coordinate is at 1/4 of the window and the y coordinate is at the center of the window

        if (birdState == BIRD_DEAD)
            gameOverAnimation.draw(g, this);
        else if (birdState != BIRD_DEAD_FALL)
            drawScore(g);
     }

    public static final int ACC_FLAP = 14; // players speed on flapping
    public static final double ACC_Y = 2; // players downward acceleration
    public static final int MAX_VEL_Y = 15; // max birdVelocity along Y, max descend speed
    private int birdVelocity = 0; // bird's birdVelocity along Y, default same as playerFlapped
    private final int BOTTOM_BOUNDARY = Constant.FRAME_HEIGHT;

    // The method showing how the bird move
    private void movement() {
        // Wings birdState, to achieve a small bird wing flight
        wingState++;
        image = birdImages[Math.min(birdState, BIRD_DEAD_FALL)][wingState / 10 % IMG_TOTAL_NUMBER];
        if (birdState == BIRD_FALL || birdState == BIRD_DEAD_FALL) {
            freeFall();
            if (birdCollisionRect.y > BOTTOM_BOUNDARY) {
                if (birdState == BIRD_FALL) {
                    Sound.playCrash();
                }
                die();
            }
        }
    }

    private void freeFall() {
        if (birdVelocity < MAX_VEL_Y)
            birdVelocity -= ACC_Y;
        birdY = Math.min((birdY - birdVelocity), BOTTOM_BOUNDARY);
        birdCollisionRect.y = birdCollisionRect.y - birdVelocity;
    }

    private void die() {
        scoreCounter.saveScore();
        birdState = BIRD_DEAD;
        Game.setGameState(Game.GAME_OVER);
    }

    // User press button to control the bird flapping
    public void birdFlap() {
        if (keyIsReleased()) {
            if (isDead())
                return;
            Sound.playFly();
            birdState = BIRD_UP;
            if (birdCollisionRect.y > Constant.TOP_BAR_HEIGHT) {
                birdVelocity = ACC_FLAP; // Changing the speed to upward speed with each wing vibration
                wingState = 0; // Reset Wing Status
            }
            keyPressed();
        }
    }

    // Birdie fall
    public void birdFall() {
        if (isDead())
            return;
        birdState = BIRD_FALL;
    }

    // Bird hit the pipe and fall
    public void deadBirdFall() {
        birdState = BIRD_DEAD_FALL;
        Sound.playCrash();
        birdVelocity = 0;  // Speed is set to 0 to prevent the bird from continuing to rise and overlap with the water pipe
    }

    // Determining if a bird is dead
    public boolean isDead() {
        return birdState == BIRD_DEAD_FALL || birdState == BIRD_DEAD;
    }

    private void drawScore(Graphics g) {
        g.setColor(Color.white);
        g.setFont(Constant.CURRENT_SCORE_FONT);
        String str = Long.toString(scoreCounter.getCurrentScore());
        int x = Constant.FRAME_WIDTH/2-15;
        g.drawString(str, x, Constant.FRAME_HEIGHT / 10);
    }

    // Reset Bird
    public void reset() {
        birdState = BIRD_NORMAL;
        birdY = Constant.FRAME_HEIGHT/2;
        birdVelocity = 0;

        int ImgHeight = birdImages[birdState][0].getHeight();
        birdCollisionRect.y = birdY - ImgHeight / 2 + RECT_DESCALE * 2;
        scoreCounter.reset(); // Resetting the scorer
    }

    private boolean keyFlag = true;
    public void keyPressed() {
        keyFlag = false;
    }

    public void keyReleased() {
        keyFlag = true;
    }

    public boolean keyIsReleased() {
        return keyFlag;
    }

    public long getCurrentScore() {
        return scoreCounter.getCurrentScore();
    }

    public long getBestScore() {
        return scoreCounter.getBestScore();
    }

    public int getBirdX() {
        return birdX;
    }

    // Get the collision rectangle of the bird
    public Rectangle getBirdCollisionRect() {
        return birdCollisionRect;
    }
}
