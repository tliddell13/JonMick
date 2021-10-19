package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

public class Level2Collisions implements CollisionListener {

    private final Level2 level;
    private final BadGuy2 guy;

    public Level2Collisions(Level2 level, BadGuy2 guy) {
        this.level = level;
        this.guy = guy;
    }
    @Override
    public void collide(CollisionEvent e) {
        //if the fireball is johns it hurts him, his own don't
        if (e.getOtherBody() instanceof Fireball && !((Fireball) e.getOtherBody()).getEnemyFireball()) {
            guy.hit();
            e.getOtherBody().destroy();
            level.setFireballCount(-1);
            if (guy.getDestroy()) {
                int dropCoin = (Math.random() <= 0.5) ? 1 : 2;
                if (dropCoin == 1) {
                    Health heart = new Health(level);
                    heart.setPosition(new Vec2(guy.getPosition()));
                }
                else {
                    Coin coin = new Coin(level);
                    coin.setPosition(new Vec2(guy.getPosition()));
                }
                guy.destroy();
                //points for killing bad guy
                level.getJohn().setScore(10);
                level.VillainDefeated();
                if (level.getVillainsDefeated() == 5) {
                    PortalBody portal = new PortalBody(level);
                    portal.setPosition(new Vec2(41,-20));
                    level.setShoot(false);
                }
            }
        }
    }
}
