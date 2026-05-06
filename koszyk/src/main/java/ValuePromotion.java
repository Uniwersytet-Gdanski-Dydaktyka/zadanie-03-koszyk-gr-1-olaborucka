public class ValuePromotion implements ICartPromotion {
    @Override
    public void apply(Cart cart) {
        if (cart.getTotalSum() > 300.0) {
            for (Product p : cart.getProducts()) {
                p.setDiscountPrice(p.getDiscountPrice() * 0.95);
            }
        }
    }
}