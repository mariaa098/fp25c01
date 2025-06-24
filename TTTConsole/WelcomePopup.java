import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class WelcomePopup extends JDialog {

    public WelcomePopup(Frame parent) {
        super(parent, "Kelompok 1", true); // true = modal

        // Label Selamat Datang
        JLabel label = new JLabel("<html><center><h2>Welcome to Tic Tac Toe!</h2><br>Klik OK untuk memulai</center></html>");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setForeground(Color.WHITE);

        // Tombol OK
        JButton okButton = new JButton("OK");
        okButton.setBackground(new Color(0, 150, 136)); // Teal
        okButton.setForeground(Color.WHITE);
        okButton.setFocusPainted(false);
        okButton.addActionListener(e -> dispose()); // Tutup jendela

        // Tombol History
        JButton historyButton = new JButton("History");
        historyButton.setBackground(new Color(120, 120, 120)); // Abu
        historyButton.setForeground(Color.WHITE);
        historyButton.setFocusPainted(false);
        historyButton.addActionListener(e -> showHistory());

        // Panel tombol bawah
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(69, 73, 74));
        buttonPanel.add(okButton);
        buttonPanel.add(historyButton);

        // Panel utama
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(69, 73, 74));
        panel.add(label, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Tambahkan ke jendela
        getContentPane().add(panel);
        setSize(320, 200);
        setLocationRelativeTo(parent);
        setResizable(false);

        // Jika di-X, keluar dari program
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    // Menampilkan history dari database
    private void showHistory() {
        List<String> historyList = DatabaseHelper.getHistoryList();
        StringBuilder message = new StringBuilder("<html><body><h3>Riwayat Permainan:</h3>");
        for (String entry : historyList) {
            message.append(entry).append("<br>");
        }
        message.append("</body></html>");

        JOptionPane.showMessageDialog(
                this,
                new JLabel(message.toString()),
                "History",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}
