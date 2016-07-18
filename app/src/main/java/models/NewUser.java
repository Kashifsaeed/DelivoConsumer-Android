package models;

/**
 * Created by Sabih Ahmed on 13-Jun-16.
 */
public class NewUser {

    String username;
    String mobilenum;
    String email;
    String fullname;

    public NewUser(String username, String mobilenum, String email, String fullname) {
        this.username = username;
        this.mobilenum = mobilenum;
        this.email = email;
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobilenum() {
        return mobilenum;
    }

    public void setMobilenum(String mobilenum) {
        this.mobilenum = mobilenum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
