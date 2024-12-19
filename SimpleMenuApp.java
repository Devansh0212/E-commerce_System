import java.sql.*;

public class SimpleMenuApp {
    static final String DB_URL = "jdbc:oracle:thin:@oracle.scs.ryerson.ca:1521:orcl"; // Change as per your DB
    static final String USER = "k2soni";
    static final String PASS = "08237159";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            System.out.println("Connected to Oracle database!");
            mainMenu(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void mainMenu(Connection conn) throws SQLException {
        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Create Tables");
            System.out.println("2. Drop Tables");
            System.out.println("3. Populate Tables");
            System.out.println("4. Query Data");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            @SuppressWarnings("resource")
            int choice = new java.util.Scanner(System.in).nextInt();
            switch (choice) {
                case 1 -> createTables(conn);
                case 2 -> dropTables(conn);
                case 3 -> populateTables(conn);
                case 4 -> queryData(conn);
                case 5 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    // Method to create tables
    public static void createTables(Connection conn) throws SQLException {
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
            System.out.println("Error creating tables: " + e.getMessage());
        }
    }

    // Method to drop tables
    public static void dropTables(Connection conn) throws SQLException {
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
                    System.out.println("Error dropping table: " + e.getMessage());
                }
            }
        }
    }

    // Method to populate tables
    public static void populateTables(Connection conn) throws SQLException {
        String[] insertStatements = {
            // Admin table
            "INSERT INTO Admin (Admin_ID, Admin_name, Admin_role) VALUES (1, 'John Doe', 'Manager')",
            "INSERT INTO Admin (Admin_ID, Admin_name, Admin_role) VALUES (2, 'Jane Smith', 'Assistant')",
            // Category table
            "INSERT INTO Category (Category_id, Category_name) VALUES (1, 'Electronics')",
            "INSERT INTO Category (Category_id, Category_name) VALUES (2, 'Clothing')",
            "INSERT INTO Category (Category_id, Category_name) VALUES (3, 'Books')",
            // Product table
            "INSERT INTO Product (Product_id, Product_name, Price, Category_id) VALUES (1, 'Laptop', 1200.00, 1)",
            "INSERT INTO Product (Product_id, Product_name, Price, Category_id) VALUES (2, 'T-shirt', 25.00, 2)",
            "INSERT INTO Product (Product_id, Product_name, Price, Category_id) VALUES (3, 'Novel', 15.00, 3)",
            // Customer table
            "INSERT INTO Customer (Customer_id, F_name, L_name, Email, Password, Contact_number, Address) VALUES (1, 'Alice', 'Johnson', 'alice@example.com', 'password123', '123-456-7890', '123 Main St')",
            "INSERT INTO Customer (Customer_id, F_name, L_name, Email, Password, Contact_number, Address) VALUES (2, 'Bob', 'Williams', 'bob@example.com', 'password456', '987-654-3210', '456 Park Ave')",
            // Order table
            "INSERT INTO \"Order\" (Order_id, Customer_id, Total_amount, Status) VALUES (1, 1, 1225.00, 'Shipped')",
            "INSERT INTO \"Order\" (Order_id, Customer_id, Total_amount, Status) VALUES (2, 2, 40.00, 'Pending')",
            // Cart table
            "INSERT INTO Cart (Cart_id, Product_id, Quantity) VALUES (1, 1, 1)",
            "INSERT INTO Cart (Cart_id, Product_id, Quantity) VALUES (2, 2, 3)",
            "INSERT INTO Cart (Cart_id, Product_id, Quantity) VALUES (3, 3, 2)",
            // Supplier table
            "INSERT INTO Supplier (Supplier_id, Supplier_name, Supplier_address) VALUES (1, 'TechCorp', '789 Industrial Rd')",
            "INSERT INTO Supplier (Supplier_id, Supplier_name, Supplier_address) VALUES (2, 'FashionHub', '101 Fashion St')",
            // Inventory table
            "INSERT INTO Inventory (Batch_id, Product_id, Quantity, Price) VALUES (1, 1, 50, 1100.00)",
            "INSERT INTO Inventory (Batch_id, Product_id, Quantity, Price) VALUES (2, 2, 200, 20.00)",
            "INSERT INTO Inventory (Batch_id, Product_id, Quantity, Price) VALUES (3, 3, 150, 12.00)",
            // Payment table
            "INSERT INTO Payment (Payment_id, Order_id, Amount, Payment_method, Payment_date) VALUES (1, 1, 1225.00, 'Credit Card', TO_DATE('2024-09-20', 'YYYY-MM-DD'))",
            "INSERT INTO Payment (Payment_id, Order_id, Amount, Payment_method, Payment_date) VALUES (2, 2, 40.00, 'PayPal', TO_DATE('2024-09-22', 'YYYY-MM-DD'))",
            // TrackingDetails table
            "INSERT INTO TrackingDetails (Tracking_id, Order_id, Shipping_method, Delivery_date) VALUES (1, 1, 'Standard Shipping', TO_DATE('2024-09-25', 'YYYY-MM-DD'))",
            "INSERT INTO TrackingDetails (Tracking_id, Order_id, Shipping_method, Delivery_date) VALUES (2, 2, 'Express Shipping', TO_DATE('2024-09-27', 'YYYY-MM-DD'))"
        };

        try (Statement stmt = conn.createStatement()) {
            for (String sql : insertStatements) {
                stmt.executeUpdate(sql);
            }
            conn.commit();
            System.out.println("All data has been inserted successfully!");
        } catch (SQLException e) {
            conn.rollback();
            System.out.println("Error inserting data: " + e.getMessage());
        }
    }

    // Method to query data
    public static void queryData(Connection conn) throws SQLException {
        String querySQL = "SELECT * FROM Admin";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(querySQL)) {
            while (rs.next()) {
                System.out.println("Admin_ID: " + rs.getInt("Admin_ID") +
                                   ", Name: " + rs.getString("Admin_name") +
                                   ", Role: " + rs.getString("Admin_role"));
            }
        } catch (SQLException e) {
            System.out.println("Error querying data: " + e.getMessage());
        }
    }
}
