package com.zheng.xiaoxian.anaggregate.auto_ret_packets;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

/**
 * 简介：qq抢红包服务
 * 作者：郑现文
 * 创建时间：2019/1/17/0017 14:38
 **/
public class QQRedPackService extends AccessibilityService {
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
    //接收事件,如触发了通知栏变化、界面变化等
        AccessibilityNodeInfo nodeInfo =  getRootInActiveWindow();
        int eventType = event.getEventType();
        switch (eventType){
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                //TODO处理通知栏来的事件类型
                Toast.makeText(this, "通知栏触发", Toast.LENGTH_SHORT).show();
                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                //TODO
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                //TODO窗口出现变化的时候处理
                //Toast.makeText(this, "窗口改变触发", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onInterrupt() {
    //服务中断，如授权关闭或者将服务杀死
    }
}
