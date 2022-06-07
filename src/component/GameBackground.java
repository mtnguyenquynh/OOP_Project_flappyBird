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

	private int layerX; // Coordinates of the background layer
	private static final BufferedImage BackgroundImg;
	private final int backGroundSpeed;

	public GameBackground() {
		this.backGroundSpeed = Constant.GAME_SPEED;
		this.layerX = 0;
	}

	static {
		BackgroundImg = GameUtil.loadBufferedImage(Constant.BG_IMG_PATH);
		assert BackgroundImg != null;
	}

	public void draw(Graphics g, Bird bird) {

		g.fillRect(0, 0, Constant.FRAME_WIDTH, Constant.FRAME_HEIGHT);

		int backgroundimgWidth = BackgroundImg.getWidth();
		int imgHeight = BackgroundImg.getHeight();

		for (int i = 0; i < 5; i++) {
			g.drawImage(BackgroundImg, backgroundimgWidth * i - layerX, Constant.FRAME_HEIGHT - imgHeight, null);
		}
		if(bird.isDead()) {  
			return;
		}
		movement();
	}

	// Motion logic of the background layer
	private void movement() {
		layerX = backGroundSpeed + layerX;
		if (BackgroundImg.getWidth() < layerX) {layerX = 0;}
	}
}
