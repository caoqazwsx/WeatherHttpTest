package com.zhao.weather.webapi;

import com.zhao.weather.common.URLCONST;
import com.zhao.weather.callback.JsonCallback;
import com.zhao.weather.callback.ResultCallback;
import com.zhao.weather.callback.WeatherCallback;
import com.zhao.weather.model.JsonModel;
import com.zhao.weather.model.Weather;
import com.zhao.weather.source.HttpDataSource;
import com.zhao.weather.util.HttpUtil;
import com.zhao.weather.util.StringHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhao on 2017/4/19.
 */

public class CommonApi {

    /**
     * 获取天气信息
     * @param city
     * @param day
     * @param callback
     */
    public static void getWeather(String city, int day, final ResultCallback callback){
        try {
            Map<String,Object> params = new HashMap<>();
            params.put("city",java.net.URLEncoder.encode(city.replace("市",""), "gb2312"));
            params.put("password","DJOYnieT8234jlsK");
            params.put("day",day);
            HttpDataSource.httpGetNoRSAByWeather(HttpUtil.makeURLNoRSA(URLCONST.method_weather,params),  new WeatherCallback() {
                @Override
                public void onFinish(Weather weather) {
                    callback.onFinish(weather,0);
                }

                @Override
                public void onError(Exception e) {
                    callback.onError(e);

                }
            });
        }catch (Exception e){
            e.printStackTrace();
            callback.onError(e);
        }

    }

    public static void updateLocation(String key,double latitude,double longitude,String description,final ResultCallback callback){
        Map<String,Object> params = new HashMap<>();
        if(!StringHelper.isEmpty(key)){
            params.put("key",key);
        }
        params.put("latitude",latitude);
        params.put("longitude",longitude);
        if(!StringHelper.isEmpty(description)){
            params.put("description",description);
        }
        HttpDataSource.httpGetNoRSA(HttpUtil.makeURLNoRSA(URLCONST.method_updateLocation, params), new JsonCallback() {
            @Override
            public void onFinish(JsonModel jsonModel) {
                callback.onFinish(jsonModel.getResult(),jsonModel.getError());
            }
            @Override
            public void onError(Exception e) {
                callback.onError(e);

            }
        });


    }




}
