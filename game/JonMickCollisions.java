package game;

import city.cs.engine.*;
import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import org.jbox2d.common.Vec2;

import java.awt.*;

public class JonMickCollisions implements CollisionListener {

    private final GameWorld level;
    private final Game game;
    private final JonMick john;

    /**
     * this class is added to a Jon to listen for collisions in a level
     * @param level level that Jon is in and the collisions are happening in
     * @param game the game that the level is a part of
     * @param john the Jon that has the collision listener
     *
     */

    public JonMickCollisions(GameWorld level, Game game, JonMick john){
        this.level = level;
        this.game = game;
        this.john = john;
    }

    @Override
    public void collide(CollisionEvent e) {
        //go through portal
        if (e.getOtherBody() instanceof PortalBody) {
            level.clearMap();
            game.getView().addScore();
            game.goToNextLevel();
        }
        else if (e.getOtherBody().getFillColor().equals(new Color(150, 150, 160))) {
            john.applyImpulse(new Vec2(1,400));
        }
        else if (e.getOtherBody() instanceof Coin) {
            //coins are worth 20 points
            john.setScore(20);
            e.getOtherBody().destroy();
        }
        //run into bad guy
        else if (e.getOtherBody() instanceof BadGuy || e.getOtherBody() instanceof Boss || e.getOtherBody() instanceof BadGuy2) {
            john.loseHealth();
            //make the bodies bump away from each other
            if (e.getOtherBody().getPosition().x > john.getPosition().x) {
                john.applyImpulse(new Vec2(-1000, 500));
                System.out.println("enter");
            }
            else {
                john.applyImpulse(new Vec2(1000, 500));
            }
            if (john.getHealth() == 0) {
                john.destroy();
                level.setIsDead(true);
                level.clearMap();
                //you died so your score is saved
                new YouDied(level,game.getView());
                new CreateHighScoresFile(john.getScore());
            }
        }
        else if (e.getOtherBody() instanceof Fireball && ((Fireball) e.getOtherBody()).getEnemyFireball()) {
            john.loseHealth();
            e.getOtherBody().destroy();
            if (john.getHealth() == 0) {
                john.destroy();
                level.setIsDead(true);
                level.clearMap();
                new YouDied(level,game.getView());
                //you died so your score is saved
                new CreateHighScoresFile(john.getScore());
            }
        }
        else if(e.getOtherBody() instanceof StaticBody) {
            if (john.getHealth() == 0) {
                john.destroy();
                level.setIsDead(true);
                level.clearMap();
                new YouDied(level,game.getView());
                //you died so your score is saved
                new CreateHighScoresFile(john.getScore());
            }
            //hit the house button to go to the secret world
            if (john.getPosition().y > 25 && john.getPosition().y < 30 && john.getPosition().x < 41) {
                game.getView().addScore();
                game.goToSecretWorld();
            }
        }
    }
}
