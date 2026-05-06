public class FreeMugPromotion implements ICartPromotion {
    @Override
    public void apply(Cart cart) {
        if (cart.getTotalSum() > 200.0) {
            boolean hasMug = cart.getProducts().stream().anyMatch(p -> p.getCode().equals("MUG"));
            if (!hasMug) {
                Product mug = new Product("MUG", "Firmowy Kubek", 0.0);
                mug.setDiscountPrice(0.0);
                cart.addProduct(mug);
            }
        }
    }
}