package com.attribe.delivo.app.models.response;

/**
 * Author: Uzair Qureshi
 * Date:  6/21/17.
 * Description:
 */

public class UserProfile
{
    private int id;

    private int company_id;

    private String phone;

    private String updated_at;

    private String email;

    private String name;

    private String created_at;

    private int customer_id;
    private boolean isLogin=true;
    private static UserProfile instance;
    public UserProfile(){

    }

    public static UserProfile getInstance()
    {
        if(instance==null){
            instance=new UserProfile();
        }
        return instance;


    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }


    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }
}
