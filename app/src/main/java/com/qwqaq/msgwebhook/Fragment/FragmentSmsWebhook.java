package com.qwqaq.msgwebhook.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.qwqaq.msgwebhook.R;

/**
 * Created by Zneia on 2017/3/5.
 */

public class FragmentSmsWebhook extends Fragment {

    private EditText loginInputUser;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  =  inflater.inflate(R.layout.fragment_sms_webhook, container, false);
        getActivity().setTitle(R.string.frag_sms_webhook); // 设置标题
        loginInputUser = (EditText) view.findViewById(R.id.loginInputUser);
        return view;
    }

    /**
     * 登录按钮操作
     */
    public void loginBtn(){

    }
}