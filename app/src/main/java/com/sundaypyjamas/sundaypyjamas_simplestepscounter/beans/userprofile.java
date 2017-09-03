package com.sundaypyjamas.sundaypyjamas_simplestepscounter.beans;

/**
 * Created by saumyamehta on 9/2/17.
 */

public class userprofile {
    private String email, phonenumber,password;

    public userprofile(String email, String phonenumber, String password) {
        this.email = email;
        this.phonenumber = phonenumber;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
