package component;

import util.Constant;


import util.GameUtil;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Game over screen
 *
 * @author BUILD SUCCESSFUL
 *
 */
public class GameOverAnimation {
    private final BufferedImage scoreImg;
    private final BufferedImage overImg;
    private final BufferedImage againImg;



    private static final int SCORE_LOCATE = 5;
    private int flash = 0;

    public GameOverAnimation(){
        overImg = GameUtil.loadBufferedImage(Constant.OVER_IMG_PATH);
        scoreImg = GameUtil.loadBufferedImage(Constant.SCORE_IMG_PATH);
        againImg = GameUtil.loadBufferedImage(Constant.AGAIN_IMG_PATH);
    }
    public void draw(Graphics g, Bird bird) {
        int x = Constant.FRAME_WIDTH - overImg.getWidth() >> 1;
        int y = Constant.FRAME_HEIGHT / 4;
        g.drawImage(overImg, x, y, null);

        x = Constant.FRAME_WIDTH - scoreImg.getWidth() >> 1;
        y = Constant.FRAME_HEIGHT / 3;
        g.drawImage(scoreImg, x+8, y, null);

        // Plotting the Bird's score 
        g.setColor(Color.yellow);
        g.setFont(Constant.SCORE_FONT);
        x = ((Constant.FRAME_WIDTH - scoreImg.getWidth() / 2) + SCORE_LOCATE)/2;// Position compensation
        y += scoreImg.getHeight() /2;
        String str = Long.toString(bird.getCurrentScore());
        x -= GameUtil.getStringWidth(Constant.SCORE_FONT, str) /2;
        y += GameUtil.getStringHeight(Constant.SCORE_FONT, str);
        g.drawString(str, x+8, y-15);

        // Plot the highest score
        if (bird.getBestScore() > 0) {
            str = Long.toString(bird.getBestScore());
            x = ((Constant.FRAME_WIDTH + scoreImg.getWidth() / 2) - SCORE_LOCATE)/2;
            x -= GameUtil.getStringWidth(Constant.SCORE_FONT, str) /2;
            g.drawString(str, x-10, y-15);
        }

        // Drawing continues the game, the image flashes
        final int COUNT = 30; //  Blink Cycle
        if (flash++ > COUNT)
            GameUtil.drawImage(againImg,Constant.FRAME_WIDTH - againImg.getWidth()-50, Constant.FRAME_HEIGHT / 5 *3+50, g);
        if (flash == COUNT * 2) // Reset blinking parameters
            flash = 0;

    }
}
