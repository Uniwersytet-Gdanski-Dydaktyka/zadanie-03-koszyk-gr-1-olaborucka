public class Buy2Get1FreePromotion implements ICartPromotion {
    @Override
    public void apply(Cart cart) {
        if (cart.getSize() >= 3) {
            Product cheapest = cart.getCheapest();
            if (cheapest != null) {
                cheapest.setDiscountPrice(0.0);
            }
        }
    }
}