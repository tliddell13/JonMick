package game;

import city.cs.engine.*;

public class Health extends DynamicBody {
    //Jon's health
    private static final Shape healthShape = new CircleShape(1);
    BodyImage health = new BodyImage("data/heart.png",2f);

    public Health(World w) {
        super(w, healthShape);
        addImage(health);
    }
}
