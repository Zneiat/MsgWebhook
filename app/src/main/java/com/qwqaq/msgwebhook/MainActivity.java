package com.qwqaq.msgwebhook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import android.widget.LinearLayout;
import android.widget.Toast;
import com.qwqaq.msgwebhook.MainActivityFragment.*;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private int mainContent;
    private FragmentManager fm;
    private FragmentHome fmHome;
    private FragmentSmsWebhook fmSmshook;
    public int activityIdCode; // 用于标识 activity 判断身份
    public View navigationHeaderView;

    /**
     * MainActivity 创建时执行
     * 每次打开程序 只会执行一次
     * @param savedInstanceState Saved Instance State
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 初始化变量
        activityIdCode = 1000; // 用于标识 activity 判断身份
        mainContent = R.id.main_activity_main_content;
        fm = getSupportFragmentManager(); // FragmentManager
        // 可爱的 Frag 们
        fmHome = new FragmentHome();
        fmSmshook = new FragmentSmsWebhook();
        // 设置视图文件
        setContentView(R.layout.main_activity);
        // 工具条
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_activity_toolbar);
        setSupportActionBar(toolbar);
        // 绑定按钮打开侧滑菜单
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.main_activity_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        // 侧滑菜单视图
        NavigationView navigationView = (NavigationView) findViewById(R.id.main_activity_sidebar_menu);
        navigationView.setNavigationItemSelectedListener(this);
        // 设置侧滑菜单项目颜色
        Resources resource = (Resources)getBaseContext().getResources();
        ColorStateList csl = (ColorStateList)resource.getColorStateList(R.color.navigation_menu_item_color);
        navigationView.setItemTextColor(csl);
        navigationHeaderView = navigationView.getHeaderView(0);
        // 初始化程序
        appInit();
    }

    /**
     * 程序初始化
     */
    public void appInit(){
        // 设置侧滑菜单登录和注册按钮操作
        Button navHeaderLoginBtn = (Button)navigationHeaderView.findViewById(R.id.navHeaderLoginBtn); // 获取按钮资源
        navHeaderLoginBtn.setOnClickListener(new Button.OnClickListener(){ // 创建监听
            public void onClick(View view) {
                openLoginActivity();
            }
        });
        // 替换 Fragment 为主页，调用这个方法将不设置返回栈
        fragmentReplaceDefault(fmHome);
        // 侧滑菜单 Login Bar 状态同步
        sidebarSetLoginBarStatu();
    }

    /**
     * 按返回键时的操作
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.main_activity_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity_bar_menu, menu);
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
            fragmentReplaceDefault(fmHome);
        } else if (id == R.id.nav_sms_webhook) {
            fragmentReplaceDefault(fmSmshook);
        }

        hideSidebar(); // 收起侧滑菜单
        hideImm(); // 隐藏输入法键盘

        return true;
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
        /**
         * [NOTICE] fm.beginTransaction().addToBackStack(null).replace(mainContent, fmHome).commit();
         * .addToBackStack(null) 在程序最开始运行时就不必用了 不然想退出程序 必须按两次返回键 ┌(。Д。)┐
         * BackStack 返回栈
         */
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

    /**
     * 立刻收起侧滑菜单
     */
    public void hideSidebar(){
        // 找到 侧滑菜单 所在资源
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.main_activity_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START); // 收起侧滑菜单
        }
    }

    /**
     * 显示一条消息，快速
     * @param content 消息内容
     * @param setActionText setActionText
     * @param setActionListener setActionListener
     */
    @NonNull
    public void showOneMsg(String content, CharSequence setActionText, final View.OnClickListener setActionListener){
        Snackbar.make(getRootView(this), content, Snackbar.LENGTH_LONG).setAction(setActionText, setActionListener).show();
    }

    /**
     * 打开登录 Activity
     */
    public void openLoginActivity(){
        // hideSidebar(); // 收起侧滑菜单
        // hideImm(); // 隐藏输入法键盘

        Intent intent = new Intent();
        intent.setClass(MainActivity.this, LoginActivity.class);
        startActivityForResult(intent, activityIdCode); // 运行这个 Activity
    }

    /**
     * 所有的 Activity 对象的返回值都是由这个方法来接收
     * @param requestCode 表示的是启动一个 Activity 时传过去的 requestCode 值，仅用于标识身份
     * @param responseCode 表示的是启动后的 Activity 回传值时的 resultCode 值，仅用于标识身份
     * @param data 表示的是启动后的 Activity 回传过来的 Intent 对象
     * @see String http://blog.csdn.net/dinglang_2009/article/details/6888111
     */
    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent data) {
        // Log.i("父 Activity 已接收到代码为 "+responseCode+" 的响应", "请求代码："+requestCode+"，响应代码："+responseCode);
        super.onActivityResult(requestCode, responseCode, data);
        // 自己写逻辑
        if(requestCode == activityIdCode){
            switch (responseCode){
                // 登录响应
                case 1001:
                    /*boolean isLogined = data.getBooleanExtra("isLogined", false);
                    if(isLogined){
                        showOneMsg("登录成功...", null, null);
                    }else{
                        showOneMsg("登录失败...", null, null);
                    }*/
                    sidebarSetLoginBarStatu(); // 侧滑菜单 Login Bar 状态同步
                    break;


            }
        }
    }

    /**
     * 侧滑菜单 Login Bar 状态同步
     */
    public void sidebarSetLoginBarStatu(){
        LinearLayout loginBar = (LinearLayout) navigationHeaderView.findViewById(R.id.main_activity_sidebar_header_login_bar);
        LinearLayout loginedBar = (LinearLayout) navigationHeaderView.findViewById(R.id.main_activity_sidebar_header_logined_bar);
        ConfHelper config = new ConfHelper();
        if(config.getIsLogin()){ // 已登录
            loginBar.setVisibility(View.GONE); // 隐藏登录按钮条
            // 获取用户信息
            String phoneNumber = config.getInstance().getString(ConfHelper.UserPhoneNum_KeyName, "");
            // 设置注销按钮文字为用户登录手机号
            Button logoutBtn = (Button) navigationHeaderView.findViewById(R.id.navHeaderLogout);
            logoutBtn.setText(phoneNumber);
            loginedBar.setVisibility(View.VISIBLE); // 显示注销按钮条
            // 注销登陆条绑定事件
            logoutBtn.setOnClickListener(new Button.OnClickListener(){ // 创建监听
                public void onClick(View view) {
                    (new ConfHelper()).userLogout();
                    sidebarSetLoginBarStatuLogout();
                    Toast.makeText(MainActivity.this, "注销成功", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            sidebarSetLoginBarStatuLogout();
        }
    }

    public void sidebarSetLoginBarStatuLogout(){
        LinearLayout loginBar = (LinearLayout) navigationHeaderView.findViewById(R.id.main_activity_sidebar_header_login_bar);
        LinearLayout loginedBar = (LinearLayout) navigationHeaderView.findViewById(R.id.main_activity_sidebar_header_logined_bar);
        loginedBar.setVisibility(View.GONE); // 隐藏注销按钮条
        loginBar.setVisibility(View.VISIBLE); // 显示登录按钮条
    }
}