import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Cart {
    // lista obiektow produktow
    private List<Product> products;

    // konstruktor
    public Cart() {
        this.products = new ArrayList<>();
    }

    public void addProduct(Product p) {
        if (p != null) products.add(p);
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
        if (products.isEmpty()) return;
        promotion.apply(this);
    }


    public void applyBestPromotions(List<ICartPromotion> promotions) {
        if (promotions == null || promotions.isEmpty()) return;

        // lista możliwych układów promocyjnych
        List<List<ICartPromotion>> allPermutations = generatePermutations(promotions);
        //najlepsza
        List<ICartPromotion> bestOrder = null;
        // najwieksza mozliwa watosc koszyka do porownywania
        double lowestTotal = Double.MAX_VALUE;

        // Testujemy każdą możliwą kolejność nałożenia promocji
        for (List<ICartPromotion> order : allPermutations) {
            resetAllDiscounts(); // Resetujemy ceny przed każdym testem
            for (ICartPromotion promo : order) {
                promo.apply(this);
            }
            double currentTotal = getTotalSum();
            if (currentTotal < lowestTotal) {
                lowestTotal = currentTotal;
                bestOrder = order;
            }
        }

        // Aplikujemy ostatecznie tę najlepszą kolejność
        resetAllDiscounts();
        if (bestOrder != null) {
            for (ICartPromotion promo : bestOrder) {
                promo.apply(this);
            }
        }
    }

    private void resetAllDiscounts() {
        for (Product p : products) {
            p.resetDiscount();
        }
    }

    // Rekurencyjny generator permutacji
    private List<List<ICartPromotion>> generatePermutations(List<ICartPromotion> original) {
        List<List<ICartPromotion>> result = new ArrayList<>();
        if (original.isEmpty()) {
            result.add(new ArrayList<>());
            return result;
        }
        ICartPromotion firstElement = original.remove(0);
        List<List<ICartPromotion>> permutations = generatePermutations(original);
        // permutacje wyliczone przed chwila + usuniety element
        for (List<ICartPromotion> smallerPermutated : permutations) {
            for (int index = 0; index <= smallerPermutated.size(); index++) {
                List<ICartPromotion> temp = new ArrayList<>(smallerPermutated);
                temp.add(index, firstElement);
                result.add(temp);
            }
        }
        original.add(0, firstElement);
        return result;
    }


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

    // przyjmuje instrikcje jak posortowac koszyk
    public void sortProducts(Comparator<Product> comparator) {
        products.sort(comparator);
    }
}