package com.example.xiaoxian.autoredpackhelper;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.SyncStatusObserver;
import android.os.Build;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import java.util.List;

/**
 * 简介：微信抢红包服务类
 * 作者：郑现文
 * 创建时间：2019/1/23/0023 09:36
 **/
public class WXRedPackService extends AccessibilityService {
    private int serviceState = -1;
    int FWZT=-1;//服务状态 小于等于0为关闭 大于0开启
    int FTZLZT=-1;//非通知栏状态 小于等于0为关闭 大于0为开启

    @Override
    public void onCreate(){
        super.onCreate();
    }

    private void showLog(int i,String string){
        System.out.println("消息"+i+string);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event)  {
        //当系统检测到与Accessibility服务指定的事件过滤参数
        // 匹配的AccessibilityEvent时调用
        if ( event.getEventType() == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED ) {
            //通知栏事件
            showLog(1,"通知栏");
            triggerNoticeBar(event);
        }else {
            //非通知栏消息
            try {
                if(serviceState<=0){
                    triggerWinChange(event);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            serviceState=-1;
        }

    }

    @Override
    public void onInterrupt() {
        //服务中断，如授权关闭或者将服务杀死

    }

    @Override
    protected void onServiceConnected(){
        AccessibilityServiceInfo serviceInfo = new AccessibilityServiceInfo();
        serviceInfo.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;
        serviceInfo.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        serviceInfo.packageNames = new String[]{"com.tencent.mm"};
        serviceInfo.notificationTimeout=100;
        setServiceInfo(serviceInfo);
       /* //设置关心的事件类型
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.eventTypes = AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED |
                AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED |
                AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        info.notificationTimeout = 100;//两个相同事件的超时时间间隔
        setServiceInfo(info);*/
    }

    //触发通知栏
    private void triggerNoticeBar(AccessibilityEvent event){
        List<CharSequence> texts = event.getText();
        if(!texts.isEmpty()){
            for(CharSequence text : texts){
                String content = text.toString();
                //如果微信红包的提示信息,则模拟点击进入相应的聊天窗口
                if(content.contains("微信红包")) {
                    if(event.getParcelableData() == null || !(event.getParcelableData().toString().isEmpty())) {

                    }
                    Notification notification = (Notification)event.getParcelableData();
                    PendingIntent pendingIntent =notification.contentIntent;
                    try{
                        pendingIntent.send();
                    }catch
                    (PendingIntent.CanceledException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    //窗口改变
    private  void triggerWinChange(AccessibilityEvent event) throws Exception{
        AccessibilityNodeInfo nodeInfo =  getRootInActiveWindow();
        List<AccessibilityNodeInfo> chatListNodes = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/b5m");//聊天列表
        List<AccessibilityNodeInfo> titleNodes = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/k3");//微信页标题名字

        //获取开按钮
        List<AccessibilityNodeInfo> kaiNodes = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/cyf");
        //获取 手慢了 提示语句的控件
        List<AccessibilityNodeInfo> slowNodes = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/cye");
        //获取关闭按钮
        List<AccessibilityNodeInfo> closeNodes = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/cv0");

        //在聊天列表页
        if(!chatListNodes.isEmpty()){
            List<AccessibilityNodeInfo> lastMsgNodes = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/b5q");//最后一条消息
            if(lastMsgNodes!=null){
                for ( AccessibilityNodeInfo lastMsgNode : lastMsgNodes ) {
                    if ( lastMsgNode.getText().toString().contains("[微信红包]") ) {
                        //还要判断是否有未读消息
                        AccessibilityNodeInfo parent = lastMsgNode.getParent();
                        if ( parent != null ) {
                            List<AccessibilityNodeInfo> unReadNodes =  parent.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/mv");//头像上未读消息的标志
                            if ( !unReadNodes.isEmpty() ) {
                                CharSequence text = unReadNodes.get(0).getText();
                                if ( text != null ) {
                                    if ( Integer.parseInt(text.toString()) != 0 ) {
                                        //此时才能跳转
                                        try {
                                            System.out.println("消息" + parent.toString());
                                            parent.performAction(AccessibilityNodeInfo.ACTION_CLICK);//点击事件
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }else

        if(!titleNodes.isEmpty()){
            List<AccessibilityNodeInfo> redPackList = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/aou");//红包消息id
            if ( redPackList == null )
                return;
            if ( redPackList.isEmpty() ){

            }else {//有 但是要检查是不是红包
                for(int i = redPackList.size() - 1; i >= 0; i-- ){
                    AccessibilityNodeInfo redPack = redPackList.get(i);
                    if(redPack.toString()!=null){
                        List<AccessibilityNodeInfo> listNodes=redPack.findAccessibilityNodeInfosByText("微信红包");
                        if(!listNodes.isEmpty()){
                            //是红包消息 再判断是否已被领取
                            List<AccessibilityNodeInfo> lqListNodes=redPack.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/aq6");//com.tencent.mm:id/ape
                            if(lqListNodes.isEmpty()) {//红包还没被领取
                                redPack.performAction(AccessibilityNodeInfo.ACTION_CLICK);//点击事件
                            }
                        }
                    }
                }

            }
        }else if(!kaiNodes.isEmpty()){
            for(AccessibilityNodeInfo kaiNode:kaiNodes){
                kaiNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);//点击事件
            }
        }else if(!slowNodes.isEmpty()){
            for (AccessibilityNodeInfo slowNode:slowNodes){
                slowNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);//点击事件
            }
        }
    }

    /**
     * 关闭红包详情界面,实现自动返回聊天窗口
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void close() {
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        if (nodeInfo != null) {
            //为了演示,直接查看了关闭按钮的id
            List<AccessibilityNodeInfo> infos = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/cv0");
            nodeInfo.recycle();
            for (AccessibilityNodeInfo item : infos) {
                item.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
        }
    }

    /**
     * 模拟点击,拆开红包
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void openPacket() {
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        if (nodeInfo != null) {
            //获取开按钮
            List<AccessibilityNodeInfo> kaiNodes = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/cyf");
            //获取 手慢了 提示语句的控件
            List<AccessibilityNodeInfo> slowNodes = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/cuz");
            //获取关闭按钮
            List<AccessibilityNodeInfo> closeNodes = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/cs9");

            if(!kaiNodes.isEmpty()){
                showLog(1,"消息");
                for(AccessibilityNodeInfo kaiNode:kaiNodes){
                    showLog(2,kaiNode.toString());
                    kaiNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                }
                //System.out.println("消息 开红包");

                /*if(!closePageNodes.isEmpty()){
                    performClick(closePageNodes);
                }*/
                //FTZLZT =-1;
            }

            if(!slowNodes.isEmpty()){
                //performClick(closeNodes);
                //FTZLZT =-1;
            }
            /*nodeInfo.recycle();
            for (AccessibilityNodeInfo item : kaiNodes) {
                item.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }*/
        }
    }

    /**
     * 模拟点击,打开抢红包界面
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void getPacket() {
        /*AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        AccessibilityNodeInfo node = recycle(rootNode);
        node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        AccessibilityNodeInfo parent = node.getParent();

        while (parent != null) {
            showLog(6,parent.toString());
            if (parent.isClickable()) {
                parent.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                break;
            }
            parent = parent.getParent();
        }
        */
        AccessibilityNodeInfo nodeInfo =  getRootInActiveWindow();
        List<AccessibilityNodeInfo> listNode = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/aou");//红包消息id
        if ( listNode == null )
            return;
        if ( listNode.isEmpty() ){

        }else {
            //有 但是要检查是不是红包
            for (int i = listNode.size() - 1; i >= 0; i--) {
                AccessibilityNodeInfo node1 = listNode.get(i);
            //for(AccessibilityNodeInfo node1: listNode){
                if ( node1 != null ) {

                    List<AccessibilityNodeInfo> listNodes=node1.findAccessibilityNodeInfosByText("微信红包");
                    if(listNodes!=null){
                        List<AccessibilityNodeInfo> lqListNodes=node1.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/ape");//com.tencent.mm:id/ape
                        if(lqListNodes.toString()=="[]") {//红包还没被领取
                            node1.performAction(AccessibilityNodeInfo.ACTION_CLICK);//点击事件
                        }

                    }
                }
            }
        }



    }

    /**
     * 递归查找当前聊天窗口中的红包信息
     *
     * 聊天窗口中的红包都存在"领取红包"一词,因此可根据该词查找红包
     *
     * @param node
     */
    public AccessibilityNodeInfo recycle(AccessibilityNodeInfo node) {
        if (node.getChildCount() == 0) {

            if (node.getText() != null) {
                if ("微信红包".equals(node.getText().toString())) {
                    return node;
                }
            }
        } else {
            for (int i = 0; i < node.getChildCount(); i++) {
                if (node.getChild(i) != null) {
                    recycle(node.getChild(i));
                }
            }
        }
        return node;
    }



    //执行点击
    public void performClick(List<AccessibilityNodeInfo> list) throws Exception{
        /*for (int j=list.size()-1;j>=0;j--){
            AccessibilityNodeInfo accInfo=list.get(j);
            accInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);//点击事件
        }*/
        AccessibilityNodeInfo accInfo=list.get(0);
        System.out.println("消息 点击 开");
        accInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);//点击事件
    }

}
