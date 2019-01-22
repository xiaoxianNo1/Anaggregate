package com.zheng.xiaoxian.anaggregate.mobile_no_track.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.zheng.xiaoxian.anaggregate.db_helper.HttpUntil;
import com.zheng.xiaoxian.anaggregate.db_helper.mvp.MvpMainView;
import com.zheng.xiaoxian.anaggregate.mobile_no_track.Model.Phone;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class MainPresenter extends BasePresenter {

    String mUrl="http://tcc.taobao.com/cc/json/mobile_tel_segment.htm";
    MvpMainView mvpMainView;
    Phone mPhone;

    public Phone getPhoneInfo(){
        return mPhone;
    }

    public MainPresenter(MvpMainView mainView){
        mvpMainView=mainView;
    }
    public void sarchPhoneInfo(String phone){
        if(phone.length()!=11){
            mvpMainView.showToast( "请输入正确的手机号" );
            return;
        }
        mvpMainView.showLoading();
        //http请求的处理逻辑
        sendHttp(phone);
    }
    private void sendHttp(String phone){
        Map<String ,String> map=new HashMap<String, String>(  );
        map.put( "tel",phone );
        HttpUntil httpUntil=new HttpUntil( new HttpUntil.HttpResponse() {
            @Override
            public void onSuccess(Object object) {
                String json=object.toString();
                int index=json.indexOf( "{" );
                json=json.substring( index,json.length() );

                //JSONObjest
//                mPhone=parseModelWithOrgJson( json );

                //GSON
                mPhone=parseModelWithGson( json );

//                //FastJson
//                mPhone=parseModeWithFastJson( json );

                mvpMainView.hidenLoading();
                mvpMainView.updateView();
            }

            @Override
            public void onFail(String error) {
                mvpMainView.showToast( error );
                mvpMainView.hidenLoading();
            }
        } );
        httpUntil.sendGetHttp( mUrl,map );
    }

    private Phone parseModelWithOrgJson(String json){
        Phone phone=new Phone();
        try {
            org.json.JSONObject jsonObject=new org.json.JSONObject( json );
            String value=jsonObject.getString( "telString" );
            phone.setTelString( value );
            value=jsonObject.getString( "province" );
            phone.setProvince( value );
            value=jsonObject.getString( "catName" );
            phone.setCatName( value );
            value=jsonObject.getString( "carrier" );
            phone.setCarrier( value );

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return phone;
    }

    private Phone parseModelWithGson(String json){
        Gson gson=new Gson();
        Phone phone=gson.fromJson( json,Phone.class );

        return phone;
    }

    private Phone parseModeWithFastJson(String json){
        Phone phone= JSONObject.parseObject( json,Phone.class );
        return phone;
    }


}
