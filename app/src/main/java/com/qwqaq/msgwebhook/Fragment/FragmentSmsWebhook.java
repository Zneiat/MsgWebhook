package com.qwqaq.msgwebhook.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.qwqaq.msgwebhook.R;

/**
 * Created by Zneia on 2017/3/5.
 */

public class FragmentSmsWebhook extends Fragment {

    private View view;
    private EditText loginInputUser;
    private EditText loginInputPassword;
    private Button loginBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(R.string.frag_sms_webhook); // 设置标题
        ((NavigationView) getActivity().findViewById(R.id.nav_view)).setCheckedItem(R.id.nav_sms_webhook); // 侧滑菜单选中

        view  =  inflater.inflate(R.layout.fragment_sms_webhook, container, false);
        loginInputUser = (EditText) view.findViewById(R.id.loginInputUser);
        loginInputPassword = (EditText) view.findViewById(R.id.loginInputPassword);

        // 第一种方式
        loginBtn = (Button)view.findViewById(R.id.loginBtn);//获取按钮资源
        loginBtn.setOnClickListener(new Button.OnClickListener(){//创建监听
            public void onClick(View view) {
                loginBtn(view);
            }

        });
        return view;
    }

    /**
     * 登录按钮操作
     */
    public void loginBtn(View view){
        loadingUi("show");
    }

    /**
     * 控制加载UI
     * @param type 改变状态
     */
    public void loadingUi(String type){
        RelativeLayout loginForm = (RelativeLayout) view.findViewById(R.id.loginForm);
        RelativeLayout loading = (RelativeLayout) view.findViewById(R.id.loginLoading);
        if(type.equals("show")){
            // 显示加载
            loading.setVisibility(View.VISIBLE);
            // 隐藏登录界面
            loginForm.setVisibility(View.INVISIBLE);
        }else if(type.equals("hide")){
            // 隐藏加载
            loading.setVisibility(View.INVISIBLE);
            // 显示登录界面
            loginForm.setVisibility(View.VISIBLE);
        }
    }
}