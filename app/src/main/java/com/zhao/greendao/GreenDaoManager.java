package com.zhao.greendao;


import com.zhao.greendao.gen.DaoMaster;
import com.zhao.greendao.gen.DaoSession;
import com.zhao.greendao.util.MySQLiteOpenHelper;
import com.zhao.weather.application.MyApplication;

/**
 * Created by zhao on 2017/3/15.
 */

public class GreenDaoManager {
    private static GreenDaoManager instance;
    private static DaoMaster daoMaster;

    public static GreenDaoManager getInstance() {
        if (instance == null) {
            instance = new GreenDaoManager();
        }
        return instance;
    }

    public GreenDaoManager(){
        MySQLiteOpenHelper mySQLiteOpenHelper = new MySQLiteOpenHelper(MyApplication.getContext(), "track", null);
        daoMaster = new DaoMaster(mySQLiteOpenHelper.getWritableDatabase());
    }



    public DaoSession getSession(){
       return daoMaster.newSession();
    }

}
