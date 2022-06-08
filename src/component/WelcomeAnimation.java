package component;

import java.awt.*;
import java.awt.image.BufferedImage;

import util.Constant;
import util.GameUtil;

/**
 * Game launch screen
 * 
 * @author BUILD SUCCESSFUL
 *
 */
public class WelcomeAnimation {

	private final BufferedImage titleImg;
	private final BufferedImage noticeImg;

	private int flashCount = 0; // Image flicker parameters

	public void draw(Graphics g) {
		int x = (Constant.FRAME_WIDTH - titleImg.getWidth()) >> 1;
		int y = Constant.FRAME_HEIGHT / 3;
		g.drawImage(titleImg, x+2, y-50, null);

		// Image blink
		final int CYCLE = 30; // Blink Cycle
		if (flashCount++ > CYCLE)
			GameUtil.drawImage(noticeImg, Constant.FRAME_WIDTH - noticeImg.getWidth() -50, Constant.FRAME_HEIGHT / 5 * 3, g);
		if (flashCount == CYCLE * 2)
			flashCount = 0;
	}
	public WelcomeAnimation() {
		titleImg = GameUtil.loadBufferedImage(Constant.TITLE_IMG_PATH);
		noticeImg = GameUtil.loadBufferedImage(Constant.NOTICE_IMG_PATH);
	}



}
