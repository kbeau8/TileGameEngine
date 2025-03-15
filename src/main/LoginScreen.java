import design.FontManager;
import profiles.PlayerProfile;
import profiles.ProfileManager;
import sounds.SoundManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginScreen extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, createProfileButton;
    private JLabel userLabel;
    private JLabel passLabel;
    Font customFont = FontManager.getPixelFont(18f);
    private Image backgroundImage = new ImageIcon("assets/backgrounds/selectionbackground.jpg").getImage();
    private GameSelectionScreen gameSelectionScreen;
    private final SoundManager soundManager = new SoundManager();

    public LoginScreen() {
        this.gameSelectionScreen = null;
        startUI();
    }

    public LoginScreen(GameSelectionScreen gameSelectionScreen) {
        this.gameSelectionScreen = gameSelectionScreen;
        startUI();
    }

    private void startUI() {
        setTitle("WELCOME TO THE TILE GAME HUB");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        BackgroundPanel backgroundPanel = new BackgroundPanel();
        setContentPane(backgroundPanel);

        backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.Y_AXIS));
        backgroundPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        backgroundPanel.setAlignmentY(Component.LEFT_ALIGNMENT);
        backgroundPanel.add(Box.createVerticalStrut(135));

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        userLabel = new JLabel("Username:");
        passLabel = new JLabel("Password:");
        loginButton = new JButton("Login");
        createProfileButton = new JButton("Create Profile");

        JPanel userPanel = new JPanel();
        userPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JPanel passPanel = new JPanel();
        passPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        userPanel.setOpaque(false);
        passPanel.setOpaque(false);
        userPanel.add(userLabel);
        userLabel.setForeground(Color.WHITE);
        userPanel.add(usernameField);
        passPanel.add(passLabel);
        passLabel.setForeground(Color.WHITE);
        passPanel.add(passwordField);
        backgroundPanel.add(userPanel);
        backgroundPanel.add(passPanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setOpaque(false);
        buttonPanel.add(loginButton);
        buttonPanel.add(createProfileButton);
        backgroundPanel.add(buttonPanel);

        loginButton.setFont(customFont);
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(Color.decode("#2b58f2"));
        createProfileButton.setFont(customFont);
        createProfileButton.setForeground(Color.WHITE);
        createProfileButton.setBackground(Color.decode("#2b58f2"));
        userLabel.setFont(customFont);
        usernameField.setFont(customFont);
        passLabel.setFont(customFont);
        passwordField.setFont(customFont);

        loginButton.addActionListener(e -> login());

        createProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createProfile();
            }
        });
        setLocationRelativeTo(null);
        setVisible(true);
        soundManager.startMusic("assets/music/menu.wav");
    }

    private void login() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (!username.isEmpty() && !password.isEmpty()) {
            ProfileManager.loadProfiles();
            PlayerProfile profile = ProfileManager.authenticateUser(username, password);

            if (profile != null) {
                soundManager.stopMusic();
                dispose();
                if (gameSelectionScreen != null) {
                    gameSelectionScreen.addPlayer();
                } else {
                    new GameSelectionScreen(profile);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Username or password not entered", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void createProfile() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (!username.isEmpty() && !password.isEmpty()) {
            ProfileManager.loadProfiles();
            PlayerProfile newProfile = ProfileManager.registerUser(username, password);

            if (newProfile != null) {
                JOptionPane.showMessageDialog(this, "Profile successfully created!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Username already taken", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Username or password not entered", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    class BackgroundPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginScreen loginScreen = new LoginScreen();
            loginScreen.setVisible(true);
        });
    }
}
