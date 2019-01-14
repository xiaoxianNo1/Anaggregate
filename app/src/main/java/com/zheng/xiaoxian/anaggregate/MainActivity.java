package com.zheng.xiaoxian.anaggregate;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.zheng.xiaoxian.anaggregate.DBHelper.PermissionHelper;
import com.zheng.xiaoxian.anaggregate.HismileSignIn.HismileMainActivity;
import com.zheng.xiaoxian.anaggregate.MobileNoTrack.MobileNoTrackActivity;
import com.zheng.xiaoxian.anaggregate.NetworkTelephone.Model.NetwordTelephoneActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private PermissionHelper mPermissionHelper;

    private Button btnMobileNoTrack,btnNetwordTelephone,btnHismileSignIn;

    Intent intent;

    //储存所有权限
    String[] allpermissions=new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            //Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.BIND_ACCESSIBILITY_SERVICE,
            Manifest.permission.CALL_PHONE,};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        applypermission();
        btnMobileNoTrack=(Button)findViewById( R.id.btnMobileNoTrack );
        btnNetwordTelephone=(Button)findViewById( R.id.btnNetwordTelephone );
        btnHismileSignIn=(Button)findViewById( R.id.btnHismileSignIn );
        btnMobileNoTrack.setOnClickListener( this );
        btnNetwordTelephone.setOnClickListener( this );
        btnHismileSignIn.setOnClickListener( this );
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
                intent=new Intent( MainActivity.this,HismileMainActivity.class );
                startActivity( intent );
                break;
        }
    }

    /**
     * 动态申请权限
     */
    public void applypermission(){
        if(Build.VERSION.SDK_INT>=23){
            boolean needapply=false;
            for(int i=0;i<allpermissions.length;i++){
                int chechpermission= ContextCompat.checkSelfPermission(getApplicationContext(),
                        allpermissions[i]);
                if(chechpermission!=PackageManager.PERMISSION_GRANTED){
                    needapply=true;
                }
            }
            if(needapply){
                ActivityCompat.requestPermissions(MainActivity.this,allpermissions,1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult( requestCode, permissions, grantResults );
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText( MainActivity.this, permissions[i] + "已授权", Toast.LENGTH_SHORT ).show();
            } else {
                Toast.makeText( MainActivity.this, permissions[i] + "拒绝授权", Toast.LENGTH_SHORT ).show();
            }
        }
    }
}
