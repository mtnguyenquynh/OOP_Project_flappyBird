package component;

import java.awt.Graphics;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import util.Constant;
import util.GameUtil;

/**
 * Foreground layer, currently managing the cloud generation 
 * logic and drawing the clouds in the container
 *
 * @author Kingyu
 */
public class GameForeground {
    private final List<Cloud> clouds; // Store clouds into List
    private final BufferedImage[] cloudImages; // Image Resources
    private long time; // Control the logical computing cycle of the cloud
    public static final int CLOUD_INTERVAL = 100; // Period of logical operations for cloud refreshing

    public GameForeground() {
        clouds = new ArrayList<>(); //Container for clouds
        // Read in image resources
        cloudImages = new BufferedImage[Constant.CLOUD_IMAGE_COUNT];
        for (int i = 0; i < Constant.CLOUD_IMAGE_COUNT; i++) {
            cloudImages[i] = GameUtil.loadBufferedImage(Constant.CLOUDS_IMG_PATH[i]);
        }
        time = System.currentTimeMillis(); // Get the current time, which is used to control the logical operation cycle of the cloud
    }

    // Drawing method
    public void draw(Graphics g, Bird bird) {
        cloudBornLogic();
        for (Cloud cloud : clouds) {
            cloud.draw(g, bird);
        }
    }

    // Control of clouds
    private void cloudBornLogic() {
        // 100ms calculation once
        if (System.currentTimeMillis() - time > CLOUD_INTERVAL) {
            time = System.currentTimeMillis(); // Reset time
            // If the number of clouds on the screen is less than the maximum number allowed, add clouds randomly according to the given probability
            if (clouds.size() < Constant.MAX_CLOUD_COUNT) {
                try {
                    if (GameUtil.isInProbability(Constant.CLOUD_BORN_PERCENT, 100)) { // Add clouds according to the given probability
                        int index = GameUtil.getRandomNumber(0, Constant.CLOUD_IMAGE_COUNT); // Randomly selected cloud images

                        // Coordinates of the cloud refresh
                        int x = Constant.FRAME_WIDTH; // Refresh from the left side of the screen
                        // The y coordinate is randomly selected in the upper 1/3 screen
                        int y = GameUtil.getRandomNumber(Constant.TOP_BAR_HEIGHT, Constant.FRAME_HEIGHT / 3);

                        // Adding clouds to the container
                        Cloud cloud = new Cloud(cloudImages[index], x, y);
                        clouds.add(cloud);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } // Add clouds

            // Remove the cloud from the container if it flies off the screen
            for (int i = 0; i < clouds.size(); i++) {
                // Iterate over the clouds in the container
                Cloud tempCloud = clouds.get(i);
                if (tempCloud.isOutFrame()) {
                    clouds.remove(i);
                    i--;
                }
            }
        }
    }
}
