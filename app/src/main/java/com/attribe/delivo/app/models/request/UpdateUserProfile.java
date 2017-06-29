package com.attribe.delivo.app.models.request;

/**
 * Author: Uzair Qureshi
 * Date:  6/22/17.
 * Description:
 */

public class UpdateUserProfile {

    private String name ;
    private String email;


    public UpdateUserProfile(String name, String email) {
        this.name = name;
        this.email = email;
    }

}
