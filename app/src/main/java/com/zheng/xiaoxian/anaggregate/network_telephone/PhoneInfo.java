package com.zheng.xiaoxian.anaggregate.network_telephone;

public class PhoneInfo {
    private String name;
    private String number;

    public PhoneInfo(String name,String number) {
        setNumber(number);
        setName(name);
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
}
