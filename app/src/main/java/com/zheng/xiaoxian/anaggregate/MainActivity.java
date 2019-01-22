package com.zheng.xiaoxian.anaggregate;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.zheng.xiaoxian.anaggregate.auto_ret_packets.WXRedPackActivity;
import com.zheng.xiaoxian.anaggregate.db_helper.apply_permissions.PermissionUtil;
import com.zheng.xiaoxian.anaggregate.hismile_signIn.HismileMainActivity;
import com.zheng.xiaoxian.anaggregate.mobile_no_track.MobileNoTrackActivity;
import com.zheng.xiaoxian.anaggregate.network_telephone.NetwordTelephoneActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnMobileNoTrack,btnNetwordTelephone,btnHismileSignIn,btnWXRedPack;

    Intent intent;

    private final int REQUEST_CODE = 1;//请求码
    //储存所有权限
    String[] allpermissions=new String[]{
            Manifest.permission.CALL_PHONE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        //初始化并发起权限申请
        PermissionUtil.getPermission(this,REQUEST_CODE);
        //初始化并发起权限申请结束

        btnMobileNoTrack=(Button)findViewById( R.id.btnMobileNoTrack );
        btnNetwordTelephone=(Button)findViewById( R.id.btnNetwordTelephone );
        btnHismileSignIn=(Button)findViewById( R.id.btnHismileSignIn );
        btnWXRedPack=(Button)findViewById(R.id.btnWXRedPack);
        btnMobileNoTrack.setOnClickListener( this );
        btnNetwordTelephone.setOnClickListener( this );
        btnHismileSignIn.setOnClickListener( this );
        btnWXRedPack.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnMobileNoTrack:
                intent=new Intent( MainActivity.this, MobileNoTrackActivity.class );
                startActivity( intent );
                break;

            case R.id.btnNetwordTelephone:
                intent=new Intent( MainActivity.this, NetwordTelephoneActivity.class );
                startActivity( intent );
                break;
            case R.id.btnHismileSignIn:
                intent=new Intent( MainActivity.this, HismileMainActivity.class );
                startActivity( intent );
                break;
            case R.id.btnWXRedPack:
                intent=new Intent(MainActivity.this, WXRedPackActivity.class);
                startActivity(intent);
                break;
        }
    }
}
