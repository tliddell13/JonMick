package game;

import city.cs.engine.BodyImage;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class JonMickController implements KeyListener {

    private static final BodyImage LeftImage =
            new BodyImage("data/JohnLeft.png", 6f);

    private static final BodyImage RightImage =
            new BodyImage("data/JohnRight.png", 6f);

    private static final BodyImage GunLeft =
            new BodyImage("data/JohnGunLeft.png",6f);

    private static final BodyImage GunRight =
            new BodyImage("data/JohnGunRight.png", 6f);

    private static final BodyImage WalkingLeft =
            new BodyImage("data/JohnWalkLeft.gif",6f);

    private static final BodyImage WalkingRight =
            new BodyImage("data/JohnWalkRight.gif", 6f);


    //Johns walking speed
    private static final float WALKING_SPEED = 20;

    //adds a John Mick to the controller

    private JonMick John;

    public JonMickController(JonMick s){ John = s; }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    //keeps track of the direction character should be facing
    boolean left = true;

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        // move left
        if (code == KeyEvent.VK_A) {
            John.startWalking(-WALKING_SPEED);
            John.removeAllImages();
            John.addImage(WalkingLeft);
            left = true;
        }
        //move right
        else if (code == KeyEvent.VK_D) {
            John.startWalking(WALKING_SPEED);
            John.removeAllImages();
            John.addImage(WalkingRight);
            left = false;
        }
        //jump
        else if (code == KeyEvent.VK_W) {
            John.jump(25);
        }
        //points gun to left
        else if (code == KeyEvent.VK_F && left) {
            John.removeAllImages();
            John.addImage(GunLeft);
        }
        //points gun to right
        else if (code == KeyEvent.VK_F) {
            John.removeAllImages();
            John.addImage(GunRight);
        }
    }

    @Override
    //keeps track of walking direction and image that should be used
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_A && left) {
            John.stopWalking();
            John.removeAllImages();
            John.addImage(LeftImage);
        }
        else if (code == KeyEvent.VK_D && !left) {
            John.stopWalking();
            John.removeAllImages();
            John.addImage(RightImage);
        }
        else if (code == KeyEvent.VK_F && left) {
            John.removeAllImages();
            John.addImage(LeftImage);
        }
        else if (code == KeyEvent.VK_F) {
            John.removeAllImages();
            John.addImage(RightImage);
        }

    }
    public void updateJohn(JonMick John){this.John = John;}
}
