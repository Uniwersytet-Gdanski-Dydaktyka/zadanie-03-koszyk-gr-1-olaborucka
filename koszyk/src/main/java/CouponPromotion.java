public class CouponPromotion implements ICartPromotion {
    private String targetProductCode;

    public CouponPromotion(String targetProductCode) {
        this.targetProductCode = targetProductCode;
    }

    @Override
    public void apply(Cart cart) {
        for (Product p : cart.getProducts()) {
            if (p.getCode().equals(targetProductCode)) {
                p.setDiscountPrice(p.getDiscountPrice() * 0.70);
                break; // jednorazowy kupon
            }
        }
    }
}