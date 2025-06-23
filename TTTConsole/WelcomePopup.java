import javax.swing.*;
import java.awt.*;

public class WelcomePopup extends JDialog {
    public WelcomePopup(Frame parent) {
        super(parent, "Selamat Datang", true);

        JLabel label = new JLabel("<html><center><h2>Welcome to Tic Tac Toe!</h2><br>Klik OK untuk memulai</center></html>");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setForeground(Color.WHITE);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> dispose());

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(69, 73, 74));
        panel.add(label, BorderLayout.CENTER);
        panel.add(okButton, BorderLayout.SOUTH);

        getContentPane().add(panel);
        setSize(300, 200);
        setLocationRelativeTo(parent);
        setResizable(false);
    }
}
