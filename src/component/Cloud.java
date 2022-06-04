package component;

import util.Constant;


import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * Cloud class, to achieve the logic of cloud drawing and movement
 *
 * @author BUILD SUCCESSFUL
 */
public class Cloud {

    private final int cloudSpeed;
    private int cloudX;
    private final int cloudY;

    private final BufferedImage cloudImg;

    private final int cloudImageWidth;
    private final int cloudImageHeight;

    // Constructors
    public Cloud(BufferedImage img, int x, int y) {
        super();
        this.cloudImg = img;
        this.cloudX = x;
        this.cloudY = y;
        this.cloudSpeed = Constant.GAME_SPEED*2;
        // Scale of cloud image scaling 1.0~2.0
        double scale = 1 + Math.random();
        // Zooming clouds image
        cloudImageWidth = (int) (scale * img.getWidth());
        cloudImageHeight = (int) (scale * img.getWidth());
    }

    // Drawing method
    public void draw(Graphics g, Bird bird) {
        int speed = this.cloudSpeed;
        if (bird.isDead())
            speed = 1;
        cloudX -= speed;
        g.drawImage(cloudImg, cloudX, cloudY, cloudImageWidth, cloudImageHeight, null);
    }

    /**
     * Determine if the clouds fly off the screen
     *
     * Returns true if it flies out, otherwise false
     */
    public boolean isOutFrame() {
        return cloudX < -1 * cloudImageWidth;
    }

}
