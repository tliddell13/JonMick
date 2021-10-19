package game;

import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;

public class HealthPickup implements CollisionListener {

    //Jon's collision listener for picking up health
    private final JonMick john;
    public HealthPickup(JonMick s){
        this.john = s;
    }

    @Override
    public void collide(CollisionEvent e) {
        //john eats the health
        if (e.getOtherBody() instanceof Health) {
            john.addHealth();
            e.getOtherBody().destroy();
        }
    }
}