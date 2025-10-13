import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Ebook {

    private static final String LOGGED_IN_USER = "B.Tech Student";
    private static final List<String> purchasedBooks = new ArrayList<>();
    private static final List<String> availableBooks = List.of(
            "Java: The Complete Reference",
            "Head First Java",
            "Effective Java",
            "Clean Code",
            "The Pragmatic Programmer",
            "The Lord of the Rings"
    );

    private static JPanel mainContentPanel;
    private static CardLayout cardLayout;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Ebook ebook = new Ebook();
            ebook.createAndShowGUI();
        });
    }

    public JPanel createEbookPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // App bar and Navbar
        JMenuBar menuBar = createMenuBar();
        
        // Main content area with CardLayout
        cardLayout = new CardLayout();
        mainContentPanel = new JPanel(cardLayout);

        // Add the dashboard and purchased books panels
        mainContentPanel.add(createDashboardPanel(), "Dashboard");
        mainContentPanel.add(createPurchasedBooksPanel(), "Purchased");
        
        mainPanel.add(menuBar, BorderLayout.NORTH);
        mainPanel.add(mainContentPanel, BorderLayout.CENTER);
        
        return mainPanel;
    }
    
    private void createAndShowGUI() {
        JFrame frame = new JFrame("E-Library Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 750);
        frame.setMinimumSize(new Dimension(900, 650));
        frame.setLocationRelativeTo(null);
        
        // Create and add the main panel
        JPanel mainPanel = createEbookPanel();
        frame.add(mainPanel);
        
        frame.setVisible(true);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(30, 136, 229));
        menuBar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(21, 101, 192)),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));

        // App bar section
        JLabel userLabel = new JLabel("📚 " + LOGGED_IN_USER + " Dashboard");
        userLabel.setForeground(Color.WHITE);
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        menuBar.add(userLabel);

        menuBar.add(Box.createHorizontalGlue());

        // Navbar section with custom styled buttons
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        navPanel.setOpaque(false);

        JButton dashboardBtn = createNavButton("Available Books", "Dashboard");
        JButton myBooksBtn = createNavButton("My Books", "Purchased");

        navPanel.add(dashboardBtn);
        navPanel.add(myBooksBtn);

        menuBar.add(navPanel);

        return menuBar;
    }

    private static JButton createNavButton(String text, String cardName) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(30, 136, 229));
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.WHITE, 1),
            BorderFactory.createEmptyBorder(6, 15, 6, 15)
        ));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(25, 118, 210));
            }
            public void mouseExited(MouseEvent e) {
                btn.setBackground(new Color(30, 136, 229));
            }
        });

        btn.addActionListener(e -> {
            if (cardName.equals("Purchased")) {
                updatePurchasedBooksPanel();
            }
            cardLayout.show(mainContentPanel, cardName);
        });

        return btn;
    }

    private static JPanel createDashboardPanel() {
        JPanel dashboardPanel = new JPanel(new BorderLayout(20, 20));
        dashboardPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        dashboardPanel.setBackground(new Color(245, 247, 250));

        JLabel titleLabel = new JLabel("Available Books", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titleLabel.setForeground(new Color(33, 33, 33));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        dashboardPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel booksPanel = new JPanel(new GridLayout(0, 3, 25, 25));
        booksPanel.setBackground(new Color(245, 247, 250));
        booksPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (String bookTitle : availableBooks) {
            booksPanel.add(createBookPanel(bookTitle));
        }

        JScrollPane scrollPane = new JScrollPane(booksPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        dashboardPanel.add(scrollPane, BorderLayout.CENTER);

        return dashboardPanel;
    }

    private static JPanel createBookPanel(String bookTitle) {
        JPanel bookPanel = new JPanel(new BorderLayout(10, 10));
        bookPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        bookPanel.setBackground(Color.WHITE);
        bookPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add hover effect
        bookPanel.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                bookPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(30, 136, 229), 2, true),
                    BorderFactory.createEmptyBorder(19, 19, 19, 19)
                ));
                bookPanel.setBackground(new Color(250, 252, 255));
            }
            public void mouseExited(MouseEvent e) {
                bookPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
                    BorderFactory.createEmptyBorder(20, 20, 20, 20)
                ));
                bookPanel.setBackground(Color.WHITE);
            }
            public void mouseClicked(MouseEvent e) {
                // Close current window and open book details
                SwingUtilities.getWindowAncestor(bookPanel).dispose();
                SwingUtilities.invokeLater(() -> OneBook.main(new String[]{bookTitle}));
            }
        });

        // Load image from root directory
        JLabel coverLabel;
        try {
            File imageFile = new File("download.jpeg");
            
            if (imageFile.exists()) {
                ImageIcon originalIcon = new ImageIcon(imageFile.getAbsolutePath());
                Image originalImage = originalIcon.getImage();
                Image resizedImage = originalImage.getScaledInstance(140, 200, Image.SCALE_SMOOTH);
                coverLabel = new JLabel(new ImageIcon(resizedImage));
                coverLabel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)
                ));
            } else {
                System.err.println("Image file not found: " + imageFile.getAbsolutePath());
                coverLabel = new JLabel("📖");
                coverLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 80));
                coverLabel.setForeground(new Color(150, 150, 150));
            }
        } catch (Exception e) {
            e.printStackTrace();
            coverLabel = new JLabel("📖");
            coverLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 80));
            coverLabel.setForeground(new Color(150, 150, 150));
        }

        coverLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bookPanel.add(coverLabel, BorderLayout.CENTER);

        // Book title
        JLabel titleLabel = new JLabel("<html><center><b>" + bookTitle + "</b></center></html>", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        titleLabel.setForeground(new Color(33, 33, 33));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Purchase button
        JButton purchaseButton = new JButton("Purchase");
        purchaseButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        purchaseButton.setBackground(new Color(76, 175, 80));
        purchaseButton.setForeground(Color.WHITE);
        purchaseButton.setFocusPainted(false);
        purchaseButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        purchaseButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        purchaseButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                purchaseButton.setBackground(new Color(67, 160, 71));
            }
            public void mouseExited(MouseEvent e) {
                purchaseButton.setBackground(new Color(76, 175, 80));
            }
        });

        purchaseButton.addActionListener(e -> {
            if (purchasedBooks.contains(bookTitle)) {
                JOptionPane.showMessageDialog(null, 
                    "You have already purchased this book!", 
                    "Already Purchased", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                int response = JOptionPane.showConfirmDialog(null, 
                    "Purchase '" + bookTitle + "' for your library?", 
                    "Confirm Purchase", 
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.YES_OPTION) {
                    purchasedBooks.add(bookTitle);
                    JOptionPane.showMessageDialog(null, 
                        "Successfully purchased '" + bookTitle + "! 🎉\nCheck 'My Books' to view it.", 
                        "Success", 
                        JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        JPanel southPanel = new JPanel(new BorderLayout(0, 8));
        southPanel.setBackground(Color.WHITE);
        southPanel.add(titleLabel, BorderLayout.NORTH);
        southPanel.add(purchaseButton, BorderLayout.SOUTH);

        bookPanel.add(southPanel, BorderLayout.SOUTH);
        return bookPanel;
    }

    private static JPanel createPurchasedBooksPanel() {
        // Create a panel that will hold our PurchasedBooksPanel
        JPanel container = new JPanel(new BorderLayout());
        
        // Create the purchased books panel with a callback for when a book is clicked
        PurchasedBooksPanel purchasedPanel = new PurchasedBooksPanel(
            purchasedBooks, 
            bookTitle -> showReader(bookTitle)
        );
        
        container.add(purchasedPanel, BorderLayout.CENTER);
        return container;
    }
    
    // Method to show the book reader
    private static void showReader(String bookTitle) {
        JFrame frame = new JFrame("Book Reader - " + bookTitle);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setLocationRelativeTo(null);
        
        // Create the BookReaderPanel with a back action
        ActionListener backAction = e -> {
            frame.dispose();
            // Show the purchased books panel when going back
            updatePurchasedBooksPanel();
            cardLayout.show(mainContentPanel, "Purchased");
        };
        
        BookReaderPanel readerPanel = new BookReaderPanel(bookTitle, backAction);
        frame.add(readerPanel);
        frame.setVisible(true);
    }
    
    private static void updatePurchasedBooksPanel() {
        // Remove the old purchased books panel
        mainContentPanel.remove(1);
        
        // Create and add a new one with updated content
        JPanel newPurchasedPanel = createPurchasedBooksPanel();
        mainContentPanel.add(newPurchasedPanel, "Purchased");
        
        // Refresh the UI
        mainContentPanel.revalidate();
        mainContentPanel.repaint();
    }
}
