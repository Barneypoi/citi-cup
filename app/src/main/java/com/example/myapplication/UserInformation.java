package com.example.myapplication;

//用于储存当前使用软件的用户信息
public class UserInformation {
    static UserInformation shared = new UserInformation();
    private String UserId;
    public String getUserId(){return UserId;}
    public void setUserId(String UserId){this.UserId = UserId;}
}
