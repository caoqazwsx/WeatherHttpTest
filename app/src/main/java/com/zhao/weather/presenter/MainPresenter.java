package com.zhao.weather.presenter;

import android.os.Handler;
import android.os.Message;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import com.zhao.weather.common.APPCONST;
import com.zhao.weather.activity.MainActivity;
import com.zhao.weather.callback.ResultCallback;
import com.zhao.weather.model.SysData;
import com.zhao.weather.model.Weather;
import com.zhao.weather.util.CacheHelper;
import com.zhao.weather.util.StringHelper;
import com.zhao.weather.webapi.CommonApi;

import org.greenrobot.greendao.annotation.Entity;

/**
 * Created by zhao on 2017/4/19.
 */

public class MainPresenter implements BasePresenter {
    private MainActivity mMainActivity;

    private Weather mWeather;

    private String city = "";

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    mMainActivity.getTextCity().setText(city);
                    break;
                case 2:
                    mMainActivity.getTextTemp().setText(mWeather.getTemperature2() + "—" + mWeather.getTemperature1() + "℃");
                    break;

            }
        }
    };

    public MainPresenter(MainActivity mainActivity) {
        mMainActivity = mainActivity;
    }

    @Override
    public void start() {

        init();
    }

    public void init(){
        initKey();
        initLocation();
    }

    private void getWeather() {
        CommonApi.getWeather(city, 0, new ResultCallback() {
            @Override
            public void onFinish(Object o, int code) {
                mWeather = (Weather) o;
                handler.sendMessage(handler.obtainMessage(2));

            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    private void initKey() {

        SysData sysData = (SysData) CacheHelper.readObject(APPCONST.SYS_DATA_FILE_NAME);
        if (sysData != null) {
            APPCONST.KEY = sysData.getId();
            APPCONST.DESCRIPTION = sysData.getDescription();
        }


    }


    private void initLocation() {
        //初始化定位
        mLocationClient = new AMapLocationClient(mMainActivity.getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (!city.equals(aMapLocation.getCity())) {
                    city = aMapLocation.getCity();
                    handler.sendMessage(handler.obtainMessage(1));
                    getWeather();
                }
                updateLocation(aMapLocation.getLatitude(), aMapLocation.getLongitude(), aMapLocation.getLocationDetail());
            }
        });
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        mLocationOption.setInterval(10000);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //获取一次定位结果：
   /*     //该方法默认为false。
        mLocationOption.setOnceLocation(true);*/
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    /**
     * 上传位置信息
     */
    private void updateLocation(double latitude, double longitude, String description) {
        CommonApi.updateLocation(APPCONST.KEY, latitude, longitude, description, new ResultCallback() {
            @Override
            public void onFinish(Object o, int code) {
                if (code == 0) {
                    if (StringHelper.isEmpty(APPCONST.KEY)) {
                        APPCONST.KEY = (String) o;
                        SysData sysData = new SysData();
                        sysData.setId(APPCONST.KEY);
                        sysData.setDescription("");
                        CacheHelper.saveObject(sysData, APPCONST.SYS_DATA_FILE_NAME);
                    }
                }

            }

            @Override
            public void onError(Exception e) {

            }
        });

    }


}
