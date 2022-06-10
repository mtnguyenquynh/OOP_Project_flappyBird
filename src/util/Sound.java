package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.sound.sampled.*;


/**
 * Play sounds effect class
 *
 * @author BUILD SUCCESSFUL
 */
public class Sound {
    private static AudioInputStream flySound;
    private static AudioInputStream crashSound;
    private static AudioInputStream scoreSound;

    private static AudioInputStream introSound;
    private static Clip clip;

        public static void playFly() {
            try {
                flySound = AudioSystem.getAudioInputStream(new File("resources/wav/fly.wav"));
                clip = AudioSystem.getClip();

                clip.open(flySound);
                clip.start();

            } catch (IOException ignored) {
            } catch (UnsupportedAudioFileException e) {
                throw new RuntimeException(e);
            } catch (LineUnavailableException e) {
                throw new RuntimeException(e);
            }
        }

    public static void playCrash() {
        try {
            crashSound = AudioSystem.getAudioInputStream(new File("resources/wav/crash.wav"));
            clip = AudioSystem.getClip();

            clip.open(crashSound);
            clip.start();

        } catch (IOException ignored) {
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }
    public static void playScore() {
        try {
            scoreSound = AudioSystem.getAudioInputStream(new File("resources/wav/score.wav"));
            clip = AudioSystem.getClip();

            clip.open(scoreSound);
            clip.start();

        } catch (IOException ignored) {
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }
}

