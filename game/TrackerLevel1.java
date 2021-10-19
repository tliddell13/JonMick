package game;

import city.cs.engine.BodyImage;
import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;
import org.jbox2d.common.Vec2;

public class TrackerLevel1 implements StepListener {

    private final GameView view;
    private final JonMick john;
    private final Level1 level;

    /**
     * each level gets a tracker to time certain events
     * @level the instance of level 1 you want to use the tracker for
     */

    BodyImage BadGuyParachute = new BodyImage("data/badGuyParachute.png", 15f);
    //checks if to continue spawning enemies
    private boolean spawnEnemyLeft = true;

    BodyImage BadGuyWalkLeft = new BodyImage("data/badGuyWalkLeft.gif", 12f);
    BodyImage BadGuyWalkRight = new BodyImage("data/badGuyWalkRight.gif",12f);
    public TrackerLevel1(GameView view, JonMick john, Level1 level) {
        this.view = view;
        this.john = john;
        this.level = level;

    }
    public void preStep(StepEvent e) {
    }
    public void postStep(StepEvent e) {
        //update John's health and fireballs
        view.setFireballCount(level.getFireballCount());
        view.setHealthCount(john.getHealth());
        view.setScore(john.getScore());
        view.repaint();
        //get new position every step
        //position initializers for John
        Vec2 johnVec = level.getJohn().getPosition();
        float johnX = johnVec.x;
        //get new position every step
        //position initializers for bad guy
        Vec2 badGuyVec = level.getGuy().getPosition();
        float badGuyX = badGuyVec.x;

        //check if you died every step
        if (level.getIsDead()) {
            level.stop();
        }

        //chase player left
        else if (johnX < badGuyX && level.getLanded()) {
            level.getGuy().move(new Vec2(-.1f,0));
            level.getGuy().removeAllImages();
            level.getGuy().addImage(BadGuyWalkLeft);
        }
        //chase player right
        else if (johnX > badGuyX && level.getLanded()) {
            level.getGuy().move(new Vec2(.1f,0));
            level.getGuy().removeAllImages();
            level.getGuy().addImage(BadGuyWalkRight);
        }

        else if (!level.getOnMap()) {
            spawnBadGuy();
        }
    }
    public void spawnBadGuy() {
        BadGuy guy = new BadGuy(level);
        if (spawnEnemyLeft) {
            guy.setPosition(new Vec2(-30, 20));
            spawnEnemyLeft = false;
        }
        else {
            guy.setPosition(new Vec2(30,20));
            spawnEnemyLeft = true;
        }
        guy.setGravityScale(2);
        guy.addImage(BadGuyParachute);
        Level1Collisions collision = new Level1Collisions(level, guy);
        guy.addCollisionListener(collision);
        level.setGuy(guy);
        level.setOnMap(true);
    }
}
