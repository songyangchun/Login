package com.example.cindy.register.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

//存储用户数据的类

public class SharePreferenceUtil {

	public static SharedPreferences sp;
	/*
      *在Application中进行初始化，设置context
      */
	public static void initSharePreferenceUtil(Context context){
		//通过调用context.getSharedPreferences()方法，获得SharedPreferences对象，参数为生成的配置文件名和操作模式
		sp=context.getSharedPreferences("login", Context.MODE_PRIVATE);

	}


	public static void LoginSuccess(){
		setBooleanSP("islogin",true);
	}

	public static boolean isLogin(){
		if(null==sp){
			sp=getSP();
		}
		return sp.getBoolean("islogin", false);
	}
	
	public static SharedPreferences getSP(){
		return sp;
	}

	public static String getStringSP(String key, String defult){
		return sp.getString(key,defult);
	}

	public static boolean getBooleanSP(String key){
		return sp.getBoolean(key,false);
	}


	//保存数据的方法
	public static void setIntSP(String key, int value){
		if(null==sp){
			sp=getSP();
		}
		Editor editor=sp.edit(); //调用sp.edit()方法获得editor对象
		editor.putInt(key, value); //调用editor对象的putXxx方法，将不同类型的数据写入，参数是键值对形式
		editor.commit(); //调用commit方法提交数据到XML文件中
	}

   //读取数据的方法
	public static int getIntSP(String key){
		return sp.getInt(key,-1);
	}

	public static void setStringSP(String key, String value){
		if(null==sp){
			sp=getSP();
		}
		Editor editor=sp.edit();
		editor.putString(key, value);
		editor.commit();
	}
	public static void setStringsSP(String[] key, String[] value){
		if(null==sp){
			sp=getSP();
		}
		Editor editor=sp.edit();
		for(int i=0;i<key.length;i++){
			editor.putString(key[i], value[i]);
		}
		editor.commit();
	}

	public static void setBooleanSP(String key, boolean value){
		if(null==sp){
			sp=getSP();
		}
		Editor editor=sp.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public static void removeStringSP(String key){
		if(null==sp){
			sp=getSP();
		}
		Editor editor=sp.edit();
		editor.remove(key);
		editor.commit();
	}


	public static void removeStringsSP(String[] key){
		if(null==sp){
			sp=getSP();
		}
		Editor editor=sp.edit();
		for(int i=0;i<key.length;i++){
			editor.remove(key[i]);
		}
		editor.commit();
	}

	public static void removeBooleanSP(String key){
		if(null==sp){
			sp=getSP();
		}
		Editor editor=sp.edit();
		editor.remove(key);
		editor.commit();
	}
}
