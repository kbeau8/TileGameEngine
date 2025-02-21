import profiles.PlayerProfile;
import profiles.ProfileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginScreen extends JFrame {
    private JTextField usernameField;
    private JButton loginButton;

    public LoginScreen() {
        setTitle("WELCOME TO PUZZLE BOBBLE");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Enter Username:", SwingConstants.CENTER);
        usernameField = new JTextField(15);
        loginButton = new JButton("Login");

        JPanel panel = new JPanel();
        panel.add(usernameField);
        panel.add(loginButton);

        add(label, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText().trim();

                if (!username.isEmpty()) {
                    ProfileManager.loadProfiles();
                    PlayerProfile profile = ProfileManager.getOrCreateProfile(username);
                    ProfileManager.saveProfiles();

                    dispose();
                    new GameSelectionScreen(profile);
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter a username", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new LoginScreen();
    }
}