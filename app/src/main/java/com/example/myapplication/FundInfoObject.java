package com.example.myapplication;

public class FundInfoObject {
    private String fundName;
    private String fundIncre;
    private String fundId;
    private String fundType;
    private String fundRisk;
    private String fundScale;
    private String fundEstablishTime;
    private String fundCompany;
    private String fundManager;
    private String fundAlloc;
    private String fundNetweigh;

    //更多信息接着定义----用于基金详细信息界面


    public FundInfoObject(String name,String incre){
        this.fundName = name;
        this.fundIncre = incre;
    }
    //主界面需要的构造器
    public FundInfoObject(String name, String incre, String id, String type){
        this.fundName = name;
        this.fundIncre = incre;
        this.fundId = id;
        this.fundType = type;
    }
    //基金基本信息界面额外需要的构造器
    public FundInfoObject(String name, String risk, String netweigh){
        this.fundName = name;
        this.fundRisk = risk;
        this.fundNetweigh = netweigh;
    }
    //基金详细信息界面额外需要的构造器
    public FundInfoObject(String name, String scale, String estTime, String company, String manager, String alloc){
        this.fundName = name;
        this.fundScale = scale;
        this.fundEstablishTime = estTime;
        this.fundCompany = company;
        this.fundManager = manager;
        this.fundAlloc = alloc;
    }
    //基金净值（排序）界面额外需要的构造器
    public FundInfoObject(String name, String netweigh, String incre,int i){
        this.fundName = name;
        this.fundNetweigh = netweigh;
        this.fundIncre = incre;
    }

    public String getFundName(){
        return fundName;
    }

    public String getFundIncre(){
        return fundIncre;
    }

    public String getFundId(){
        return fundId;
    }

    public String getFundType(){
        return fundType;
    }

    public String getFundNetweigh() {
        return fundNetweigh;
    }

    public String getFundRisk() {
        return fundRisk;
    }

    public String getFundScale() {
        return fundScale;
    }

    public String getFundEstablishTime() {
        return fundEstablishTime;
    }

    public String getFundCompany() {
        return fundCompany;
    }

    public String getFundManager() {
        return fundManager;
    }

    public String getFundAlloc() {
        return fundAlloc;
    }

    public float getFundIncreFloat()
    {
        if(getFundIncre().startsWith("0")||getFundIncre().equals(""))
            return 0;
        if(getFundIncre().startsWith("+"))
        {
            String str = getFundIncre().substring(1,getFundIncre().length()-1);
            return Float.parseFloat(str);
        }else
        {
            String str = getFundIncre().substring(1,getFundIncre().length()-1);
            return -Float.parseFloat(str);
        }
    }

}
