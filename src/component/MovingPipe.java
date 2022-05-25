package component;

import java.awt.Graphics;


import util.Constant;

/**
 * Mobile water pipe class, inherits Pipe class
 *
 * @author BUILD SUCEESFUL
 */

public class MovingPipe extends Pipe {

    private int dealtY; // Coordinates for moving water pipes
    public static final int MAX_DELTA = 50; // Maximum travel distance
    private int direction;
    public static final int DIR_UP = 0;
    public static final int DIR_DOWN = 1;

    // Constructors
    public MovingPipe() {
        super();
    }

    /**
     * Set water pipe parameters
     *
     * @param x:x coordinate
     * @param y:y coordinate
     * @param height：Water pipe height
     * @param type：Water pipe type
     * @param visible：Water pipe visibility
     */
    public void setAttribute(int x, int y, int height, int type, boolean visible) {
        super.setAttribute(x, y, height, type, visible);
        dealtY = 0;
        direction = DIR_DOWN;
        if (type == TYPE_TOP_HARD) {
            direction = DIR_UP;
        }
    }

    // Drawing method
    public void draw(Graphics g, Bird bird) {
        switch (type) {
            case TYPE_HOVER_HARD:
                drawHoverHard(g);
                break;
            case TYPE_TOP_HARD:
                drawTopHard(g);
                break;
            case TYPE_BOTTOM_HARD:
                drawBottomHard(g);
                break;

        }
        // The water pipe stops moving after the bird dies
        if (bird.isDead()) {
            return;
        }
        movement();

        // Drawing collision rectangles
//		g.setColor(Color.black);
//		g.drawRect((int) pipeRect.getX(), (int) pipeRect.getY(), (int) pipeRect.getWidth(), (int) pipeRect.getHeight());
    }

    // Drawing a moving suspended water pipe
    private void drawHoverHard(Graphics g) {
        // Number of splices
        int count = (height - 2 * PIPE_HEAD_HEIGHT) / PIPE_HEIGHT + 1;
        // Draw the upper top of the water pipe
        g.drawImage(imgs[2], x - ((PIPE_HEAD_WIDTH - width) >> 1), y + dealtY, null);
        // Drawing the body of the water pipe
        for (int i = 0; i < count; i++) {
            g.drawImage(imgs[0], x, y + dealtY + i * PIPE_HEIGHT + PIPE_HEAD_HEIGHT, null);
        }
        // Draw the lower bottom of the water pipe
        int y = this.y + height - PIPE_HEAD_HEIGHT;
        g.drawImage(imgs[1], x - ((PIPE_HEAD_WIDTH - width) >> 1), y + dealtY, null);
    }

    // Draw the moving water pipe from top to bottom
    private void drawTopHard(Graphics g) {
        // Number of splices
        int count = (height - PIPE_HEAD_HEIGHT) / PIPE_HEIGHT + 1; // Rounding + 1
        // Drawing the body of the water pipe
        for (int i = 0; i < count; i++) {
            g.drawImage(imgs[0], x, y + dealtY + i * PIPE_HEIGHT, null);
        }
        //  Drawing the top of the water pipe
        g.drawImage(imgs[1], x - ((PIPE_HEAD_WIDTH - width) >> 1),
                height - Constant.TOP_PIPE_LENGTHENING - PIPE_HEAD_HEIGHT + dealtY, null);
    }

    // Draw the moving water pipe from the bottom to the top
    private void drawBottomHard(Graphics g) {
        // Number of splices
        int count = (height - PIPE_HEAD_HEIGHT) / PIPE_HEIGHT + 1;
        // Drawing the body of the water pipe
        for (int i = 0; i < count; i++) {
            g.drawImage(imgs[0], x, Constant.FRAME_HEIGHT - PIPE_HEIGHT - i * PIPE_HEIGHT + dealtY, null);
        }
        // Drawing the top of the water pipe
        g.drawImage(imgs[2], x - ((PIPE_HEAD_WIDTH - width) >> 1), Constant.FRAME_HEIGHT - height + dealtY, null);
    }

    /**
     * Movement logic of movable water pipes
     */
    private void movement() {
        // The motion logic of the x coordinate is the same as that of a normal water pipe
        x -= speed;
        pipeRect.x -= speed;
        if (x < -1 * PIPE_HEAD_WIDTH) {// The water pipe is completely out of the window
            visible = false;
        }

        // Logic of moving water pipes up and down
        if (direction == DIR_DOWN) {
            dealtY++;
            if (dealtY > MAX_DELTA) {
                direction = DIR_UP;
            }
        } else {
            dealtY--;
            if (dealtY <= 0) {
                direction = DIR_DOWN;
            }
        }
        pipeRect.y = this.y + dealtY;
    }

}
