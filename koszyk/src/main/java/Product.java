public class Product {
    // Pola oznaczamy jako final - są niemutowalne (immutable)
    private final String code;
    private final String name;
    private final double price;

    // Tylko to pole jest mutowalne, bo zmienia się pod wpływem promocji
    private double discountPrice;

    public Product(String code, String name, double price) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.discountPrice = price; // na start cena promocyjna to cena bazowa
    }

    public String getCode() { return code; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public double getDiscountPrice() { return discountPrice; }

    // Zostawiamy TYLKO ten setter
    public void setDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
    }

    // Metoda pomocnicza do resetowania ceny (np. przy sprawdzaniu innych promocji)
    public void resetDiscount() {
        this.discountPrice = this.price;
    }
}