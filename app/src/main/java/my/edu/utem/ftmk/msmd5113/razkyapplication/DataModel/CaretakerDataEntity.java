package my.edu.utem.ftmk.msmd5113.razkyapplication.DataModel;

public class CaretakerDataEntity {
    public String orphanageName;
    public String caretakerName;
    public String email;
    public String phone;

    public CaretakerDataEntity(String orphanageName, String caretakerName, String email,  String phone) {
        this.orphanageName = orphanageName;
        this.caretakerName = caretakerName;
        this.email = email;
        this.phone = phone;
    }
}
