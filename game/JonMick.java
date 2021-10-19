package game;

import city.cs.engine.*;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class JonMick extends Walker {
    //polygon shape for John Mick
    private static final Shape JohnShape = new PolygonShape(
            -1.18f,2.37f, 0.66f,2.37f, 1.22f,0.43f, 1.23f,-2.47f, -1.18f,-2.43f, -1.18f,2.37f);

    private static final BodyImage RightImage =
            new BodyImage("data/JohnRight.png", 6f);

    private int health;
    private int score;

    private static SoundClip JohnHit;
    static {
        try {
            JohnHit = new SoundClip("data/JohnHit.wav");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }

    public JonMick(GameWorld world) {
        super(world, JohnShape);
        addImage(RightImage);
        //gave really high health for ease of play for the professor
        health = 15;
    }

    //This will be used to keep track of health and pickup health
    public void addHealth(){
        health++;
        System.out.println("HealthCount" +
                "Health = " + health);
    }
    public void loseHealth(){
        health--;
        JohnHit.play();
        System.out.println("HealthCount" +
                "Health = " + health);
    }
    public int getHealth(){
        return health;
    }
    //carries score over from other levels
    public void resetScore(int x) {score = x;}
    public void setScore(int x) {score = score + x;}
    public int getScore() {return score;}


}