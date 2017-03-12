package com.qwqaq.msgwebhook;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.util.Log;

import static android.content.Context.MODE_APPEND;

/**
 * APP 配置帮助类
 * Created by Zneia on 2017/3/12.
 */

public class ConfHelper {

    // SharedPreferences
    public SharedPreferences appConfig;
    private Context context;

    public ConfHelper(){
        // 获取值
        context = ContextUtil.getInstance();
        appConfig = context.getSharedPreferences("appConfig",MODE_APPEND);
    }

    /* 存储数据的键名 */
    public static String UserIsLogin_KeyName = "isLogin";
    public static String UserPhoneNum_KeyName = "userData_PhoneNum";
    public static String UserPassword_KeyName = "userData_Password";

    public static String SendUrl_KeyName = "userData_SendUrl";
    public static String SendParPhoneNum_KeyName = "userData_SendParPhoneNum";
    public static String SendParContent_KeyName = "userData_SendParContent";
    public static String SendParTimestamp_KeyName = "userData_SendParTimestamp";

    /**
     * 获取 SharedPreferences
     * @return SharedPreferences
     */
    public SharedPreferences getInstance(){
        return appConfig;
    }

    /**
     * 获取是否已登录
     * @return true or false
     */
    public boolean getIsLogin(){
        return appConfig.getBoolean(UserIsLogin_KeyName, false);
    }

    /**
     * 用户保存登录数据
     * @param phoneNum 手机号
     * @param password 密码
     * @param SendUrl 请求地址
     * @param SendParPhoneNum 请求参数·发件人号码
     * @param SendParContent 请求参数·短信内容
     * @param SendParTimestamp 请求内容·时间戳
     */
    public void userLogin(String phoneNum, String password, String SendUrl, String SendParPhoneNum, String SendParContent, String SendParTimestamp){
        SharedPreferences.Editor editor = appConfig.edit();
        editor.putString(UserPhoneNum_KeyName, phoneNum);
        editor.putString(UserPassword_KeyName, password);
        editor.apply();
        // 配置发送参数
        setSend(SendUrl, SendParPhoneNum, SendParContent, SendParTimestamp);
        // 设置已登录
        setIsLogin(true);
    }

    /**
     * 注销
     */
    public void userLogout(){
        SharedPreferences.Editor editor = appConfig.edit();
        editor.putString(UserPhoneNum_KeyName, "");
        editor.putString(UserPassword_KeyName, "");
        editor.apply();
        // 配置发送参数
        setSend("", "", "", "");
        // 设置已登录
        setIsLogin(false);
    }

    /**
     * 设置登录
     * @param logined bool：true or false
     */
    public void setIsLogin(boolean logined){
        SharedPreferences.Editor editor = appConfig.edit();
        editor.putBoolean(UserIsLogin_KeyName, logined);
        editor.apply();
    }

    /**
     * 设置发送参数
     * @param SendUrl userData_SendUrlKey_Name 请求地址
     * @param SendParPhoneNum userData_SendParPhoneNum_KeyName 请求参数·发件人号码
     * @param SendParContent userData_SendParContent_KeyName 请求参数·短信内容
     * @param SendParTimestamp userData_SendParTimestamp_KeyName 请求内容·时间戳
     */
    public void setSend(String SendUrl, String SendParPhoneNum, String SendParContent, String SendParTimestamp){
        SharedPreferences.Editor editor = appConfig.edit();
        editor.putString(SendUrl_KeyName, SendUrl);
        editor.putString(SendParPhoneNum_KeyName, SendParPhoneNum);
        editor.putString(SendParContent_KeyName, SendParContent);
        editor.putString(SendParTimestamp_KeyName, SendParTimestamp);
        editor.apply();
    }
}