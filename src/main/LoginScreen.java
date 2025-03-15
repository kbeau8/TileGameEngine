import design.FontManager;
import profiles.PlayerProfile;
import profiles.ProfileManager;

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

    public LoginScreen() {
        setTitle("WELCOME TO THE TILE GAME HUB");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new GridLayout(12, 3));

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        userLabel = new JLabel("Username:");
        passLabel = new JLabel("Password:");
        loginButton = new JButton("Login");
        createProfileButton = new JButton("Create Profile");

        add(userLabel);
        add(usernameField);
        add(passLabel);
        add(passwordField);
        add(loginButton);
        add(createProfileButton);

        loginButton.setFont(customFont);
        createProfileButton.setFont(customFont);
        userLabel.setFont(customFont);
        usernameField.setFont(customFont);
        passLabel.setFont(customFont);
        passwordField.setFont(customFont);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        createProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createProfile();
            }
        });
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void login() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (!username.isEmpty() && !password.isEmpty()) {
            ProfileManager.loadProfiles();
            PlayerProfile profile = ProfileManager.authenticateUser(username, password);

            if (profile != null) {
                dispose();
                new GameSelectionScreen(profile);
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

    public static void main(String[] args) {
        new LoginScreen();
    }
}
