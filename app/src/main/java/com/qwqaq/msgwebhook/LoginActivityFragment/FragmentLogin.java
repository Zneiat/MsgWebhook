package com.qwqaq.msgwebhook.LoginActivityFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.qwqaq.msgwebhook.ConfHelper;
import com.qwqaq.msgwebhook.HttpReqUtil;
import com.qwqaq.msgwebhook.LoginActivity;
import com.qwqaq.msgwebhook.R;
import com.zhy.http.okhttp.callback.StringCallback;
import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by Zneia on 2017/3/5.
 */

public class FragmentLogin extends Fragment {

    private View pragmentView;
    private EditText loginInputUser;
    private EditText loginInputPassword;
    private Button loginBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(R.string.frag_login); // 设置标题

        pragmentView  =  inflater.inflate(R.layout.login_activity_fragment_login, container, false);
        loginInputUser = (EditText) pragmentView.findViewById(R.id.loginInputUser);
        loginInputPassword = (EditText) pragmentView.findViewById(R.id.loginInputPassword);

        // 第一种方式
        loginBtn = (Button)pragmentView.findViewById(R.id.loginBtn);//获取按钮资源
        loginBtn.setOnClickListener(new Button.OnClickListener(){//创建监听
            public void onClick(View view) {
                loginBtnAction(view);
            }

        });
        return pragmentView;
    }


    private String result = "";
    /**
     * 登录按钮操作
     */
    public void loginBtnAction(View view){
        // 收起输入法
        ((LoginActivity) getActivity()).hideImm();

        HttpReqUtil ha = new HttpReqUtil();
        ha.run(loginInputUser.getText().toString(), loginInputPassword.getText().toString(), new StringCallback(){
            @Override
            public void onBefore(Request request, int id) {
                loadingUi("show");
                /*Toast.makeText(getActivity(), "loading...", Toast.LENGTH_SHORT).show();*/
            }

            @Override
            public void onAfter(int id) {
                /*Toast.makeText(getActivity(), "请求完毕", Toast.LENGTH_SHORT).show();*/
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                e.printStackTrace();
                result = "请求错误:\n" + e.getMessage();
                EditText en = (EditText) pragmentView.findViewById(R.id.LoginReqResult);
                en.setText(result.toCharArray(), 0, result.length());
            }

            @Override
            public void onResponse(String response, int id) {
                loadingUi("hide");
                result = "响应结果：\n" + response;
                EditText en = (EditText) pragmentView.findViewById(R.id.LoginReqResult);
                en.setText(result.toCharArray(), 0, result.length());
                // 显示测试按钮
                Button testBtn = (Button) pragmentView.findViewById(R.id.loginTestSave);
                testBtn.setVisibility(View.VISIBLE);
                testBtn.setOnClickListener(new Button.OnClickListener(){
                    public void onClick(View view) {
                        // 存储用户数据
                        ConfHelper ch = new ConfHelper();
                        String phoneNum = loginInputUser.getText().toString().trim();
                        String password = loginInputPassword.getText().toString().trim();
                        ch.userLogin(phoneNum, password, "http://tools.qwqaq.com/msgWebHook/receive.php", "phoneNum", "content", "timestamp");
                        Toast.makeText(getActivity(), "登录成功", Toast.LENGTH_SHORT).show();
                        ((LoginActivity) getActivity()).downDownDown();
                    }
                });
            }
        });
    }

    /**
     * 控制加载UI
     * @param type 改变状态
     */
    public void loadingUi(String type){
        RelativeLayout loginForm = (RelativeLayout) pragmentView.findViewById(R.id.loginForm);
        RelativeLayout loading = (RelativeLayout) pragmentView.findViewById(R.id.loginLoading);
        if(type.equals("show")){
            // 显示加载
            loading.setVisibility(View.VISIBLE);
            // 隐藏登录界面
            loginForm.setVisibility(View.GONE);
        }else if(type.equals("hide")){
            // 隐藏加载
            loading.setVisibility(View.GONE);
            // 显示登录界面
            loginForm.setVisibility(View.VISIBLE);
        }
    }
}