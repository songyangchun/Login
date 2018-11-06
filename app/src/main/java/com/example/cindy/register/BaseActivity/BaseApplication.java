package com.example.cindy.register.BaseActivity;

import android.app.Application;

import com.example.cindy.register.utils.SharePreferenceUtil;
import com.mob.MobSDK;

import org.litepal.LitePal;

public class BaseApplication extends Application {
    protected static BaseApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance =this;
        LitePal.initialize(this);//数据库初始化
        MobSDK.init(this, "287c670a4f724","dde647cdfee680ac3402393b36559ef2");//mob初始化
//        SMSSDK.initSDK(this, "287c670a4f724", "dde647cdfee680ac3402393b36559ef2");
        SharePreferenceUtil.initSharePreferenceUtil(this);  //初始化


    }
    public static BaseApplication getInstance() {
        return instance;
    }

}
