package com.example.cindy.register.utils;

import cn.smssdk.EventHandler;

public class MobUtil {

    public EventHandler eh; //事件接收器

    /**
     * 初始化事件接收器
     */
//    private void init() {
//        eh = new EventHandler() {
//            @Override
//            public void afterEvent(int event, int result, Object data) {
//                //坑：进入mob平台官网，进入后台，点击SMSSDK，进入短信设置，关闭是否开启Mob云验证，否则同一个手机号只能发送一次短信。
//                if (result == SMSSDK.RESULT_COMPLETE) { //回调完成
//                    //                   boolean smart = (Boolean)data;
////                    if(smart) {
////                        //通过Mob云验证
////                    } else {
////                        //依然走短信验证
////                    }
//                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) { //提交验证码成功
//                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class)); //页面跳转
//                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) { //获取验证码成功
//                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) { //返回支持发送验证码的国家列表
//                    }
//                } else {
//                    ((Throwable) data).printStackTrace();
//                }
//            }
//        };
//        SMSSDK.registerEventHandler(eh); //注册短信回调
//    }
}
