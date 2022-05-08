package component;

import util.Constant;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * Cloud class, to achieve the logic of cloud drawing and movement
 *
 * @author Kingyu
 */
public class Cloud {

    private final int speed; // Speed
    private int x; // Coordinates
    private final int y;

    private final BufferedImage img;

    private final int scaleImageWidth;
    private final int scaleImageHeight;

    // Constructors
    public Cloud(BufferedImage img, int x, int y) {
        super();
        this.img = img;
        this.x = x;
        this.y = y;
        this.speed = Constant.GAME_SPEED * 2; // Speed of clouds
        // Scale of cloud image scaling 1.0~2.0
        double scale = 1 + Math.random(); // Math.random() returns a random value from 0.0 to 1.0
        // Zooming clouds image
        scaleImageWidth = (int) (scale * img.getWidth());
        scaleImageHeight = (int) (scale * img.getWidth());
    }

    // Drawing method
    public void draw(Graphics g, Bird bird) {
        int speed = this.speed;
        if (bird.isDead())
            speed = 1;
        x -= speed;
        g.drawImage(img, x, y, scaleImageWidth, scaleImageHeight, null);
    }

    /**
     * Determine if the clouds fly off the screen
     *
     * Returns true if it flies out, otherwise false
     */
    public boolean isOutFrame() {
        return x < -1 * scaleImageWidth;
    }

}
