package my.edu.utem.ftmk.msmd5113.razkyapplication.DataModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;

import java.util.List;

public class OrphanageDataEntity {

    @SerializedName("orphanageName")
    @Expose
    private String orphanageName;
    @SerializedName("stockDetails")
    @Expose
    private List<StockDetails> stockDetails = null;
    @SerializedName("inventoryLeftValue")
    @Expose
    private String inventoryLeftValue;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("alertIcon")
    @Expose
    private String alertIcon;
    @SerializedName("updatedDate")
    @Expose
    private String updatedDate;
    @SerializedName("totalItemsRequire")
    @Expose
    private String totalItemsRequire;
    @SerializedName("stockOutDays")
    @Expose
    private String stockOutDays;

    public OrphanageDataEntity() {
    }

    public OrphanageDataEntity(String orphanageName, List<StockDetails> stockDetails, String inventoryLeftValue, String createdDate, String alertIcon, String updatedDate, String totalItemsRequire, String stockOutDays) {
        this.orphanageName = orphanageName;
        this.stockDetails = stockDetails;
        this.inventoryLeftValue = inventoryLeftValue;
        this.createdDate = createdDate;
        this.alertIcon = alertIcon;
        this.updatedDate = updatedDate;
        this.totalItemsRequire = totalItemsRequire;
        this.stockOutDays = stockOutDays;
    }

    public String getOrphanageName() {
        return orphanageName;
    }

    public void setOrphanageName(String orphanageName) {
        this.orphanageName = orphanageName;
    }

    public String getInventoryLeftValue() {
        return inventoryLeftValue;
    }

    public void setInventoryLeftValue(String inventoryLeftValue) {
        this.inventoryLeftValue = inventoryLeftValue;
    }

    public String getAlertIcon() {
        return alertIcon;
    }

    public void setAlertIcon(String alertIcon) {
        this.alertIcon = alertIcon;
    }


    public String getStockOutDays() {
        return stockOutDays;
    }

    public void setStockOutDays(String stockOutDays) {
        this.stockOutDays = stockOutDays;
    }

    public List<StockDetails> getStockDetails() {
        return stockDetails;
    }

    public void setStockDetails(List<StockDetails> stockDetails) {
        this.stockDetails = stockDetails;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getTotalItemsRequire() {
        return totalItemsRequire;
    }

    public void setTotalItemsRequire(String totalItemsRequire) {
        this.totalItemsRequire = totalItemsRequire;
    }
}
