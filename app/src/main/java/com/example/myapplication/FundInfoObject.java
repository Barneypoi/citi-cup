package com.example.myapplication;

public class FundInfoObject {
    private String fundName;
    private String fundInfo;

    public FundInfoObject(String name,String info){
        this.fundName = name;
        this.fundInfo = info;
    }

    public String getFundName(){
        return fundName;
    }

    public String getFundInfo(){
        return fundInfo;
    }
}
