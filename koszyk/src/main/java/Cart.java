import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Cart {
    private List<Product> products;

    public Cart() {
        this.products = new ArrayList<>();
    }

    public void addProduct(Product p) {
        if (p != null) { // Obsługa sytuacji brzegowej (null)
            products.add(p);
        }
    }

    public int getSize() { return products.size(); }
    public List<Product> getProducts() { return products; }

    public double getTotalSum() {
        double sum = 0;
        for (Product p : products) {
            sum += p.getDiscountPrice();
        }
        return sum;
    }

    public void applyPromotion(ICartPromotion promotion) {
        if (products.isEmpty()) return; // Obsługa pustego koszyka
        promotion.apply(this);
    }

    // --- WYSZUKIWANIE ---
    public Product getCheapest() {
        return products.stream().min(Comparator.comparing(Product::getPrice)).orElse(null);
    }

    public Product getMostExpensive() {
        return products.stream().max(Comparator.comparing(Product::getPrice)).orElse(null);
    }

    public List<Product> getNCheapest(int n) {
        return products.stream().sorted(Comparator.comparing(Product::getPrice)).limit(n).collect(Collectors.toList());
    }

    public List<Product> getNMostExpensive(int n) {
        return products.stream().sorted(Comparator.comparing(Product::getPrice).reversed()).limit(n).collect(Collectors.toList());
    }

    // --- SORTOWANIE (Dependency Inversion - otwarte na modyfikacje) ---
    public void sortProducts(Comparator<Product> comparator) {
        products.sort(comparator);
    }
}