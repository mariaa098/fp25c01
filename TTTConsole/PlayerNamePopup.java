import javax.swing.*;
import java.awt.*;

public class PlayerNamePopup extends JDialog {
    private JTextField player1Field;
    private JTextField player2Field;
    private boolean submitted = false;

    public PlayerNamePopup(Frame parent) {
        super(parent, "Input Nama", true); // true = modal

        // Panel layout
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        panel.add(new JLabel("Player 1 (X):"));
        player1Field = new JTextField();
        panel.add(player1Field);

        panel.add(new JLabel("Player 2 (O):"));
        player2Field = new JTextField();
        panel.add(player2Field);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            submitted = true;
            setVisible(false);
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {
            submitted = false;
            setVisible(false);
        });

        panel.add(okButton);
        panel.add(cancelButton);

        add(panel);
        pack();
        setLocationRelativeTo(parent); // posisi dialog di tengah
    }

    public boolean isSubmitted() {
        return submitted;
    }

    public String getPlayer1Name() {
        return player1Field.getText().trim();
    }

    public String getPlayer2Name() {
        return player2Field.getText().trim();
    }
}
