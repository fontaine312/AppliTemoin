package IHM;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImagePanel extends JPanel {

    private BufferedImage image;
    final String path = "C:\\Users\\yvesf\\IdeaProjects\\ProjetFramework\\Image\\logo.png";

    public ImagePanel() {
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, getParent().getX(), getParent().getY(), getParent()); // see javadoc for more info on the parameters
    }

}