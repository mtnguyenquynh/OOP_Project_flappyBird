package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.sound.sampled.*;


/**
 * 音乐工具类
 *
 * @author BUILD SUCCESSFUL
 * wav音频：JDK提供的类可直接解码 mp3音频：JDK没有提供支持，需要使用第三方的工具包
 */
public class Sound {
    private static AudioInputStream flySound;
    private static AudioInputStream crashSound;
    private static AudioInputStream scoreSound;
    private static Clip clip;

        // wav播放
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

