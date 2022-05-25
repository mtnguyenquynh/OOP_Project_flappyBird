package util;

import java.awt.Color;

import java.awt.Font;



/**
 * Constants Class. Subsequent optimization can be 
 * written to a database or file for easy modification
 * 
 * @author BUILD SUCESSFUL
 */

public class Constant {
	// Window size
	public static final int FRAME_WIDTH = 400;
	public static final int FRAME_HEIGHT = 600;

	// Game Title
	public static final String GAME_TITLE = "OOP_PROJECT 'FLAPPY BIRD' BUILD SUCESSFUL TEAM";

	// Window Location
	public static final int FRAME_X = 600;
	public static final int FRAME_Y = 100;

	// Image Resource Path
	public static final String ICON_IMG_PATH = "resources/img/icon.png";
	public static final String BG_IMG_PATH = "resources/img/background.png"; 
	// Pictures of small birds
	public static final String[][] BIRDS_IMG_PATH = {
			{ "resources/img/0.png", "resources/img/1.png", "resources/img/2.png", "resources/img/3.png",
					"resources/img/4.png", "resources/img/5.png", "resources/img/6.png", "resources/img/7.png" },
			{ "resources/img/up.png", "resources/img/up.png", "resources/img/up.png", "resources/img/up.png",
					"resources/img/up.png", "resources/img/up.png", "resources/img/up.png", "resources/img/up.png" },
			{ "resources/img/down_0.png", "resources/img/down_1.png", "resources/img/down_2.png",
					"resources/img/down_3.png", "resources/img/down_4.png", "resources/img/down_5.png",
					"resources/img/down_6.png", "resources/img/down_7.png" },
			{ "resources/img/dead.png", "resources/img/dead.png", "resources/img/dead.png", "resources/img/dead.png",
					"resources/img/dead.png", "resources/img/dead.png", "resources/img/dead.png",
					"resources/img/dead.png", } };

	// Cloud Pictures
	public static final String[] CLOUDS_IMG_PATH = { "resources/img/meteorite.png", "resources/img/spaceship.png" };

	// Water pipe pictures
	public static final String[] PIPE_IMG_PATH = { "resources/img/pipe.png", "resources/img/pipe_top.png",
			"resources/img/pipe_bottom.png" };

	public static final String TITLE_IMG_PATH = "resources/img/title.png";
	public static final String NOTICE_IMG_PATH = "resources/img/startButton.png";
	public static final String SCORE_IMG_PATH = "resources/img/scoreboard.png";
	public static final String OVER_IMG_PATH = "resources/img/gameOver.png";
	public static final String AGAIN_IMG_PATH = "resources/img/restartButton.png";

	public static final String SCORE_FILE_PATH = "resources/score"; // Score file path

	// Game speed (speed of movement of water pipes and background layers)
	public static final int GAME_SPEED = 4;
 
	// Game background color
	//public static final Color BG_COLOR = new Color(0x4bc4cf);

	// Game refresh rate
	public static final int FPS = 1000 / 30;

	// Title bar height
	public static final int TOP_BAR_HEIGHT = 20;

	// Ground height
	public static final int GROUND_HEIGHT = 35;

	// Upper pipe lengthening
	public static final int TOP_PIPE_LENGTHENING = 100;

	public static final int CLOUD_BORN_PERCENT = 6; // Cloud formation probability (%)
	public static final int CLOUD_IMAGE_COUNT = 2; // Number of images from cloud to cloud (%)
	public static final int MAX_CLOUD_COUNT = 7; // Maximum number of clouds

	public static final Font CURRENT_SCORE_FONT = new Font("Mandarin Amber", Font.BOLD, 32);// Fonts
	public static final Font SCORE_FONT = new Font("Mandarin Amber", Font.BOLD, 24);// Fonts

}
