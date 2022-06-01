package component;

import java.io.DataInputStream;



import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import util.Constant;
import util.Sound;

/**
 * Score counter
 * 
 * @author BUILD SUCCESSFUL
 *
 */
public class ScoreCounter {

	private static class ScoreCounterHolder {
		private static final ScoreCounter scoreCounter = new ScoreCounter();
	}

	public static ScoreCounter getInstance() {
		return ScoreCounterHolder.scoreCounter;
	}

	private long score = 0; 
	private long bestScore; 

	private ScoreCounter() {
		bestScore = -1;
		try {
			loadBestScore();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadBestScore() throws Exception {
		File file = new File(Constant.SCORE_FILE_PATH);
		if (file.exists()) {
			DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file));
			bestScore = dataInputStream.readLong();
			dataInputStream.close();
		}
	}

	public void saveScore() {
		bestScore = Math.max(bestScore, getCurrentScore());
		try {
			File file = new File(Constant.SCORE_FILE_PATH);
			DataOutputStream dataOutputStream= new DataOutputStream(new FileOutputStream(file));
			dataOutputStream.writeLong(bestScore);
			dataOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void score(Bird bird) {
		if (!bird.isDead()) {
			Sound.playScore();
			score += 1;
		}
	}

	public long getBestScore() {
		return bestScore;
	}

	public long getCurrentScore() {
		return score;
	}

	public void reset() {
		score = 0;
	}

}