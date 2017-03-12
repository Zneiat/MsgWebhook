package com.qwqaq.msgwebhook.MainActivityFragment;

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

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(R.string.frag_sms_webhook); // 设置标题

        view  =  inflater.inflate(R.layout.main_activity_fragment_sms_webhook, container, false);

        return view;
    }
}