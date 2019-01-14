package com.zheng.xiaoxian.anaggregate.MobileNoTrack.Model;

public class Phone {
    String telString;//手机号
    String province;//省
    String catName;//运营商
    String carrier;//归属运营商
    String click;//查询次数

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
        carrier = carrier;
    }
}
