package com.example.xiaoxian.autoredpackhelper;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.SyncStatusObserver;
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
    int FWZT=-1;//服务状态 小于等于0为关闭 大于0开启
    int FTZLZT=-1;//非通知栏状态 小于等于0为关闭 大于0为开启

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event)  {

        int eventType = event.getEventType();
        if(FWZT<=0){
            FWZT=1;
            switch (eventType){
                case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                    //TODO处理通知栏来的事件类型
                    triggerNoticeBar(event);
                    FWZT=- 1;
                    break;
                case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                    FWZT=- 1;
                    break;
                    //TODO
                case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                    //TODO窗口出现变化的时候处理
                    try{
                        triggerWinChange(event);
                    }catch (Exception e){

                    }
                    //Toast.makeText(this, "窗口改变触发", Toast.LENGTH_SHORT).show();

                    FWZT=- 1;
                    break;
            }

        }

    }

    @Override
    public void onInterrupt() {
        //服务中断，如授权关闭或者将服务杀死

    }

    @Override
    protected void onServiceConnected(){
        //设置关心的事件类型
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.eventTypes = AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED |
                AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED |
                AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        info.notificationTimeout = 100;//两个相同事件的超时时间间隔
        setServiceInfo(info);
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
    private void triggerWinChange(AccessibilityEvent event) throws Exception{
        if(FTZLZT<=0){
            FTZLZT =1;
            Thread.sleep(200);
            AccessibilityNodeInfo nodeInfo =  getRootInActiveWindow();
            List<AccessibilityNodeInfo> chatListNodes = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/b4m");//聊天列表
            List<AccessibilityNodeInfo> titleNodes = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/jw");//微信页标题名字

            /*//获取开按钮
            List<AccessibilityNodeInfo> kaiNodes = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/cv0");
            //获取 手慢了 提示语句的控件
            List<AccessibilityNodeInfo> slowNodes = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/cuz");
            //获取关闭按钮
            List<AccessibilityNodeInfo> closeNodes = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/cs9");*/
            //获取左上角 返回 按钮
            List<AccessibilityNodeInfo> closePageNodes = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/k4");//抢到红包后的关闭按钮

            //在聊天列表页
            if(!chatListNodes.isEmpty()){
                List<AccessibilityNodeInfo> lastMsgNodes = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/b4q");//最后一条消息
                if(lastMsgNodes!=null){
                    for ( AccessibilityNodeInfo lastMsgNode : lastMsgNodes ) {
                        if ( lastMsgNode.getText().toString().contains("[微信红包]") ) {
                            //还要判断是否有未读消息
                            AccessibilityNodeInfo parent = lastMsgNode.getParent();
                            if ( parent != null ) {
                                List<AccessibilityNodeInfo> unReadNodes =  parent.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/mm");//头像上未读消息的标志
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
               // FTZLZT =-1;
            }

            //在聊天页面
            if(!titleNodes.isEmpty()){

                List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/ao4");//红包消息id
                if ( list == null )
                    return;
                if ( list.isEmpty() ){
                    //showToast("消息：没有 直接返回");
                    //没有 直接返回
           /* List<AccessibilityNodeInfo> backNodes = nodeInfo.findAccessibilityNodeInfosByViewId();//("com.tencent.mm:id/a78");
            if ( !backNodes.isEmpty() ) {
                //AccessibilityHelper.performClick(backNodes.get(0));
            }*/
                }else {
                    //有 但是要检查是不是红包
                    for(int i = list.size() - 1; i >= 0; i-- ){
                        AccessibilityNodeInfo node = list.get(i);
                        if(node!=null){
                            List<AccessibilityNodeInfo> listNodes=node.findAccessibilityNodeInfosByText("微信红包");
                            if(listNodes!=null){
                                //是红包消息 再判断是否已被领取
                                List<AccessibilityNodeInfo> lqListNodes=node.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/ape");//com.tencent.mm:id/ape
                                if(lqListNodes.toString()=="[]"){//红包还没被领取
                                    node.performAction(AccessibilityNodeInfo.ACTION_CLICK);//点击事件

                                    Thread.sleep(200);
                                    AccessibilityNodeInfo nodeInfo2 =  getRootInActiveWindow();
                                    //获取开按钮
                                    List<AccessibilityNodeInfo> kaiNodes = nodeInfo2.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/cv0");
                                    //获取 手慢了 提示语句的控件
                                    List<AccessibilityNodeInfo> slowNodes = nodeInfo2.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/cuz");
                                    //获取关闭按钮
                                    List<AccessibilityNodeInfo> closeNodes = nodeInfo2.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/cs9");

                                    if(!kaiNodes.isEmpty()){
                                        //System.out.println("消息 开红包");
                                        performClick(kaiNodes);
                                        if(!closePageNodes.isEmpty()){
                                            performClick(closePageNodes);
                                        }
                                        //FTZLZT =-1;
                                    }

                                    if(!slowNodes.isEmpty()){
                                        performClick(closeNodes);
                                        //FTZLZT =-1;
                                    }

                                }else{
                                    //红包已被领取
                                    //System.out.println("消息2");
                                }
                            }
                        }
                    }
                    //FTZLZT =-1;
                }
                /*//判断标题最后是否是一个括号，括号中是数字，当然最好是用正则
                String title = titleNodes.get(0).getText().toString();
                if ( !TextUtils.isEmpty(title) ) {
                    if (title.contains("(")) {
                        int indexLeft = title.lastIndexOf("(");
                        String end = title.substring(indexLeft);
                        end = end.substring(1, end.length() - 1);
                        try {
                            Integer.parseInt(end);
                            //群聊
                            System.out.println("消息1：群聊");
                        } catch (Exception e) {
                            //私聊
                            System.out.println("消息2：私聊");
                        }
                    } else {
                        //私聊 默认私聊
                        System.out.println("消息3：私聊 默认私聊");
                    }
                }*/
            }





            FTZLZT =-1;
        }
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
