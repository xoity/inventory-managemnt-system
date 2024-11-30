# Inventory Management System

## Introduction

The **Inventory Management System** is a robust Java application designed to streamline inventory control and management. With a menu-driven interface, users can efficiently add, update, search, sort, and delete products while tracking sales and generating reports. The application is designed with Object-Oriented Programming principles and includes comprehensive error handling, making it a powerful tool for businesses managing stock and revenue.

## Features

- **Add Products**: Add new products with attributes like ID, name, quantity, and price.
- **Update/Delete Products**: Modify product details or remove products from inventory.
- **Search Products**: Locate products by ID, name, price, or quantity.
- **Sort Products**: View inventory sorted by ID, price, or quantity.
- **Track Sales**: Record sales, adjust stock levels, and calculate revenue.
- **Reorder and Restock**: Alert for low stock, reorder items, and manage out-of-stock products.
- **File Persistence**: Automatically save and load product and transaction data from files.
- **Error Handling**: Manage invalid inputs and system errors gracefully.

## Architecture

The project is modular and includes the following key components:

- **App.java**: The main entry point managing user interaction.
- **Inventory.java**: Core inventory management functionalities.
- **Product.java**: Defines the product model with attributes and methods.
- **InventoryInterface.java**: Ensures a standardized structure for inventory operations.
- **Reporter.java**: Manages logs and revenue tracking.
- **Search.java**: Provides methods to search inventory items.
- **Sort.java**: Implements sorting by various product attributes.
- **Transaction.java**: Tracks inventory transactions.

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- An IDE (e.g., IntelliJ IDEA, Eclipse) or a text editor with Java support
- Basic knowledge of Java and Object-Oriented Programming

### Setup

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/xoity/inventory-managemnt-system.git
   ```
2. **Navigate to the Project Directory**:
   ```bash
   cd Inventory-Management-System
   ```
3. **Compile the Source Code**:
   ```bash
   javac *.java
   ```
4. **Run the Application**:
   ```bash
   java App
   ```

### Usage

1. Choose an option from the main menu.
2. Follow on-screen prompts to perform operations like adding, updating, or searching for products.
3. Check the `products.txt` and `logs.txt` files for saved inventory and transaction data.

## File Descriptions

- **products.txt**: Stores product data in a comma-separated format.
- **logs.txt**: Logs all system events, sales, and revenue changes.
- **App.java**: Contains the main method and orchestrates user interactions.
- Other Java classes define specific functionalities, ensuring modularity and maintainability.

## Example Outputs

- **Adding a Product**:
  ```text
  Enter product ID: 101
  Enter product name: Widget A
  Enter product quantity: 50
  Enter product price: 10.5
  Product added successfully!
  ```

- **Low Stock Alert**:
  ```text
  ALERT: Product Widget A is low in stock.
  Do you want to reorder this product? (y/n):
  ```

- **Sale Transaction**:
  ```text
  Sale completed. Revenue: $105.0
  ```

## Future Enhancements

- Support for database integration.
- Enhanced GUI using JavaFX or Swing.
- REST API for remote inventory management.

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.
