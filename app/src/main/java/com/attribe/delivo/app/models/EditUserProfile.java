package com.attribe.delivo.app.models;

/**
 * Created by Maaz on 8/15/2016.
 */
public class EditUserProfile {

    private String fullname;
    private String email;

    public EditUserProfile(String fullname, String email) {
        this.fullname = fullname;
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
