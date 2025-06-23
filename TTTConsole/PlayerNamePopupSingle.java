import javax.swing.*;
import java.awt.*;

public class PlayerNamePopupSingle extends JDialog {
    private JTextField nameField;
    private boolean submitted = false;

    public PlayerNamePopupSingle(Frame parent, String title, String labelText, Color bgColor) {
        super(parent, title, true);

        JPanel inputPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 10, 25));
        inputPanel.setBackground(bgColor);

        JLabel label = new JLabel(labelText);
        label.setForeground(Color.WHITE);
        inputPanel.add(label);

        nameField = new JTextField();
        nameField.setBackground(new Color(69, 73, 74));
        nameField.setForeground(Color.WHITE);
        nameField.setCaretColor(Color.WHITE);
        inputPanel.add(nameField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(bgColor);

        JButton okButton = new JButton("OK");
        okButton.setBackground(new Color(22, 58, 111));
        okButton.setForeground(Color.WHITE);
        okButton.setFocusPainted(false);
        okButton.addActionListener(e -> {
            String input = nameField.getText().trim();
            if (input.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Nama tidak boleh kosong!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            } else {
                submitted = true;
                setVisible(false);
            }
        });

        // Aktifkan ENTER untuk OK
        getRootPane().setDefaultButton(okButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBackground(new Color(224, 224, 224));
        cancelButton.setForeground(Color.BLACK);
        cancelButton.setFocusPainted(false);
        cancelButton.addActionListener(e -> {
            submitted = false;
            setVisible(false);
        });

        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
        setResizable(false);
    }

    public boolean isSubmitted() {
        return submitted;
    }

    public String getPlayerName() {
        return nameField.getText().trim();
    }
}
