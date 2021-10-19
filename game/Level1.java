package game;

import city.cs.engine.*;
import city.cs.engine.Shape;
import org.jbox2d.common.Vec2;

import java.awt.*;

public class Level1 extends GameWorld {

    //keeps track of enemies defeated
    private int VillainsDefeated = 0;
    //images for the bad guys
    private final BodyImage BadGuyParachute = new BodyImage("data/badGuyParachute.png", 15f);
    //initialize the ground
    private final StaticBody ground;
    StaticBody bouncyPlatform;
    //initialize the bad guy
    private BadGuy guy = new BadGuy(this);

    //check if bad guy lands
    private boolean landed = false;

    //check if a bad guy is on map
    private boolean onMap = true;
    public Level1(Game game) {
        super(game);

        //color for the ground
        Color green = new Color(68,105,52);
        //see through walls
        Color seeThrough = new Color(0,0,0,1);

        guy.setPosition(new Vec2(0,0));

        //the ground for level 1
        Shape floor = new BoxShape(60,5);
        //ground
        ground = new StaticBody(this, floor);
        SolidFixture groundFriction = new SolidFixture(ground,floor);
        groundFriction.setFriction(4);
        ground.setPosition(new Vec2(0,-33));
        ground.setFillColor(green);
        ground.setLineColor(green);
        //walls that make an invisible box
        Shape side = new BoxShape(5,60);

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

        Shape platformShape = new BoxShape(7,1);

        bouncyPlatform = new StaticBody(this,platformShape);
        SolidFixture bouncy = new SolidFixture(bouncyPlatform,platformShape);
        bouncy.setRestitution(1);
        bouncy.setFriction(4);
        bouncyPlatform.setPosition(new Vec2(0,-15));
        bouncyPlatform.setFillColor(new Color(150,150,160));
        bouncyPlatform.setLineColor(Color.gray);

        StaticBody rightPlatform = new StaticBody(this,platformShape);
        rightPlatform.setPosition(new Vec2(40,0));
        SolidFixture platformRightFriction = new SolidFixture(rightPlatform,platformShape);
        platformRightFriction.setFriction(4);
        rightPlatform.setFillColor(Color.gray);
        rightPlatform.setLineColor(Color.gray);

        StaticBody leftPlatform = new StaticBody(this,platformShape);
        leftPlatform.setPosition(new Vec2(-40,0));
        SolidFixture platformLeftFriction = new SolidFixture(leftPlatform,platformShape);
        platformLeftFriction.setFriction(4);
        leftPlatform.setFillColor(Color.gray);
        leftPlatform.setLineColor(Color.gray);

        //spawn point for john
        getJohn().setPosition(new Vec2(-40,-29));

        //collisions for John
        JonMickCollisions johnListener = new JonMickCollisions(this,game,getJohn());
        getJohn().addCollisionListener(johnListener);

        Coin coin1 = new Coin(this);
        coin1.setPosition(new Vec2(40,2));

        Coin coin2 = new Coin(this);
        coin2.setPosition(new Vec2(-40,2));


        //spawn the enemy
        spawnBadGuy();

        //pickup the health
        HealthPickup pickup = new HealthPickup(getJohn());
        getJohn().addCollisionListener(pickup);

        //carry over score from the previous level
        getJohn().resetScore(game.getView().getScore());
    }
    public void spawnBadGuy() {
        guy.setPosition(new Vec2(30,20));
        guy.setGravityScale(10);
        guy.addImage(BadGuyParachute);
        Level1Collisions collision = new Level1Collisions(this, guy);
        guy.addCollisionListener(collision);
    }

    public BadGuy getGuy() {
        return guy;
    }
    public void setGuy(BadGuy g) {
        guy = g;
    }
    public StaticBody getGround() {
        return ground;
    }
    public void setLanded(boolean t) {
        landed = t;
    }
    public boolean getLanded() {
        return landed;
    }
    public int getVillainsDefeated() {return VillainsDefeated;}
    public void setVillainsDefeated() {VillainsDefeated = VillainsDefeated + 1;}
    public boolean getOnMap() {return onMap;}
    public void setOnMap(boolean t) {onMap = t;}

}
