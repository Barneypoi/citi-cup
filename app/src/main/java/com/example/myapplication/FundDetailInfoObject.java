package com.example.myapplication;

//用于在基金详情界面显示信息的基金类
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
