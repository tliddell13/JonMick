package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.io.IOException;

public class TrackerLevel2 implements StepListener {
    private final GameView view;
    private final JonMick john;
    private final Level2 level;
    private boolean twoMap = false;
    private boolean spawnGround = false;
    private BadGuy2 guyRight;
    private BadGuy2 guyLeft;
    private BadGuy2 guyRightGround;
    private BadGuy2 guyLeftGround;

    /**
     * each level gets a tracker to time certain events
     * @level the instance of level 2 you want to use the tracker for
     */
    //need coordinates to pass to constructor for fireball tracker
    Point point = new Point(0, 0);

    private static final BodyImage BadGuyLeft = new BodyImage("data/BadGuy2standLeft.png",11f);
    private static final BodyImage BadGuyRight = new BodyImage("data/BadGuy2standRight.png",11f);
    private static final BodyImage GuyRightShoot = new BodyImage("data/BadGuy2shootLeft.png",11f);
    private static final BodyImage GuyLeftShoot = new BodyImage("data/BadGuy2shootRight.png",11f);
    private static final BodyImage GuyRightWalk = new BodyImage("data/badGuy2walkLeft.gif",12f);
    private static final BodyImage GuyLeftWalk = new BodyImage("data/badGuy2walkRight.gif",12f);



    private static final BodyImage EnemyFireballLeft = new BodyImage("data/EnemyFireballLeft.png",2f);
    private static final BodyImage EnemyFireballRight = new BodyImage("data/EnemyFireballRight.png",2f);
    private static SoundClip Destroy;
    static {
        try {
            Destroy = new SoundClip("data/RobotDestroy.wav");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }

    public TrackerLevel2(GameView view, JonMick john, Level2 level) {
        this.view = view;
        this.john = john;
        this.level = level;
    }
    public void preStep(StepEvent e) { }
    public void postStep(StepEvent e) {
        //update Johns health and fireballs
        view.setFireballCount(level.getFireballCount());
        view.setHealthCount(john.getHealth());
        view.setScore(john.getScore());
        view.repaint();
        //move the platform
        level.movePlatform();
        //check if you died every step
        if (level.getIsDead()) {
            level.stop();
        }
        //checks if villains should attack
        else if (level.getShoot()) {
            //fireball shooting for first bad guy
            if (level.getSimulationSettings().getFrameCount() % 60 == 0 && !twoMap) {
                shootFireball(0);
            }
            //fireball shooting for 2 bad guys
            else if (twoMap && level.getSimulationSettings().getFrameCount() % 60 == 0) {
                if (guyLeft.getOnMap() && guyRight.getOnMap()) {
                    shootFireball(1);
                    shootFireball(2);
                }
                else if (!guyLeft.getOnMap() && guyRight.getOnMap()) {
                    shootFireball(1);
                }
                else if (guyLeft.getOnMap() && !guyRight.getOnMap()) {
                    shootFireball(2);
                }
            }
            //makes image change for bad guys firing action
            else if (level.getSimulationSettings().getFrameCount() % 61 == 0 && !twoMap) {
                level.getGuy().removeAllImages();
                level.getGuy().addImage(BadGuyLeft);

            }
            else if (level.getSimulationSettings().getFrameCount() % 61 == 0 && twoMap) {
                guyRight.removeAllImages();
                guyRight.addImage(BadGuyLeft);

                guyLeft.removeAllImages();
                guyLeft.addImage(BadGuyRight);
            }
             else if (!level.getGuy().getOnMap() && !twoMap) {
                spawnBadGuyRightPlatform();
                spawnBadGuyLeftPlatform();
                twoMap = true;
                level.getGuy().setOnMap(true);
                //after these villains are defeated more villains will be spawned on the ground
                spawnGround = true;
            }
             else if (level.getVillainsDefeated() == 3 && spawnGround) {
                 spawnBadGuyLeft();
                 spawnBadGuyRight();
                 //don't want to spawn infinite bad guys
                 spawnGround = false;
            }
            else if (level.getVillainsDefeated() == 3) {
                guyRightGround.move(new Vec2(-.1f,0));
                guyLeftGround.move(new Vec2(.1f,0));
                guyRightGround.removeAllImages();
                guyRightGround.addImage(GuyRightWalk);
                guyLeftGround.removeAllImages();
                guyLeftGround.addImage(GuyLeftWalk);
            }
        }

    }
    public void spawnBadGuyRightPlatform() {
        guyRight = new BadGuy2(level);
        guyRight.setPosition(new Vec2(40,-5));
        guyRight.addImage(BadGuyLeft);
        Level2Collisions collision = new Level2Collisions(level, guyRight);
        guyRight.addCollisionListener(collision);

        level.getGuy().setOnMap(true);
    }
    public void spawnBadGuyLeftPlatform() {
        guyLeft = new BadGuy2(level);
        guyLeft.setPosition(new Vec2(-40,-12));
        guyLeft.addImage(BadGuyRight);
        Level2Collisions collision = new Level2Collisions(level, guyLeft);
        guyLeft.addCollisionListener(collision);

        level.getGuy().setOnMap(true);
    }
    public void spawnBadGuyRight() {
        guyRightGround = new BadGuy2(level);
        guyRightGround.setPosition(new Vec2(40,-20));
        guyRightGround.addImage(BadGuyLeft);
        Level2Collisions collision = new Level2Collisions(level, guyRightGround);
        guyRightGround.addCollisionListener(collision);

        level.getGuy().setOnMap(true);
    }
    public void spawnBadGuyLeft() {
        guyLeftGround = new BadGuy2(level);
        guyLeftGround.setPosition(new Vec2(-40,-20));
        guyLeftGround.addImage(BadGuyRight);
        Level2Collisions collision = new Level2Collisions(level, guyLeftGround);
        guyLeftGround.addCollisionListener(collision);

        level.getGuy().setOnMap(true);
    }
    public void shootFireball(int position) {
        Destroy.play();
        //create new fireball
        Fireball fireball = new Fireball(level);
        FireballTracker tracker = new FireballTracker(view, level.getJohn(), level, fireball, point);
        level.addStepListener(tracker);
        //collisions for fireball
        FireballCollisions collisions = new FireballCollisions(level, fireball);
        fireball.addCollisionListener(collisions);
        fireball.removeAllImages();
        //the fireball can be fired from 3 different positions
        switch (position) {
            case 0 -> {
                fireball.addImage(EnemyFireballLeft);
                fireball.setPosition(new Vec2(level.getGuy().getPosition().x - 2, level.getGuy().getPosition().y));
                fireball.setEnemyFireball(true);
                level.getGuy().removeAllImages();
                level.getGuy().addImage(GuyRightShoot);
            }
            case 1 -> {
                fireball.addImage(EnemyFireballLeft);
                fireball.setPosition(new Vec2(38, -5));
                fireball.setEnemyFireball(true);
                guyRight.removeAllImages();
                guyRight.addImage(GuyRightShoot);
            }
            case 2 -> {
                fireball.addImage(EnemyFireballRight);
                fireball.setPosition(new Vec2(-40, -10));
                fireball.setEnemyFireball(true);
                fireball.setLeft(false);
                guyLeft.removeAllImages();
                guyLeft.addImage(GuyLeftShoot);
            }
        }
    }
}
