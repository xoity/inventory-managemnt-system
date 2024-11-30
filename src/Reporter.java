import java.util.ArrayList;
import java.util.List;
import java.io.*;


public class Reporter {
    private List<String> logs;
    private double totalRevenue;
    private String logFile = "logs.txt";
    private boolean isCacheValid = false;

    // Constructor
    public Reporter() {
        this.logs = new ArrayList<>();
        this.totalRevenue = 0.0;
        loadLogsFromFile();
    }

    public void logTransaction(Transaction transaction) {
        logs.add("Transaction Logged: " + transaction);
        isCacheValid = false;
    }

    public void logEvent(String message) {
        logs.add("EVENT: " + message);
        isCacheValid = false;
    }

    public void logSale(Product product, int quantity, double revenue) {
        logs.add("SALE: Product ID: " + product.getId() + ", Name: " + product.getName() + ", Quantity: " + quantity + ", Revenue: $" + revenue);
        totalRevenue += revenue;
        isCacheValid = false;
    }

    public void deductRevenue(double amount) {
        totalRevenue -= amount;
        isCacheValid = false;
    }

    public void showLogs() {
        if (!isCacheValid) {
            loadLogsFromFile();
        }
        if (logs.isEmpty()) {
            System.out.println("No logs available.");
        } else {
            for (String log : logs) {
                System.out.println(log);
            }
        }
        System.out.println("Total Revenue: $" + totalRevenue);
    }

    public void clearLogs() {
        logs.clear();
        totalRevenue = 0.0;
        System.out.println("All logs have been cleared.");
        isCacheValid = false;
    }

    public void saveLogsToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String log : logs) {
                writer.write(log);
                writer.newLine();
            }
            writer.write("Total Revenue: $" + totalRevenue);
            writer.newLine();
            System.out.println("Logs saved to " + filename);
        } catch (IOException e) {
            System.err.println("Error saving logs to file: " + e.getMessage());
        }
        isCacheValid = true;
    }

    private void loadLogsFromFile() {
        if (isCacheValid) {
            return;
        }
        logs.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(logFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Total Revenue: $")) {
                    totalRevenue = Double.parseDouble(line.substring(15).replace("$", ""));
                } else {
                    logs.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading logs from file: " + e.getMessage());
        }
        isCacheValid = true;
    }
}
