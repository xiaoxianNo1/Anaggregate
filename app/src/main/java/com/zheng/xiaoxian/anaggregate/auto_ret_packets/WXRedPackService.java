package com.zheng.xiaoxian.anaggregate.auto_ret_packets;

import android.accessibilityservice.AccessibilityService;
import android.app.Notification;
import android.app.PendingIntent;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import java.util.List;
import java.util.Timer;

public class WXRedPackService extends AccessibilityService {

    private int serviceState = -1;

    private static final int FLAG_BACKGROUND = 100;

    private int behavior = -1;

    private static final int FLAG_FOUND = 900;

    private int start = -1;

    private static final int START_BACKGROUND = 100;

    private boolean isHandle = false;
    private boolean isDJLT=false;
    private boolean isRed=false;


    @Override
    public void onCreate(){
        super.onCreate();
    }
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        //当系统检测到与Accessibility服务指定的事件过滤参数
        // 匹配的AccessibilityEvent时调用
        if ( event.getEventType() == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED ) {
            //通知栏事件
            try {
                if(handleNotification(event)){
                    sleep(500);
                    getRedMessage();
                    isHandle=false;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        } else {
            //非通知栏消息
            try {
                //showToast("在首页");或者在聊天页
                if(chatListPage()){
                    handleMessages(event);
                    isDJLT=false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onInterrupt() {
        //当系统想要中断服务提供的反馈时调用
    }

    @Override
    public void onDestroy(){
        //当系统即将关闭辅助功能服务时调用
    }

    //处理通知栏事件
    public boolean handleNotification(AccessibilityEvent event) throws Exception{
        List<CharSequence> texts = event.getText();
        if(!texts.isEmpty()){
            for (CharSequence text : texts){
                String content = text.toString();
                //如果微信红包的提示信息,则模拟点击进入相应的聊天窗口
                if(content.contains("微信红包")){
                    if (event.getParcelableData() != null && event.getParcelableData() instanceof Notification){
                        Notification notification = (Notification) event.getParcelableData();
                        PendingIntent pendingIntent = notification.contentIntent;
                        behavior = FLAG_FOUND;
                        serviceState = FLAG_BACKGROUND;
                        start = START_BACKGROUND;
                        pendingIntent.send();
                        isHandle=true;
                    }
                }
            }
        }
        return isHandle;
    }

    //处理红包消息
    public void handleMessages(AccessibilityEvent event) throws Exception{
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        List<AccessibilityNodeInfo> chatNodes = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/alr");//微信页里标志性的id (+)
        if(chatNodes.isEmpty()){
            //不在微信聊天页
            return;
        }else{
            //在聊天页面
            List<AccessibilityNodeInfo> titleNodes = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/jw");//微信页标题名字
            if ( titleNodes.isEmpty() ) {
                //无法判断类型
            } else {
                //判断标题最后是否是一个括号，括号中是数字，当然最好是用正则
                String title = titleNodes.get(0).getText().toString();
                if ( !TextUtils.isEmpty(title) ) {
                    if ( title.contains("(") ) {
                        int indexLeft = title.lastIndexOf("(");
                        String end = title.substring(indexLeft);
                        end = end.substring(1, end.length() - 1);
                        try {
                            Integer.parseInt(end);
                            //群聊
                            //showToast("消息： 群聊");
                            getRedMessage();
                        } catch ( Exception e ) {
                            //私聊
                            showToast("消息： 私聊");
                        }
                    }else{
                        //私聊 默认私聊
                        getRedMessage();


                    }
                }
            }
        }
    }

    //获取列表是否有红包消息
    public void getRedMessage() throws Exception{
        //在聊天页面
        AccessibilityNodeInfo nodeInfo =  getRootInActiveWindow();
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
                if(isRedPack(node)){//是红包消息
                    node.performAction(AccessibilityNodeInfo.ACTION_CLICK);//点击事件
                    sleep(500);
                    openRedPack();

                    isRed=false;
                }
            }
        }

    }
    //判断是否是红包消息
    public boolean isRedPack(AccessibilityNodeInfo node){
        if ( node != null ) {

            List<AccessibilityNodeInfo> listNodes=node.findAccessibilityNodeInfosByText("微信红包");
            if(listNodes!=null){
                isRed=true;
            }
        }
        return isRed;
    }

    //点击 开 打开红包
    public void openRedPack()throws Exception{
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        //获取开按钮
        List<AccessibilityNodeInfo> kaiNodes = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/cv0");
        //获取 手慢了 提示语句的控件
        List<AccessibilityNodeInfo> slowNodes = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/cuz");
        //获取关闭按钮
        List<AccessibilityNodeInfo> closeNodes = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/cs9");
        //showToast("消息："+kaiNodes);
        if(!kaiNodes.isEmpty()){
            performClick(kaiNodes);
            sleep(3000);
            closePage();
            return;
        }else{
            //手慢了 提示语句的控件
            if(!slowNodes.isEmpty()){
                sleep(1000);
                for(int closeNodei=closeNodes.size()-1;closeNodei>=0;closeNodei--){
                    AccessibilityNodeInfo closeNode=closeNodes.get(closeNodei);
                    closeNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);//点击事件
                }
            }

        }
    }

    //在聊天列表页
    public boolean chatListPage(){
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        List<AccessibilityNodeInfo> listItemNodes = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/b4m");//聊天列表
        if ( listItemNodes.isEmpty() ) {
            //反正不是在首页 不理会
            return isDJLT;
        } else {
            //在首页
            List<AccessibilityNodeInfo> nodes = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/b4q");//最后一条消息
            if(nodes!=null){
                for ( AccessibilityNodeInfo node : nodes ) {
                    if ( node.getText().toString().contains("[微信红包]") ) {
                        //还要判断是否有未读消息
                        AccessibilityNodeInfo parent = node.getParent().getParent().getParent().getParent();
                        if ( parent != null ) {
                            List<AccessibilityNodeInfo> numsNodes =  parent.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/mm");//头像上未读消息的标志
                            if ( !numsNodes.isEmpty() ) {
                                CharSequence text = numsNodes.get(0).getText();
                                if ( text != null ) {
                                    if ( Integer.parseInt(text.toString()) != 0 ) {
                                        //此时才能跳转
                                        try {
                                            parent.performAction(AccessibilityNodeInfo.ACTION_CLICK);//点击事件
                                            isDJLT=true;
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }

                                        //performClick(listItemNodes);
                                    }
                                }
                            }
                            return isDJLT;
                        }
                        return isDJLT;
                    }
                }
            }
        }
        return isDJLT;
    }

    //关闭抢到红包页
    public void closePage(){
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        //获取左上角 返回 按钮
        List<AccessibilityNodeInfo> closePageNodes = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/k4");//抢到红包后的关闭按钮
        showToast("消息");

        if(!closePageNodes.isEmpty()){
            showToast("消息不为空");
           // performClick(closePageNodes);
            for (int j=closePageNodes.size()-1;j>=0;j--){
                AccessibilityNodeInfo accInfo=closePageNodes.get(j);
                accInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);//点击事件
                showToast("点击关闭页");
            }
        }
        return;
    }

    //执行点击
    public void performClick(List<AccessibilityNodeInfo> list) throws Exception{
        for (int j=list.size()-1;j>=0;j--){
            AccessibilityNodeInfo accInfo=list.get(j);
            accInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);//点击事件
        }
    }

    public void showToast(String msgText){
        Toast.makeText(this, msgText, Toast.LENGTH_SHORT).show();
        System.out.println(msgText);
    }

    public void sleep(int millis)throws Exception{
        Thread.sleep(1000);
    }

}
