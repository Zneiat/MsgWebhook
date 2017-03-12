package com.qwqaq.msgwebhook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import com.qwqaq.msgwebhook.LoginActivityFragment.FragmentLogin;

/**
 * Created by Zneia on 2017/3/12.
 */

public class LoginActivity extends AppCompatActivity {

    private int mainContent;
    private FragmentManager fm;
    private FragmentLogin fmLogin;
    public int activityIdCode; // 用于标识 activity 判断身份
    private boolean isLogined; // 是否已成功登录

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 初始化变量
        mainContent = R.id.login_activity_main_content;
        fm = getSupportFragmentManager(); // FragmentManager
        activityIdCode = 1001; // 用于标识 activity 判断身份
        // Frag 们
        fmLogin = new FragmentLogin();
        // 设置视图文件
        setContentView(R.layout.login_activity);
        // 工具条
        Toolbar toolbar = (Toolbar) findViewById(R.id.login_activity_toolbar);
        setSupportActionBar(toolbar);
        // 设置工具条返回按钮
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 设置显示返回图标
        }
        isLogined = true;
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            // 自定义点击返回图标操作
            @Override
            public void onClick(View v) {
                // Toast.makeText(LoginActivity.this,"你已放弃登录...",Toast.LENGTH_LONG).show();
                downDownDown();
            }
        });
        // 初始化
        init();
    }

    /**
     * 初始化
     */
    private void init(){
        fragmentReplaceDefault(fmLogin);
    }

    /**
     * 按返回键时的操作
     */
    @Override
    public void onBackPressed() {
        downDownDown();
        super.onBackPressed();
    }

    /**
     * “已完成使命” 函数
     */
    public void downDownDown(){
        Intent intent = new Intent();
        intent.putExtra("isLogined", isLogined);
        // 调用 setResult 方法表示我将 Intent 对象返回给父 Activity，父的 onActivityResult 方法中得到 Intent 对象
        setResult(activityIdCode, intent);
        finish();
    }

    /**
     * 替换 Fragment
     * @param fragment Fragment 对象
     * @param addBackStack 是否添加返回栈
     * @param fragmentTransaction 切换动画效果，例如：FragmentTransaction.TRANSIT_FRAGMENT_FADE
     */
    public void fragmentReplace(Fragment fragment, boolean addBackStack, int fragmentTransaction){
        FragmentTransaction ft = fm.beginTransaction();
        ft.addToBackStack(null); // 设置返回栈
        ft.setTransition(fragmentTransaction); // 动画 来一个 FragmentTransaction.TRANSIT_FRAGMENT_FADE 淡入
        ft.replace(mainContent, fragment).commit();
    }

    /**
     * 通用快捷 替换 Fragment
     * @param fragment Fragment 对象
     */
    public void fragmentReplaceDefault(Fragment fragment){
        fragmentReplace(fragment, false, FragmentTransaction.TRANSIT_FRAGMENT_FADE); // 不添加 返回栈，动画 淡入
    }

    /**
     * 获取根视图
     * @param context 例如：this
     * @return View
     */
    private static View getRootView(Activity context) {
        return ((ViewGroup)context.findViewById(android.R.id.content)).getChildAt(0);
    }

    /**
     * 立刻隐藏输入法键盘
     */
    public void hideImm(){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getRootView(this).getWindowToken(),0);
    }
}