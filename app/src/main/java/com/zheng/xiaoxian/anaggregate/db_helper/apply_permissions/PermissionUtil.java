package com.zheng.xiaoxian.anaggregate.db_helper.apply_permissions;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;


/**
 * 简介：动态权限工具类
 * 作者：郑现文
 * 创建时间：2019/1/16/0016 14:30
 **/
public class PermissionUtil {
    /**
     * 返回时、否拥有改权限
     * @param activity
     * @param permission
     * @return
     */
    public static boolean isOwnPermisson(Activity activity, String permission){
        return ContextCompat.checkSelfPermission(activity,permission)== PackageManager.PERMISSION_GRANTED;
    }
    public static void requestPermission(Activity activity, String permission,int requestCode) {
        if (ContextCompat.checkSelfPermission(activity,permission)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,permission)) {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(activity,
                        new String[]{permission,
                                /*Manifest.permission.READ_CONTACTS,
                                Manifest.permission.CALL_PHONE*/
                        },//需要请求多个权限，可以在这里添加
                        requestCode);

            }
        }
    }

    public static void getPermission(Activity activity,int REQUEST_CODE){
        //初始化并发起权限申请
        PermissionUtil permissionUtil=new PermissionUtil();
        if (permissionUtil.isOwnPermisson(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            //如果已经拥有改权限
            Log.i("request","own");
        } else {
            //没有改权限，需要进行请求
            permissionUtil.requestPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE, REQUEST_CODE);
        }
        //初始化并发起权限申请结束
    }

}
