package com.qwqaq.msgwebhook;

import android.app.Application;

/**
 * 在任意位置获取 Context
 * 用法 ContextUtil.getInstance()
 */
public class ContextUtil extends Application {
    private static ContextUtil instance;

    public static ContextUtil getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        instance = this;
    }
}