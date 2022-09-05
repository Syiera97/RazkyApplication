package my.edu.utem.ftmk.msmd5113.razkyapplication.DataModel;

public class DonorDataEntity {
    String donatorID;
    String donatorName;
    String donatorEmail;
    String effectiveDate;
    boolean isAnonymous;
    String accNo;
    String phoneNo;

    public DonorDataEntity() {
    }

    public DonorDataEntity(String donatorName, String donatorEmail, boolean isAnonymous, String phoneNo) {
        this.donatorName = donatorName;
        this.donatorEmail = donatorEmail;
        this.isAnonymous = isAnonymous;
        this.phoneNo = phoneNo;
    }

    public String getDonatorID() {
        return donatorID;
    }

    public void setDonatorID(String donatorID) {
        this.donatorID = donatorID;
    }

    public String getDonatorName() {
        return donatorName;
    }

    public void setDonatorName(String donatorName) {
        this.donatorName = donatorName;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public boolean isAnonymous() {
        return isAnonymous;
    }

    public void setAnonymous(boolean anonymous) {
        isAnonymous = anonymous;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getDonatorEmail() {
        return donatorEmail;
    }

    public void setDonatorEmail(String donatorEmail) {
        this.donatorEmail = donatorEmail;
    }
}
