package com.fukai.dianping_client.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fukai.dianping_client.R;

/**
 * Created by fukai on 2015/11/25.
 */
public class FragmentSearch extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_index,null);
        return view;
    }
}
