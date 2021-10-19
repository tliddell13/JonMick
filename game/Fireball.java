package game;

import city.cs.engine.*;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Fireball extends DynamicBody {
    //fireball can only hit the ground once
    private int groundCollisions =0;
    //type of fireball
    private boolean enemyFireball = false;
    private boolean left = true;
    private static final Shape fireballShape = new CircleShape(1);

    private static SoundClip fireballExplode;
    static {
        try {
            fireballExplode = new SoundClip("data/fireballDestroy.wav");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }

    public Fireball(World w) {
        super(w,fireballShape);
    }

    public boolean getEnemyFireball() {
        return enemyFireball;
    }

    public void setEnemyFireball(boolean enemyFireball) {
        this.enemyFireball = enemyFireball;
    }
    public int getGroundCollisions() {
        return groundCollisions;
    }

    public void groundCollision(){
        groundCollisions++;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }
    public boolean getLeft() {
        return left;
    }
    @Override
    public void destroy()
    {
        fireballExplode.play();
        super.destroy();
    }
}
