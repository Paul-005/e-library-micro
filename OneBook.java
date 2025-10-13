import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class OneBook {

    private static String bookTitle = "Java: The Complete Reference";

    public static void main(String[] args) {
        if (args.length > 0) {
            bookTitle = args[0];
        }
        SwingUtilities.invokeLater(OneBook::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Book Details - E-Library");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

    private static JPanel createHeader() {
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
            
            // Create and show the Ebook view
            SwingUtilities.invokeLater(() -> {
                JFrame ebookFrame = new JFrame("E-Library Dashboard");
                ebookFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                ebookFrame.setSize(1100, 750);
                ebookFrame.setMinimumSize(new Dimension(900, 650));
                ebookFrame.setLocationRelativeTo(null);
                
                // Create and add the Ebook panel
                Ebook ebook = new Ebook();
                JPanel ebookPanel = ebook.createEbookPanel();
                ebookFrame.add(ebookPanel);
                
                ebookFrame.setVisible(true);
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

    private static JPanel createBookDetailPanel() {
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

    private static JPanel createImagePanel() {
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setBackground(Color.WHITE);
        imagePanel.setPreferredSize(new Dimension(280, 400));

        // Load book cover image
        JLabel coverLabel;
        try {
            File imageFile = new File("download.jpeg");
            
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

    private static JPanel createBookInfoPanel() {
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

        JLabel priceLabel = new JLabel("₹899");
        priceLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        priceLabel.setForeground(new Color(76, 175, 80));

        JLabel originalPrice = new JLabel("₹1,499");
        originalPrice.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        originalPrice.setForeground(new Color(150, 150, 150));
        
        JLabel discount = new JLabel("40% OFF");
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

        JButton readButton = createStyledButton("Read Book", new Color(30, 136, 229), new Color(25, 118, 210));
        readButton.addActionListener(e -> {
            SwingUtilities.getWindowAncestor(readButton).dispose();
            SwingUtilities.invokeLater(() -> {
                JFrame frame = new JFrame("Book Reader - " + bookTitle);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(1000, 700);
                frame.setLocationRelativeTo(null);
                
                // Create the BookReaderPanel with a back action
                ActionListener backAction = evt -> {
                    frame.dispose();
                    OneBook.main(new String[]{bookTitle});
                };
                
                BookReaderPanel readerPanel = new BookReaderPanel(bookTitle, backAction);
                frame.add(readerPanel);
                frame.setVisible(true);
            });
        });

        JButton purchaseButton = createStyledButton("Purchase Now", new Color(76, 175, 80), new Color(67, 160, 71));
        JButton addToCartButton = createStyledButton("Add to Cart", new Color(100, 100, 100), new Color(80, 80, 80));

        buttonPanel.add(readButton);
        buttonPanel.add(purchaseButton);
        buttonPanel.add(addToCartButton);

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
