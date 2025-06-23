import javax.swing.*;
import java.awt.*;
import java.net.URL;

public enum Seed {
    CROSS("X.png"),
    NOUGHT("O.png"),
    NO_SEED(null);

    private final ImageIcon icon;

    Seed(String imagePath) {
        if (imagePath != null) {
            URL imageURL = getClass().getResource(imagePath);
            if (imageURL != null) {
                icon = new ImageIcon(imageURL);
            } else {
                System.err.println("Image not found: " + imagePath);
                icon = null;
            }
        } else {
            icon = null;
        }
    }

    public void draw(Graphics g, int x, int y, int size) {
        if (icon != null) {
            Image img = icon.getImage();
            g.drawImage(img, x + 10, y + 10, size - 25, size - 25, null);
        }
    }
}
