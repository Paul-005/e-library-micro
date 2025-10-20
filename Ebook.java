import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import javax.swing.event.*;

public class Ebook extends JFrame {

    private final String username;
    private List<String> availableBooks = List.of(
        "Cracking The Coding Interview",
        "The Hooked: How to Build Habit-forming Products",
        "Clean Code",
        "The Pragmatic Programmer",
        "Head First Java",
        "Effective Java"
    );
    
    private JPanel mainContentPanel;
    private CardLayout cardLayout;
    private JLabel userLabel;
    
    // Constructor that accepts username
    public Ebook(String username) {
        this.username = username;
        initializeUI();
    }
    
    // For backward compatibility
    public Ebook() {
        this("Guest"); // Default to guest user if no username provided
    }

    public static void main(String[] args) {
        // This should be launched from Signup.java after successful login
        JOptionPane.showMessageDialog(null, 
            "Please launch the application from the Signup/Login screen.", 
            "Info", 
            JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    private void initializeUI() {
        setTitle("E-Library - " + username);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 750);
        setMinimumSize(new Dimension(900, 650));
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Load user's purchased books from database
        List<String> userBooks = DatabaseManager.getPurchasedBooks(username);
        
        // App bar and Navbar
        JMenuBar menuBar = createMenuBar();
        
        // Main content area with CardLayout
        cardLayout = new CardLayout();
        mainContentPanel = new JPanel(cardLayout);

        // Add the dashboard and purchased books panels
        mainContentPanel.add(createDashboardPanel(), "Dashboard");
        mainContentPanel.add(createPurchasedBooksPanel(DatabaseManager.getPurchasedBooks(username)), "Purchased");
        
        mainPanel.add(menuBar, BorderLayout.NORTH);
        mainPanel.add(mainContentPanel, BorderLayout.CENTER);
        
        add(mainPanel);
    }
    

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(30, 136, 229));
        menuBar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(21, 101, 192)),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));

        // App bar section
        userLabel = new JLabel("📚 " + username + "'s Dashboard");
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

    private JButton createNavButton(String text, String cardName) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setForeground(Color.WHITE);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(e -> {
            if ("Purchased".equals(cardName)) {
                // Refresh purchased books list when switching to the Purchased tab
                List<String> userBooks = DatabaseManager.getPurchasedBooks(username);
                mainContentPanel.remove(1); // Remove old purchased panel
                mainContentPanel.add(createPurchasedBooksPanel(userBooks), "Purchased");
                mainContentPanel.revalidate();
                mainContentPanel.repaint();
            }
            cardLayout.show(mainContentPanel, cardName);
        });
        return button;
    }

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = new JLabel("Available Books");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Books grid
        // ALIGNMENT CHANGE: new GridLayout(3, 2, 20, 20) for 3 rows and 2 columns
        JPanel booksPanel = new JPanel(new GridLayout(3, 2, 20, 20));
        booksPanel.setBackground(Color.WHITE);

        for (String book : availableBooks) {
            JPanel bookCard = createBookCard(book, false);
            booksPanel.add(bookCard);
        }

        JScrollPane scrollPane = new JScrollPane(booksPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createBookCard(String bookTitle, boolean isPurchased) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Book title
        JLabel titleLabel = new JLabel(bookTitle);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        // Book cover image
        JPanel coverPlaceholder = new JPanel(new BorderLayout());
        coverPlaceholder.setPreferredSize(new Dimension(150, 200));
        coverPlaceholder.setBackground(new Color(245, 245, 245));
        
        // Find book index for image
        int bookIndex = availableBooks.indexOf(bookTitle);
        String imagePath = "images/" + bookIndex + "." + (bookIndex == 1 ? "png" : "jpeg");
        File imageFile = new File(imagePath);
        
        try {
            if (imageFile.exists()) {
                ImageIcon originalIcon = new ImageIcon(imageFile.getAbsolutePath());
                Image originalImage = originalIcon.getImage();
                Image resizedImage = originalImage.getScaledInstance(150, 200, Image.SCALE_SMOOTH);
                JLabel coverLabel = new JLabel(new ImageIcon(resizedImage));
                coverLabel.setHorizontalAlignment(JLabel.CENTER);
                coverPlaceholder.add(coverLabel, BorderLayout.CENTER);
            } else {
                // Fallback to icon if image not found
                JLabel iconLabel = new JLabel("📚", JLabel.CENTER);
                iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 60));
                coverPlaceholder.add(iconLabel);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Fallback to icon if there's an error loading the image
            JLabel iconLabel = new JLabel("📚", JLabel.CENTER);
            iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 60));
            coverPlaceholder.add(iconLabel);
        }
        
        // Add border and padding
        coverPlaceholder.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        
        // Action button - changes based on whether book is purchased
        JButton actionButton = new JButton(isPurchased ? "Read Now" : "View More");
        actionButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        if (isPurchased) {
            // Blue color for Read Now button
            actionButton.setBackground(new Color(66, 133, 244));
            actionButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    actionButton.setBackground(new Color(51, 103, 214));
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    actionButton.setBackground(new Color(66, 133, 244));
                }
            });
            
            // Open book reader directly for purchased books
            actionButton.addActionListener(e -> {
                showReader(bookTitle);
            });
        } else {
            // Green color for View More button
            actionButton.setBackground(new Color(102, 187, 106));
            actionButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    actionButton.setBackground(new Color(85, 170, 85));
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    actionButton.setBackground(new Color(102, 187, 106));
                }
            });
            
            // Show book details for non-purchased books
            actionButton.addActionListener(e -> {
                try {
                    OneBook oneBook = new OneBook(username, bookTitle);
                    oneBook.show();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(Ebook.this, 
                        "Error opening book details: " + ex.getMessage(), 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        }
        
        actionButton.setForeground(Color.WHITE);
        actionButton.setBorderPainted(false);
        actionButton.setOpaque(true);
        actionButton.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        actionButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add components to card
        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.setOpaque(false);
        textPanel.add(titleLabel, BorderLayout.NORTH);
        textPanel.add(Box.createVerticalStrut(10), BorderLayout.CENTER);
        textPanel.add(actionButton, BorderLayout.SOUTH);

        card.add(coverPlaceholder, BorderLayout.CENTER);
        card.add(textPanel, BorderLayout.SOUTH);

        return card;
    }
    
    private JPanel createPurchasedBooksPanel(List<String> purchasedBooks) {
        // Create a panel that will hold our PurchasedBooksPanel
        JPanel container = new JPanel(new BorderLayout());
        
        // Create the purchased books panel with a callback for when a book is clicked
        // ALIGNMENT CHANGE: new GridLayout(0, 2, 20, 20) for 2 columns (0 rows lets the layout determine the rows)
        JPanel purchasedPanel = new JPanel(new GridLayout(0, 2, 20, 20));
        purchasedPanel.setBackground(Color.WHITE);
        
        for (String book : purchasedBooks) {
            JPanel bookCard = createBookCard(book, true);
            purchasedPanel.add(bookCard);
        }
        
        JScrollPane scrollPane = new JScrollPane(purchasedPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        container.add(scrollPane, BorderLayout.CENTER);
        return container;
    }

    // Method to add a book to purchased books list
    public void addPurchasedBook(String bookTitle) {
        if (DatabaseManager.addPurchasedBook(username, bookTitle)) {
            updatePurchasedBooks();
        }
    }

    // Method to check if a book is already purchased
    public boolean isBookPurchased(String bookTitle) {
        return DatabaseManager.isBookPurchased(username, bookTitle);
    }
    
    // Method to show the book reader
    private void showReader(String bookTitle) {
        JFrame frame = new JFrame("Book Reader - " + bookTitle);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH); // Maximize both width and height
        frame.setLocationRelativeTo(this);
        
        // Create the BookReaderPanel with a back action
        ActionListener backAction = e -> {
            frame.dispose();
            // Show the purchased books panel when going back
            updatePurchasedBooks();
            cardLayout.show(mainContentPanel, "Purchased");
        };
        
        BookReaderPanel readerPanel = new BookReaderPanel(bookTitle, backAction);
        frame.add(readerPanel);
        frame.setVisible(true);
    }
    
    private void updatePurchasedBooks() {
        // Remove the old purchased books panel
        mainContentPanel.remove(1);
        
        // Create and add a new one with updated data
        JPanel newPurchasedPanel = createPurchasedBooksPanel(DatabaseManager.getPurchasedBooks(username));
        mainContentPanel.add(newPurchasedPanel, "Purchased");
        
        // Refresh the display
        mainContentPanel.revalidate();
        mainContentPanel.repaint();
    }
}