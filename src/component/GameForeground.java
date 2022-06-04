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
    
    //truyền vào các hình ảnh trang trí thông qua array list , cloudimage sẽ lấy theo thứ tự hình ảnh 1 và 2 ( tổng hai hình ảnh trang trí )
    public GameForeground() {
        clouds = new ArrayList<>();
        cloudImages = new BufferedImage[Constant.CLOUD_IMAGE_NUMBER];
        for (int i = 0; i < Constant.CLOUD_IMAGE_NUMBER; i++) {
            cloudImages[i] = GameUtil.loadBufferedImage(Constant.CLOUDS_IMG_PATH[i]);
        }
    }
    //draw từ array list clouds phía trên
    public void draw(Graphics g, Bird bird) {
        cloudBornLogic();
        for (Cloud cloud : clouds) {
            cloud.draw(g, bird);
        }
    }
    
    private void cloudBornLogic() {
        //nếu mà số lượng các hình ảnh trang trí trên screen ít hơn cái tỉ số tối đa, thì truyền ngẫu nhiên các hình ảnh,  miễn saothõa điều kiện
            if (clouds.size() < Constant.MAX_CLOUD_NUMBER) {
                try {
                    if (GameUtil.isInProbability(Constant.CLOUD_BORN_PERCENT, 100)) { // một class probability thực hiện nhiệm vụ 
                        int index = GameUtil.getRandomNumber(0, Constant.CLOUD_IMAGE_NUMBER); // ngẫu nhiên chọn các hình ảnh trang trí

                     //tọa độ x ,y , x là trải dài ngang toàn màn hình, y chỉ lấy tọa độ từ trên màn hình xuống( 1/3 màn hình )
                        int cloudX = Constant.FRAME_WIDTH;
                      
                        int cloudY = GameUtil.getRandomNumber(Constant.TOP_BAR_HEIGHT, Constant.FRAME_HEIGHT / 3);

                        Cloud cloud = new Cloud(cloudImages[index], cloudX, cloudY);
                        clouds.add(cloud);
                        //// xuất hình ảnh trang trí theo các tọa độ đã cho
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } 

           // khi hình ảnh trang trí vừa ra khỏi màn hình  bên trái thì remove
            for (int i = 0; i < clouds.size(); i++) {
                Cloud tempCloud = clouds.get(i);
                if (tempCloud.isOutFrame()) {
                    clouds.remove(i);
                    i--;
                }
            }
        }
    }

