package util;

import java.awt.*;


import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

import javax.imageio.ImageIO;

/**
 * Tools, the tools used in the game are in this category
 *
 * @author BUILD SUCCESSFUL
 */
public class GameUtil {

    private GameUtil() {
    } // Privatize to prevent other classes from instantiating such

    /**
     * Method of loading images
     *
     * @param imgPath Image path
     * @return Image Resources
     */
    public static BufferedImage loadBufferedImage(String imgPath) {
        try {
            return ImageIO.read(new FileInputStream(imgPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Determine whether a probabilistic event of arbitrary probability occurs
     *
     * @param numerator numerator, not less than the value of 0
     * @param denominator Denominator, not less than the value of 0
     * @return Returns true if a probabilistic event occurs, false otherwise
     */
    public static boolean isInProbability(int numerator, int denominator) throws Exception {
        // The numerator and denominator are not less than 0
        if (numerator <= 0 || denominator <= 0) {
            throw new Exception("The numerator and denominator are not less than 0");
        }
        // The numerator is greater than the denominator and must occur
        if (numerator >= denominator) {
            return true;
        }

        return getRandomNumber(1, denominator + 1) <= numerator;
    }

    /**
     * Returns a random number in the specified interval
     *
     * @param min The minimum value of the interval, containing
     * @param max The maximum value of the interval, not including
     * @return The random number of this interval
     */
    public static int getRandomNumber(int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }

    /**
     * Get the width and height of the specified string in the specified font
     */
    public static int getStringWidth(Font font, String str) {
        AffineTransform affinetransform = new AffineTransform();
        FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
        return (int) (font.getStringBounds(str, frc).getWidth());
    }

    public static int getStringHeight(Font font, String str) {
        AffineTransform affinetransform = new AffineTransform();
        FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
        return (int) (font.getStringBounds(str, frc).getHeight());
    }


    /**
     *
     * @param image Image Resources
     * @param x is x coordinate
     * @param y is y coordinate
     * @param g is brushes
     */
    public static void drawImage(BufferedImage image, int x, int y, Graphics g) {
        g.drawImage(image, x, y, null);
    }

}
