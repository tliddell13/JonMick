package game;

import city.cs.engine.*;

public class FireballCollisions implements CollisionListener {
    private final GameWorld level;
    private final Fireball fireball;

    public FireballCollisions(GameWorld level, Fireball fireball) {
        this.level = level;
        this.fireball = fireball;
    }
    @Override
    public void collide(CollisionEvent e){
        fireball.groundCollision();
        //when a user fireball is destroyed we must set the fireball count
        if (fireball.getGroundCollisions() > 1 && !fireball.getEnemyFireball()) {
            fireball.destroy();
            level.setFireballCount(-1);
        }
        //enemy fireballs bounce but not bosses raining fireballs
        if (fireball.getGroundCollisions() > 0 && fireball.getEnemyFireball() && level instanceof BossLevel) {
            fireball.destroy();
        }
        else if (fireball.getGroundCollisions() > 5 && fireball.getEnemyFireball()) {
            fireball.destroy();
        }
        //fireballs destroy each other
        else if(e.getOtherBody() instanceof Fireball && e.getReportingBody() instanceof Fireball) {
            //the bad guys fireballs don't destroy each other or affect the fireball count
            if (!((Fireball) e.getOtherBody()).getEnemyFireball() || !((Fireball) e.getOtherBody()).getEnemyFireball()) {
                e.getReportingBody().destroy();
                level.setFireballCount(-1);
            }
        }
        else if (e.getOtherBody() instanceof Health) {
            e.getOtherBody().destroy();
        }
    }
}
