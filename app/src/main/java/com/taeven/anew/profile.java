package com.taeven.anew;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class profile extends Fragment {
    ArrayList<profile_single_ele> list;
    ListView vertical;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);
        list = new ArrayList<profile_single_ele>();
        prepare_content_profile();
        profile_adapter adapter = new profile_adapter(getActivity(),list);
        vertical=(ListView)view.findViewById(R.id.vertical_view);
        vertical.setAdapter(adapter);


        return view;
    }

    public void prepare_content_profile()
    {
        profile_single_ele ele =new profile_single_ele("Name","Vaibhav Sisodiya");
        list.add(ele);
        ele = new profile_single_ele("email","vaibhav@fdsafa");
        list.add(ele);
        ele = new profile_single_ele("dob","11031997");
        list.add(ele);
    }


}
