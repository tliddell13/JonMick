package game;

import city.cs.engine.BodyImage;
import city.cs.engine.SoundClip;
import org.jbox2d.common.Vec2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class MouseHandler extends MouseAdapter {

    private final GameWorld world;
    private final GameView view;
    private final Game game;
    private boolean left;
    private boolean save = false;

    BodyImage playButton = new BodyImage("data/PlayButton.png",3f);
    BodyImage pauseButton = new BodyImage("data/PauseButton.png",3f);

    BodyImage saveButton = new BodyImage("data/Save.png",2f);
    BodyImage saveButtonSmall = new BodyImage("data/Save.png",1.5f);

    private static final BodyImage LeftImage =
            new BodyImage("data/JohnLeft.png", 6f);

    private static final BodyImage RightImage =
            new BodyImage("data/JohnRight.png", 6f);

    private static final BodyImage GunLeft =
            new BodyImage("data/JohnGunLeft.png",6f);

    private static final BodyImage GunRight =
            new BodyImage("data/JohnGunRight.png", 6f);

    private static final BodyImage fireballRight =
            new BodyImage("data/fireballRight.gif",2f);

    private static final BodyImage fireballLeft =
            new BodyImage("data/fireballLeft.gif",2f);
    private static SoundClip fireballCast;
    static {
        try {
            fireballCast = new SoundClip("data/FireballCast.wav");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }

    public MouseHandler(GameWorld w, GameView v, Game g){
        this.world = w;
        this.view = v;
        this.game = g;
        w.getHomePosition();
    }
    public int mouseInButton(float x,float y) {
        if (x > 39 && x < 41) {
            //home button
            if (y > 24 && y < 26) {
                return 1;
            }
            //pause button
            if (y > 19 && y < 21) {
                return 2;
            }
            if (y > 14 && y <16) {
                return 3;
            }
        }
        return 0;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //get the mouse coordinates
        Point mousePoint = e.getPoint();
        //transform them to world coordinates
        Vec2 worldPoint = view.viewToWorld(mousePoint);

        //go to home menu
        if (mouseInButton(worldPoint.x,worldPoint.y) == 1) {
            world.stop();
            game.getFrame().dispose();
            game.getGameMusic().stop();
            view.addScore();
            try {
                new Game(game.getLevel(), view.getScore());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        //pause the game
        else if (mouseInButton(worldPoint.x,worldPoint.y) == 2 && world.isRunning()) {
            world.getPauseButton().removeAllImages();
            world.getPauseButton().addImage(playButton);
            world.stop();
        }
        //resume the game
        else if (mouseInButton(worldPoint.x,worldPoint.y) == 2) {
            world.getPauseButton().removeAllImages();
            world.getPauseButton().addImage(pauseButton);
            world.start();
        }
        //save your progress
        else if (mouseInButton(worldPoint.x,worldPoint.y) == 3) {
            world.getSaveButton().removeAllImages();
            world.getSaveButton().addImage(saveButtonSmall);
            if (world instanceof Level2) {
                game.setLevel(2);
            }
            else if (world instanceof BossLevel) {
                game.setLevel(3);
            }
            save = true;
        }

        if (mouseInButton(worldPoint.x,worldPoint.y) == 0) {
            //limit the amount of fireballs user can shoot so they can't spam it
            if (world.getFireballCount() < 5) {
                fireballCast.play();
                world.setFireballCount(1);
                Fireball fireball = new Fireball(world);

                FireballTracker tracker = new FireballTracker(view, world.getJohn(), world, fireball, mousePoint);
                world.addStepListener(tracker);

                FireballCollisions collisions = new FireballCollisions(world, fireball);
                fireball.addCollisionListener(collisions);
                //send fireball right
                if (worldPoint.x > world.getJohn().getPosition().x) {
                    fireball.setPosition(new Vec2(world.getJohn().getPosition().x + 1, world.getJohn().getPosition().y));
                    fireball.removeAllImages();
                    fireball.addImage(fireballRight);
                    world.getJohn().removeAllImages();
                    world.getJohn().addImage(GunRight);
                    left = false;
                }
                //send fireball left
                else if (worldPoint.x < world.getJohn().getPosition().x) {
                    fireball.setPosition(new Vec2(world.getJohn().getPosition().x - 1, world.getJohn().getPosition().y));
                    fireball.removeAllImages();
                    fireball.addImage(fireballLeft);
                    world.getJohn().removeAllImages();
                    world.getJohn().addImage(GunLeft);
                    left = true;
                }
            }
        }
    }

    //empty methods for interface

    @Override
    public void mouseClicked(MouseEvent e) {
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        if (save) {
            world.getSaveButton().removeAllImages();
            world.getSaveButton().addImage(saveButton);
        }
        if (left) {
            world.getJohn().removeAllImages();
            world.getJohn().addImage(LeftImage);
        }
        else {
            world.getJohn().removeAllImages();
            world.getJohn().addImage(RightImage);
        }
    }
    @Override
    public void mouseEntered(MouseEvent e) {

    }
    @Override
    public void mouseExited(MouseEvent e) {
    }
}
