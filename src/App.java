import java.util.*;

public class App {
    public static void main(String[] args) throws Exception {
        Reporter reporter = new Reporter();
        Inventory inventory = new Inventory(reporter);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Add Product");
            System.out.println("2. Update Product");
            System.out.println("3. Display Products");
            System.out.println("4. Search Product");
            System.out.println("5. Make Sale");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();

            try {
                switch (option) {
                    case 1:
                        addProduct(scanner, inventory, reporter);
                        break;
                    case 2:
                        updateOrRemoveProduct(scanner, inventory, reporter);
                        break;
                    case 3:
                        displayProducts(scanner, inventory);
                        break;
                    case 4:
                        searchProduct(scanner, inventory);
                        break;
                    case 5:
                        makeSale(scanner, inventory, reporter);
                        break;
                    case 6:
                        exitApplication(reporter, scanner);
                        break;
                    default:
                        System.out.println("Invalid option");
                        break;
                }

                checkStockLevels(scanner, inventory, reporter);
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
                reporter.logEvent("Error: " + e.getMessage());
                reporter.saveLogsToFile("logs.txt");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                reporter.logEvent("Error: " + e.getMessage());
                reporter.saveLogsToFile("logs.txt");
            }
            System.out.println();
        }
    }

    private static void addProduct(Scanner scanner, Inventory inventory, Reporter reporter) {
        System.out.print("\nEnter product id: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();
        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty.");
        }
        System.out.print("Enter product quantity: ");
        int quantity = scanner.nextInt();
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }
        System.out.print("Enter product price: ");
        double price = scanner.nextDouble();
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        double cost = quantity * price;
        inventory.add_new_product(new Product(id, name, price, quantity));
        reporter.logEvent("Added new product: " + name);
        reporter.logEvent("Cost of adding product: $" + cost);
        reporter.deductRevenue(cost);
        reporter.saveLogsToFile("logs.txt");
    }

    private static void updateOrRemoveProduct(Scanner scanner, Inventory inventory, Reporter reporter) {
        System.out.println("\n1. Change product details");
        System.out.println("2. Remove product");
        System.out.print("Choose an option: ");
        int updateOption = scanner.nextInt();
        if (updateOption == 1) {
            updateProduct(scanner, inventory, reporter);
        } else if (updateOption == 2) {
            removeProduct(scanner, inventory, reporter);
        } else {
            System.out.println("Invalid option");
        }
    }

    private static void updateProduct(Scanner scanner, Inventory inventory, Reporter reporter) {
        System.out.print("\nEnter product id: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Change:");
        System.out.println("1. Name");
        System.out.println("2. Quantity");
        System.out.println("3. Price");
        System.out.print("Choose an option: ");
        int detailOption = scanner.nextInt();
        scanner.nextLine();
        Product productToUpdate = inventory.products.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
        if (productToUpdate != null) {
            switch (detailOption) {
                case 1:
                    System.out.print("Enter new product name: ");
                    String newName = scanner.nextLine();
                    if (newName.trim().isEmpty()) {
                        throw new IllegalArgumentException("Product name cannot be empty.");
                    }
                    productToUpdate.setName(newName);
                    break;
                case 2:
                    System.out.print("Enter new product quantity: ");
                    int newQuantity = scanner.nextInt();
                    if (newQuantity < 0) {
                        throw new IllegalArgumentException("Quantity cannot be negative.");
                    }
                    productToUpdate.setQuantity(newQuantity);
                    break;
                case 3:
                    System.out.print("Enter new product price: ");
                    double newPrice = scanner.nextDouble();
                    if (newPrice < 0) {
                        throw new IllegalArgumentException("Price cannot be negative.");
                    }
                    productToUpdate.setPrice(newPrice);
                    break;
                default:
                    System.out.println("Invalid option");
                    return;
            }
            inventory.update_product(id, productToUpdate);
            reporter.logEvent("Updated product: " + productToUpdate.getName());
            reporter.saveLogsToFile("logs.txt");
        } else {
            System.out.println("Product not found.");
        }
    }

    private static void removeProduct(Scanner scanner, Inventory inventory, Reporter reporter) {
        System.out.print("\nEnter product id to remove: ");
        int id = scanner.nextInt();
        inventory.remove_product(id);
        reporter.logEvent("Removed product with id: " + id);
        reporter.saveLogsToFile("logs.txt");
    }

    private static void displayProducts(Scanner scanner, Inventory inventory) {
        System.out.println("\nDisplay products by:");
        System.out.println("1. ID");
        System.out.println("2. Price");
        System.out.println("3. Quantity");
        System.out.print("Choose an option: ");
        int sortOption = scanner.nextInt();
        switch (sortOption) {
            case 1:
                Sort.sortById(inventory.products);
                break;
            case 2:
                Sort.sortByPrice(inventory.products);
                break;
            case 3:
                Sort.sortByQuantity(inventory.products);
                break;
            default:
                System.out.println("Invalid option");
                return;
        }
        inventory.display_all_products();
    }

    private static void searchProduct(Scanner scanner, Inventory inventory) {
        System.out.println("\nSearch by:");
        System.out.println("1. ID");
        System.out.println("2. Name");
        System.out.println("3. Price");
        System.out.println("4. Quantity");
        System.out.print("Choose an option: ");
        int searchOption = scanner.nextInt();
        String searchType = "";
        Object value = null;

        switch (searchOption) {
            case 1:
                searchType = "id";
                System.out.print("\nEnter product ID: ");
                value = scanner.nextInt();
                if ((int) value < 0) {
                    throw new IllegalArgumentException("ID cannot be negative.");
                }
                break;
            case 2:
                searchType = "name";
                System.out.print("\nEnter product name: ");
                value = scanner.next();
                break;
            case 3:
                searchType = "price";
                System.out.print("\nEnter product price: ");
                value = scanner.nextDouble();
                if ((double) value < 0) {
                    throw new IllegalArgumentException("Price cannot be negative.");
                }
                break;
            case 4:
                searchType = "quantity";
                System.out.print("\nEnter product quantity: ");
                value = scanner.nextInt();
                if ((int) value < 0) {
                    throw new IllegalArgumentException("Quantity cannot be negative.");
                }
                break;
            default:
                System.out.println("Invalid option");
                return;
        }

        List<Product> foundProducts = Search.searchItems(inventory.products, searchType, value);
        if (!foundProducts.isEmpty()) {
            System.out.println("------------------------------------------------------------------------------------------------");
            System.out.println("ID | Name                         | Price                      | Quantity");
            System.out.println("------------------------------------------------------------------------------------------------");
            for (Product product : foundProducts) {
                System.out.printf("%-3d| %-28s| %-26.2f| %-8d\n", product.getId(), product.getName(), product.getPrice(), product.getQuantity());
            }
            System.out.println("------------------------------------------------------------------------------------------------");
        } else {
            System.out.println("No products found.");
        }
    }

    private static void makeSale(Scanner scanner, Inventory inventory, Reporter reporter) {
        System.out.print("\nEnter product id to sell: ");
        int saleId = scanner.nextInt();
        System.out.print("Enter quantity to sell: ");
        int saleQuantity = scanner.nextInt();
        if (saleQuantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }
        Product productToSell = inventory.products.stream()
                .filter(p -> p.getId() == saleId)
                .findFirst()
                .orElse(null);
        if (productToSell != null) {
            if (productToSell.getQuantity() < saleQuantity) {
                throw new IllegalArgumentException("Not enough stock to sell.");
            }
            productToSell.setQuantity(productToSell.getQuantity() - saleQuantity);
            double revenue = saleQuantity * productToSell.getPrice();
            reporter.logSale(productToSell, saleQuantity, revenue);
            inventory.save();
            reporter.saveLogsToFile("logs.txt");
            System.out.println("Sale completed. Revenue: $" + revenue);
        } else {
            System.out.println("Product not found.");
        }
    }

    private static void exitApplication(Reporter reporter, Scanner scanner) {
        System.out.println("\nExiting...");
        reporter.saveLogsToFile("logs.txt");
        scanner.close();
        System.exit(0);
    }

    private static void checkStockLevels(Scanner scanner, Inventory inventory, Reporter reporter) {
        for (Product product : inventory.products) {
            if (product.getQuantity() <= 5) {
                if (product.getQuantity() == 0) {
                    System.out.println("\nALERT: Product " + product.getName() + " is out of stock.");
                    System.out.print("Do you want to remove this product? (y/n): ");
                    String response = scanner.next();
                    if (response.equalsIgnoreCase("y")) {
                        inventory.remove_product(product.getId());
                        reporter.logEvent("Removed out of stock product: " + product.getName());
                        reporter.saveLogsToFile("logs.txt");
                        continue;
                    }
                } else {
                    System.out.println("\nALERT: Product " + product.getName() + " is low in stock.");
                }
                System.out.print("Do you want to reorder this product? (y/n): ");
                String response = scanner.next();
                if (response.equalsIgnoreCase("y")) {
                    System.out.print("Enter quantity to reorder: ");
                    int reorderQuantity = scanner.nextInt();
                    if (reorderQuantity < 0) {
                        throw new IllegalArgumentException("Quantity cannot be negative.");
                    }
                    System.out.print("Enter price per unit (originally $" + product.getPrice() + "): ");
                    double reorderPrice = scanner.nextDouble();
                    if (reorderPrice < 0) {
                        throw new IllegalArgumentException("Price cannot be negative.");
                    }
                    double reorderCost = reorderQuantity * reorderPrice;
                    product.setQuantity(product.getQuantity() + reorderQuantity);
                    product.setPrice(reorderPrice);
                    reporter.logEvent("Reordered product: " + product.getName());
                    reporter.logEvent("Cost of reordering product: $" + reorderCost);
                    reporter.deductRevenue(reorderCost);
                    inventory.save();
                    reporter.saveLogsToFile("logs.txt");
                }
            }
        }
    }
}


