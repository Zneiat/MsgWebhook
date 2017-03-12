package com.qwqaq.msgwebhook.MainActivityFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qwqaq.msgwebhook.ConfHelper;
import com.qwqaq.msgwebhook.MainActivity;
import com.qwqaq.msgwebhook.R;

/**
 * Created by Zneia on 2017/3/5.
 */

public class FragmentHome extends Fragment {

    private boolean loginChecked = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  =  inflater.inflate(R.layout.main_activity_fragment_home, container, false);
        getActivity().setTitle(R.string.app_name); // 设置标题
        // ((NavigationView) getActivity().findViewById(R.id.nav_view)).setCheckedItem(R.id.nav_sms_webhook); // 侧滑菜单选中

        ConfHelper confHelper = new ConfHelper();

        if(!loginChecked) {
            if(!confHelper.getIsLogin()) {
                MainActivity parentActivity = (MainActivity) getActivity();
                parentActivity.openLoginActivity(); // 打开登录 Activity
            }
            loginChecked = true;
        }

        return view;
    }
}