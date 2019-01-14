package com.zheng.xiaoxian.anaggregate.MobileNoTrack;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.zheng.xiaoxian.anaggregate.DBHelper.SQListHelper;
import com.zheng.xiaoxian.anaggregate.MobileNoTrack.Model.Phone;
import com.zheng.xiaoxian.anaggregate.MobileNoTrack.impl.ListViewDemo;
import com.zheng.xiaoxian.anaggregate.R;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    private ListView listMobileHistory;
    private SQListHelper moh;
    private SQLiteDatabase sd;
    private ArrayList<Phone> phonelist =new ArrayList<>(  );
    SQLiteDatabase db;
    ListViewDemo listViewDemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_history );
        setTitle( R.string.HistoryActivityTitle );

        try {
            query();
            listMobileHistory=(ListView)findViewById( R.id.listMobileHistory );
//            if (listViewDemo != null){
//                listViewDemo.notifyDataSetChanged();
//                listMobileHistory.setSelection( phonelist.size()-1 );
//            }else {
//                listViewDemo=new ListViewDemo( this,phonelist );
//                listMobileHistory.setAdapter( listViewDemo );
//                listMobileHistory.setSelection( phonelist.size()-1 );
//            }
            updateView();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private boolean query(){
        SQListHelper helper=new SQListHelper( HistoryActivity.this,"Anaggregate",null,1 );
        db=helper.getReadableDatabase();

        Cursor cursor=db.query( "mobileHistory" ,new String[]{"telString","province","catName","carrier","click"},null,null,null,null,null);

        String telString=null;
        String province=null;
        String catName=null;
        String carrier=null;
        String click=null;

        while (cursor.moveToNext()){
            telString=cursor.getString( cursor.getColumnIndex( "telString" ) );
            province=cursor.getString( cursor.getColumnIndex( "province" ) );
            catName=cursor.getString( cursor.getColumnIndex( "catName" ) );
            carrier=cursor.getString( cursor.getColumnIndex( "carrier" ) );
            click=cursor.getString( cursor.getColumnIndex( "click" ) );
            Phone phone=new Phone( telString,province,catName,carrier,click );
            phonelist.add( phone );

        }
        return false;
    }

    private void delect(String telString){
        SQListHelper helper=new SQListHelper( HistoryActivity.this,"Anaggregate",null,1 );
        db=helper.getWritableDatabase();
        db.delete( "mobileHistory","telString=?",new String []{telString} );
        db.close();
    }

    private void updateView(){
        listMobileHistory.setAdapter( new BaseAdapter() {
            @Override
            public int getCount() {
                return phonelist.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                View view;
                if(convertView==null){
                    view=View.inflate( getBaseContext(),R.layout.history_item,null );
                }else{
                    view=convertView;
                }
                Phone ph=phonelist.get( position );
                TextView telString=(TextView)view.findViewById( R.id.txtTel );
                TextView province=(TextView)view.findViewById( R.id.txtProvince );
                TextView catName=(TextView)view.findViewById( R.id.txtCatName );
                TextView carrier=(TextView)view.findViewById( R.id.txtCarrier );
                TextView click=(TextView)view.findViewById( R.id.txtClick );
                Button delete=(Button)view.findViewById( R.id.btnDelect ) ;
                telString.setText( ph.getTelString() );
                province.setText( ph.getProvince( ) );
                catName.setText( ph.getCatName() );
                carrier.setText( ph.getCarrier() );
                click.setText( ph.getClick() );
                delete.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String telString =phonelist.get( position ).getTelString();
                        delect( telString );
                    }
                } );
                return view;
            }
        } );
    }


}

