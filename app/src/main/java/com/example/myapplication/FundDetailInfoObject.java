package com.example.myapplication;

public class FundDetailInfoObject {
    private String infoName;
    private String infoContent;

    public FundDetailInfoObject(String name,String content){
        this.infoName = name;
        this.infoContent = content;
    }

    public String getInfoName(){
        return infoName;
    }

    public String getInfoContent(){
        return infoContent;
    }

}
