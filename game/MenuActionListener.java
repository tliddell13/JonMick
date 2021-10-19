package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/*
the menu action listener is used with the menu screen to perform actions based on the mouse
 */
public class MenuActionListener implements MouseListener {
private final MenuScreen menu;
private final JFrame frame;
private final JButton button;

Font buttonFont = new Font("Sans Serif", Font.BOLD,30);
Font buttonFontBig = new Font("Sans Serif", Font.BOLD,35);
ImageIcon soundImage = new ImageIcon("data/sound.png");
ImageIcon soundImageOff = new ImageIcon("data/soundOff.png");

MenuActionListener(MenuScreen menu, JFrame frame,JButton button) {
    this.menu = menu;
    this.frame = frame;
    this.button = button;
}

    @Override
    public void mouseClicked(MouseEvent e) {
        switch (button.getActionCommand()) {
            //New Game
            case "1":
                frame.dispose();
                this.menu.game.goToNextLevel();
                break;
            //Continue Game
            case "2":
                frame.dispose();
                for (int x = 0; x < menu.game.getLevel(); x++) {
                    this.menu.game.goToNextLevel();
                }
                break;
            //shut the music off
            case "3":
                if (menu.game.soundOn) {
                    button.setIcon(soundImageOff);
                    menu.game.soundOn = false;
                    menu.game.getGameMusic().stop();
                } else {
                    button.setIcon(soundImage);
                    menu.game.soundOn = true;
                }
                break;
            //see the recorded scores
            case "4":
                try {
                    seeScores();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                System.out.println("enter");
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //I don't want the text to get big if the mouse is in the volume button
        if (this.button.getActionCommand().equals("1") || this.button.getActionCommand().equals("2")) {
            this.button.setFont(buttonFontBig);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //change the button font back to small when mouse exits
        if (this.button.getActionCommand().equals("1") || this.button.getActionCommand().equals("2")) {
            this.button.setFont(buttonFont);
        }
    }
    public void seeScores() throws IOException {
    //lists that hold info about scores
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Integer> scores = new ArrayList<>();

        try (FileReader fr = new FileReader("AgentJohnHighScores.txt"); BufferedReader reader = new BufferedReader(fr)) {
            String line = reader.readLine();
            while (line != null) {
                String[] tokens = line.split(": ");
                String name = tokens[0];
                names.add(name);
                int score = Integer.parseInt(tokens[1]);
                scores.add(score);
                System.out.println("Name: " + name + " Score: " + score);
                line = reader.readLine();
            }
        }
        JFrame frame = new JFrame();
        //arrays of text areas to display the text on the JFrame
        JTextArea [] nameText = new JTextArea[names.size()];
        JTextArea [] scoreText = new JTextArea[scores.size()];

        int x = 0;
        //loop through names and display them
        for (String name : names) {
            nameText[x] = new JTextArea(name);
            nameText[x].setBounds(20,20 + (x * 25),120,20);
            frame.add(nameText[x]);
            x++;
        }
        int y = 0;
        //loop through scores and display them with the names
        for (Integer score : scores) {
            scoreText[y] = new JTextArea(String.valueOf(score));
            scoreText[y].setBounds(200,20 + (y * 25),100,20);
            frame.add(scoreText[y]);
            y++;
        }
        frame.setSize(300,900);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    
}
