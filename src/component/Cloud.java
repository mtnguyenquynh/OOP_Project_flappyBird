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

    private int cloudX;
    private final int cloudY;
    private final int cloudSpeed;

    private final BufferedImage cloudImg;

    private final int cloudImageHeight;
    private final int cloudImageWidth;

    // Constructors
    public Cloud(BufferedImage img, int x, int y) {
        super();
        this.cloudX = x;
        this.cloudY = y;
        this.cloudImg = img;
        this.cloudSpeed = Constant.GAME_SPEED*2;
        // Scale of cloud image scaling 1.0~2.0
        double scale = 1 + Math.random();
        // Zooming clouds image
        cloudImageHeight = (int) (scale * img.getWidth());
        cloudImageWidth = (int) (scale * img.getWidth());
    }

    // Method draw the cloud
    public void draw(Graphics g, Bird bird) {
        int speed = this.cloudSpeed;
        if (bird.isDead())
            speed = 1;
        cloudX -= speed;
        g.drawImage(cloudImg, cloudX, cloudY, cloudImageWidth, cloudImageHeight, null);
    }
     // Check the clouds fly off the screen , true if out of the screen, otherwise false
    public boolean isOutFrame() {
        return cloudX < -1 * cloudImageWidth;
    }
}
