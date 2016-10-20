package models;

import models.response.GenerateTokenResponse;
import models.response.Meta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sabih Ahmed on 14-Jun-16.
 */
public class User implements Serializable{
    public Meta meta;
    public Data data = new Data();
    public GenerateTokenResponse userToken = new GenerateTokenResponse();
    public static User user;

    private User(){}

    public static User getInstance()
    {
        if (user == null)
            return  user = new User();
        else
            return user;
    }
    public static User getInstance(User newUser)
    {
        if (newUser != null)
            return  user = newUser;
        else
            return getInstance();
    }

    public User(Meta meta, Data data) {
        this.meta = meta;
        this.data = data;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }


    public GenerateTokenResponse getUserToken() {
        return userToken;
    }

    public void setUserToken(GenerateTokenResponse userToken) {
        this.userToken = userToken;
    }

    public class Data implements Serializable{
        public String id;
        public Object provider;
        public Object avatar;
        public String username;
        public String fullname;
        public String email;
        public String password;
        public Object interest;
        public String userType;
        public Object key;
        public Object activationURL;
        public Boolean activate;
        public List<String> roles = new ArrayList<String>();
        public Object tokens;
        public Object location;
        public Object currentlocation;
        public Boolean isavailable;
        public String mobilenum;
        public List<Object> agentReference = new ArrayList<Object>();
        public Object clientId;
        public Object clientName;
        public Object clientSecret;
        public String guestUserId;


        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
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

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getMobilenum() {
            return mobilenum;
        }

        public void setMobilenum(String mobilenum) {
            this.mobilenum = mobilenum;
        }

        public Boolean getIsavailable() {
            return isavailable;
        }

        public void setIsavailable(Boolean isavailable) {
            this.isavailable = isavailable;
        }
    }

}
