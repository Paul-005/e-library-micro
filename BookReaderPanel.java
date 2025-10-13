import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Custom JPanel component dedicated to rendering the book content (Reader View).
 * This component is self-contained and handles its own layout, content display, 
 * and requires an ActionListener for the back button functionality.
 */
public class BookReaderPanel extends JPanel {
    
    /**
     * Constructor for the BookReaderPanel.
     * @param bookTitle The title of the book to display.
     * @param backAction The ActionListener to be executed when the back button is clicked 
     * (typically switches back to the purchased books list).
     */
    public BookReaderPanel(String bookTitle, ActionListener backAction) {
        // Use BorderLayout for the main panel
        super(new BorderLayout(20, 20));
        
        this.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        this.setBackground(new Color(250, 250, 245)); // Off-white for reading comfort

        // --- 1. Top Bar (Title and Back Button) ---
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(250, 250, 245));
        
        JButton backButton = new JButton("◀ Back to My Books");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBackground(new Color(240, 240, 240));
        backButton.setFocusPainted(false);
        // Attach the provided action listener for navigation
        backButton.addActionListener(backAction);
        
        JLabel titleLabel = new JLabel(bookTitle, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 28));
        titleLabel.setForeground(new Color(30, 30, 30));

        topBar.add(backButton, BorderLayout.WEST);
        topBar.add(titleLabel, BorderLayout.CENTER);
        this.add(topBar, BorderLayout.NORTH);

        // --- 2. Content Area (Simulated PDF/Book Text) ---
        JTextArea contentArea = new JTextArea();
        contentArea.setEditable(false);
        contentArea.setFont(new Font("Georgia", Font.PLAIN, 16));
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        contentArea.setBackground(new Color(255, 255, 255));
        
        // Mock Content Generation
        String content = generateMockBookContent(bookTitle);
        contentArea.setText(content);

        JScrollPane scrollPane = new JScrollPane(contentArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 1));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Smooth scrolling

        this.add(scrollPane, BorderLayout.CENTER);
    }
    
    /**
     * Generates simulated book text for the reader.
     */
    private static String generateMockBookContent(String title) {
        // Use StringBuilder for cleaner multi-paragraph text generation
        StringBuilder sb = new StringBuilder();
        
        String chapterTitle = "Chapter 1: Establishing the Digital Foundation";
        sb.append(String.format("--- %s (%s) ---\n\n", chapterTitle, title));

        sb.append("Welcome to the digital reading experience for \"" + title + "\". This interface is designed to emulate "
                + "a high-fidelity electronic publication, providing a clean, easy-to-read layout that minimizes eye strain "
                + "and distraction, making it suitable for long, focused reading sessions.\n\n");
        
        sb.append("The design principles prioritize **legibility** above all. By using a classic serif typeface "
                + "(Georgia) and generous line spacing, we aim to replicate the comfort of reading a physical book. "
                + "This simulated view demonstrates how your digital content, whether a PDF or an eBook file, "
                + "would be rendered into a dynamic, flowing text format for optimal consumption across different screen sizes.\n\n");
        
        sb.append("----------------------------------\n\n");
        sb.append("## Core Concepts in Reader Experience\n\n");
        
        sb.append("A truly successful e-reader should have minimal **cognitive load**. The reader's focus should "
                + "remain entirely on the subject matter, not the interface itself. Simple navigational controls, "
                + "like the clear back button, ensure that context is maintained without complex menus or overlays. "
                + "In a full PDF implementation, this area would manage page rendering, text reflow, and search indexing.\n\n");
                
        sb.append("This mock content is repeated to simulate a multi-page document:\n\n"
                + "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. "
                + "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor "
                + "in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, "
                + "sunt in culpa qui officia deserunt mollit anim id est laborum.\n\n"
                + "Finis.\n");

        return sb.toString();
    }
    
    /**
     * Main method for testing the BookReaderPanel component in isolation.
     */
    public static void main(String[] args) {
        // This main method is kept for testing purposes but won't be used in the main flow
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Book Reader");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 700);
            frame.setLocationRelativeTo(null);
            
            // Default back action that just shows a message
            ActionListener testBackAction = e -> {
                frame.dispose();
                // In the actual flow, this would be handled by the OneBook class
                OneBook.main(new String[]{"Test Book Title"});
            };
            
            BookReaderPanel readerPanel = new BookReaderPanel("Test Book Title: The Art of Component Design", testBackAction);
            frame.add(readerPanel);
            frame.setVisible(true);
        });
    }
}
