package my.edu.utem.ftmk.msmd5113.razkyapplication.Donor.DonateNow;

import java.util.List;

public class OrphanagesDataEntity {
    public String orphanageName;
    public String alertIcon;
    public String stockOutValue;
    public String createdDate;
    public String inventoryLeftValue;
    public String totalAmount;
    public List<StockDataEntity> stockDataEntityList;

    public String getOrphanageName() {
        return orphanageName;
    }

    public String getAlertIcon() {
        return alertIcon;
    }

    public String getStockOutValue() {
        return stockOutValue;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getInventoryLeftValue() {
        return inventoryLeftValue;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public List<StockDataEntity> getStockDataEntityList() {
        return stockDataEntityList;
    }
}
