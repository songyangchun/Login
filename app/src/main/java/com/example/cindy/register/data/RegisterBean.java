package com.example.cindy.register.data;

import org.litepal.crud.LitePalSupport;

public class RegisterBean extends LitePalSupport{
    private  int id;
    private String phone;      //手机号
    private String password;    //密码

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
