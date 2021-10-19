package game;

import city.cs.engine.*;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.io.IOException;

public class Game {


    private GameWorld world;

    JFrame frame = new JFrame("John Mick Menu");
    JFrame GameFrame = new JFrame("John Mick");
    Image bossLevelBackground = new ImageIcon("data/EntryScene.png").getImage();

    /** A graphical display of the world (a specialised JPanel). */
    private final GameView view;

    private SoundClip gameMusic;

    JonMickController controller;

    boolean soundOn = true;

    StepListener tracker;
    //keeps track of what level to go to
    private int level;
    //keeps track of score
    private final int score;
    /*
    The constructor for the game takes parameters for the level user is on, and the score so
    that the score doesn't reset at a return to menu screen
     */
    public Game(int i,int x) throws IOException {

        level = i;
        score = x;
        // start at level 1
        world = new MenuScreen(this,frame);

        // make a view
        //already did a lot with the view being this size, but i kept the zoom below 20
        view = new GameView(world, 1200, 800);


        //set the music
        if (soundOn) {
            try {
                gameMusic = new SoundClip("data/Level1Music.wav");   // Open an audio input stream
                gameMusic.loop();  // Set it to continuous playback (looping)
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                System.out.println(e);
            }
        }
        // add the view to a frame (Java top level window)
        frame.add(view);
        // enable the frame to quit the application
        // when the x button is pressed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);

        // don't let the frame be resized
        frame.setResizable(false);

        frame.setVisible(true);
        // start our game world simulation!
        world.start();
    }
    public void goToNextLevel() {
        //start of level 1
        if (world instanceof MenuScreen || world instanceof SecretWorld) {
            gameMusic.stop();

            Image level1Background = new ImageIcon("data/Level1Background.png").getImage();

            world.stop();

            world = new Level1(this);

            view.setWorld(world);

            view.setZoom(13);
            tracker = new TrackerLevel1(view,world.getJohn(),(Level1) world);

            world.addStepListener(tracker);

            // add some mouse actions
            // add this to the view, so coordinates are relative to the view
            view.addMouseListener(new MouseHandler(world, view,this));

            controller = new JonMickController(world.getJohn());
            view.addKeyListener(controller);
            view.addMouseListener(new GiveFocus(view));

            controller.updateJohn(world.getJohn());

            view.setBackground(level1Background);
            //set the tunes
            if (soundOn) {
                try {
                    gameMusic = new SoundClip("data/Level1Music.wav");   // Open an audio input stream
                    gameMusic.loop();  // Set it to continuous playback (looping)
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                    System.out.println(e);
                }
            }

            GameFrame.add(view);
            // enable the frame to quit the application
            // when the x button is pressed
            GameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //GameFrame.setLocationByPlatform(true);

            // don't let the frame be resized
            GameFrame.setResizable(false);

            GameFrame.pack();

            GameFrame.setVisible(true);

            world.start();
        }
        //start of level 2
        else if (world instanceof Level1) {

            Image level2background = new ImageIcon("data/level2background.png").getImage();
            world.stop();
            world.removeStepListener(tracker);
            world = new Level2(this);
            view.setWorld(world);
            view.setZoom(13);
            view.setBackground(level2background);

            removeAllMouseListeners(view);
            view.addMouseListener(new MouseHandler(world, view,this));
            view.addMouseListener(new GiveFocus(view));
            //change the tunes
            if (soundOn) {
                try {
                    gameMusic = new SoundClip("data/Level2Music.wav");   // Open an audio input stream
                    gameMusic.loop();  // Set it to continuous playback (looping)
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                    System.out.println(e);
                }
            }

            world.start();
            controller.updateJohn(world.getJohn());
            GameFrame.setVisible(true);
            //step listener for level 2
            tracker = new TrackerLevel2(view,world.getJohn(),(Level2) world);
            world.addStepListener(tracker);
        }
        else if (world instanceof Level2) {
            world.stop();
            world.removeStepListener(tracker);
            world = new BossLevel(this);
            view.setWorld(world);
            view.setZoom(13);
            view.setBackground(bossLevelBackground);

            removeAllMouseListeners(view);
            view.addMouseListener(new MouseHandler(world, view, this));
            view.addMouseListener(new GiveFocus(view));
            //Change the tunes
            if (soundOn) {
                try {
                    gameMusic = new SoundClip("data/BossTrack.wav");   // Open an audio input stream
                    gameMusic.loop();  // Set it to continuous playback (looping)
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                    System.out.println(e);
                }
            }

            world.start();
            controller.updateJohn(world.getJohn());
            //step listener for boss level
            tracker = new TrackerBossLevel(view, world.getJohn(), (BossLevel) world);
            world.addStepListener(tracker);
        }
        else if (world instanceof BossLevel) {
            world.stop();
            world.removeStepListener(tracker);
            world = new YouWon(this);
            view.setWorld(world);
            view.setZoom(13);
            view.setBackground(bossLevelBackground);

            removeAllMouseListeners(view);
            view.addMouseListener(new MouseHandler(world, view, this));
            view.addMouseListener(new GiveFocus(view));
            //Change the tunes
            if (soundOn) {
                try {
                    gameMusic = new SoundClip("data/BossTrack.wav");   // Open an audio input stream
                    gameMusic.loop();  // Set it to continuous playback (looping)
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                    System.out.println(e);
                }
            }

            world.start();
            //step listener for boss level
            tracker = new YouWonTracker((YouWon) world);
            world.addStepListener(tracker);
        }
    }
    public void goToSecretWorld() {
        world.stop();
        world.removeStepListener(tracker);
        world = new SecretWorld(this);
        view.setWorld(world);
        view.setZoom(13);
        view.setBackground(bossLevelBackground);

        removeAllMouseListeners(view);
        view.addMouseListener(new MouseHandler(world, view, this));
        view.addMouseListener(new GiveFocus(view));
        //Change the tunes
        if (soundOn) {
            try {
                gameMusic = new SoundClip("data/BossTrack.wav");   // Open an audio input stream
                gameMusic.loop();  // Set it to continuous playback (looping)
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                System.out.println(e);
            }
        }

        tracker = new SecretWorldTracker(view,(SecretWorld) world);
        world.addStepListener(tracker);

        world.start();
        controller.updateJohn(world.getJohn());

    }
    //function I made to get rid of all the mouse listeners
    public void removeAllMouseListeners(GameView view) {
        MouseListener[] listeners = view.getMouseListeners();
        for (MouseListener listener : listeners) {
            view.removeMouseListener(listener);
        }
    }
    public void setLevel(int x) {this.level = x;}
    public GameView getView() {
        return view;
    }
    public JFrame getFrame() {
        return GameFrame;
    }
    public int getLevel() { return level;}
    public int getScore() {return score;}


    public SoundClip getGameMusic() {
        return gameMusic;
    }

    /** Run the game. */
    public static void main(String[] args) throws IOException {
        new Game(1,0);
    }
}
