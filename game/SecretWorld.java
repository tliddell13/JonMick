package game;

import city.cs.engine.BoxShape;
import city.cs.engine.Shape;
import city.cs.engine.SolidFixture;
import city.cs.engine.StaticBody;
import org.jbox2d.common.Vec2;

import java.awt.*;

public class SecretWorld extends GameWorld {
    //the secret world you can go to by hitting the home button
    public SecretWorld(Game game) {
        super(game);
        Color seeThrough = new Color(0,0,0,1);

        Shape side = new BoxShape(5,60);
        Shape floor = new BoxShape(60,5);

        StaticBody ground = new StaticBody(this, floor);
        SolidFixture groundFriction = new SolidFixture(ground,floor);
        groundFriction.setFriction(4);
        ground.setPosition(new Vec2(0,-33));
        ground.setFillColor(Color.blue);
        ground.setLineColor(Color.blue);

        StaticBody wall = new StaticBody(this,side);
        wall.setPosition(new Vec2(-50,0));
        wall.setFillColor(seeThrough);
        wall.setLineColor(seeThrough);

        StaticBody wall2 = new StaticBody(this,side);
        wall2.setPosition(new Vec2(50,0));
        wall2.setFillColor(seeThrough);
        wall2.setLineColor(seeThrough);

        StaticBody wall3 = new StaticBody(this,floor);
        wall3.setPosition(new Vec2(0,40));
        wall3.setFillColor(seeThrough);
        wall3.setLineColor(seeThrough);

        PortalBody portal1 = new PortalBody(this);
        portal1.setPosition(new Vec2(0,20));

        PortalBody portal2 = new PortalBody(this);
        portal2.setPosition(new Vec2(33,-20));

        getJohn().setPosition(new Vec2(0,10));
        JonMickCollisions johnListener = new JonMickCollisions(this,game,getJohn());
        getJohn().addCollisionListener(johnListener);

        getJohn().resetScore(game.getView().getScore());
    }
}
