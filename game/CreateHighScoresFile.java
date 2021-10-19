package game;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CreateHighScoresFile {
    //keeps track of scores on current computer
    public CreateHighScoresFile(int score) {
        try {
            //create new file if one doesn't exist
            File highScores = new File("AgentJohnHighScores.txt");
            if (highScores.createNewFile()) {
                String input = JOptionPane.showInputDialog("First high score, enter name:");
                try (FileWriter writer = new FileWriter(highScores, true)) {
                    writer.write(input + ": " + score + "\n");
                }

            }
            else {
                FileWriter writer = null;
                try {
                    String input = JOptionPane.showInputDialog("Enter a name for your score:");
                    writer = new FileWriter(highScores, true);
                    writer.write(input + ": " + score + "\n");
                } finally {
                    if (writer != null) {
                        writer.close();
                    }
                }
            }
        }
            catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

    }
}
