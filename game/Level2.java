package game;

import city.cs.engine.*;
import city.cs.engine.Shape;
import org.jbox2d.common.Vec2;

import java.awt.*;

public class Level2 extends GameWorld{
    private final StaticBody movingPlatform;
    private final BadGuy2 guy = new BadGuy2(this);
    private int VillainsDefeated = 0;
    private boolean shoot = true;


    BodyImage BadGuy2standLeft = new BodyImage("data/badGuy2standLeft.png",11f);

    public Level2(Game game) {
        super(game);

        //color for the ground
        Color tan = new Color(210,180,140);
        //invisible walls
        Color seeThrough = new Color(0,0,0,1);
        //the ground
        Shape floor = new BoxShape(60,5);
        //initialize the ground
        StaticBody ground = new StaticBody(this, floor);
        SolidFixture groundFriction = new SolidFixture(ground,floor);
        groundFriction.setFriction(4);
        ground.setPosition(new Vec2(0,-33));
        ground.setFillColor(tan);
        ground.setLineColor(tan);

        Shape side = new BoxShape(5,60);

        StaticBody wall = new StaticBody(this,side);
        wall.setPosition(new Vec2(-50,0));
        wall.setFillColor(seeThrough);
        wall.setLineColor(seeThrough);

        StaticBody wall2 = new StaticBody(this,side);
        wall2.setPosition(new Vec2(50,0));
        wall2.setFillColor(seeThrough);
        wall2.setLineColor(seeThrough);

        //platforms
        Shape platformShape = new BoxShape(5,1);
        //right side
        StaticBody platformRight = new StaticBody(this,platformShape);
        SolidFixture platformRightFriction = new SolidFixture(platformRight,platformShape);
        platformRightFriction.setFriction(4);
        platformRight.setPosition(new Vec2(40,-10));
        platformRight.setFillColor(tan);
        platformRight.setLineColor(tan);
        //left side
        StaticBody platformLeft = new StaticBody(this,platformShape);
        SolidFixture platformLeftFriction = new SolidFixture(platformLeft,platformShape);
        platformLeftFriction.setFriction(4);
        platformLeft.setPosition(new Vec2(-40,-15));
        platformLeft.setFillColor(tan);
        platformLeft.setLineColor(tan);

        //center moving
        movingPlatform = new StaticBody(this,platformShape);
        SolidFixture movingPlatformFriction = new SolidFixture(movingPlatform,platformShape);
        movingPlatformFriction.setFriction(4);
        movingPlatform.setPosition(new Vec2(0,-29));
        movingPlatform.setFillColor(tan);
        movingPlatform.setLineColor(tan);
        //spawn John
        getJohn().setPosition(new Vec2(-43,-29));

        //collisions for John
        JonMickCollisions johnListener = new JonMickCollisions(this,game,getJohn());
        getJohn().addCollisionListener(johnListener);
        //pickup health for John
        HealthPickup pickup = new HealthPickup(getJohn());
        getJohn().addCollisionListener(pickup);

        guy.setPosition(new Vec2(40,-29));
        guy.addImage(BadGuy2standLeft);

        Level2Collisions collision = new Level2Collisions(this, guy);
        guy.addCollisionListener(collision);

        //carry over score from the previous level
        getJohn().resetScore(game.getView().getScore());
    }

    public BadGuy2 getGuy() {
        return guy;
    }

    public int getVillainsDefeated() {
        return VillainsDefeated;
    }
    public void VillainDefeated() {
        VillainsDefeated++;
    }
    public boolean getShoot() {
        return shoot;
    }
    public void setShoot(boolean t) {
        this.shoot = t;
    }


    int x = 0;
    boolean DirectionSwitch = true;
    //Moves the platform, function called in Tracker class
    public void movePlatform() {
        if (x < 400 && DirectionSwitch) {
            movingPlatform.move(new Vec2(0,.1f));
            x++;
            if (x == 400)
                DirectionSwitch = false;
        }
        else {
            movingPlatform.move(new Vec2(0f, -.1f));
            x--;
            if (x == 0)
                DirectionSwitch = true;
        }
    }

}
