# 📚 eLibrary Management System

Java project using MySQL for user management.

🛠️ Setup

1. Install MySQL Server (Ubuntu)

Install the database server required for the project:

sudo apt update
sudo apt install mysql-server
# (Optional: sudo mysql_secure_installation)


2. Install JDBC Driver

Place the required MySQL Connector/J driver (mysql-connector-j-9.4.0.jar) in the system-wide Java path:

# Assuming the JAR file is in your Downloads folder
sudo mv ~/Downloads/mysql-connector-j-9.4.0.jar /usr/share/java/


3. Create Database and Schema

Log into MySQL and create the elibrary database with a users table:

sudo mysql -u root -p

-- Create the main database
CREATE DATABASE elibrary;

EXIT;


🚀 Build and Run

The classpath (-cp) is mandatory to include the JDBC driver.

1. Compile

javac -cp .:/usr/share/java/mysql-connector-j-9.4.0.jar *.java


2. Run

java -cp .:/usr/share/java/mysql-connector-j-9.4.0.jar Signup
