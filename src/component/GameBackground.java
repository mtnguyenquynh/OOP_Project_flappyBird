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

	private static final BufferedImage BackgroundImg;// Background Image

	private final int speed; // Speed of the background layer
	private int layerX; // Coordinates of the background layer

	public static final int GROUND_HEIGHT;

	static {
		BackgroundImg = GameUtil.loadBufferedImage(Constant.BG_IMG_PATH);
		assert BackgroundImg != null;
		GROUND_HEIGHT = BackgroundImg.getHeight() / 2;
	}

	// Constructor
	public GameBackground() {
		this.speed = Constant.GAME_SPEED;
		this.layerX = 0;
	}

	// Drawing method
	public void draw(Graphics g, Bird bird) {
		// Drawing background color
		//g.setColor(Constant.BG_COLOR);
		g.fillRect(0, 0, Constant.FRAME_WIDTH, Constant.FRAME_HEIGHT);

		// Get the size of the background image
		int imgWidth = BackgroundImg.getWidth();
		int imgHeight = BackgroundImg.getHeight();

		int count = Constant.FRAME_WIDTH / imgWidth + 2; //  Get the number of times the image is drawn based on the window width
		for (int i = 0; i < count; i++) {
			g.drawImage(BackgroundImg, imgWidth * i - layerX, Constant.FRAME_HEIGHT - imgHeight, null);
		}
		// Birds are no longer drawn if they die
		if(bird.isDead()) {  
			return;
		}
		movement();
	}

	// Motion logic of the background layer
	private void movement() {
		layerX += speed;
		if (layerX > BackgroundImg.getWidth())
			layerX = 0;
	}
}
