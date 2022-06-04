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
 * @author BUILD SUCCESSFUL
 */
public class GameForeground {
    private final List<Cloud> clouds;
    private final BufferedImage[] cloudImages;
    
    //import clouds( two pictures ) into arraylist , cloudimage will select randomly
    public GameForeground() {
        clouds = new ArrayList<>();
        cloudImages = new BufferedImage[Constant.CLOUD_IMAGE_NUMBER];
        for (int i = 0; i < Constant.CLOUD_IMAGE_NUMBER; i++) {
            cloudImages[i] = GameUtil.loadBufferedImage(Constant.CLOUDS_IMG_PATH[i]);
        }
    }
    //draw picture in clouds(array list) 
    public void draw(Graphics g, Bird bird) {
        cloudBornLogic();
        for (Cloud cloud : clouds) {
            cloud.draw(g, bird);
        }
    }
    
    private void cloudBornLogic() {
      // if the number of cloud less than the rule ( 7 clouds on the screen ) , then continue import til it still satisfied the rule
            if (clouds.size() < Constant.MAX_CLOUD_NUMBER) {
                try {
                    if (GameUtil.isInProbability(Constant.CLOUD_BORN_PERCENT, 100)) { // using probability class 
                        int index = GameUtil.getRandomNumber(0, Constant.CLOUD_IMAGE_NUMBER); // select randomly cloud 

                     //x is the width of frame it's means that the cloud will run from the right of screen 
                        int cloudX = Constant.FRAME_WIDTH; 
                      // y randomly from the top screen to the 1/3  frame height
                        int cloudY = GameUtil.getRandomNumber(Constant.TOP_BAR_HEIGHT, Constant.FRAME_HEIGHT / 3);

                        Cloud cloud = new Cloud(cloudImages[index], cloudX, cloudY);
                        clouds.add(cloud);
                        //// list add cloud
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } 

           // if cloud out of the screen, remove from list 
            for (int i = 0; i < clouds.size(); i++) {
                Cloud tempCloud = clouds.get(i);
                if (tempCloud.isOutFrame()) {
                    clouds.remove(i);
                    i--;
                }
            }
        }
    }

