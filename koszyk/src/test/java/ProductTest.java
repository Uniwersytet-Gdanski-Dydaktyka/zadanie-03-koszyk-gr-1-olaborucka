import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductTest {

    @Test
    void shouldProperlyInitializeProduct() {
        // Arrange
        String expectedCode = "P001";
        String expectedName = "Myszka komputerowa";
        double expectedPrice = 150.0;
        double expectedDiscountPrice = expectedPrice;

        // Act
        Product product = new Product(expectedCode, expectedName, expectedPrice);

        // Assert
        assertEquals(expectedCode, product.getCode());
        assertEquals(expectedPrice, product.getPrice());
        assertEquals(expectedName, product.getName());
        assertEquals(expectedDiscountPrice, product.getDiscountPrice());
    }

    @Test
    void shouldUpdateDiscountPrice() {
        // Arrange
        Product product = new Product("P002", "Klawiatura", 200.0);
        double newDiscountPrice = 150.0;

        // Act
        product.setDiscountPrice(newDiscountPrice);

        // Assert
        // Sprawdzamy, czy cena promocyjna faktycznie się zmieniła
        assertEquals(newDiscountPrice, product.getDiscountPrice());
        // Sprawdzamy dodatkowo, czy cena wyjściowa (price) pozostała nietknięta
        assertEquals(200.0, product.getPrice());
    }

    @Test
    void shouldResetDiscountPriceToOriginalPrice() {
        // Arrange
        Product product = new Product("P003", "Monitor", 1000.0);
        product.setDiscountPrice(800.0); // Najpierw obniżamy cenę, symulując nałożenie promocji

        // Act
        product.resetDiscount(); // Resetujemy zniżki (przywracamy cenę bazową)

        // Assert
        // Sprawdzamy, czy discountPrice wrócił do poziomu oryginalnego price
        assertEquals(1000.0, product.getDiscountPrice());
        assertEquals(1000.0, product.getPrice());
    }
}