package component;

import java.io.File;
import java.util.Scanner;

import javax.sound.sampled.*;

public class Audio {

    static void sound_fly(){
    try {
        try (Scanner sc = new Scanner(System.in)) {
            File file = new File("fly.wav");
   AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            String response = "";
            while(!response.equals("Q")){
                response = sc.next();
               
            }
            clip.start();
        }
    } catch (Exception e) {
        e.printStackTrace();
    }}

    public static void main(String[] args){
    }
} 

    