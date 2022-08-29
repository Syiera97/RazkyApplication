package my.edu.utem.ftmk.msmd5113.razkyapplication.DataModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DonationDetailsDataEntity {
    @SerializedName("orphanageName")
    @Expose
    private String orphanageName;
    @SerializedName("donorName")
    @Expose
    private String donorName;
    @SerializedName("isAnonymous")
    @Expose
    private Boolean isAnonymous;
    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("donationItem")
    @Expose
    private List<DonationItem> donationItem = null;
    @SerializedName("effectiveDate")
    @Expose
    private String effectiveDate;
    @SerializedName("donatorEmail")
    @Expose
    private String donatorEmail;
    @SerializedName("noItemsDonated")
    @Expose
    private String noItemsDonated;

    public DonationDetailsDataEntity() {
    }

    public DonationDetailsDataEntity(String orphanageName, String donorName, Boolean isAnonymous, String phoneNumber, List<DonationItem> donationItem, String effectiveDate, String donatorEmail, String noItemsDonated) {
        this.orphanageName = orphanageName;
        this.donorName = donorName;
        this.isAnonymous = isAnonymous;
        this.phoneNumber = phoneNumber;
        this.donationItem = donationItem;
        this.effectiveDate = effectiveDate;
        this.donatorEmail = donatorEmail;
        this.noItemsDonated = noItemsDonated;
    }

    public String getOrphanageName() {
        return orphanageName;
    }

    public void setOrphanageName(String orphanageName) {
        this.orphanageName = orphanageName;
    }

    public String getDonorName() {
        return donorName;
    }

    public void setDonorName(String donorName) {
        this.donorName = donorName;
    }

    public Boolean getAnonymous() {
        return isAnonymous;
    }

    public void setAnonymous(Boolean anonymous) {
        isAnonymous = anonymous;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<DonationItem> getDonationItem() {
        return donationItem;
    }

    public void setDonationItem(List<DonationItem> donationItem) {
        this.donationItem = donationItem;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getDonatorEmail() {
        return donatorEmail;
    }

    public void setDonatorEmail(String donatorEmail) {
        this.donatorEmail = donatorEmail;
    }

    public String getNoItemsDonated() {
        return noItemsDonated;
    }

    public void setNoItemsDonated(String noItemsDonated) {
        this.noItemsDonated = noItemsDonated;
    }
}
