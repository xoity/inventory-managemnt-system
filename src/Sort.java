
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Sort {
    public static void sortById(List<Product> products) {
        Collections.sort(products, Comparator.comparingInt(Product::getId));
    }

    public static void sortByPrice(List<Product> products) {
        Collections.sort(products, Comparator.comparingDouble(Product::getPrice));
    }

    public static void sortByQuantity(List<Product> products) {
        Collections.sort(products, Comparator.comparingInt(Product::getQuantity));
    }
}