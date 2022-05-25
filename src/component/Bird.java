package component;

import java.awt.Color;



import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import app.Game;
import util.Constant;
import util.GameUtil;

/**
 * Small bird class, implement 
 * the logic of drawing and flying of small birds
 * 
 * @author BUILD SUCCESSFUL
 */
public class Bird {
    public static final int IMG_COUNT = 8; // Number of images
    public static final int STATE_COUNT = 4; // Status number
    private final BufferedImage[][] birdImages; // Image array object of birds
    private final int x;
    private int y; // Coordinates of the bird
    private int wingState; // Wing status

    // Image resources
    private BufferedImage image; // Real time bird images

    // Status of the bird
    private int state;
    public static final int BIRD_NORMAL = 0;
    public static final int BIRD_UP = 1;
    public static final int BIRD_FALL = 2;
    public static final int BIRD_DEAD_FALL = 3;
    public static final int BIRD_DEAD = 4;

    private final Rectangle birdCollisionRect; // Collision rectangle
    public static final int RECT_DESCALE = 2; // Parameters to compensate for the width and height of the collision rectangle
    private final ScoreCounter counter; // Scorer
    private final GameOverAnimation gameOverAnimation;

    public static int BIRD_WIDTH;
    public static int BIRD_HEIGHT;

    // Initialization of resources in the constructor
    public Bird() {
        counter = ScoreCounter.getInstance(); // Scorer
        gameOverAnimation = new GameOverAnimation();

        // Read birdie image resources
        birdImages = new BufferedImage[STATE_COUNT][IMG_COUNT];
        for (int i = 0; i < STATE_COUNT; i++) {
            for (int j = 0; j < IMG_COUNT; j++) {
                birdImages[i][j] = GameUtil.loadBufferedImage(Constant.BIRDS_IMG_PATH[i][j]);
            }
        }

        assert birdImages[0][0] != null;
        BIRD_WIDTH = birdImages[0][0].getWidth();
        BIRD_HEIGHT = birdImages[0][0].getHeight();

        // Initialize the coordinates of the bird
        x = Constant.FRAME_WIDTH >> 2;
        y = Constant.FRAME_HEIGHT >> 1;

        // Initialize collision rectangle
        int rectX = x - BIRD_WIDTH / 2;
        int rectY = y - BIRD_HEIGHT / 2;
        birdCollisionRect = new Rectangle(rectX + RECT_DESCALE, rectY + RECT_DESCALE * 2, BIRD_WIDTH - RECT_DESCALE * 3,
                BIRD_WIDTH - RECT_DESCALE * 4); // The coordinates of the collision rectangle are the same as those of the bird
    }

    // Drawing method
    public void draw(Graphics g) {
        movement();
        int state_index = Math.min(state, BIRD_DEAD_FALL); // Image Resource Index
        // Birdie center point calculation
        int halfImgWidth = birdImages[state_index][0].getWidth() >> 1;
        int halfImgHeight = birdImages[state_index][0].getHeight() >> 1;
        if (velocity > 0)
            image = birdImages[BIRD_UP][0];
        g.drawImage(image, x - halfImgWidth, y - halfImgHeight, null); // The x coordinate is at 1/4 of the window and the y coordinate is at the center of the window.

        if (state == BIRD_DEAD)
            gameOverAnimation.draw(g, this);
        else if (state != BIRD_DEAD_FALL)
            drawScore(g);
        // Drawing collision rectangles
//      g.setColor(Color.black);
//      g.drawRect((int) birdRect.getX(), (int) birdRect.getY(), (int) birdRect.getWidth(), (int) birdRect.getHeight());
    }

    public static final int ACC_FLAP = 14; // players speed on flapping
    public static final double ACC_Y = 2; // players downward acceleration
    public static final int MAX_VEL_Y = 15; // max velocity along Y, max descend speed
    private int velocity = 0; // bird's velocity along Y, default same as playerFlapped
    private final int BOTTOM_BOUNDARY = 550;//Constant.FRAME_HEIGHT - GameBackground.GROUND_HEIGHT - (BIRD_HEIGHT / 2);

    // The flight logic of a small bird
    private void movement() {
        // Wings state, to achieve a small bird wing flight
        wingState++;
        image = birdImages[Math.min(state, BIRD_DEAD_FALL)][wingState / 10 % IMG_COUNT];
        if (state == BIRD_FALL || state == BIRD_DEAD_FALL) {
            freeFall();
            if (birdCollisionRect.y > BOTTOM_BOUNDARY) {
                if (state == BIRD_FALL) {
                }
                die();
            }
        }
    }

    private void freeFall() {
        if (velocity < MAX_VEL_Y)
            velocity -= ACC_Y;
        y = Math.min((y - velocity), BOTTOM_BOUNDARY);
        birdCollisionRect.y = birdCollisionRect.y - velocity;
    }

    private void die() {
        counter.saveScore();
        state = BIRD_DEAD;
        Game.setGameState(Game.GAME_OVER);
    }

    // Birdie on the wing
    public void birdFlap() {
        if (keyIsReleased()) {
            if (isDead())
                return;
            state = BIRD_UP;
            if (birdCollisionRect.y > Constant.TOP_BAR_HEIGHT) {
                velocity = ACC_FLAP; // Changing the speed to upward speed with each wing vibration
                wingState = 0; // Reset Wing Status
            }
            keyPressed();
        }
    }

    // Birdie drop
    public void birdFall() {
        if (isDead())
            return;
        state = BIRD_FALL;
    }

    // Little bird falling (dead)
    public void deadBirdFall() {
        state = BIRD_DEAD_FALL;
        velocity = 0;  // Speed is set to 0 to prevent the bird from continuing to rise and overlap with the water pipe
    }

    // Determining if a bird is dead
    public boolean isDead() {
        return state == BIRD_DEAD_FALL || state == BIRD_DEAD;
    }

    // Plotting real-time scores
    private void drawScore(Graphics g) {
        g.setColor(Color.white);
        g.setFont(Constant.CURRENT_SCORE_FONT);
        String str = Long.toString(counter.getCurrentScore());
        int x = Constant.FRAME_WIDTH - GameUtil.getStringWidth(Constant.CURRENT_SCORE_FONT, str) >> 1;
        g.drawString(str, x, Constant.FRAME_HEIGHT / 10);
    }

    // Reset Birdie
    public void reset() {
        state = BIRD_NORMAL; // Bird status
        y = Constant.FRAME_HEIGHT >> 1; // Bird coordinates
        velocity = 0; // Birdie Speed

        int ImgHeight = birdImages[state][0].getHeight();
        birdCollisionRect.y = y - ImgHeight / 2 + RECT_DESCALE * 2; // Bird collision rectangle coordinates

        counter.reset(); // Resetting the scorer
    }

    private boolean keyFlag = true; // Keystroke status, true is released so that the method is not repeatedly called when the key is held down

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
        return counter.getCurrentScore();
    }

    public long getBestScore() {
        return counter.getBestScore();
    }

    public int getBirdX() {
        return x;
    }

    // Get the collision rectangle of the bird
    public Rectangle getBirdCollisionRect() {
        return birdCollisionRect;
    }
}
