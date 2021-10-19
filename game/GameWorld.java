package game;

import city.cs.engine.*;
import city.cs.engine.Shape;
import org.jbox2d.common.Vec2;

import java.util.List;

public abstract class GameWorld extends World {
    private final JonMick Jon;
    private final Game game;
    private int fireballCount = 0;
    private boolean isDead = false;

    //I made the buttons bodies in the world and added a secret world if a player is able to hit one of the buttons
    BodyImage home = new BodyImage("data/HomeButton.png",3f);
    BodyImage pause = new BodyImage("data/PauseButton.png",3f);
    BodyImage save = new BodyImage("data/Save.png",2f);

    Shape Button = new BoxShape(1,1);

    StaticBody HomeButton;
    StaticBody PauseButton;
    StaticBody SaveButton;

    public GameWorld(Game game) {
        this.game = game;
        Jon = new JonMick(this);
        Jon.setPosition(new Vec2(0,0));
        Jon.setGravityScale(2);

        /*
        I added buttons as static bodies so I could customize them more, and mainly so I could implement the Secret
        world when the player manages to land on the house in the first level
         */
        //brings you back to menu screen
        HomeButton = new StaticBody(this,Button);
        HomeButton.addImage(home);
        HomeButton.setPosition(new Vec2(40,25));

        //pauses the game
        PauseButton = new StaticBody(this,Button);
        PauseButton.addImage(pause);
        PauseButton.setPosition(new Vec2(40,20));

        //saves what level you are on
        SaveButton = new StaticBody(this,Button);
        SaveButton.addImage(save);
        SaveButton.setPosition(new Vec2(40,15));

    }

    public JonMick getJohn(){return Jon;}
    public Game getGame(){return game;}
    public int getFireballCount() {
        return fireballCount;
    }
    public void setFireballCount(int x) {
        fireballCount = fireballCount + x;
    }
    public boolean getIsDead() {return isDead;}
    public void setIsDead(boolean dead) {isDead = dead;}
    public void getHomePosition() {
        HomeButton.getPosition();
    }

    public StaticBody getPauseButton() {
        return PauseButton;
    }
    public StaticBody getSaveButton() {return SaveButton;}
    public void clearMap() {
        List<DynamicBody> list = getDynamicBodies();
        List<StaticBody> list1 = getStaticBodies();
        list.forEach(Body::destroy);
        list1.forEach(Body::destroy);

        HomeButton = new StaticBody(this,Button);
        HomeButton.addImage(home);
        HomeButton.setPosition(new Vec2(40,25));
    }

}
