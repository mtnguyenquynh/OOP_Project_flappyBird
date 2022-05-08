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
    private final BufferedImage scoreImg; // Scoreboard
    private final BufferedImage overImg; // End marks
    private final BufferedImage againImg; // Continue the logo

    public GameOverAnimation(){
        overImg = GameUtil.loadBufferedImage(Constant.OVER_IMG_PATH);
        scoreImg = GameUtil.loadBufferedImage(Constant.SCORE_IMG_PATH);
        againImg = GameUtil.loadBufferedImage(Constant.AGAIN_IMG_PATH);
    }

    private static final int SCORE_LOCATE = 5; // Scoreboard position compensation parameters
    private int flash = 0; // Image blinking parameters

    public void draw(Graphics g, Bird bird) {
        int x = Constant.FRAME_WIDTH - overImg.getWidth() >> 1;
        int y = Constant.FRAME_HEIGHT / 4;
        g.drawImage(overImg, x, y, null);

        // Drawing the scoreboard
        x = Constant.FRAME_WIDTH - scoreImg.getWidth() >> 1;
        y = Constant.FRAME_HEIGHT / 3;
        g.drawImage(scoreImg, x, y, null);

        // Plotting the Bureau's score 
        g.setColor(Color.white);
        g.setFont(Constant.SCORE_FONT);
        x = (Constant.FRAME_WIDTH - scoreImg.getWidth() / 2 >> 1) + SCORE_LOCATE;// Position compensation
        y += scoreImg.getHeight() >> 1;
        String str = Long.toString(bird.getCurrentScore());
        x -= GameUtil.getStringWidth(Constant.SCORE_FONT, str) >> 1;
        y += GameUtil.getStringHeight(Constant.SCORE_FONT, str);
        g.drawString(str, x, y);

        // Plot the highest score
        if (bird.getBestScore() > 0) {
            str = Long.toString(bird.getBestScore());
            x = (Constant.FRAME_WIDTH + scoreImg.getWidth() / 2 >> 1) - SCORE_LOCATE;
            x -= GameUtil.getStringWidth(Constant.SCORE_FONT, str) >> 1;
            g.drawString(str, x, y);
        }

        // Drawing continues the game, the image flashes
        final int COUNT = 30; //  Blink Cycle
        if (flash++ > COUNT)
            GameUtil.drawImage(againImg,Constant.FRAME_WIDTH - againImg.getWidth() >> 1, Constant.FRAME_HEIGHT / 5 * 3, g);
        if (flash == COUNT * 2) // Reset blinking parameters
            flash = 0;
    }
}
