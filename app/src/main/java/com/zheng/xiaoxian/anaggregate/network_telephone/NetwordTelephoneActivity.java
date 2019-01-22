package com.zheng.xiaoxian.anaggregate.network_telephone;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.zheng.xiaoxian.anaggregate.db_helper.GetNumber;
import com.zheng.xiaoxian.anaggregate.db_helper.apply_permissions.PermissionUtil;
import com.zheng.xiaoxian.anaggregate.network_telephone.impl.NetWordTelAdapter;
import com.zheng.xiaoxian.anaggregate.R;

public class NetwordTelephoneActivity extends AppCompatActivity  {

    private ListView lvNetwordTelephone;
    private NetWordTelAdapter adapter;

    public final int REQUEST_CODE = 2;//请求码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_netword_telephone );

        //初始化并发起权限申请
        PermissionUtil permissionUtil=new PermissionUtil();
        if (permissionUtil.isOwnPermisson(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            //如果已经拥有改权限
            Log.i("request","own");
        } else {
            //没有改权限，需要进行请求
            permissionUtil.requestPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, REQUEST_CODE);
        }
        //初始化并发起权限申请结束

        GetNumber.getNumber(this);
        /*lvNetwordTelephone=(ListView)findViewById( R.id.lvNetwordTelephone );
        adapter=new NetWordTelAdapter( GetNumber.lists,this );
        lvNetwordTelephone.setAdapter( adapter );*/
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i("request", "success");
                } else {
                    Log.i("request", "failed");
                }
                return;
            }
        }

    }

}
