package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

public class BossLevelCollisions implements CollisionListener {
    private final BossLevel level;
    private final Game game;
    private final Boss boss;

    //boss facing right
    BodyImage BossRight = new BodyImage("data/BOSSRight.png",10f);
    //gif for boss appearing facing right
    BodyImage BossAppearRight = new BodyImage("data/BossAppearRight.gif", 10f);
    //gif for boss appearing facing left
    BodyImage BossAppearLeft = new BodyImage("data/BossAppearLeft.gif", 10f);
    //image facing left
    BodyImage BossLeft = new BodyImage("data/BOSSLeft.png", 10f);

    public BossLevelCollisions(BossLevel level,Game game, Boss boss) {
        this.level = level;
        this.game = game;
        this.boss = boss;
    }
    @Override
    public void collide(CollisionEvent e) {
        //if a user fireball hits an enemy fireball
        if (e.getOtherBody() instanceof Fireball && !((Fireball) e.getOtherBody()).getEnemyFireball()) {
            boss.hit();
            level.setFireballCount(-1);
            e.getOtherBody().destroy();
            //controls where he appears after being hit
            if (boss.getLeft()) {
                boss.setPosition(new Vec2(-40, 0));
                boss.removeAllImages();
                boss.setLeft(false);
                changeBossImages();
            }
            else if (!boss.getLeft()) {
                boss.setPosition(new Vec2(40, 0));
                boss.removeAllImages();
                boss.setLeft(true);
                changeBossImages();
            }
            if (boss.getDestroy()) {
                boss.destroy();
                game.setLevel(4);
                game.goToNextLevel();
                level.getJohn().setScore(50);
            }
        }
    }
    //function that changes the bosses images based on the location of the player
    public void changeBossImages() {
        if (level.getJohn().getPosition().x > boss.getPosition().x) {
            boss.addImage(BossAppearRight);
            boss.removeAllImages();
            boss.addImage(BossRight);
        }
        else if (level.getJohn().getPosition().x < boss.getPosition().x) {
            boss.addImage(BossAppearLeft);
            boss.removeAllImages();
            boss.addImage(BossLeft);
        }
    }
}
