package game;

import city.cs.engine.*;
import city.cs.engine.Shape;
import org.jbox2d.common.Vec2;

import java.awt.*;

public class BossLevel extends GameWorld{

    Shape platformShape = new BoxShape(5,1);
    private final StaticBody movingPlatform = new StaticBody(this,platformShape);
    StaticBody ground;


    /**
     * each level gets a tracker to time certain events
     * @level the instance of the boss level you want to use the tracker for
     */
    Boss boss = new Boss(this);

    public BossLevel(Game game) {

        super(game);

        //gif for when the boss appears from the ground
        BodyImage BossAppearLeft = new BodyImage("data/BossAppearLeft.gif", 10f);
        //image facing left
        BodyImage BossLeft = new BodyImage("data/BOSSLeft.png", 10f);

        Color seeThrough = new Color(0,0,0,1);

        // Buildings they stand on
        Shape BuildingShape = new BoxShape(10, 25);

        StaticBody building1 = new StaticBody(this, BuildingShape);
        SolidFixture building1Friction = new SolidFixture(building1,BuildingShape);
        building1Friction.setFriction(4);
        building1.setPosition(new Vec2(-35, -25));
        building1.setFillColor(Color.darkGray);
        building1.setLineColor(Color.darkGray);


        StaticBody building2 = new StaticBody(this, BuildingShape);
        SolidFixture building2Friction = new SolidFixture(building2,BuildingShape);
        building2Friction.setFriction(4);
        building2.setPosition(new Vec2(35,-25));
        building2.setFillColor(Color.darkGray);
        building2.setLineColor(Color.darkGray);

        Shape side = new BoxShape(5,60);

        Shape floor = new BoxShape(60,5);
        //ground
        ground = new StaticBody(this, floor);
        ground.setPosition(new Vec2(0,-33));
        ground.setFillColor(seeThrough);
        ground.setLineColor(seeThrough);

        StaticBody wall = new StaticBody(this,side);
        wall.setPosition(new Vec2(-50,0));
        wall.setFillColor(seeThrough);
        wall.setLineColor(seeThrough);

        StaticBody wall2 = new StaticBody(this,side);
        wall2.setPosition(new Vec2(50,0));
        wall2.setFillColor(seeThrough);
        wall2.setLineColor(seeThrough);


        SolidFixture platformFriction = new SolidFixture(movingPlatform,platformShape);
        platformFriction.setFriction(4);
        movingPlatform.setLineColor(Color.gray);
        //spawn point for the moving platform
        movingPlatform.setPosition(new Vec2(-20,0));

        // adds the spawn point for the villain
        boss.setPosition(new Vec2(30,0));
        boss.addImage(BossAppearLeft);
        boss.removeAllImages();
        boss.addImage(BossLeft);

        //collisions for the boss
        BossLevelCollisions collision = new BossLevelCollisions(this,game,boss);
        boss.addCollisionListener(collision);

        //Adds new John Mick to the world
        getJohn().setPosition(new Vec2(-40, 6.5f));
        //Pickup Health
        HealthPickup pickup = new HealthPickup(getJohn());
        getJohn().addCollisionListener(pickup);
        //Hit Boss
        JonMickCollisions hit = new JonMickCollisions(this,game,getJohn());
        getJohn().addCollisionListener(hit);

        //carry over score from the previous level
        getJohn().resetScore(game.getView().getScore());
    }

    int x = 0;
    boolean DirectionSwitch = true;


    //Moves the platform, function called in Tracker class
    public void movePlatform() {
        if (x < 400 && DirectionSwitch) {
            movingPlatform.move(new Vec2(.1f,0));
            x++;
            if (x == 400)
                DirectionSwitch = false;
        }
        else {
                movingPlatform.move(new Vec2(-.1f, 0));
                x--;
                if (x == 0)
                    DirectionSwitch = true;
        }
    }
    public Boss getBoss() {return boss;}

}

