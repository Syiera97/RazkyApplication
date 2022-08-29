package my.edu.utem.ftmk.msmd5113.razkyapplication.DataModel;

public class UserComments {
    public String username;
    public String dateTime;
    public String comment;

    public UserComments() {
    }

    public UserComments(String username, String dateTime, String comment) {
        this.username = username;
        this.dateTime = dateTime;
        this.comment = comment;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
