import java.awt.*;

/**
 * The Cell class models each individual cell of the game board.
 */
public class Cell {
    public static final int SIZE = 145; // Ukuran sel persegi
    public static final int PADDING = SIZE / 5;

    Seed content;
    int row, col;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        content = Seed.NO_SEED;
    }

    public void newGame() {
        content = Seed.NO_SEED;
    }

    public void paint(Graphics g) {
        // Hitung posisi x dan y untuk menggambar
        int x = col * SIZE;
        int y = row * SIZE;

        // Gambar border sel (opsional)
        g.setColor(new Color(220, 220, 220));
        g.fillRect(x, y, SIZE, SIZE);
        g.setColor(Color.GRAY);
        g.drawRect(x, y, SIZE, SIZE);

        // Gambar X atau O jika ada isinya
        if (content != Seed.NO_SEED) {
            content.draw(g, x, y, SIZE);
        }
    }
}
