package com.zheng.xiaoxian.anaggregate.auto_ret_packets.Model;

/**
 * 简介：构造方法
 * 作者：郑现文
 * 创建时间：2019/1/22/0022 11:22
 **/
public class WXRedPack {

    //微信页里标志性的id (+)
    private String WXIconicId;

    //微信页标题名字
    private String WXTitleId;

    //红包消息id
    private String WXRedId;

    //获取 开 按钮
    private String WXOpenId;

    //获取 手慢了 提示语句的控件
    private String WXBothId;

    //获取 关闭 按钮 红包下方的 X
    private String WXRedClose;

    //聊天列表
    private String WXChatList;

    //列表显示的信息
    private String WXEndMsg;

    //未读消息标志
    private String WXUnreadMsg;

    //抢到红包后的关闭按钮
    private String WXClosePage;

    private int WXSleepTime;

    public String getWXIconicId() {
        return WXIconicId;
    }

    public void setWXIconicId(String WXIconicId) {
        this.WXIconicId = WXIconicId;
    }

    public String getWXTitleId() {
        return WXTitleId;
    }

    public void setWXTitleId(String WXTitleId) {
        this.WXTitleId = WXTitleId;
    }

    public String getWXRedId() {
        return WXRedId;
    }

    public void setWXRedId(String WXRedId) {
        this.WXRedId = WXRedId;
    }

    public String getWXOpenId() {
        return WXOpenId;
    }

    public void setWXOpenId(String WXOpenId) {
        this.WXOpenId = WXOpenId;
    }

    public String getWXBothId() {
        return WXBothId;
    }

    public void setWXBothId(String WXBothId) {
        this.WXBothId = WXBothId;
    }

    public String getWXRedClose() {
        return WXRedClose;
    }

    public void setWXRedClose(String WXRedClose) {
        this.WXRedClose = WXRedClose;
    }

    public String getWXChatList() {
        return WXChatList;
    }

    public void setWXChatList(String WXChatList) {
        this.WXChatList = WXChatList;
    }

    public String getWXEndMsg() {
        return WXEndMsg;
    }

    public void setWXEndMsg(String WXEndMsg) {
        this.WXEndMsg = WXEndMsg;
    }

    public String getWXUnreadMsg() {
        return WXUnreadMsg;
    }

    public void setWXUnreadMsg(String WXUnreadMsg) {
        this.WXUnreadMsg = WXUnreadMsg;
    }

    public String getWXClosePage() {
        return WXClosePage;
    }

    public void setWXClosePage(String WXClosePage) {
        this.WXClosePage = WXClosePage;
    }

    public int getWXSleepTime() {
        return WXSleepTime;
    }

    public void setWXSleepTime(int WXSleepTime) {
        this.WXSleepTime = WXSleepTime;
    }
}
