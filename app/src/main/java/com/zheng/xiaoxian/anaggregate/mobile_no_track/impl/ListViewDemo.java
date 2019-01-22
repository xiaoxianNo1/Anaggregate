package com.zheng.xiaoxian.anaggregate.mobile_no_track.impl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.zheng.xiaoxian.anaggregate.mobile_no_track.Model.Phone;
import com.zheng.xiaoxian.anaggregate.R;

import java.util.ArrayList;

public class ListViewDemo extends BaseAdapter {

    TextView telString;
    TextView province;
    TextView catName;
    TextView carrier;
    TextView click;
    Button delete;

    private Context context;//上下文对象
   // private List<String> dataList;//ListView显示的数据
    private ArrayList<Phone> phonelist =new ArrayList<>(  );;

    public ListViewDemo(Context context, ArrayList<Phone> phonelist) {
        this.context = context;
        this.phonelist = phonelist;
    }


    @Override
    public int getCount() {
        return phonelist == null ? 0 : phonelist.size();
    }

    @Override
    public Object getItem(int position) {
        return phonelist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate( R.layout.history_item,null );
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Phone ph=phonelist.get( position );
        telString.setText( ph.getTelString() );
        province.setText( ph.getProvince( ) );
        catName.setText( ph.getCatName() );
        carrier.setText( ph.getCarrier() );
        click.setText( ph.getClick() );

        return convertView;
    }
    public final class ViewHolder{

        public ViewHolder(View view){

            telString=(TextView)view.findViewById( R.id.txtTel );
            province=(TextView)view.findViewById( R.id.txtProvince );
            catName=(TextView)view.findViewById( R.id.txtCatName );
            carrier=(TextView)view.findViewById( R.id.txtCarrier );
            click=(TextView)view.findViewById( R.id.txtClick );
            delete=(Button)view.findViewById( R.id.btnDelect ) ;
        }
    }

}
