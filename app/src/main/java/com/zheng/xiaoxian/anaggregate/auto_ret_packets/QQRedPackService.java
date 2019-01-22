package com.zheng.xiaoxian.anaggregate.auto_ret_packets;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;

/**
 * 简介：qq抢红包服务
 * 作者：郑现文
 * 创建时间：2019/1/17/0017 14:38
 **/
public class QQRedPackService extends AccessibilityService {
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
    //接收事件,如触发了通知栏变化、界面变化等
    }

    @Override
    public void onInterrupt() {
    //服务中断，如授权关闭或者将服务杀死
    }
}
