import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class Signup extends JFrame {

    private JTextField emailField;
    private JTextField userField;
    private JPasswordField passField;
    private JButton signupButton;
    private JButton loginButton;
    private JLabel emailLabel;
    private JPanel emailContainer;
    private JLabel passwordStrengthLabel;
    private boolean isLoginMode = false;

    public Signup() {
        DatabaseManager.initializeDatabase();
        
        setTitle("E-Library - Sign Up");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 800);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(Color.WHITE);
        
        initializeUI();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Signup signup = new Signup();
            signup.setVisible(true);
        });
    }
    
    private void initializeUI() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 40, 50));

        // App Icon
        JLabel iconLabel = new JLabel("📚");
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(iconLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Title
        JLabel titleLabel = new JLabel("Create Your Account");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(new Color(33, 33, 33));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Subtitle
        JLabel subtitleLabel = new JLabel("Welcome to E-Library");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(120, 120, 120));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(subtitleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 35)));

        // Email Container
        emailContainer = new JPanel();
        emailContainer.setLayout(new BoxLayout(emailContainer, BoxLayout.Y_AXIS));
        emailContainer.setBackground(Color.WHITE);
        emailContainer.setMaximumSize(new Dimension(350, 80));
        
        emailLabel = new JLabel("Email Address");
        emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        emailLabel.setForeground(new Color(70, 70, 70));
        emailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        emailContainer.add(emailLabel);
        emailContainer.add(Box.createRigidArea(new Dimension(0, 6)));
        
        emailField = new JTextField();
        emailField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        emailField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
                BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));
        emailField.setMaximumSize(new Dimension(350, 45));
        emailField.setAlignmentX(Component.CENTER_ALIGNMENT);
        addRealTimeValidation(emailField, "email");
        emailContainer.add(emailField);
        
        mainPanel.add(emailContainer);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 18)));

        // Username Field
        JLabel userLabel = new JLabel("Username");
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        userLabel.setForeground(new Color(70, 70, 70));
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(userLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 6)));
        
        userField = new JTextField();
        userField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        userField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
                BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));
        userField.setMaximumSize(new Dimension(350, 45));
        userField.setAlignmentX(Component.CENTER_ALIGNMENT);
        addRealTimeValidation(userField, "username");
        mainPanel.add(userField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 18)));

        // Password Field
        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        passLabel.setForeground(new Color(70, 70, 70));
        passLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(passLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 6)));
        
        passField = new JPasswordField();
        passField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
                BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));
        passField.setMaximumSize(new Dimension(350, 45));
        passField.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(passField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 8)));

        // Password Strength Label
        passwordStrengthLabel = new JLabel("");
        passwordStrengthLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        passwordStrengthLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        addPasswordStrengthValidation(passField, passwordStrengthLabel);
        mainPanel.add(passwordStrengthLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        // Sign Up Button
        signupButton = new JButton("Create Account");
        signupButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        signupButton.setBackground(new Color(41, 128, 185));
        signupButton.setForeground(Color.WHITE);
        signupButton.setFocusPainted(false);
        signupButton.setBorderPainted(false);
        signupButton.setOpaque(true);
        signupButton.setMaximumSize(new Dimension(350, 48));
        signupButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        signupButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signupButton.setBorder(BorderFactory.createEmptyBorder(14, 0, 14, 0));
        
        signupButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                signupButton.setBackground(new Color(31, 108, 165));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                signupButton.setBackground(new Color(41, 128, 185));
            }
        });
        
        signupButton.addActionListener(e -> handleSignup());
        mainPanel.add(signupButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        // Toggle Panel
        JPanel togglePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        togglePanel.setBackground(Color.WHITE);
        togglePanel.setMaximumSize(new Dimension(350, 30));
        
        JLabel accountLabel = new JLabel("Already have an account?");
        accountLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        accountLabel.setForeground(new Color(100, 100, 100));
        
        loginButton = new JButton("Sign In");
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        loginButton.setForeground(new Color(41, 128, 185));
        loginButton.setBorderPainted(false);
        loginButton.setContentAreaFilled(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.addActionListener(e -> toggleLoginSignup());
        
        togglePanel.add(accountLabel);
        togglePanel.add(loginButton);
        mainPanel.add(togglePanel);

        add(mainPanel);
        
        try {
            ImageIcon icon = new ImageIcon("images/icon.png");
            setIconImage(icon.getImage());
        } catch (Exception e) {
            // Icon not found
        }
    }

    private static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }

    private void toggleLoginSignup() {
        isLoginMode = !isLoginMode;
        
        if (isLoginMode) {
            setTitle("E-Library - Sign In");
            signupButton.setText("Sign In");
            emailContainer.setVisible(false);
            
            Component[] components = ((JPanel)loginButton.getParent()).getComponents();
            ((JLabel)components[0]).setText("Don't have an account?");
            loginButton.setText("Create Account");
            
            Component[] mainComponents = ((JPanel)signupButton.getParent()).getComponents();
            for (Component comp : mainComponents) {
                if (comp instanceof JLabel) {
                    JLabel label = (JLabel) comp;
                    if (label.getFont().getSize() == 26) {
                        label.setText("Welcome Back!");
                        break;
                    }
                }
            }
        } else {
            setTitle("E-Library - Sign Up");
            signupButton.setText("Create Account");
            emailContainer.setVisible(true);
            
            Component[] components = ((JPanel)loginButton.getParent()).getComponents();
            ((JLabel)components[0]).setText("Already have an account?");
            loginButton.setText("Sign In");
            
            Component[] mainComponents = ((JPanel)signupButton.getParent()).getComponents();
            for (Component comp : mainComponents) {
                if (comp instanceof JLabel) {
                    JLabel label = (JLabel) comp;
                    if (label.getFont().getSize() == 26) {
                        label.setText("Create Your Account");
                        break;
                    }
                }
            }
        }
        
        revalidate();
        repaint();
    }
    
    private void handleSignup() {
        String email = emailField.getText().trim();
        String username = userField.getText().trim();
        String password = new String(passField.getPassword());
        
        if ((!isLoginMode && (email.isEmpty() || !isValidEmail(email))) || 
            username.isEmpty() || 
            password.isEmpty()) {
            
            String errorMessage = "Please fill in all fields correctly.";
            if (!isLoginMode && !email.isEmpty() && !isValidEmail(email)) {
                errorMessage = "Please enter a valid email address.";
            }
            
            JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (isLoginMode) {
            if (DatabaseManager.loginUser(username, password)) {
                JOptionPane.showMessageDialog(this, "Login successful! Loading your library...", "Welcome Back!", JOptionPane.INFORMATION_MESSAGE);
                openEbookApp(username);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Invalid username or password. Please try again.", 
                    "Login Failed", 
                    JOptionPane.ERROR_MESSAGE);
                passField.setText("");
            }
        } else {
            if (DatabaseManager.registerUser(email, username, password)) {
                JOptionPane.showMessageDialog(this, 
                    "Registration successful!\nWelcome to E-Library, " + username + "!", 
                    "Welcome!", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                emailField.setText("");
                userField.setText("");
                passField.setText("");
                
                openEbookApp(username);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Registration failed. The username might already be taken.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void openEbookApp(String username) {
        Ebook ebookApp = new Ebook(username);
        ebookApp.setVisible(true);
        this.dispose();
    }
    
    private static class DocumentAdapter implements DocumentListener {
        @Override
        public void insertUpdate(DocumentEvent e) { update(e); }
        @Override
        public void removeUpdate(DocumentEvent e) { update(e); }
        @Override
        public void changedUpdate(DocumentEvent e) { update(e); }
        protected void update(DocumentEvent e) {}
    }

    private void addRealTimeValidation(JTextField textField, String fieldType) {
        textField.getDocument().addDocumentListener(new DocumentAdapter() {
            @Override
            protected void update(DocumentEvent e) {
                String text = textField.getText().trim();
                switch (fieldType) {
                    case "email":
                        if (!text.isEmpty() && !isValidEmail(text)) {
                            textField.setBorder(BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(new Color(231, 76, 60), 1, true),
                                BorderFactory.createEmptyBorder(12, 15, 12, 15)
                            ));
                        } else {
                            textField.setBorder(BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
                                BorderFactory.createEmptyBorder(12, 15, 12, 15)
                            ));
                        }
                        break;
                    case "username":
                        if (!text.isEmpty() && text.length() < 3) {
                            textField.setBorder(BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(new Color(243, 156, 18), 1, true),
                                BorderFactory.createEmptyBorder(12, 15, 12, 15)
                            ));
                        } else {
                            textField.setBorder(BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
                                BorderFactory.createEmptyBorder(12, 15, 12, 15)
                            ));
                        }
                        break;
                }
            }
        });
    }

    private void addPasswordStrengthValidation(JPasswordField passwordField, JLabel strengthLabel) {
        passwordField.getDocument().addDocumentListener(new DocumentAdapter() {
            @Override
            protected void update(DocumentEvent e) {
                String password = new String(passwordField.getPassword());
                String strength = getPasswordStrength(password);
                Color color = getPasswordStrengthColor(strength);
                
                if (!password.isEmpty()) {
                    strengthLabel.setText("Strength: " + strength);
                    strengthLabel.setForeground(color);
                    
                    passwordField.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(color, 1, true),
                        BorderFactory.createEmptyBorder(12, 15, 12, 15)
                    ));
                } else {
                    strengthLabel.setText("");
                    passwordField.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
                        BorderFactory.createEmptyBorder(12, 15, 12, 15)
                    ));
                }
            }
        });
    }

    private static String getPasswordStrength(String password) {
        if (password.isEmpty()) return "";
        
        int score = 0;
        if (password.matches(".*[a-z].*")) score++;
        if (password.matches(".*[A-Z].*")) score++;
        if (password.matches(".*[0-9].*")) score++;
        if (password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) score++;
        if (password.length() >= 8) score++;
        if (password.length() >= 12) score++;
        
        if (score <= 2) return "Weak";
        if (score <= 3) return "Medium";
        if (score <= 4) return "Strong";
        return "Very Strong";
    }

    private static Color getPasswordStrengthColor(String strength) {
        switch (strength) {
            case "Weak": return new Color(231, 76, 60);
            case "Medium": return new Color(243, 156, 18);
            case "Strong": return new Color(46, 204, 113);
            case "Very Strong": return new Color(39, 174, 96);
            default: return new Color(149, 165, 166);
        }
    }
}