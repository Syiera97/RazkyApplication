package my.edu.utem.ftmk.msmd5113.razkyapplication.DataModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DonationDetailsDataEntity {
    @SerializedName("orphanageName")
    @Expose
    private String orphanageName;
    @SerializedName("AccNo")
    @Expose
    private String accNo;
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
    @SerializedName("donationAmount")
    @Expose
    private String donationAmount;
    @SerializedName("effectiveDate")
    @Expose
    private String effectiveDate;
    @SerializedName("referenceID")
    @Expose
    private String referenceID;
    @SerializedName("donatorID")
    @Expose
    private String donatorID;
    @SerializedName("donatorEmail")
    @Expose
    private String donatorEmail;

    public String getDonatorID() {
        return donatorID;
    }

    public void setDonatorID(String donatorID) {
        this.donatorID = donatorID;
    }

    public String getOrphanageName() {
        return orphanageName;
    }

    public void setOrphanageName(String orphanageName) {
        this.orphanageName = orphanageName;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
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

    public String getDonationAmount() {
        return donationAmount;
    }

    public void setDonationAmount(String donationAmount) {
        this.donationAmount = donationAmount;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getReferenceID() {
        return referenceID;
    }

    public void setReferenceID(String referenceID) {
        this.referenceID = referenceID;
    }

    public String getDonatorEmail() {
        return donatorEmail;
    }

    public void setDonatorEmail(String donatorEmail) {
        this.donatorEmail = donatorEmail;
    }
}
