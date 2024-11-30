public interface InventoryInterface {

    abstract void add_new_product(Product product);
    abstract void remove_product(int prod_id);
    abstract void update_product(int prod_id, Product product);
    abstract void display_all_products();
    abstract void save();
    abstract void load();
    
}
