package com.zheng.xiaoxian.anaggregate.DBHelper;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.zheng.xiaoxian.anaggregate.NetworkTelephone.Model.PhoneInfo;

import java.util.ArrayList;
import java.util.List;

public class GetNumber {
    public static List<PhoneInfo> lists = new ArrayList<PhoneInfo>();

    public static String getNumber(Context context){
//        try {
           // Cursor cursor = context.getContentResolver().query( ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null, null, null, null);
            Cursor cursor=context.getContentResolver().query( ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null );
        String phoneNumber;
            String phoneName;
            while (cursor.moveToNext()) {
                phoneNumber = cursor.getString(cursor.getColumnIndex( ContactsContract.CommonDataKinds.Phone.NUMBER));
                phoneName = cursor.getString(cursor.getColumnIndex( ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                PhoneInfo phoneInfo = new PhoneInfo(phoneName, phoneNumber);
                lists.add(phoneInfo);
                System.out.println(phoneName+phoneNumber);
            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }

        return null;
    }
}
