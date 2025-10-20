import java.sql.*;
import javax.swing.*;

public class DatabaseManager {
    // Database connection parameters - replace with your MySQL credentials
    private static final String DB_URL = "jdbc:mysql://localhost:3306/elibrary";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";
    
    // Initialize the database and create necessary tables if they don't exist
    public static void initializeDatabase() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            
            // Create users table
            String createUsersTable = "CREATE TABLE IF NOT EXISTS users (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "email VARCHAR(100) NOT NULL UNIQUE, " +
                "username VARCHAR(50) NOT NULL UNIQUE, " +
                "password VARCHAR(100) NOT NULL, " +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")";
            
            // Create purchased_books table
            String createBooksTable = "CREATE TABLE IF NOT EXISTS purchased_books (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "user_id INT NOT NULL, " +
                "book_title VARCHAR(255) NOT NULL, " +
                "purchased_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE, " +
                "UNIQUE KEY unique_user_book (user_id, book_title)" +
                ")";
            
            stmt.execute(createUsersTable);
            stmt.execute(createBooksTable);
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error initializing database: " + e.getMessage(), 
                "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Get database connection
    private static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found", e);
        }
    }
    
    // Register a new user
    public static boolean registerUser(String email, String username, String password) {
        String sql = "INSERT INTO users (email, username, password) VALUES (?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, email);
            pstmt.setString(2, username);
            pstmt.setString(3, password); // In a real app, hash the password
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            if (e.getMessage().contains("Duplicate entry")) {
                if (e.getMessage().contains("email")) {
                    JOptionPane.showMessageDialog(null, "Email already registered!", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (e.getMessage().contains("username")) {
                    JOptionPane.showMessageDialog(null, "Username already taken!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Registration failed: " + e.getMessage(), 
                    "Database Error", JOptionPane.ERROR_MESSAGE);
            }
            return false;
        }
    }
    
    // Authenticate user login
    public static boolean loginUser(String username, String password) {
        String sql = "SELECT id, username FROM users WHERE username = ? AND password = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, password); // In a real app, verify hashed password
            
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // Returns true if user exists with given credentials
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Add a purchased book for a user
    public static boolean addPurchasedBook(String username, String bookTitle) {
        String sql = "INSERT INTO purchased_books (user_id, book_title) " +
                    "SELECT id, ? FROM users WHERE username = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, bookTitle);
            pstmt.setString(2, username);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            if (e.getMessage().contains("Duplicate entry")) {
                return true; // Book already purchased
            }
            e.printStackTrace();
            return false;
        }
    }
    
    // Get all purchased books for a user
    public static java.util.List<String> getPurchasedBooks(String username) {
        java.util.List<String> books = new java.util.ArrayList<>();
        String sql = "SELECT pb.book_title FROM purchased_books pb " +
                    "JOIN users u ON pb.user_id = u.id WHERE u.username = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    books.add(rs.getString("book_title"));
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return books;
    }
    
    // Check if a book is already purchased by the user
    public static boolean isBookPurchased(String username, String bookTitle) {
        String sql = "SELECT 1 FROM purchased_books pb " +
                    "JOIN users u ON pb.user_id = u.id " +
                    "WHERE u.username = ? AND pb.book_title = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, bookTitle);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
