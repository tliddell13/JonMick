package game;

import city.cs.engine.BodyImage;
import city.cs.engine.SoundClip;
import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;
import org.jbox2d.common.Vec2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.io.IOException;

//dont use this class yet, but will when level gets more advanced
public class TrackerBossLevel implements StepListener {
    private final GameView view;
    private final JonMick john;
    private final BossLevel level;

    private static final BodyImage EnemyFireballFall =
            new BodyImage("data/EnemyFireballFall.png",4f);
    private static SoundClip BossLaugh;
    static {
        try {
            BossLaugh = new SoundClip("data/EvilLaugh.wav");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }

    public TrackerBossLevel(GameView view, JonMick john, BossLevel level) {
        this.view = view;
        this.john = john;
        this.level = level;
    }
    public void preStep(StepEvent e) { }
    public void postStep(StepEvent e) {
        //update John's health and fireballs
        view.setFireballCount(level.getFireballCount());
        view.setHealthCount(john.getHealth());
        view.setScore(john.getScore());
        view.repaint();
        //move platform in boss level
        level.movePlatform();
        //check if you died every step
        if (level.getIsDead()) {
            level.stop();
        }
        //this is how often the villain brings fire from the sky
        else if (level.getSimulationSettings().getFrameCount() % 200 == 0 && !level.getBoss().getDestroy()) {
            BossLaugh.play();
            for (int x = 0; x <= 12; x++) {
                Point point = new Point(0, 0);

                //create new fireball
                Fireball fireball = new Fireball(level);
                FireballTracker tracker = new FireballTracker(view, level.getJohn(), level, fireball, point);
                level.addStepListener(tracker);
                //collisions for fireball
                FireballCollisions collisions = new FireballCollisions(level, fireball);
                fireball.addCollisionListener(collisions);

                fireball.addImage(EnemyFireballFall);
                fireball.setPosition(new Vec2(-40 + (x * 7),30));
                fireball.setEnemyFireball(true);
            }
        }
        //death by falling in the boss level
        else if (john.getPosition().y < -20) {
            while (john.getHealth() > 0) {
                john.loseHealth();
            }
        }
    }
}