package game;

import city.cs.engine.*;
import city.cs.engine.Shape;
import org.jbox2d.common.Vec2;

public class YouWon extends GameWorld {
    private final StaticBody YouWon;

    public YouWon(Game game) {
        super(game);

        Shape platformShape = new BoxShape(10,2);
        YouWon = new StaticBody(this,platformShape);
        BodyImage youWinPic = new BodyImage("data/YouWin.png", 10);
        YouWon.addImage(youWinPic);
        YouWon.setPosition(new Vec2(0,40));
        getJohn().setPosition(new Vec2(50,50));
    }

    public StaticBody getYouWon() {
        return YouWon;
    }
    public void MoveYouWon() {
        YouWon.move(new Vec2(0,-.1f));
    }

}
