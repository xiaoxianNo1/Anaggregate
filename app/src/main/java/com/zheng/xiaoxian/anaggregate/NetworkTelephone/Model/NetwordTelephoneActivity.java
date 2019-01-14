package com.zheng.xiaoxian.anaggregate.NetworkTelephone.Model;

import android.Manifest;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.zheng.xiaoxian.anaggregate.DBHelper.GetNumber;
import com.zheng.xiaoxian.anaggregate.DBHelper.PermissionHelper;
import com.zheng.xiaoxian.anaggregate.DBHelper.PermissionInterface;
import com.zheng.xiaoxian.anaggregate.NetworkTelephone.Model.impl.NetWordTelAdapter;
import com.zheng.xiaoxian.anaggregate.R;

public class NetwordTelephoneActivity extends AppCompatActivity implements PermissionInterface {

    private ListView lvNetwordTelephone;
    private NetWordTelAdapter adapter;


    private PermissionHelper mPermissionHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_netword_telephone );
        GetNumber.getNumber(this);
        lvNetwordTelephone=(ListView)findViewById( R.id.lvNetwordTelephone );
        adapter=new NetWordTelAdapter( GetNumber.lists,this );
        lvNetwordTelephone.setAdapter( adapter );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(mPermissionHelper.requestPermissionsResult(requestCode, permissions, grantResults)){
            //权限请求结果，并已经处理了该回调
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public int getPermissionsRequestCode() {
        //设置权限请求requestCode，只有不跟onRequestPermissionsResult方法中的其他请求码冲突即可。
        return 10000;
    }

    @Override
    public String[] getPermissions() {
        //设置该界面所需的全部权限
        return new String[]{
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.CALL_PHONE
        };
    }

    @Override
    public void requestPermissionsSuccess() {
        //权限请求用户已经全部允许
        initViews();
    }

    @Override
    public void requestPermissionsFail() {
        //权限请求不被用户允许。可以提示并退出或者提示权限的用途并重新发起权限申请。
      //  finish();
    }

    private void initViews(){
        //已经拥有所需权限，可以放心操作任何东西了

    }
}
