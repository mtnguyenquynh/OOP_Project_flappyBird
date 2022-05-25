package component;

import java.awt.*;


import java.awt.image.BufferedImage;

import util.Constant;
import util.GameUtil;

/**
 * Water pipe class, to achieve the drawing and 
 * movement logic of the water pipe
 *
 * @author BUILD SUCCESSFUL
 */
public class Pipe {
    static BufferedImage[] imgs; // Pictures of water pipes, static ensures that the picture is loaded only once

    static {// Static code block, when the class is loaded, initialize the image
        final int PIPE_IMAGE_COUNT = 3;
        imgs = new BufferedImage[PIPE_IMAGE_COUNT];
        for (int i = 0; i < PIPE_IMAGE_COUNT; i++) {
            imgs[i] = GameUtil.loadBufferedImage(Constant.PIPE_IMG_PATH[i]);
        }
    }

    // Width and height of all water pipes
    public static final int PIPE_WIDTH = imgs[0].getWidth();
    public static final int PIPE_HEIGHT = imgs[0].getHeight();
    public static final int PIPE_HEAD_WIDTH = imgs[1].getWidth();
    public static final int PIPE_HEAD_HEIGHT = imgs[1].getHeight();

    int x, y; // Coordinates of the water pipe, relative to the element layer
    int width, height; // Width of water pipe, height

    boolean visible; // Water pipe visible status, true is visible, false means can be returned to the object pool
    // Type of water pipe
    int type;
    public static final int TYPE_TOP_NORMAL = 0;
    public static final int TYPE_TOP_HARD = 1;
    public static final int TYPE_BOTTOM_NORMAL = 2;
    public static final int TYPE_BOTTOM_HARD = 3;
    public static final int TYPE_HOVER_NORMAL = 4;
    public static final int TYPE_HOVER_HARD = 5;

    // Speed of water pipes
    int speed;

    Rectangle pipeRect; // Collision rectangle of water pipe

    // Constructors
    public Pipe() {
        this.speed = Constant.GAME_SPEED;
        this.width = PIPE_WIDTH;

        pipeRect = new Rectangle();
        pipeRect.width = PIPE_WIDTH;
    }

    /**
     * Set water pipe parameters
     *
     * @param x: x coordinate
     * @param y：y coordinate
     * @param height：Water pipe height
     * @param type：Water pipe type
     * @param visible：Water pipe visibility
     */
    public void setAttribute(int x, int y, int height, int type, boolean visible) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.type = type;
        this.visible = visible;
        setRectangle(this.x, this.y, this.height);
    }

    /**
     * Set collision rectangle parameters
     */
    public void setRectangle(int x, int y, int height) {
        pipeRect.x = x;
        pipeRect.y = y;
        pipeRect.height = height;
    }

    // Determine if a water pipe is located in a window
    public boolean isVisible() {
        return visible;
    }

    //  Drawing method
    public void draw(Graphics g, Bird bird) {
        switch (type) {
            case TYPE_TOP_NORMAL:
                drawTopNormal(g);
                break;
            case TYPE_BOTTOM_NORMAL:
                drawBottomNormal(g);
                break;
            case TYPE_HOVER_NORMAL:
                drawHoverNormal(g);
                break;
        }
//      Drawing collision rectangles
//      g.setColor(Color.black);
//      g.drawRect((int) pipeRect.getX(), (int) pipeRect.getY(), (int) pipeRect.getWidth(), (int) pipeRect.getHeight());

        // The water pipe stops moving after the bird dies
        if (bird.isDead()) {
            return;
        }
        movement();
    }

    // Draw the common water pipe from top to bottom
    private void drawTopNormal(Graphics g) {
        // Number of splices
        int count = (height - PIPE_HEAD_HEIGHT) / PIPE_HEIGHT + 1; // Rounding + 1
        // Drawing the body of the water pipe
        for (int i = 0; i < count; i++) {
            g.drawImage(imgs[0], x, y + i * PIPE_HEIGHT, null);
        }
        // Drawing the top of the water pipe
        g.drawImage(imgs[1], x - ((PIPE_HEAD_WIDTH - width) >> 1),
                height - Constant.TOP_PIPE_LENGTHENING - PIPE_HEAD_HEIGHT, null); // The width of the head of the water pipe is different from the body of the water pipe, and the x coordinate needs to be handled
    }

    // Draw the common water pipe from the bottom to the top
    private void drawBottomNormal(Graphics g) {
        // Number of splices
        int count = (height - PIPE_HEAD_HEIGHT - Constant.GROUND_HEIGHT) / PIPE_HEIGHT + 1;
        // Drawing the body of the water pipe
        for (int i = 0; i < count; i++) {
            g.drawImage(imgs[0], x, Constant.FRAME_HEIGHT - PIPE_HEIGHT - Constant.GROUND_HEIGHT - i * PIPE_HEIGHT,
                    null);
        }
        // Drawing the top of the water pipe
        g.drawImage(imgs[2], x - ((PIPE_HEAD_WIDTH - width) >> 1), Constant.FRAME_HEIGHT - height, null);
    }

    // Drawing the suspended common water pipe
    private void drawHoverNormal(Graphics g) {
        // Number of splices
        int count = (height - 2 * PIPE_HEAD_HEIGHT) / PIPE_HEIGHT + 1;
        // Draw the upper top of the water pipe
        g.drawImage(imgs[2], x - ((PIPE_HEAD_WIDTH - width) >> 1), y, null);
        // Drawing the body of the water pipe
        for (int i = 0; i < count; i++) {
            g.drawImage(imgs[0], x, y + i * PIPE_HEIGHT + PIPE_HEAD_HEIGHT, null);
        }
        // Draw the lower bottom of the water pipe
        int y = this.y + height - PIPE_HEAD_HEIGHT;
        g.drawImage(imgs[1], x - ((PIPE_HEAD_WIDTH - width) >> 1), y, null);
    }

    /**
     * Movement logic of ordinary water pipes
     */
    private void movement() {
        x -= speed;
        pipeRect.x -= speed;
        if (x < -1 * PIPE_HEAD_WIDTH) {// The water pipe is completely out of the window
            visible = false;
        }
    }

    /**
     * Determine if the current water pipe is fully present in the window
     *
     * @return Returns true if fully present, false otherwise
     */
    public boolean isInFrame() {
        return x + width < Constant.FRAME_WIDTH;
    }

    // Get the x coordinate of the water pipe
    public int getX() {
        return x;
    }

    // Get the collision rectangle of the water pipe
    public Rectangle getPipeRect() {
        return pipeRect;
    }

}
