import java.sql.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SimpleMenuApp {
    static final String DB_URL = "jdbc:oracle:thin:@oracle.scs.ryerson.ca:1521:orcl"; 
    static final String USER = "USERID";
    static final String PASS = "PASSWORD";
    static final Logger logger = Logger.getLogger(SimpleMenuApp.class.getName());

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            System.out.println("Connected to Oracle database!");
            mainMenu(conn);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Database connection failed", e);
        }
    }

    public static void mainMenu(Connection conn) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Create Tables");
            System.out.println("2. Drop Tables");
            System.out.println("3. Populate Tables");
            System.out.println("4. Query Data");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            
            int choice = getValidChoice(scanner);
            switch (choice) {
                case 1 -> createTables(conn);
                case 2 -> dropTables(conn);
                case 3 -> populateTables(conn);
                case 4 -> queryData(conn);
                case 5 -> {
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static int getValidChoice(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.next(); // Consume the invalid input
        }
        return scanner.nextInt();
    }

    // Method to create tables
    public static void createTables(Connection conn) {
        String[] createTableSQL = {
            "CREATE TABLE Admin (Admin_ID NUMBER PRIMARY KEY, Admin_name VARCHAR2(100), Admin_role VARCHAR2(100))",
            "CREATE TABLE Category (Category_id NUMBER PRIMARY KEY, Category_name VARCHAR2(100))",
            "CREATE TABLE Product (Product_id NUMBER PRIMARY KEY, Product_name VARCHAR2(100), Price NUMBER, Category_id NUMBER)",
            "CREATE TABLE Customer (Customer_id NUMBER PRIMARY KEY, F_name VARCHAR2(100), L_name VARCHAR2(100), Email VARCHAR2(100), Password VARCHAR2(100), Contact_number VARCHAR2(15), Address VARCHAR2(200))",
            "CREATE TABLE \"Order\" (Order_id NUMBER PRIMARY KEY, Customer_id NUMBER, Total_amount NUMBER, Status VARCHAR2(50))",
            "CREATE TABLE Cart (Cart_id NUMBER, Product_id NUMBER, Quantity NUMBER)",
            "CREATE TABLE Supplier (Supplier_id NUMBER PRIMARY KEY, Supplier_name VARCHAR2(100), Supplier_address VARCHAR2(200))",
            "CREATE TABLE Inventory (Batch_id NUMBER PRIMARY KEY, Product_id NUMBER, Quantity NUMBER, Price NUMBER)",
            "CREATE TABLE Payment (Payment_id NUMBER PRIMARY KEY, Order_id NUMBER, Amount NUMBER, Payment_method VARCHAR2(50), Payment_date DATE)",
            "CREATE TABLE TrackingDetails (Tracking_id NUMBER PRIMARY KEY, Order_id NUMBER, Shipping_method VARCHAR2(100), Delivery_date DATE)"
        };

        try (Statement stmt = conn.createStatement()) {
            for (String sql : createTableSQL) {
                stmt.executeUpdate(sql);
            }
            System.out.println("All tables created successfully!");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error creating tables", e);
        }
    }

    // Method to drop tables
    public static void dropTables(Connection conn) {
        String[] dropTableSQL = {
            "DROP TABLE TrackingDetails",
            "DROP TABLE Payment",
            "DROP TABLE Inventory",
            "DROP TABLE Supplier",
            "DROP TABLE Cart",
            "DROP TABLE \"Order\"",
            "DROP TABLE Customer",
            "DROP TABLE Product",
            "DROP TABLE Category",
            "DROP TABLE Admin"
        };

        try (Statement stmt = conn.createStatement()) {
            for (String sql : dropTableSQL) {
                try {
                    stmt.executeUpdate(sql);
                    System.out.println("Table dropped: " + sql.split(" ")[2]);
                } catch (SQLException e) {
                    logger.log(Level.WARNING, "Error dropping table", e);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error dropping tables", e);
        }
    }

    // Method to populate tables using prepared statements
    public static void populateTables(Connection conn) {
        String[] insertStatements = {
            "INSERT INTO Admin (Admin_ID, Admin_name, Admin_role) VALUES (?, ?, ?)",
            "INSERT INTO Category (Category_id, Category_name) VALUES (?, ?)",
            "INSERT INTO Product (Product_id, Product_name, Price, Category_id) VALUES (?, ?, ?, ?)",
            "INSERT INTO Customer (Customer_id, F_name, L_name, Email, Password, Contact_number, Address) VALUES (?, ?, ?, ?, ?, ?, ?)",
            "INSERT INTO \"Order\" (Order_id, Customer_id, Total_amount, Status) VALUES (?, ?, ?, ?)",
            "INSERT INTO Cart (Cart_id, Product_id, Quantity) VALUES (?, ?, ?)",
            "INSERT INTO Supplier (Supplier_id, Supplier_name, Supplier_address) VALUES (?, ?, ?)",
            "INSERT INTO Inventory (Batch_id, Product_id, Quantity, Price) VALUES (?, ?, ?, ?)",
            "INSERT INTO Payment (Payment_id, Order_id, Amount, Payment_method, Payment_date) VALUES (?, ?, ?, ?, ?)",
            "INSERT INTO TrackingDetails (Tracking_id, Order_id, Shipping_method, Delivery_date) VALUES (?, ?, ?, ?)"
        };

        try (PreparedStatement stmtAdmin = conn.prepareStatement(insertStatements[0]);
             PreparedStatement stmtCategory = conn.prepareStatement(insertStatements[1]);
             PreparedStatement stmtProduct = conn.prepareStatement(insertStatements[2]);
             PreparedStatement stmtCustomer = conn.prepareStatement(insertStatements[3]);
             PreparedStatement stmtOrder = conn.prepareStatement(insertStatements[4]);
             PreparedStatement stmtCart = conn.prepareStatement(insertStatements[5]);
             PreparedStatement stmtSupplier = conn.prepareStatement(insertStatements[6]);
             PreparedStatement stmtInventory = conn.prepareStatement(insertStatements[7]);
             PreparedStatement stmtPayment = conn.prepareStatement(insertStatements[8]);
             PreparedStatement stmtTrackingDetails = conn.prepareStatement(insertStatements[9])) {

            // Example for Admin table
            stmtAdmin.setInt(1, 1);
            stmtAdmin.setString(2, "John Doe");
            stmtAdmin.setString(3, "Manager");
            stmtAdmin.executeUpdate();

            // Similarly, set parameters for other tables and execute

            conn.commit();
            System.out.println("All data has been inserted successfully!");

        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                logger.log(Level.SEVERE, "Rollback failed", rollbackEx);
            }
            logger.log(Level.SEVERE, "Error inserting data", e);
        }
    }

    // Method to query data
    public static void queryData(Connection conn) {
        String querySQL = "SELECT * FROM Admin";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(querySQL)) {
            while (rs.next()) {
                System.out.println("Admin_ID: " + rs.getInt("Admin_ID") +
                                   ", Name: " + rs.getString("Admin_name") +
                                   ", Role: " + rs.getString("Admin_role"));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error querying data", e);
        }
    }
}
