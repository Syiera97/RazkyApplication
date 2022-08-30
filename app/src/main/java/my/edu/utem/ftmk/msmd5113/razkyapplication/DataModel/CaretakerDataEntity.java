package my.edu.utem.ftmk.msmd5113.razkyapplication.DataModel;

public class CaretakerDataEntity {
    public String orphanageName;
    public String caretakerName;
    public String email;
    public String phone;

    public CaretakerDataEntity() {
    }

    public CaretakerDataEntity(String orphanageName, String caretakerName, String email, String phone) {
        this.orphanageName = orphanageName;
        this.caretakerName = caretakerName;
        this.email = email;
        this.phone = phone;
    }

    public String getOrphanageName() {
        return orphanageName;
    }

    public void setOrphanageName(String orphanageName) {
        this.orphanageName = orphanageName;
    }

    public String getCaretakerName() {
        return caretakerName;
    }

    public void setCaretakerName(String caretakerName) {
        this.caretakerName = caretakerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
