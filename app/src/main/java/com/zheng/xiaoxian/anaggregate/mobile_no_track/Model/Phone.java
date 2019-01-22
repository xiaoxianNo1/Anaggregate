package com.zheng.xiaoxian.anaggregate.mobile_no_track.Model;

public class Phone {
    private String telString;//手机号
    private String province;//省
    private String catName;//运营商
    private String carrier;//归属运营商
    private String click;//查询次数

    public String getClick() {
        return click;
    }

    public void setClick(String click) {
        this.click = click;
    }



    public Phone(){

    }
    public Phone(String telString,String province,String catName,String carrier,String click){
        this.telString=telString;
        this.province=province;
        this.catName=catName;
        this.carrier=carrier;
        this.click=click;
    }

    public String getTelString() {
        return telString;
    }

    public void setTelString(String telString) {
        this.telString = telString;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }
}
