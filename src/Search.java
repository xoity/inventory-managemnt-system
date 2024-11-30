import java.util.ArrayList;
import java.util.List;

public class Search {
    public static List<Product> searchItems(List<Product> products, String searchType, Object value) {
        List<Product> matchingProducts = new ArrayList<>();
        for (Product product : products) {
            switch (searchType.toLowerCase()) {
                case "id":
                    if (product.getId() == (int) value) {
                        matchingProducts.add(product);
                    }
                    break;
                case "name":
                    if (product.getName().equalsIgnoreCase((String) value)) {
                        matchingProducts.add(product);
                    }
                    break;
                case "price":
                    if (product.getPrice() == (double) value) {
                        matchingProducts.add(product);
                    }
                    break;
                case "quantity":
                    if (product.getQuantity() == (int) value) {
                        matchingProducts.add(product);
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Invalid search type: " + searchType);
            }
        }
        return matchingProducts;
    }
}