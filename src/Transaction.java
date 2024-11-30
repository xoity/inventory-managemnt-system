import java.util.Date;

public class Transaction {
    private String transactionId;
    private String transactionType;
    private Product product;
    private Date date;
    private String status;

    public Transaction(String transactionId, String transactionType, Product product) {
        this.transactionId = transactionId;
        this.transactionType = transactionType;
        this.product = product;
        this.date = new Date(); 
        this.status = "Pending";
    }

    // Getters
    public String getTransactionId() {
        return transactionId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public Product getProduct() {
        return product;
    }

    public Date getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public void completeTransaction() {
        this.status = "Completed";
    }

    
    @Override
    public String toString() {
        return "Transaction ID: " + transactionId +
               ", Type: " + transactionType +
               ", Product: " + product +
               ", Date: " + date +
               ", Status: " + status;
    }
}
