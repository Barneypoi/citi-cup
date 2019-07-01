package com.example.myapplication;

public class FundInfoObject {
    private String fundName;
    private String fundInfo;
    //用于listitem_main中
    private String fundOtherInfo1;
    private String fundOtherInfo2;

    //更多信息接着定义----用于基金详细信息界面


    public FundInfoObject(String name,String info){
        this.fundName = name;
        this.fundInfo = info;
    }

    public FundInfoObject(String name, String info, String othInfo1, String othInfo2){
        this.fundName = name;
        this.fundInfo = info;
        this.fundOtherInfo1 = othInfo1;
        this.fundOtherInfo2 = othInfo2;
    }

    public String getFundName(){
        return fundName;
    }

    public String getFundInfo(){
        return fundInfo;
    }

    public String getFundOtherInfo1(){
        return fundOtherInfo1;
    }

    public String getFundOtherInfo2(){
        return fundOtherInfo2;
    }

}
