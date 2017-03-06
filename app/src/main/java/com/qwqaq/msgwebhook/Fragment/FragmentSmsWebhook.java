package com.qwqaq.msgwebhook.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.qwqaq.msgwebhook.R;

/**
 * Created by Zneia on 2017/3/5.
 */

public class FragmentSmsWebhook extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  =  inflater.inflate(R.layout.fragment_sms_webhook, container, false);
        getActivity().setTitle("Sms Webhook 配置");
        return view;
    }
}