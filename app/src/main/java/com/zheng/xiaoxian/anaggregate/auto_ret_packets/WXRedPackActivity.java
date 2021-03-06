package com.zheng.xiaoxian.anaggregate.auto_ret_packets;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.zheng.xiaoxian.anaggregate.R;
import com.zheng.xiaoxian.anaggregate.auto_ret_packets.Model.WXRedPack;

public class WXRedPackActivity extends AppCompatActivity implements View.OnClickListener {

    String TAG="WXRedPackActivity";

    private Button btnWXPermission,btnWXRespTime;
    private EditText edtWXRespTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_wx_red_pack);

        btnWXPermission=(Button)findViewById(R.id.btnWXPermission);
        btnWXRespTime=(Button)findViewById(R.id.btnWXRespTime);
        edtWXRespTime=(EditText) findViewById(R.id.edtWXRespTime);
        btnWXRespTime.setOnClickListener(this);
        btnWXPermission.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnWXPermission:
                openAccessibility(".auto_ret_packets.WXRedPackService",this);
                break;
            case R.id.btnWXRespTime:
                break;
        }
    }

    /**
     * 跳转到系统设置页面开启辅助功能
     * @param accessibilityServiceName：指定辅助服务名字
     * @param context：上下文
     */
    private void openAccessibility(String accessibilityServiceName, Context context){
        if (!isAccessibilitySettingsOn(accessibilityServiceName,context)) {
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivity(intent);
        }
    }

    /**
     * 该辅助功能开关是否打开了
     * @param accessibilityServiceName：指定辅助服务名字
     * @param context：上下文
     * @return
     */
    private boolean isAccessibilitySettingsOn(String accessibilityServiceName, Context context) {
        int accessibilityEnable = 0;
        String serviceName = context.getPackageName() + "/" +accessibilityServiceName;
        try {
            accessibilityEnable = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.ACCESSIBILITY_ENABLED, 0);
        } catch (Exception e) {
            Log.e(TAG, "消息：获取可访问性启用失败，错误:" + e.getMessage());
        }
        if (accessibilityEnable == 1) {
            TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');
            String settingValue = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                mStringColonSplitter.setString(settingValue);
                while (mStringColonSplitter.hasNext()) {
                    String accessibilityService = mStringColonSplitter.next();
                    if (accessibilityService.equalsIgnoreCase(serviceName)) {
                        Log.v(TAG, "消息：我们已经找到了正确的设置——可访问性被打开了!");
                        return true;
                    }
                }
            }
        }else {
            Log.d(TAG,"Accessibility service disable");
        }
        return false;
    }



}
