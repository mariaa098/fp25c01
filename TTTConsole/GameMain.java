import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.sound.sampled.*;
import java.io.*;

public class GameMain extends JPanel {
    private static final long serialVersionUID = 1L;

    public static final String TITLE = "Tic Tac Toe";
    public static final Color COLOR_BG = new Color(246, 246, 246);
    public static final Color COLOR_BG_STATUS = new Color(69, 73, 74);
    public static final Color COLOR_CROSS = new Color(182   , 212, 255);
    public static final Color COLOR_NOUGHT = new Color(14, 51, 107);
    public static final Font FONT_STATUS = new Font("OhhCR A Extended", Font.PLAIN, 12);

    private Board board;
    private State currentState;
    private Seed currentPlayer;
    private JLabel statusBar;

    private JButton resetButton;


    private AIPlayer aiPlayer;
    private boolean vsAI = true;

    private String player1Name = "Player";
    private String player2Name = "AI";

    public GameMain() {
        super.setLayout(new BorderLayout());

        // ==== Status Bar ====
        statusBar = new JLabel();
        statusBar.setFont(FONT_STATUS);
        statusBar.setBackground(COLOR_BG_STATUS);
        statusBar.setOpaque(true);
        statusBar.setHorizontalAlignment(JLabel.LEFT);
        statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 12));
        statusBar.setForeground(Color.WHITE);

        resetButton = new JButton("New Game");
        resetButton.setBackground(new Color(14, 51, 107));
        resetButton.setForeground(Color.WHITE);
        resetButton.setFocusPainted(false);
        resetButton.setPreferredSize(new Dimension(115, 30));
        resetButton.addActionListener(e -> restartGame());

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(COLOR_BG_STATUS);
        bottomPanel.add(statusBar, BorderLayout.CENTER);
        bottomPanel.add(resetButton, BorderLayout.EAST);
        super.add(bottomPanel, BorderLayout.PAGE_END);

        super.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();
                int row = mouseY / Cell.SIZE;
                int col = mouseX / Cell.SIZE;

                if (currentState == State.PLAYING) {
                    if (row >= 0 && row < Board.ROWS && col >= 0 && col < Board.COLS
                            && board.cells[row][col].content == Seed.NO_SEED) {

                        board.cells[row][col].content = currentPlayer;

                        if (currentPlayer == Seed.CROSS) {
                            playSound("sound_x.wav");
                        } else {
                            playSound("sound_o.wav");
                        }

                        State previousState = currentState;
                        currentState = board.stepGame(currentPlayer, row, col);

                        if (previousState == State.PLAYING && currentState != State.PLAYING) {
                            Timer timer = new Timer(300, new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    playGameStateSound(currentState, currentPlayer);
                                    ((Timer) e.getSource()).stop();
                                }
                            });
                            timer.start();
                        }

                        if (vsAI) {
                            if (currentState == State.PLAYING) {
                                currentPlayer = Seed.NOUGHT;

                                repaint();

                                Timer aiTimer = new Timer(300, new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        aiMove();
                                        ((Timer) e.getSource()).stop();
                                    }
                                });
                                aiTimer.setRepeats(false);
                                aiTimer.start();

                                return;
                            }
                            currentPlayer = Seed.CROSS;
                        } else {
                            currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
                        }
                    }
                } else {
                    newGame();
                }
                repaint();
            }
        });

//        statusBar = new JLabel();
        statusBar.setFont(FONT_STATUS);
        statusBar.setBackground(COLOR_BG_STATUS);
        statusBar.setOpaque(true);
        statusBar.setPreferredSize(new Dimension(300, 30));
        statusBar.setHorizontalAlignment(JLabel.LEFT);
        statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 12));

//        super.setLayout(new BorderLayout());
//        super.add(statusBar, BorderLayout.PAGE_END);
        super.setPreferredSize(new Dimension(Board.CANVAS_WIDTH, Board.CANVAS_HEIGHT + 30));
        super.setBorder(BorderFactory.createLineBorder(COLOR_BG_STATUS, 2, false));

        initGame();
        newGame();
    }

    public void initGame() {
        board = new Board();
        aiPlayer = new AIPlayerTableLookup(board);
    }

    public void newGame() {
        for (int row = 0; row < Board.ROWS; ++row) {
            for (int col = 0; col < Board.COLS; ++col) {
                board.cells[row][col].content = Seed.NO_SEED;
            }
        }
        currentPlayer = Seed.CROSS;
        currentState = State.PLAYING;
        aiPlayer.setSeed(Seed.NOUGHT);
        repaint();
    }

    private void chooseMode() {
        Object[] options = {"Lawan AI", "Lawan Teman"};
        int n = JOptionPane.showOptionDialog(
                null,
                "Pilih Mode Permainan:",
                "Mode Permainan",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (n == JOptionPane.CLOSED_OPTION) {
            System.exit(0); // Keluar jika user klik tombol X
        }

        vsAI = (n == JOptionPane.YES_OPTION);

    }
    private void askPlayerNames() {
        String player1 = "", player2 = "";

        while (player1.trim().isEmpty()) {
            PlayerNamePopupSingle popup1 = new PlayerNamePopupSingle(
                    null, "Player 1", "Masukkan nama Player 1 (X):", new Color(69, 73, 74)
            );
            popup1.setVisible(true);
            if (!popup1.isSubmitted()) System.exit(0);

            player1 = popup1.getPlayerName();
            if (player1.trim().isEmpty()) {
                JOptionPane.showMessageDialog(
                        null, "Nama Player 1 tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE
                );
            }
        }
        player1Name = player1;  // simpan

        while (player2.trim().isEmpty()) {
            PlayerNamePopupSingle popup2 = new PlayerNamePopupSingle(
                    null, "Player 2", "Masukkan nama Player 2 (O):", new Color(69, 73, 74)
            );
            popup2.setVisible(true);
            if (!popup2.isSubmitted()) System.exit(0);

            player2 = popup2.getPlayerName();
            if (player2.trim().isEmpty()) {
                JOptionPane.showMessageDialog(
                        null, "Nama Player 2 tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE
                );
            }
        }
        player2Name = player2;  // simpan
    }

    private void aiMove() {
        int[] move = aiPlayer.move();
        board.cells[move[0]][move[1]].content = aiPlayer.mySeed;
        playSound("sound_o.wav");

        State prevState = currentState;
        currentState = board.stepGame(aiPlayer.mySeed, move[0], move[1]);

        if (prevState == State.PLAYING && currentState != State.PLAYING) {
            Timer timer = new Timer(300, e -> {
                playGameStateSound(currentState, aiPlayer.mySeed);
                ((Timer) e.getSource()).stop();
            });
            timer.start();
        }

        currentPlayer = Seed.CROSS;
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(COLOR_BG);
        board.paint(g);

        if (currentState == State.PLAYING) {
            statusBar.setForeground(Color.white);
            if (currentPlayer == Seed.CROSS) {
                statusBar.setText(player1Name + "'s Turn");
            } else {
                statusBar.setText(player2Name + "'s Turn");
            }
        } else if (currentState == State.DRAW) {
            statusBar.setForeground(Color.white);
            statusBar.setText("It's a Draw! Click to play again.");
        } else if (currentState == State.CROSS_WON) {
            statusBar.setForeground(new Color(255, 255, 255));
            statusBar.setText("'" + player1Name + "' Won! Click to play again.");
        } else if (currentState == State.NOUGHT_WON) {
            statusBar.setForeground(new Color(255,255,255));
            statusBar.setText("'" + player2Name + "' Won! Click to play again.");
        }
    }

    private void playSound(String soundFileName) {
        try {
            InputStream audioSrc = getClass().getResourceAsStream("/" + soundFileName);
            if (audioSrc == null) return;
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new BufferedInputStream(audioSrc));
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void playGameStateSound(State gameState, Seed winner) {
        switch (gameState) {
            case CROSS_WON:
            case NOUGHT_WON:
                playSound("sound_win.wav");
                break;
            case DRAW:
                playSound("sound_draw.wav");
                break;
            default:
                break;
        }
    }
    private void restartGame() {
        chooseMode();
        if (!vsAI) {
            askPlayerNames();
        }
        newGame();
    }

    public static void playStartupMusic() {
        try {
            InputStream audioSrc = GameMain.class.getResourceAsStream("opening.wav");
            if (audioSrc == null) {
                System.err.println("File opening.wav tidak ditemukan.");
                return;
            }
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new BufferedInputStream(audioSrc));
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {

        playStartupMusic();
        new WelcomePopup(new JFrame()).setVisible(true);

        try {

            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());

            UIManager.put("OptionPane.background", new Color(40, 44, 52));
            UIManager.put("Panel.background", new Color(40, 44, 52));
            UIManager.put("OptionPane.messageForeground", Color.WHITE);
            UIManager.put("Button.background", new Color(200, 200, 200));
            UIManager.put("Button.foreground", Color.BLACK);

        } catch (Exception e) {
            e.printStackTrace();
        }

        javax.swing.SwingUtilities.invokeLater(() -> {

            GameMain gamePanel = new GameMain();

            gamePanel.chooseMode();
            if (!gamePanel.vsAI) {
                gamePanel.askPlayerNames();
            }




            System.out.println("Player 1: " + gamePanel.player1Name);
            System.out.println("Player 2: " + gamePanel.player2Name);
            DatabaseHelper.insertPlayers(gamePanel.player1Name, gamePanel.player2Name);


            JFrame frame = new JFrame(TITLE);
            frame.setContentPane(gamePanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}