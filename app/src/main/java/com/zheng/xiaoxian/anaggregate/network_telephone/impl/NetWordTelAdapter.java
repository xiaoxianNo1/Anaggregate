package com.zheng.xiaoxian.anaggregate.network_telephone.impl;

import java.util.List;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zheng.xiaoxian.anaggregate.network_telephone.PhoneInfo;
import com.zheng.xiaoxian.anaggregate.R;

public class NetWordTelAdapter  extends BaseAdapter{

    private List<PhoneInfo> lists;
    private Context context;
    private LinearLayout layout;

    public NetWordTelAdapter(List<PhoneInfo> lists,Context context){
        this.lists = lists;
        this.context = context;
    }


    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.netword_telephone_item, null);
            holder = new ViewHolder();
            holder.nametv = (TextView) convertView.findViewById(R.id.name);
            holder.numbertv = (TextView) convertView.findViewById( R.id.number);
            holder.imageView=(ImageView)convertView.findViewById( R.id.Contactsgrid );
            holder.nametv.setText(lists.get(position).getName());
            holder.numbertv.setText(lists.get(position).getNumber());
           // holder.imageView.setImageResource( lists.get(position) );
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
            holder.nametv.setText(lists.get(position).getName());
            holder.numbertv.setText(lists.get(position).getNumber());
        }
        return convertView;
    }
    private static class ViewHolder{
        TextView nametv;
        TextView numbertv;
        ImageView imageView;
    }
}
