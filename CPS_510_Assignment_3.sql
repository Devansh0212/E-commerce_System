
--	1.  Select All Admins: Retrieves all records from the Admin table.
SELECT * FROM Admin;

--	2.  Count Number of Customers: Counts the total number of entries in the Customer table.
SELECT COUNT(*) AS Total_Customers FROM Customer;

--	3.  List All Products with their Categories: Joins Product and Category tables to display each product along with its category name.
SELECT P.Product_id, P.Product_name, C.Category_name
FROM Product P
JOIN Category C ON P.Category_id = C.Category_id;

--  4.  Select All Orders and Corresponding Customer Names: Joins the Order and Customer tables to list orders with customer names and total amounts.
SELECT O.Order_id, C.F_name, C.L_name, O.Total_amount, O.Status
FROM "Order" O
JOIN Customer C ON O.Customer_id = C.Customer_id;

--	5.	Get Inventory Details for a Specific Product: Fetches details about a specific product in the Inventory table. You can change the Product_id for different products.
SELECT I.Batch_id, I.Quantity, I.Price
FROM Inventory I
WHERE I.Product_id = 1;  -- Change Product_id as needed

--	6.	Total Sales Amount by Customer: Groups by customer to sum their total sales.
SELECT C.Customer_id, C.F_name, C.L_name, SUM(O.Total_amount) AS Total_Sales
FROM Customer C
JOIN "Order" O ON C.Customer_id = O.Customer_id
GROUP BY C.Customer_id, C.F_name, C.L_name;

--	7.	Get All Payments and their Corresponding Orders: Joins the Payment and Order tables to show each payment associated with an order.
SELECT P.Payment_id, P.Amount, P.Payment_method, O.Order_id
FROM Payment P
JOIN "Order" O ON P.Order_id = O.Order_id;

