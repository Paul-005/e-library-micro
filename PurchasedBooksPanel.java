import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;
import java.util.function.Consumer;

/**
 * Custom JPanel component dedicated to displaying all purchased books
 * in aesthetic cards with a 'Read Now' option.
 */
public class PurchasedBooksPanel extends JPanel {

    private final List<String> purchasedBooks;
    private final Consumer<String> readBookCallback; // Function to call showReader in Ebook.java

    /**
     * @param purchasedBooks The current list of purchased book titles.
     * @param readBookCallback A callback function that takes the book title as input 
     * and triggers the navigation to the Reader View.
     */
    public PurchasedBooksPanel(List<String> purchasedBooks, Consumer<String> readBookCallback) {
        this.purchasedBooks = purchasedBooks;
        this.readBookCallback = readBookCallback;
        
        // Setting up the main panel layout and theme
        this.setLayout(new BorderLayout(20, 20));
        this.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        this.setBackground(new Color(245, 247, 250));
        
        // Build the UI components
        this.add(createTitleLabel(), BorderLayout.NORTH);
        this.add(createContentArea(), BorderLayout.CENTER);
    }

    private JLabel createTitleLabel() {
        JLabel titleLabel = new JLabel("My Purchased Books", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titleLabel.setForeground(new Color(33, 33, 33));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        return titleLabel;
    }

    private JComponent createContentArea() {
        if (purchasedBooks.isEmpty()) {
            // Display message if no books are purchased
            JPanel emptyPanel = new JPanel(new GridBagLayout());
            emptyPanel.setBackground(new Color(245, 247, 250));
            JLabel emptyLabel = new JLabel("You have no purchased books yet! Visit 'Available Books' to find your next read.");
            emptyLabel.setFont(new Font("Segoe UI", Font.ITALIC, 18));
            emptyPanel.add(emptyLabel);
            return emptyPanel;
        }

        // Display books in a grid layout
        JPanel booksContainer = new JPanel(new GridLayout(0, 3, 25, 25));
        booksContainer.setBackground(new Color(245, 247, 250));
        booksContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (String bookTitle : purchasedBooks) {
            booksContainer.add(createPurchasedBookCard(bookTitle));
        }

        JScrollPane scrollPane = new JScrollPane(booksContainer);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        return scrollPane;
    }

    /**
     * Creates an aesthetic book panel specifically for the 'My Books' section with a 'Read Now' button.
     */
    private JPanel createPurchasedBookCard(String bookTitle) {
        JPanel bookPanel = new JPanel(new BorderLayout(10, 10));
        bookPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(190, 210, 230), 2, true), // Light blue border for purchased
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        bookPanel.setBackground(Color.WHITE);
        bookPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add hover effect
        bookPanel.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                bookPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(30, 136, 229), 3, true),
                    BorderFactory.createEmptyBorder(19, 19, 19, 19)
                ));
                bookPanel.setBackground(new Color(250, 252, 255));
            }
            public void mouseExited(MouseEvent e) {
                bookPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(190, 210, 230), 2, true),
                    BorderFactory.createEmptyBorder(20, 20, 20, 20)
                ));
                bookPanel.setBackground(Color.WHITE);
            }
        });

        // Load image from root directory (using the placeholder image)
        JLabel coverLabel = getBookCoverLabel();
        coverLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bookPanel.add(coverLabel, BorderLayout.CENTER);

        // Book title
        JLabel titleLabel = new JLabel("<html><center><b>" + bookTitle + "</b></center></html>", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        titleLabel.setForeground(new Color(33, 33, 33));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Create a panel for the title with appropriate padding
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.setBackground(Color.WHITE);
        southPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        southPanel.add(titleLabel, BorderLayout.CENTER);

        bookPanel.add(southPanel, BorderLayout.SOUTH);

        return bookPanel;
    }

    // Helper method to load the image/emoji
    private JLabel getBookCoverLabel() {
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
                coverLabel = new JLabel("📚");
                coverLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 80));
                coverLabel.setForeground(new Color(150, 150, 150));
            }
        } catch (Exception e) {
            e.printStackTrace();
            coverLabel = new JLabel("📚");
            coverLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 80));
            coverLabel.setForeground(new Color(150, 150, 150));
        }
        return coverLabel;
    }
}
