package com.qwqaq.msgwebhook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.qwqaq.msgwebhook.Fragment.*;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private int mainContent;
    private FragmentManager fm;
    private FragmentHome fmHome;
    private FragmentLogin fmLogin;
    private FragmentSmsWebhook fmSmshook;

    /**
     * MainActivity 创建时执行
     * 每次打开程序 只会执行一次
     * @param savedInstanceState Saved Instance State
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 工具栏
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // 初始化变量
        mainContent = R.id.main_content;
        fm = getSupportFragmentManager(); // FragmentManager
        // 可爱的 Frag 们
        fmHome = new FragmentHome();
        fmLogin = new FragmentLogin();
        fmSmshook = new FragmentSmsWebhook();
        // 悬浮按钮
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "换成你自己的操作", Snackbar.LENGTH_LONG)
                        .setAction("操作", null).show();
            }
        });*/
        // ActivityMain.xml
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        // 侧滑菜单视图
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // 设置菜单颜色
        Resources resource = (Resources)getBaseContext().getResources();
        ColorStateList csl = (ColorStateList)resource.getColorStateList(R.color.navigation_menu_item_color);
        navigationView.setItemTextColor(csl);
        View navigationHeaderView = navigationView.getHeaderView(0);
        // 设置侧滑菜单登录和注册按钮操作
        Button navHeaderLoginBtn = (Button)navigationHeaderView.findViewById(R.id.navHeaderLoginBtn); // 获取按钮资源
        navHeaderLoginBtn.setOnClickListener(new Button.OnClickListener(){ // 创建监听
            public void onClick(View view) {
                changeToLoginFrag(true);
            }
        });

        fm.beginTransaction().replace(mainContent, fmHome).commit();
        /*Log.i("哈哈哈",fm.getClass().getName());*/
        /**
         * [NOTICE] fm.beginTransaction().addToBackStack(null).replace(mainContent, fmHome).commit();
         * .addToBackStack(null) 在程序最开始运行时就不必用了 不然想退出程序 必须按两次返回键 ┌(。Д。)┐
         * BackStack 返回栈
         */
    }

    /**
     * 按返回键时的操作
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * 工具栏三点菜单，当列表项目选中时触发
     * @param item 当前选项项目
     * @return 父
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 侧滑栏 继承函数，当列表项目选中时触发
     * @param item 当前选项项目
     * @return true
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        // 选择 执行对应操作
        if (id == R.id.nav_home){
            changeFragmentOnNavigationItem(fmHome);
        } else if (id == R.id.nav_sms_webhook) {
            changeFragmentOnNavigationItem(fmSmshook);
        }/* else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        // 找到 侧滑菜单 所在资源
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START); // 收起侧滑菜单

        nowHideImm(); // 隐藏输入法键盘

        return true;
    }

    /**
     * 打开 Fragment
     * @param fragment Fragment 对象
     * @param addBackStack 是否添加返回栈
     * @param fragmentTransaction 切换动画效果，例如：FragmentTransaction.TRANSIT_FRAGMENT_FADE
     */
    public void changeFragment(Fragment fragment, boolean addBackStack, int fragmentTransaction){
        FragmentTransaction ft = fm.beginTransaction();
        if(addBackStack) ft = ft.addToBackStack(null); // 设置返回栈
        ft = ft.setTransition(fragmentTransaction); // 动画 来一个 FragmentTransaction.TRANSIT_FRAGMENT_FADE 淡入
        ft.replace(mainContent, fragment).commit();
    }


    /**
     * 在侧滑栏上打开 Fragment
     * @param fragment Fragment 对象
     */
    public void changeFragmentOnNavigationItem(Fragment fragment){
        changeFragment(fragment, false, FragmentTransaction.TRANSIT_FRAGMENT_FADE); // 不添加 返回栈，动画 淡入
    }

    /**
     * 打开 Login Fragment
     * @param addBackStack 是否添加返回栈
     */
    public void changeToLoginFrag(boolean addBackStack){
        if(!getTitle().equals(getString(R.string.frag_login))){
            changeFragment(fmLogin, addBackStack, FragmentTransaction.TRANSIT_FRAGMENT_OPEN); // 动画 从上到下 滚动进入
        }

        // 找到 侧滑菜单 所在资源
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START); // 收起侧滑菜单

        nowHideImm();
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
    public void nowHideImm(){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getRootView(this).getWindowToken(),0);
    }
}