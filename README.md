# Simple Menu Application - Database Management System

## Overview

The **Simple Menu Application** is a Java-based console application that allows users to interact with an Oracle database for performing common database operations such as creating tables, dropping tables, populating tables with data, and querying data. This application provides an intuitive menu-driven interface for seamless interaction with the database, offering a simple yet effective tool for managing relational data.

The application uses JDBC for connecting to an Oracle database and employs PreparedStatements for secure database queries and operations. The application also implements error handling and logging mechanisms for easy debugging and troubleshooting.

## Features

- **Menu-Driven Interface**: A console-based interface with a list of options for users to select the desired operation.
- **Create Tables**: Dynamically create multiple tables including Admin, Category, Product, Customer, Orders, and more.
- **Drop Tables**: Drop existing tables from the database.
- **Populate Tables**: Populate tables with sample data using PreparedStatements.
- **Query Data**: Retrieve and display data from the database using SQL queries.
- **Transaction Management**: Use transactions to ensure data consistency when populating tables with data.
- **Logging**: The application logs key actions and errors for easier debugging and traceability.
- **Secure Database Operations**: All database queries use `PreparedStatement` to prevent SQL injection and ensure secure data handling.

## Requirements

- **JDK 8 or higher**: The project is developed using Java 8 or higher, leveraging JDBC for database communication.
- **Oracle Database**: The application connects to an Oracle database for performing SQL operations. The database connection details (username, password, and URL) should be provided for proper functionality.
- **Maven** (optional): For managing dependencies if extending or modifying the application.

## Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/yourusername/simple-menu-app.git
cd simple-menu-app
```
### 2. Configure Database Connection

Open the `SimpleMenuApp.java` file and replace the following values with your own Oracle database connection details:

```java
static final String DB_URL = "jdbc:oracle:thin:@oracle.scs.ryerson.ca:1521:orcl";
static final String USER = "USERID";
static final String PASS = "PASSWORD";
```


### 3. Create Tables

The application includes a method that creates all necessary tables in the Oracle database. When you select **1. Create Tables** from the menu, the following SQL queries are executed to create the tables:

- **Admin**: Stores admin details.
- **Category**: Stores product categories.
- **Product**: Stores product details, including price and associated category.
- **Customer**: Stores customer details such as name, email, and contact information.
- **Order**: Stores order details for customers.
- **Cart**: Stores products added to the shopping cart by customers.
- **Supplier**: Stores supplier information for products.
- **Inventory**: Stores inventory details of products.
- **Payment**: Stores payment details for orders.
- **TrackingDetails**: Stores tracking information for orders.

The method that executes this functionality is `createTables(Connection conn)`, and it uses the following SQL statements to create the tables:

```java
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
```
Once the tables are created successfully, you will see the following message:
```bash
All tables created successfully!
```

### Step 4: Drop Tables

```markdown
### 4. Drop Tables

The **Drop Tables** feature allows you to drop the tables if needed. This can be useful if you want to reset the database schema or if you're testing your application and need to start fresh. When you select **2. Drop Tables** from the menu, the following SQL queries are executed to drop the tables:

- **Admin**
- **Category**
- **Product**
- **Customer**
- **Order**
- **Cart**
- **Supplier**
- **Inventory**
- **Payment**
- **TrackingDetails**
```
The method that executes this functionality is `dropTables(Connection conn)`, and it uses the following SQL statements to drop the tables:

```java
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
```

Use the menu to perform various operations on the database. 

The application will prompt you for your choice. Based on the option selected, the corresponding database operation will be executed.

#### Example Output

When the application is running, it might look something like this:


```bash

Connected to Oracle database!

Menu:
	1.	Create Tables
	2.	Drop Tables
	3.	Populate Tables
	4.	Query Data
	5.	Exit
Choose an option: 1
All tables created successfully!

Menu:
	1.	Create Tables
	2.	Drop Tables
	3.	Populate Tables
	4.	Query Data
	5.	Exit
Choose an option: 4
Admin_ID: 1, Name: John Doe, Role: Manager
...

```
This output indicates the successful execution of the **Create Tables** and **Query Data** options. 

#### Troubleshooting

If you encounter any issues while interacting with the application, refer to the **Error Handling** section for more details on common errors and solutions.

---

### Code Structure

The project is structured as follows:

- **SimpleMenuApp.java**: Main class that contains the menu and methods for interacting with the database.
- **Database Connection**: The JDBC connection is established using `DriverManager.getConnection` with the configured connection URL, username, and password.
- **CRUD Operations**: Methods for creating tables, dropping tables, inserting data, and querying the database.
- **Logging**: Utilizes Java’s `Logger` to log operations and errors.
- **Transaction Management**: Ensures that data insertions are handled within a transaction for consistency.

This modular approach keeps the application clean and easy to extend. The **SimpleMenuApp.java** file manages the user interface and interacts with the database through defined methods, while the database connection and CRUD operations are handled in separate methods to ensure reusability.

---
