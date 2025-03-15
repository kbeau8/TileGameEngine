import profiles.PlayerProfile;
import design.FontManager;
import sounds.SoundManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameSelectionScreen extends JFrame {
    private PlayerProfile player1;
    private PlayerProfile player2 = null;
    JButton addPlayerButton = new JButton("Add Player 2");
    Font customFont = FontManager.getPixelFont(20f);
    private Image backgroundImage = new ImageIcon("assets/backgrounds/selectionbackground.jpg").getImage();
    private final SoundManager soundManager = new SoundManager();

    public GameSelectionScreen(PlayerProfile player1) {
        this.player1 = player1;
        setTitle("Select a Game");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        BackgroundPanel backgroundPanel = new BackgroundPanel();
        setContentPane(backgroundPanel);

        backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.Y_AXIS));

        JLabel welcomeLabel = new JLabel("Welcome, " + player1.getUsername() + "!", SwingConstants.CENTER);
        welcomeLabel.setForeground(Color.WHITE);
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 200, 5));
        headerPanel.setBackground(Color.BLUE);
        headerPanel.setMaximumSize(new Dimension(800,50));

        welcomeLabel.setFont(customFont);
        headerPanel.add(welcomeLabel);
        addPlayerButton.setFont(customFont);
        addPlayerButton.setForeground(Color.WHITE);
        addPlayerButton.setBackground(Color.decode("#2b58f2"));
        headerPanel.add(addPlayerButton);
        backgroundPanel.add(headerPanel);

        backgroundPanel.add(Box.createVerticalStrut(150));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setOpaque(false);

        JPanel buttonContainer = new JPanel();
        buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.Y_AXIS));
        buttonContainer.setOpaque(false);

        JButton twenty48Button = new JButton("Play 2048");
        JButton puzzlesAndDragonsButton = new JButton("Play Puzzles & Dragons");
        twenty48Button.setFont(customFont);
        twenty48Button.setForeground(Color.WHITE);
        twenty48Button.setBackground(Color.decode("#2b58f2"));
        puzzlesAndDragonsButton.setFont(customFont);
        puzzlesAndDragonsButton.setForeground(Color.WHITE);
        puzzlesAndDragonsButton.setBackground(Color.decode("#2b58f2"));

        buttonContainer.add(twenty48Button);
        buttonContainer.add(Box.createVerticalStrut(10));
        buttonContainer.add(puzzlesAndDragonsButton);
        buttonPanel.add(buttonContainer);
        backgroundPanel.add(buttonPanel);
        backgroundPanel.add(Box.createVerticalGlue());
        setLocationRelativeTo(null);
        setVisible(true);

        addPlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginScreen(GameSelectionScreen.this);
            }
        });

        twenty48Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                soundManager.stopMusic();
                dispose();
                new Twenty48Game(player1);
            }
        });

        puzzlesAndDragonsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                soundManager.stopMusic();
                dispose();
                // new PADGame(player1, player2);
            }
        });
    }

    public void addPlayer() {
        this.player2 = player2;
        JOptionPane.showMessageDialog(this, "Second player logged in: " + player2.getUsername());
        addPlayerButton.setEnabled(false);
    }

    class BackgroundPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
