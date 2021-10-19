package game;

import city.cs.engine.*;

//the villain for level 2
public class BadGuy2 extends Walker {
    private final static Shape BadGuyShape = new BoxShape(2,5);
    //keep track of how many times hes been hit
    private int hits = 0;
    private boolean destroy = false;
    private boolean onMap = true;
    public BadGuy2(World world) {
        super(world,BadGuyShape);
    }
    //when bad guy is hit by a fireball
    public void hit() {
        hits++;
        //3 hits to kill a bad guy in level 2
        int dead = 3;
        if (hits == dead) {
            destroy = true;
            onMap = false;
        }
    }
    public boolean getDestroy() {
        return destroy;
    }
    public boolean getOnMap() {
        return onMap;
    }

    public void setOnMap(boolean onMap) {
        this.onMap = onMap;
    }
}
