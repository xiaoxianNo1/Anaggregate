package com.example.xiaoxian.autoredpackhelper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.xiaoxian.autoredpackhelper.utils.SqlHelper;
import com.example.xiaoxian.autoredpackhelper.utils.getUrl;

import java.util.Calendar;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtCode,edtKey;
    private Button btnLogin;
    Calendar cal;
    String year;
    String month;
    String day;
    String hour;
    String minute;
    String second;
    String time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtCode=(EditText)findViewById(R.id.edtCode);
        edtKey=(EditText)findViewById(R.id.edtKey);
        btnLogin=(Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

    }

    //使用Sharepreferences进行保存数据
    public void login(View view) throws Exception{
        cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));

        year = String.valueOf(cal.get(Calendar.YEAR));
        month = String.valueOf(cal.get(Calendar.MONTH))+1;
        day = String.valueOf(cal.get(Calendar.DATE));

        if (cal.get(Calendar.AM_PM) == 0)
            hour = String.valueOf(cal.get(Calendar.HOUR));
        else
            hour = String.valueOf(cal.get(Calendar.HOUR)+12);
        minute = String.valueOf(cal.get(Calendar.MINUTE));
        second = String.valueOf(cal.get(Calendar.SECOND));
        time=year+month+day+hour;//+minute+second;
        getUrl.login_encrypt(time,1);

        //获取code和key
        String codeStr=edtCode.getText().toString();
        String keyStr=edtKey.getText().toString();
        Intent intent=new Intent(MainActivity.this,WXRedPackActivity.class);
        startActivity(intent);

        /*if (TextUtils.isEmpty(codeStr) || TextUtils.isEmpty(keyStr)) {
            Toast.makeText(this, "请输入code 和key", Toast.LENGTH_SHORT).show();
        }else{
            if(TextUtils.isEmpty(keyStr)){
                Toast.makeText(this, "请输入key", Toast.LENGTH_SHORT).show();
            }else{
                //验证code 和key
                SQLiteDatabase db;
                SqlHelper sqlHelper=new SqlHelper(MainActivity.this,"autoredpack",null,1);
                db=sqlHelper.getReadableDatabase();
                db.query("user",new String[]{"code","create_time"},null,null,null,null,null);
                //db.delete( "user","code=?",codeStr );
                db.close();
                *//*String mKey=getUrl.login_encrypt(keyStr,0);  //1 +
                if(mKey.equals(time)){
                    SharedPreferences sp=getSharedPreferences("config",0);
                    SharedPreferences.Editor editor=sp.edit();
                    //把数据进行保存
                    editor.putString("name",codeStr);
                    editor.putString("password",keyStr);

                }*//*
            }

            //getSharedPreferences(name,model);,name 会生成一个xml文件，model ：模式，可读可写等模式

        }*/

    }

    @Override
    public void onClick(View v) {
        try{
            switch (v.getId()){
                case R.id.btnLogin:
                    login(v);
                    break;
            }
        }catch (Exception e){

        }

    }
}
