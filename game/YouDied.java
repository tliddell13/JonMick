package game;

import city.cs.engine.SoundClip;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class YouDied {
    //screen that appears when you die
    Image YouDied = new ImageIcon("data/YouDied.png").getImage();
    private static SoundClip GameOver;
    static {
        try {
            GameOver = new SoundClip("data/GameOver.wav");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }
    YouDied(GameWorld world,GameView view) {
        world.stop();
        view.setBackground(YouDied);
        GameOver.play();
        System.out.println("dead");
    }
}
