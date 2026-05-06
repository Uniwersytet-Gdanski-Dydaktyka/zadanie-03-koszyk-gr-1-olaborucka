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
}

