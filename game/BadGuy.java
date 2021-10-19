package game;

import city.cs.engine.*;

public class BadGuy extends Walker {
    //Walker class for bad guy in first level
    private final static Shape BadGuyShape = new BoxShape(2,4);
    private int hits = 0;
    private boolean destroy = false;
    public BadGuy(World world) {
        super(world,BadGuyShape);
    }
    //when bad guy is hit by a fireball
    public void hit() {
        hits++;
        int dead = 2;
        if (hits == dead) {
            destroy = true;
        }
    }
    public boolean getDestroy() {
        return destroy;
    }
}
