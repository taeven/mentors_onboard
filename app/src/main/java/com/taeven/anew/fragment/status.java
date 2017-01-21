package com.taeven.anew.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.taeven.anew.R;
import com.taeven.anew.background.set_status;

public class status extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_status, container, false);
        getActivity().setTitle("Status");

        set_status stat = new set_status(getActivity());
        stat.execute();
        return view;
    }

}
