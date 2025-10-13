import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Signup {

    public static void main(String[] args) {
        // Run the GUI creation on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(Signup::createAndShowGUI);
    }

    public static void createAndShowGUI() {
        // 1. Setup the main JFrame
        JFrame frame = new JFrame("E-Library Sign Up");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setResizable(false);
        frame.getContentPane().setBackground(new Color(245, 245, 245)); // Light gray background

        // 2. Main Panel using GridBagLayout for flexible centering and alignment
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(245, 245, 245));

        // GridBagConstraints for layout management
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding between components
        gbc.fill = GridBagConstraints.HORIZONTAL; // Make components fill their grid cell horizontally

        // --- Title ---
        JLabel titleLabel = new JLabel("Create Your Account");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(41, 128, 185)); // Deep blue color
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Span across two columns
        mainPanel.add(titleLabel, gbc);

        // --- Email Field ---
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JTextField emailField = new JTextField(20);
        emailField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(emailLabel, gbc);

        gbc.gridx = 1;
        mainPanel.add(emailField, gbc);

        // --- Username Field ---
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JTextField userField = new JTextField(20);
        userField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        gbc.gridy = 2;
        gbc.gridx = 0;
        mainPanel.add(userLabel, gbc);

        gbc.gridx = 1;
        mainPanel.add(userField, gbc);

        // --- Password Field ---
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JPasswordField passField = new JPasswordField(20);
        passField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        gbc.gridy = 3;
        gbc.gridx = 0;
        mainPanel.add(passLabel, gbc);

        gbc.gridx = 1;
        mainPanel.add(passField, gbc);

        // --- Sign Up Button ---
        JButton signupButton = new JButton("Sign Up");
        signupButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        signupButton.setBackground(new Color(41, 128, 185)); // Deep blue
        signupButton.setForeground(Color.WHITE); // White text
        signupButton.setFocusPainted(false);
        signupButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add action listener for validation and feedback
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String username = userField.getText();
                String password = new String(passField.getPassword());

                if (email.isEmpty() || username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // In a real app, this is where you'd save data to a database
                    // Close the signup window and open the Ebook page
                    frame.dispose();
                    SwingUtilities.invokeLater(() -> Ebook.main(null));
                }
            }
        });

        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 10, 10); // Extra top padding for the button
        mainPanel.add(signupButton, gbc);

        // 3. Add main panel to the frame
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
