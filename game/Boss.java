package game;

import city.cs.engine.*;
//class for the boss
public class Boss extends Walker {
    //walker class for boss in final level
    //shape for the boss
    private static final Shape BossShape = new BoxShape(3,5);

    //tracks how many times he has been hit
    private int hits = 0;
    private boolean destroy = false;
    //so we can know what direction he is facing
    private boolean left = true;
    public Boss(World world) {
        super(world, BossShape);
    }

    public void hit() {
        hits = hits + 1;
        //10 hits to kill the boss in the final level
        int dead = 10;
        if (hits == dead) {
            destroy = true;
        }
    }
    public boolean getDestroy() {
        return destroy;
    }
    public boolean getLeft() {return left;}
    public void setLeft(boolean l) {this.left = l;}
}
