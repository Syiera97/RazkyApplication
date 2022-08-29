package my.edu.utem.ftmk.msmd5113.razkyapplication.DataModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DonationItem {
    @SerializedName("productImage")
    @Expose
    private String productImage;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("currentStock")
    @Expose
    private String currentStock;
    @SerializedName("productName")
    @Expose
    private String productName;
    @SerializedName("minStock")
    @Expose
    private String minStock;

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCurrentStock() {
        return currentStock;
    }

    public void setCurrentStock(String currentStock) {
        this.currentStock = currentStock;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getMinStock() {
        return minStock;
    }

    public void setMinStock(String minStock) {
        this.minStock = minStock;
    }
}
