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
    static BufferedImage[] pipeImage;

    static {
        final int PIPE_IMAGE_COUNT = 3;
        pipeImage = new BufferedImage[PIPE_IMAGE_COUNT];
        for (int i = 0; i < PIPE_IMAGE_COUNT; i++) {
            pipeImage[i] = GameUtil.loadBufferedImage(Constant.PIPE_IMG_PATH[i]);
        }
    }

    // Width and height of all water pipes
    public static final int PIPE_WIDTH = pipeImage[0].getWidth();
    public static final int PIPE_HEIGHT = pipeImage[0].getHeight();
    public static final int PIPE_HEAD_WIDTH = pipeImage[1].getWidth();
    public static final int PIPE_HEAD_HEIGHT = pipeImage[1].getHeight();

    int pipeX, pipeY;
    int width, height;

    boolean visible; // Water pipe visible status, true is visible, false means can be returned to the object pool
    // Type of water pipe
    int type;
    public static final int TYPE_TOP_NORMAL = 0;
    public static final int TYPE_TOP_MOVE = 1;
    public static final int TYPE_BOTTOM_NORMAL = 2;
    public static final int TYPE_BOTTOM_MOVE = 3;
    public static final int TYPE_HOVER_NORMAL = 4;
    public static final int TYPE_HOVER_MOVE = 5;

    int pipeSpeed;

    Rectangle pipeRect; // Collision rectangle of water pipe

    // Constructors
    public Pipe() {
        this.pipeSpeed = Constant.GAME_SPEED;
        this.width = PIPE_WIDTH;

        pipeRect = new Rectangle();
        pipeRect.width = PIPE_WIDTH;
    }

    public void setAttribute(int x, int y, int height, int type, boolean visible) {
        this.pipeX = x;
        this.pipeY = y;
        this.height = height;
        this.type = type;
        this.visible = visible;
        setRectangle(this.pipeX, this.pipeY, this.height);
    }

    /**
     * Set collision rectangle parameters
     */
    public void setRectangle(int x, int y, int height) {
        pipeRect.x = x;
        pipeRect.y = y;
        pipeRect.height = height;
    }

    public boolean isVisible() {
        return visible;
    }

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
        if (bird.isDead()) {
            return;
        }
        movement();
    }

    // Draw the normal water pipe from top to bottom
    private void drawTopNormal(Graphics g) {

        // Number of splices
        int count = (height - PIPE_HEAD_HEIGHT) / PIPE_HEIGHT+1; // Cho nó bao phủ được cột trên
        // Drawing the body of the water pipe
        for (int i = 0; i < count; i++) {
            g.drawImage(pipeImage[0], pipeX, pipeY + i * PIPE_HEIGHT, null);
        }
        // Drawing the top of the water pipe
        // The width of the head of the water pipe is different from the body of the water pipe, and the pipeX coordinate needs to be handled
       g.drawImage(pipeImage[1], pipeX - ((PIPE_HEAD_WIDTH - width) /2),
                height - Constant.TOP_PIPE_LENGTHENING - PIPE_HEAD_HEIGHT, null);
    }

    // Draw the normal water pipe from the bottom to the top
    private void drawBottomNormal(Graphics g) {
        // Number of splices
        int count = (height - PIPE_HEAD_HEIGHT) / PIPE_HEIGHT + 1;
        // Drawing the body of the water pipe
        for (int i = 0; i < count; i++) {
            g.drawImage(pipeImage[0], pipeX, Constant.FRAME_HEIGHT - PIPE_HEIGHT - i * PIPE_HEIGHT,
                    null);
        }
        // Drawing the top of the water pipe
        g.drawImage(pipeImage[2], pipeX - ((PIPE_HEAD_WIDTH - width) >> 1), Constant.FRAME_HEIGHT - height, null);
    }

    // Drawing the hover normal water pipe
    private void drawHoverNormal(Graphics g) {
        // Number of splices
        int count = (height - 2 * PIPE_HEAD_HEIGHT) / PIPE_HEIGHT + 1;
        // Draw the upper top of the water pipe
        g.drawImage(pipeImage[2], pipeX - ((PIPE_HEAD_WIDTH - width) /2), pipeY, null);
        // Drawing the body of the water pipe
        for (int i = 0; i < count; i++) {
            g.drawImage(pipeImage[0], pipeX, pipeY + i * PIPE_HEIGHT + PIPE_HEAD_HEIGHT, null);
        //Draw the lower bottom of the water pipe
        int y = this.pipeY + height - PIPE_HEAD_HEIGHT;
        g.drawImage(pipeImage[1], pipeX - ((PIPE_HEAD_WIDTH - width) /2), y, null);
    }}

    /**
     * Movement logic of ordinary water pipes
     */
    private void movement() {
        pipeX -= pipeSpeed;
        pipeRect.x -= pipeSpeed;
        if(pipeX <-1*PIPE_HEAD_WIDTH){
            visible = false;
        }
    }

    public boolean isInFrame() {
        return pipeX + width < Constant.FRAME_WIDTH;
    }

    public int getPipeX() {
        return pipeX;
    }

    public Rectangle getPipeRect() {
        return pipeRect;
    }
}
