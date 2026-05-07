public class Product {
    // niemutowalne
    private final String code;
    private final String name;
    private final double price;

    // mutowalne -promocje
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


    public void setDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
    }

    // Metoda pomocnicza do resetowania ceny
    public void resetDiscount() {
        this.discountPrice = this.price;
    }
}