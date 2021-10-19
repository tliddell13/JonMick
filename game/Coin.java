package game;

import city.cs.engine.*;

public class Coin extends DynamicBody {
    //coin to increase score
    private static final Shape coinShape = new CircleShape(.7f);
    BodyImage coin = new BodyImage("data/coin.png",1.5f);

    public Coin(World w) {
        super(w, coinShape);
        addImage(coin);
    }
}
