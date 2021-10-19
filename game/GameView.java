package game;

import city.cs.engine.UserView;

import javax.swing.*;
import java.awt.*;

public class GameView extends UserView {
    //private final Image level1background;
    private Image background;
    private final Image health = new ImageIcon("data/heart.png").getImage();
    private final Image fireball = new ImageIcon("data/fireballRight.gif").getImage();
    private int healthCount;
    private int fireballCount;
    private int score;
    private int tempScore;
    Font font = new Font("SansSerif", Font.BOLD | Font.ITALIC,20);

    public GameView(GameWorld w, int width, int height) {
        super(w, width, height);
        background = new ImageIcon("data/Level1Background.png").getImage();
        healthCount = w.getJohn().getHealth();
        fireballCount = 5;
        score = w.getJohn().getScore() + w.getGame().getScore();
    }
    public void setBackground(Image pic) {
        background = pic;
    }
    public void setHealthCount(int x) {
        healthCount = x;
    }
    //fireballs available minus fireballs on the map
    public void setFireballCount(int x) {fireballCount = 5 - x;}
    public void setScore(int x) {tempScore = x;}
    public int getScore() {return score;}
    public void addScore() {
        score = score + tempScore;
    }

    @Override
    protected void paintForeground(Graphics2D g) {
        super.paintForeground(g);
        g.setFont(font);
        g.drawString("Ammo:",40,70);
        g.drawString("Health:", 40,40);
        g.drawString("Score: " + tempScore,40,100);
        for (int x = 0; x < healthCount; x++) {
            g.drawImage(health, 120 + x * 20, 30, 15, 15, this);
        }
        for (int x = 0; x < fireballCount; x++)  {
            g.drawImage(fireball, 120 + x * 25, 55,25,15,this);
        }
    }

    @Override
    protected void paintBackground(Graphics2D g) {
        super.paintBackground(g);
        //background image
        g.drawImage(background, 0, -40, this);
    }

    @Override
    public void setBackground(Color bg) {
        super.setBackground(bg);
    }
}
