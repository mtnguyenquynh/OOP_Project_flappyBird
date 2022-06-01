package component;

import java.awt.Graphics;


import java.awt.image.BufferedImage;

import util.Constant;
import util.GameUtil;

/**
 * Game background class, to achieve the drawing of the game background
 * 
 * @author BUILD SUCCESSFUL
 *
 */
public class GameBackground {

	private static final BufferedImage BackgroundImg;

	private final int backGroundSpeed;
	private int layerX; // Coordinates of the background layer

	public static final int GROUND_HEIGHT;

	static {
		BackgroundImg = GameUtil.loadBufferedImage(Constant.BG_IMG_PATH);
		assert BackgroundImg != null;
		GROUND_HEIGHT = BackgroundImg.getHeight() / 2;
	}

	public GameBackground() {
		this.backGroundSpeed = Constant.GAME_SPEED;
		this.layerX = 0;
	}

	public void draw(Graphics g, Bird bird) {

		g.fillRect(0, 0, Constant.FRAME_WIDTH, Constant.FRAME_HEIGHT);

		int backgroundimgWidth = BackgroundImg.getWidth();
		int imgHeight = BackgroundImg.getHeight();

		int count = Constant.FRAME_WIDTH / backgroundimgWidth + 2; //  Get the number of times the image is drawn based on the window width
		for (int i = 0; i < count; i++) {
			g.drawImage(BackgroundImg, backgroundimgWidth * i - layerX, Constant.FRAME_HEIGHT - imgHeight, null);
		}
		if(bird.isDead()) {  
			return;
		}
		movement();
	}

	// Motion logic of the background layer
	private void movement() {
		layerX += backGroundSpeed;
		if (layerX > BackgroundImg.getWidth())
			layerX = 0;
	}
}
