package com.taeven.anew.fragment;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.taeven.anew.background.edit_profile_background;
import com.taeven.anew.shared_preference;

import android.widget.ProgressBar;

import com.taeven.anew.R;

import org.json.JSONException;
import org.json.JSONObject;


public class edit_profile extends Fragment {

    private String shared_email,shared_password;
    private ProgressBar progressBar;


    private View view;
    private Context context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragmen
        View view= inflater.inflate(R.layout.fragment_edit_profile, container, false);
        progressBar = (ProgressBar)view.findViewById(R.id.retrieve_data_progressprogreebar);
        progressBar.setVisibility(View.VISIBLE);


         context = getActivity();
        getActivity().setTitle("Edit profile");
        
        this.view = view;
        do_work();


        return view;
    }




    public void do_work() {

        JSONObject credentials =new JSONObject();
        shared_preference sp =new shared_preference(context);
        shared_email = sp.getUsername();
        shared_password = sp.getPassword();
        try {
            credentials.put("password",shared_password);
            credentials.put("username",shared_email);
            edit_profile_background edit = new edit_profile_background(view,context);
            edit.execute(new JSONObject[]{credentials});
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



}
