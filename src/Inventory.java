import java.io.*;
import java.util.*;

public class Inventory implements InventoryInterface {
 
    public ArrayList<Product> products = new ArrayList<>();
    public String file = "products.txt";
    private Reporter reporter;
    
    public Inventory(Reporter reporter) {
        this.reporter = reporter;
        load();
    }
    
    @Override
    public void add_new_product(Product product) {
        for (Product p : products) {
            if (p.getId() == product.getId()) {
                throw new IllegalArgumentException("Product ID already exists.");
            }
        }
        products.add(product);
        save();
        createTransaction("Add", product);
    }

    @Override
    public void remove_product(int prod_id) {
        products.removeIf(product -> product.getId() == prod_id);
        save();
    }

    @Override
    public void update_product(int prod_id, Product product) {
        for (Product p : products) {
            if (p.getId() == prod_id) {
                p.setPrice(product.getPrice());
                p.setQuantity(product.getQuantity());
                break;
            }
        }
        save();
        createTransaction("Update", product);
    }

    @Override
    public void display_all_products() {
        System.out.println("------------------------------------------------------------------------------------------------");
        System.out.println("ID | Name                         | Price                      | Quantity");
        System.out.println("------------------------------------------------------------------------------------------------");
        for (Product product : products) {
            System.out.printf("%-3d| %-28s| %-26.2f| %-8d\n", product.getId(), product.getName(), product.getPrice(), product.getQuantity());
        }
        System.out.println("------------------------------------------------------------------------------------------------");
    }
    
    @Override
    public void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Product product : products) {
                writer.write(product.getId() + "," + product.getName() + "," +
                             product.getPrice() + "," + product.getQuantity());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving file: " + e.getMessage());
        }
    }

    @Override
    public void load() {
        products.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                double price = Double.parseDouble(parts[2]);
                int quantity = Integer.parseInt(parts[3]);
                products.add(new Product(id, name, price, quantity));
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error loading file: " + e.getMessage());
        }
    }

    private void createTransaction(String type, Product product) {
        Transaction transaction = new Transaction(UUID.randomUUID().toString(), type, product);
        reporter.logTransaction(transaction);
    }
}
