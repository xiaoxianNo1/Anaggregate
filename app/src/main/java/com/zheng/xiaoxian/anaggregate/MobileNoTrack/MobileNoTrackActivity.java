package com.zheng.xiaoxian.anaggregate.MobileNoTrack;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zheng.xiaoxian.anaggregate.DBHelper.SQListHelper;
import com.zheng.xiaoxian.anaggregate.DBHelper.mvp.MvpMainView;
import com.zheng.xiaoxian.anaggregate.MobileNoTrack.Model.Phone;
import com.zheng.xiaoxian.anaggregate.MobileNoTrack.impl.MainPresenter;
import com.zheng.xiaoxian.anaggregate.R;

public class MobileNoTrackActivity extends AppCompatActivity implements View.OnClickListener,MvpMainView{

    //号码归属地查询
    private EditText edtPhoneNumb;
    private Button btnOK;
    private TextView txtPhoneNumb,txtShengfen,txtYunying,txtGuishuYunying,txtHistory;
    MainPresenter mainPresenter;
    ProgressDialog progressDialog;
    SQLiteDatabase db;

    private SQListHelper moh;
    private SQLiteDatabase sd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_mobile_no_track );
        setTitle( R.string.MobileNoTrackActivityTitle );

        edtPhoneNumb=(EditText)findViewById( R.id.edtPhoneNumb );
        btnOK=(Button)findViewById( R.id.btnOK );
        txtPhoneNumb=(TextView)findViewById( R.id.txtPhoneNumb );
        txtShengfen=(TextView)findViewById( R.id.txtShengfen );
        txtYunying=(TextView)findViewById( R.id.txtYunying );
        txtGuishuYunying=(TextView)findViewById( R.id.txtGuishuYunying );
        txtHistory=(TextView)findViewById( R.id.txtHistory ) ;

        btnOK.setOnClickListener( this );
        txtHistory.setOnClickListener( this );

        mainPresenter=new MainPresenter( this );
        mainPresenter.attach( this );

//        SQListHelper helper=new SQListHelper( MobileNoTrackActivity.this,"Anaggregate",null,1 );
//        db=helper.getReadableDatabase();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnOK:
                mainPresenter.sarchPhoneInfo( edtPhoneNumb.getText().toString() );
                break;
            case R.id.txtHistory:
                Intent intent=new Intent( MobileNoTrackActivity.this,HistoryActivity.class );
                startActivity( intent );
                break;
        }

    }

    @Override
    public void showToast(String mas) {
        Toast.makeText( this, mas, Toast.LENGTH_SHORT ).show();
    }

    @Override
    public void updateView() {
        Phone phone=mainPresenter.getPhoneInfo();
        txtPhoneNumb.setText( "手机号码："+phone.getTelString() );
        txtShengfen.setText( "省份："+phone.getProvince() );
        txtYunying.setText( "运营商："+phone.getCatName() );
        txtGuishuYunying.setText( "归属运营商："+phone.getCarrier() );
        selectByPhone(phone.getTelString(),phone.getProvince(),phone.getCatName(),phone.getCarrier());
    }

    @Override
    public void showLoading() {
        if(progressDialog==null){
            progressDialog=ProgressDialog.show( this,"","正在加载……",true,false );
        }else if(progressDialog.isShowing()){
            progressDialog.setTitle( "" );
            progressDialog.setMessage( "正在加载" );
        }
        progressDialog.show();
    }

    @Override
    public void hidenLoading() {
        if(progressDialog!=null&&progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }

    private void insertPhoneHistory(String telString ,String province ,String catName,String carrier){

          SQListHelper helper=new SQListHelper( MobileNoTrackActivity.this,"Anaggregate",null,1 );
          db=helper.getWritableDatabase();
           ContentValues cValue = new ContentValues();
          cValue.put("telString",telString);
          cValue.put("province",province);
          cValue.put("catName",catName);
          cValue.put("carrier",carrier);
          cValue.put("click","1");
          db.insert( "mobileHistory",null,cValue );
          db.close();

    }

    private void selectByPhone(String telString ,String province ,String catName,String carrier){
        SQListHelper helper=new SQListHelper( MobileNoTrackActivity.this,"Anaggregate",null,1 );
        db=helper.getWritableDatabase();
        String click;

        try {
            Cursor cursor = db.query("mobileHistory",new String[]{"telString","province","catName","carrier","click"},"telString=?",new String[]{telString},null,null,null);

            if(cursor.getCount()==0){
            insertPhoneHistory(telString,province,catName,carrier);
            //插入操作
            }else if(cursor.getCount()>0){
            //更新click +1
                while(cursor.moveToNext()){

                    click=cursor.getString( cursor.getColumnIndex( "click" ) );
                    update(telString,click);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        db.close();
    }

    private void update(String telString,String click){
        SQListHelper helper=new SQListHelper( MobileNoTrackActivity.this,"Anaggregate",null,1 );
        db=helper.getWritableDatabase();
        int intClick=Integer.parseInt( click );
        ContentValues values = new ContentValues();
        values.put("click",intClick+1);
        db.update( "mobileHistory",values,"telString=?",new String[]{telString} );
        System.out.print( "更新成功" );
        db.close();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}
