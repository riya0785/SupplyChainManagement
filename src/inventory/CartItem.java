package inventory;

public class CartItem {
    private int productId;
    private String item;
    private int quantity;
    private float totalPrice;

    public CartItem(int productId, String item, int quantity, float totalPrice) {
        this.productId = productId;
        this.item = item;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }
}

