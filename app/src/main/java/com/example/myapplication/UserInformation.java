package com.example.myapplication;

public class UserInformation {
    static UserInformation shared = new UserInformation();
    private String UserId;
    public String getUserId(){return UserId;}
    public void setUserId(String UserId){this.UserId = UserId;}
}
