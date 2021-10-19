package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

public class Level1Collisions implements CollisionListener {
    private final Level1 level;
    private final BadGuy guy;

    BodyImage BadGuyFaceLeft = new BodyImage("data/badGuyStandLeft.png", 10f);

    public Level1Collisions(Level1 level, BadGuy guy) {
        this.level = level;
        this.guy = guy;
    }

    @Override
    public void collide(CollisionEvent e) {
        if (e.getOtherBody() == level.getGround()) {
            e.getReportingBody().removeAllImages();
            e.getReportingBody().addImage(BadGuyFaceLeft);
            level.setLanded(true);
        }
        if (e.getOtherBody() instanceof Fireball) {
            guy.hit();
            e.getOtherBody().destroy();
            level.setFireballCount(-1);
            if (guy.getDestroy() && level.getVillainsDefeated() < 5) {
                //drops either coin or health at random
                int dropCoin = (Math.random() <= 0.5) ? 1 : 2;
                if (dropCoin == 1) {
                    Health heart = new Health(level);
                    heart.setPosition(new Vec2(guy.getPosition()));
                }
                else {
                    Coin coin = new Coin(level);
                    coin.setPosition(new Vec2(guy.getPosition()));
                }
                //the enemy is defeated
                guy.destroy();
                level.setOnMap(false);
                level.setLanded(false);
                //add another bad guy defeated
                level.setVillainsDefeated();
                //score 10 points for killing an enemy
                level.getJohn().setScore(10);
            }
            //after defeating 5 enemies a portal opens for you to progress to the new level
            else if (level.getVillainsDefeated() == 5) {
                guy.destroy();
                level.setOnMap(true);
                PortalBody portal = new PortalBody(level);
                portal.setPosition(new Vec2(41,-20));
            }
        }
    }
}
