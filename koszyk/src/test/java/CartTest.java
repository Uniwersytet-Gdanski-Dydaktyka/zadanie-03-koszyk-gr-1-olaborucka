import org.junit.jupiter.api.Test;
import java.util.Comparator;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CartTest {

    @Test
    void shouldAddProductToCart() {
        Cart cart = new Cart();
        Product product = new Product("111", "mleko", 10);

        cart.addProduct(product);

        assertEquals(1, cart.getSize());
    }

    @Test
    void shouldCalculateTotalSum(){
        Cart cart = new Cart();
        Product product = new Product("111", "mleko", 10);
        Product product2 = new Product("111", "ser", 5);
        Product product3 = new Product("111", "mleko", 10);

        cart.addProduct(product);
        cart.addProduct(product2);
        cart.addProduct(product3);

        assertEquals(25, cart.getTotalSum());
    }

    @Test
    void shouldApply5PercentDiscountWhenSumOver300() {
        // Arrange
        Cart cart = new Cart();
        cart.addProduct(new Product("P99", "Drogi Ser", 400.0));

        // Act - Tutaj wrzucamy naszą nową strategię do koszyka!
        cart.applyPromotion(new ValuePromotion());

        // Assert
        assertEquals(380.0, cart.getTotalSum());
    }

    @Test
    void shouldFindCheapestProduct() {
        // Arrange
        Cart cart = new Cart();
        cart.addProduct(new Product("1", "Drogi", 200.0));
        cart.addProduct(new Product("2", "Tani", 50.0));
        cart.addProduct(new Product("3", "Sredni", 100.0));

        // Act
        Product cheapest = cart.getCheapest();

        // Assert
        assertEquals("Tani", cheapest.getName());
        assertEquals(50.0, cheapest.getPrice());
    }

    @Test
    void shouldSortProductsDescendingByPriceThenAlphabetically() {
        // Arrange
        Cart cart = new Cart();
        cart.addProduct(new Product("1", "Zebra", 100.0));
        cart.addProduct(new Product("2", "Arbuz", 100.0));
        cart.addProduct(new Product("3", "Banan", 200.0));

        // Act - Sortowanie według wymogu: malejąco po cenie, potem alfabetycznie
        cart.sortProducts(Comparator.comparing(Product::getPrice).reversed().thenComparing(Product::getName));

        // Assert
        List<Product> sorted = cart.getProducts();
        assertEquals("Banan", sorted.get(0).getName()); // Najdroższy na szczycie
        assertEquals("Arbuz", sorted.get(1).getName()); // Ta sama cena, więc A przed Z
        assertEquals("Zebra", sorted.get(2).getName());
    }

    @Test
    void shouldApplyBuy2Get1FreePromotion() {
        // Arrange
        Cart cart = new Cart();
        cart.addProduct(new Product("1", "Produkt A", 100.0));
        cart.addProduct(new Product("2", "Tani Produkt B", 50.0)); // Ten powinien być gratis
        cart.addProduct(new Product("3", "Produkt C", 200.0));

        // Act
        cart.applyPromotion(new Buy2Get1FreePromotion());

        // Assert - Zamiast 350 zł, powinno być 300 zł (100 + 0 + 200)
        assertEquals(300.0, cart.getTotalSum());
    }

    @Test
    void shouldAddFreeMugWhenSumOver200() {
        // Arrange
        Cart cart = new Cart();
        cart.addProduct(new Product("1", "Drogi Sprzet", 250.0));

        // Act
        cart.applyPromotion(new FreeMugPromotion());

        // Assert
        assertEquals(2, cart.getSize()); // Pojawił się drugi produkt
        assertEquals("MUG", cart.getProducts().get(1).getCode()); // Kod to MUG
        assertEquals(0.0, cart.getProducts().get(1).getDiscountPrice()); // Kubek jest darmowy
    }

    @Test
    void shouldApplyCouponToSpecificProduct() {
        // Arrange
        Cart cart = new Cart();
        cart.addProduct(new Product("1", "Produkt A", 100.0));
        cart.addProduct(new Product("TARGET", "Produkt B", 100.0)); // Zniżka ma wejść tutaj

        // Act - Kupon na 30% na konkretny kod
        cart.applyPromotion(new CouponPromotion("TARGET"));

        // Assert - (100) + (100 - 30%) = 170
        assertEquals(170.0, cart.getTotalSum());
    }
}