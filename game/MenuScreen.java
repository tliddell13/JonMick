package game;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class MenuScreen extends GameWorld {
    Game game;
    MenuScreen(Game game,JFrame frame) throws IOException {
        super(game);

        ImageIcon MenuBackground = new ImageIcon("data/EntryScene.png");
        ImageIcon agentJonImage = new ImageIcon("data/AgentJohn.png");
        ImageIcon soundImage = new ImageIcon("data/sound.png");

        this.game = game;

        //fonts
        Font buttonFont = new Font("Sans Serif", Font.BOLD,30);
        Font scoreFont = new Font("Sans Serif", Font.BOLD,20);

        //buttons
        JButton NewGame = new JButton("New Game");
        JButton Continue = new JButton("Continue");
        JButton sound = new JButton();
        JButton seeAll = new JButton("See All");

        JPanel panel = new JPanel();
        panel.setBounds(500,300,200,100);
        panel.setBackground(new Color(130,130,130));

        JPanel soundPanel = new JPanel();
        soundPanel.setBounds(1000,80,200,120);
        soundPanel.setBackground(new Color(130,130,130));

        NewGame.setBounds(500,400,200,100);
        NewGame.setActionCommand("1");
        NewGame.setOpaque(false);
        NewGame.setContentAreaFilled(false);
        NewGame.setBorderPainted(false);
        NewGame.addMouseListener((new MenuActionListener(this,frame,NewGame)));
        NewGame.setFont(buttonFont);

        Continue.setBounds(500,300,200,100);
        Continue.setActionCommand("2");
        Continue.setOpaque(false);
        Continue.setContentAreaFilled(false);
        Continue.setBorderPainted(false);
        Continue.addMouseListener((new MenuActionListener(this,frame,Continue)));
        Continue.setFont(buttonFont);

        sound.setBounds(500,300,200,100);
        sound.setActionCommand("3");
        sound.setOpaque(false);
        sound.setContentAreaFilled(false);
        sound.setBorderPainted(false);
        sound.addMouseListener((new MenuActionListener(this,frame,sound)));
        sound.setIcon(soundImage);
        sound.setHideActionText(true);

        seeAll.setBounds(40,350,100,20);
        seeAll.setActionCommand("4");
        seeAll.addMouseListener(new MenuActionListener(this,frame,seeAll));


        JLabel background = new JLabel(MenuBackground);
        background.setBounds(0,0,1200,800);

        JLabel agentJon = new JLabel(agentJonImage);
        agentJon.setBounds(200,100,800,200);

        panel.add(Continue);
        panel.add(NewGame);

        soundPanel.add(sound);

        ArrayList<String> names = new ArrayList<>();
        ArrayList<Integer> scores = new ArrayList<>();

        //display the top score on the menu
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

        int one = Collections.max(scores);
        int onePos = scores.indexOf(one);

        //couldn't orientation right on high score text without using html, so split it into separate labels
        JTextArea HighScore = new JTextArea("HIGH SCORE");
        HighScore.setFont(scoreFont);
        HighScore.setLineWrap(true);
        HighScore.setOpaque(false);
        HighScore.setBorder(BorderFactory.createEmptyBorder());
        HighScore.setBounds(20, 200,200,50);

        JTextArea Name = new JTextArea("Name: " + names.get(onePos));
        Name.setFont(scoreFont);
        Name.setLineWrap(true);
        Name.setOpaque(false);
        Name.setBorder(BorderFactory.createEmptyBorder());
        Name.setBounds(20, 250,250,50);

        JTextArea Score = new JTextArea("Score: " + scores.get(onePos));
        Score.setFont(scoreFont);
        Score.setLineWrap(true);
        Score.setOpaque(false);
        Score.setBorder(BorderFactory.createEmptyBorder());
        Score.setBounds(20, 300,200,50);

        //add everything to the frame
        frame.setSize(1200,800);
        frame.add(panel);
        frame.add(agentJon);
        frame.add(seeAll);
        frame.add(HighScore,BorderLayout.CENTER);
        frame.add(Name,BorderLayout.CENTER);
        frame.add(Score,BorderLayout.CENTER);
        frame.add(background);
        frame.add(soundPanel);
        frame.setLayout(null);
    }

}

