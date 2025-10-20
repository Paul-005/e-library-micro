import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.event.*;

public class OneBook {

    private String bookTitle = "Java: The Complete Reference";
    private boolean isPurchased = false;
    private String username;
    private JFrame frame;
    private JButton actionButton;

    public OneBook(String username, String bookTitle) {
        this.username = username;
        this.bookTitle = bookTitle;
        this.isPurchased = DatabaseManager.isBookPurchased(username, bookTitle);
    }

    public static void main(String[] args) {
        // This should be launched from Ebook.java
        JOptionPane.showMessageDialog(null, 
            "Please access books through the main application.", 
            "Info", 
            JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    public void show() {
        frame = new JFrame("Book Details - " + bookTitle);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1100, 750);
        frame.setMinimumSize(new Dimension(900, 650));
        frame.setLocationRelativeTo(null);

        // Header/Navigation Bar
        JPanel headerPanel = createHeader();
        frame.add(headerPanel, BorderLayout.NORTH);

        // Main content panel
        JPanel mainPanel = createBookDetailPanel();
        frame.add(mainPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private void openBookReader() {
        JFrame readerFrame = new JFrame("Reading: " + bookTitle);
        readerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        readerFrame.setSize(1000, 700);
        readerFrame.setLocationRelativeTo(frame);

        BookReaderPanel readerPanel = new BookReaderPanel(bookTitle, e -> {
            readerFrame.dispose();
            // When closing the reader, show the book details again
            frame.setVisible(true);
        });

        readerFrame.add(readerPanel);
        frame.setVisible(false);
        readerFrame.setVisible(true);
    }

    private JPanel createHeader() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(30, 136, 229));
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(21, 101, 192)),
            BorderFactory.createEmptyBorder(12, 20, 12, 20)
        ));

        // Back button
        JButton backButton = new JButton("← Back to Library");
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(new Color(30, 136, 229));
        backButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.WHITE, 1),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        backButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                backButton.setBackground(new Color(25, 118, 210));
            }
            public void mouseExited(MouseEvent e) {
                backButton.setBackground(new Color(30, 136, 229));
            }
        });
        
        // Add action to the back button
        backButton.addActionListener(e -> {
            // Close the current window
            JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(backButton);
            currentFrame.dispose();
            
            // Create and show the Ebook view with the current username
            SwingUtilities.invokeLater(() -> {
                Ebook ebook = new Ebook(username);
                ebook.setVisible(true);
            });
        });

        // Title
        JLabel titleLabel = new JLabel("📚 E-Library - Book Details");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));

        headerPanel.add(backButton, BorderLayout.WEST);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        return headerPanel;
    }

    private JPanel createBookDetailPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(30, 30));
        mainPanel.setBackground(new Color(245, 247, 250));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));

        // Content wrapper for centering
        JPanel contentWrapper = new JPanel(new BorderLayout(30, 20));
        contentWrapper.setBackground(Color.WHITE);
        contentWrapper.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            BorderFactory.createEmptyBorder(40, 40, 40, 40)
        ));

        // Top section - Image and Basic Info
        JPanel topSection = new JPanel(new BorderLayout(30, 0));
        topSection.setBackground(Color.WHITE);

        // Left side - Book Image
        JPanel imagePanel = createImagePanel();
        topSection.add(imagePanel, BorderLayout.WEST);

        // Right side - Book Info
        JPanel infoPanel = createBookInfoPanel();
        topSection.add(infoPanel, BorderLayout.CENTER);

        contentWrapper.add(topSection, BorderLayout.NORTH);

        // Bottom section - Description
        JPanel descriptionPanel = createDescriptionPanel();
        contentWrapper.add(descriptionPanel, BorderLayout.CENTER);

        // Scroll pane for the entire content
        JScrollPane scrollPane = new JScrollPane(contentWrapper);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        return mainPanel;
    }

    private JPanel createImagePanel() {
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setBackground(Color.WHITE);
        imagePanel.setPreferredSize(new Dimension(280, 400));

        // Define book titles and their corresponding image indices
        String[] bookTitles = {
            "Cracking The Coding Interview",
            "The Hooked: How to Build Habit-forming Products",
            "Clean Code",
            "The Pragmatic Programmer",
            "Head First Java",
            "Effective Java"
        };

        // Find the index of the current book
        int bookIndex = -1;
        for (int i = 0; i < bookTitles.length; i++) {
            if (bookTitles[i].equals(bookTitle)) {
                bookIndex = i;
                break;
            }
        }

        // Load book cover image
        JLabel coverLabel;
        try {
            String imagePath = "images/" + bookIndex + "." + (bookIndex == 1 ? "png" : "jpeg");
            File imageFile = new File(imagePath);
            
            if (imageFile.exists()) {
                ImageIcon originalIcon = new ImageIcon(imageFile.getAbsolutePath());
                Image originalImage = originalIcon.getImage();
                Image resizedImage = originalImage.getScaledInstance(250, 370, Image.SCALE_SMOOTH);
                coverLabel = new JLabel(new ImageIcon(resizedImage));
                coverLabel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
                ));
            } else {
                coverLabel = new JLabel("📖");
                coverLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 120));
                coverLabel.setForeground(new Color(150, 150, 150));
            }
        } catch (Exception e) {
            coverLabel = new JLabel("📖");
            coverLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 120));
            coverLabel.setForeground(new Color(150, 150, 150));
        }

        coverLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imagePanel.add(coverLabel, BorderLayout.CENTER);

        return imagePanel;
    }

    private JPanel createBookInfoPanel() {
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);

        // Book Title
        JLabel titleLabel = new JLabel(bookTitle);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(new Color(33, 33, 33));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Author
        JLabel authorLabel = new JLabel("by Herbert Schildt");
        authorLabel.setFont(new Font("Segoe UI", Font.ITALIC, 18));
        authorLabel.setForeground(new Color(100, 100, 100));
        authorLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        authorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Rating
        JPanel ratingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        ratingPanel.setBackground(Color.WHITE);
        ratingPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel ratingLabel = new JLabel("⭐⭐⭐⭐⭐");
        ratingLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        
        JLabel ratingText = new JLabel("4.8 (2,453 reviews)");
        ratingText.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        ratingText.setForeground(new Color(100, 100, 100));
        
        ratingPanel.add(ratingLabel);
        ratingPanel.add(ratingText);

        // Separator
        JSeparator separator1 = new JSeparator();
        separator1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        separator1.setForeground(new Color(220, 220, 220));

        // Book Details
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new GridLayout(5, 1, 0, 12));
        detailsPanel.setBackground(Color.WHITE);
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        detailsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        detailsPanel.add(createDetailRow("Publisher:", "McGraw Hill Education"));
        detailsPanel.add(createDetailRow("Edition:", "12th Edition (2022)"));
        detailsPanel.add(createDetailRow("Pages:", "1,248 pages"));
        detailsPanel.add(createDetailRow("Language:", "English"));
        detailsPanel.add(createDetailRow("ISBN:", "978-1260463415"));

        // Separator
        JSeparator separator2 = new JSeparator();
        separator2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        separator2.setForeground(new Color(220, 220, 220));

        // Price Section
        JPanel pricePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        pricePanel.setBackground(Color.WHITE);
        pricePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        pricePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel priceLabel = new JLabel("$9.99");
        priceLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        priceLabel.setForeground(new Color(76, 175, 80));

        JLabel originalPrice = new JLabel("$19.99");
        originalPrice.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        originalPrice.setForeground(new Color(150, 150, 150));
        
        JLabel discount = new JLabel("50% OFF");
        discount.setFont(new Font("Segoe UI", Font.BOLD, 16));
        discount.setForeground(Color.WHITE);
        discount.setBackground(new Color(244, 67, 54));
        discount.setOpaque(true);
        discount.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        pricePanel.add(priceLabel);
        pricePanel.add(originalPrice);
        pricePanel.add(discount);

        // Purchase Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Action button
        actionButton = new JButton(isPurchased ? "Read Now" : "Purchase for $9.99");
        actionButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        actionButton.setBackground(isPurchased ? new Color(76, 175, 80) : new Color(33, 150, 243));
        actionButton.setForeground(Color.WHITE);
        actionButton.setFocusPainted(false);
        actionButton.setBorderPainted(false);
        actionButton.setOpaque(true);
        actionButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        actionButton.setPreferredSize(new Dimension(200, 50));

        actionButton.addActionListener(e -> {
            if (isPurchased) {
                // Open book reader
                openBookReader();
            } else {
                // Purchase book
                int confirm = JOptionPane.showConfirmDialog(
                    frame,
                    "Purchase '" + bookTitle + "' for $9.99?",
                    "Confirm Purchase",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    if (DatabaseManager.addPurchasedBook(username, bookTitle)) {
                        isPurchased = true;
                        actionButton.setText("Read Now");
                        actionButton.setBackground(new Color(76, 175, 80));
                        JOptionPane.showMessageDialog(
                            frame,
                            "Thank you for your purchase! You can now read the book.",
                            "Purchase Successful",
                            JOptionPane.INFORMATION_MESSAGE
                        );
                    } else {
                        JOptionPane.showMessageDialog(
                            frame,
                            "Failed to complete purchase. Please try again.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                        );
                    }
                }
            }
        });

        buttonPanel.add(actionButton);

        // Add all components
        infoPanel.add(titleLabel);
        infoPanel.add(authorLabel);
        infoPanel.add(ratingPanel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        infoPanel.add(separator1);
        infoPanel.add(detailsPanel);
        infoPanel.add(separator2);
        infoPanel.add(pricePanel);
        infoPanel.add(buttonPanel);

        return infoPanel;
    }

    private static JPanel createDetailRow(String label, String value) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        row.setBackground(Color.WHITE);

        JLabel labelComponent = new JLabel(label);
        labelComponent.setFont(new Font("Segoe UI", Font.BOLD, 15));
        labelComponent.setForeground(new Color(66, 66, 66));
        labelComponent.setPreferredSize(new Dimension(100, 20));

        JLabel valueComponent = new JLabel(value);
        valueComponent.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        valueComponent.setForeground(new Color(100, 100, 100));

        row.add(labelComponent);
        row.add(valueComponent);

        return row;
    }

    private static JButton createStyledButton(String text, Color bgColor, Color hoverColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setBorder(BorderFactory.createEmptyBorder(12, 30, 12, 30));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    private static JPanel createDescriptionPanel() {
        JPanel descPanel = new JPanel(new BorderLayout(0, 15));
        descPanel.setBackground(Color.WHITE);
        descPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        // Section Title
        JLabel descTitle = new JLabel("Book Description");
        descTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        descTitle.setForeground(new Color(33, 33, 33));

        // Description Text
        JTextArea descText = new JTextArea();
        descText.setText(
            "The Definitive Java Programming Guide\n\n" +
            "Fully updated for Java SE 17, Java: The Complete Reference, Twelfth Edition explains how to develop, " +
            "compile, debug, and run Java programs. Best-selling programming author Herb Schildt covers the entire " +
            "Java language, including its syntax, keywords, and fundamental programming principles.\n\n" +
            "You'll also find information on key portions of the Java API library, such as I/O, the Collections " +
            "Framework, the stream library, and the concurrency utilities. Swing, JavaBeans, and servlets are " +
            "examined and numerous examples demonstrate Java in action. Of course, the very important module " +
            "system is discussed in detail. This Oracle Press resource also offers an introduction to JShell, " +
            "Java's interactive programming tool.\n\n" +
            "Best of all, the book is written in the clear, crisp, uncompromising style that has made Schildt " +
            "the choice of millions worldwide.\n\n" +
            "Key Features:\n" +
            "• Comprehensive coverage of the Java language and standard library\n" +
            "• Detailed information on Java SE 17's newest features\n" +
            "• In-depth discussions of modular programming\n" +
            "• Practical examples demonstrating Java programming techniques\n" +
            "• Expert guidance from a best-selling programming author\n\n" +
            "Perfect for both beginners and experienced programmers, this guide provides everything you need to " +
            "master Java programming."
        );
        descText.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        descText.setForeground(new Color(66, 66, 66));
        descText.setLineWrap(true);
        descText.setWrapStyleWord(true);
        descText.setEditable(false);
        descText.setBackground(Color.WHITE);
        descText.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        descPanel.add(descTitle, BorderLayout.NORTH);
        descPanel.add(descText, BorderLayout.CENTER);

        return descPanel;
    }
}
