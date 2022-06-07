package component;

import java.awt.Graphics;

import util.Constant;

/**
 * Moving water pipe class, inherits Pipe class
 *
 * @author BUILD SUCEESSFUL
 */

public class MovingPipe extends Pipe {

    private int deltaY; // Coordinates for moving water pipes
    public static final int MAX_DELTA = 50; // Maximum travel distance
    private int direction;
    public static final int DIRECTION_UP = 0;
    public static final int DIRECTION_DOWN = 1;

    // Constructors
    public MovingPipe() {
        super();
    }

    // Attribute of each water pipe parameter 
    public void setAttribute(int x, int y, int height, int type, boolean visible) {
        super.setAttribute(x, y, height, type, visible);
        deltaY = 0;
        direction = DIRECTION_DOWN;
        if (type == TYPE_TOP_MOVE) {
            direction = DIRECTION_UP;
        }
    }

    // Drawing method
    public void draw(Graphics g, Bird bird) {
        switch (type) {
            case TYPE_HOVER_MOVE:
                drawHoverHard(g);
                break;
            case TYPE_TOP_MOVE:
                drawTopHard(g);
                break;
            case TYPE_BOTTOM_MOVE:
                drawBottomHard(g);
                break;

        }
        //The water pipe stops moving after the bird dies
        if (bird.isDead()) {
            return;
        }
        movement();
    }

    // Drawing a moving hover pipe
    private void drawHoverHard(Graphics g) {
        // Number of splices
        int count = (height - 2 * PIPE_HEAD_HEIGHT) / PIPE_HEIGHT + 1;
        // Draw the upper top of the water pipe
        g.drawImage(pipeImage[2], pipeX - ((PIPE_HEAD_WIDTH - width)/2), pipeY + deltaY, null);
        // Drawing the body of the water pipe
        for (int i = 0; i < count; i++) {
            g.drawImage(pipeImage[0], pipeX, pipeY + deltaY + i * PIPE_HEIGHT + PIPE_HEAD_HEIGHT, null);
        }
        // Draw the lower bottom of the water pipe
        int y = this.pipeY + height - PIPE_HEAD_HEIGHT;
        g.drawImage(pipeImage[1], pipeX - ((PIPE_HEAD_WIDTH - width) /2), y + deltaY, null);
    }

    // Draw the moving water pipe from top to bottom
    private void drawTopHard(Graphics g) {
        // Number of splices
        int count = (height - PIPE_HEAD_HEIGHT) / PIPE_HEIGHT + 1; // Rounding + 1
        // Drawing the body of the water pipe
        for (int i = 0; i < count; i++) {
            g.drawImage(pipeImage[0], pipeX, pipeY + deltaY + i * PIPE_HEIGHT, null);
        }
        //  Drawing the top of the water pipe
        g.drawImage(pipeImage[1], pipeX - ((PIPE_HEAD_WIDTH - width)/2),
                height - Constant.TOP_PIPE_LENGTHENING - PIPE_HEAD_HEIGHT + deltaY, null);
    }

    // Draw the moving water pipe from bottom to top
    private void drawBottomHard(Graphics g) {
        // Number of splices
        int count = (height - PIPE_HEAD_HEIGHT) / PIPE_HEIGHT + 1;
        // Drawing the body of the water pipe
        for (int i = 0; i < count; i++) {
            g.drawImage(pipeImage[0], pipeX, Constant.FRAME_HEIGHT - PIPE_HEIGHT - i * PIPE_HEIGHT + deltaY, null);
        }
        // Drawing the top of the water pipe
        g.drawImage(pipeImage[2], pipeX - ((PIPE_HEAD_WIDTH - width)/2), Constant.FRAME_HEIGHT - height + deltaY, null);
    }

    // Movement logic of water pipes
    private void movement() {
        // The motion logic of the pipeX coordinate is the same as that of a normal water pipe
        pipeX -= pipeSpeed;
        pipeRect.x -= pipeSpeed;
        if (pipeX < -1 * PIPE_HEAD_WIDTH) {// The water pipe is completely out of the window
            visible = false;
        }

        // Logic of moving water pipes up and down
        if (direction == DIRECTION_DOWN) {
            deltaY++;
            if (deltaY > MAX_DELTA) {
                direction = DIRECTION_UP;
            }
        } else {
            deltaY--;
            if (deltaY <= 0) {
                direction = DIRECTION_DOWN;
            }
        }
        pipeRect.y = this.pipeY + deltaY;
    }

}
