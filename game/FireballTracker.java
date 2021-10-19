package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import java.awt.*;

//moves the fireball
public class FireballTracker implements StepListener {
    private final GameWorld level;
    private final Fireball fireball;

    final Vec2 worldPoint;

    final float MouseX;
    final float MouseY;
    final float JohnX;
    final float JohnY;

    public FireballTracker(GameView view, JonMick john, GameWorld level, Fireball fireball, Point point) {
        this.level = level;
        this.fireball = fireball;

        //change the coordinates to applicable ones
        worldPoint = view.viewToWorld(point);

        MouseX = worldPoint.x;
        MouseY = worldPoint.y;
        JohnX = john.getPosition().x;
        JohnY = john.getPosition().y;
    }

    public void preStep(StepEvent e) {

    }
    public void postStep(StepEvent e) {

        //if it is a users fireball
        if(!fireball.getEnemyFireball()) {

            if (MouseX > JohnX) {
                fireball.setLinearVelocity(new Vec2(10f,.4f));
                fireball.applyImpulse(new Vec2(70,.1f));
            }
            else if (MouseX < JohnX) {
                fireball.setLinearVelocity(new Vec2(-10f, .4f));
                fireball.applyImpulse(new Vec2(-70,.1f));
            }
        }
        //enemy fireball travel
        else if (fireball.getEnemyFireball() && level instanceof Level2) {
            if (fireball.getLeft()) {
                fireball.move(new Vec2(-.4f, .1f));
            }
            else if (!fireball.getLeft()) {
                fireball.move(new Vec2(.4f,.1f));
            }
        }
    }
}
