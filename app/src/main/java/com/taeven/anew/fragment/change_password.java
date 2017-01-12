package com.taeven.anew.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.taeven.anew.R;
import com.taeven.anew.background.change_password_background;
import com.taeven.anew.shared_preference;

import org.json.JSONException;
import org.json.JSONObject;

public class change_password extends Fragment {
    private EditText old_pass,new_pass,re_new_pass;
    private Button change_pass;
    private String shared_password,shared_email;
    private String old_pass_text,new_pass_text,re_new_pass_text;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        setViews(view);
        setListeners();
        getActivity().setTitle("Change password");
        return view;
    }
    private void setViews(View view)
    {
        old_pass = (EditText)view.findViewById(R.id.old_pass_change);
        new_pass = (EditText)view.findViewById(R.id.new_pass_change);
        re_new_pass = (EditText)view.findViewById(R.id.re_new_pass_change);
        change_pass = (Button) view.findViewById(R.id.change_pass_butt);
    }
    private void setListeners()
    {
        change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSharedPassword();
                if(check_credentials())
                {
                    change_password_background change_pass = new change_password_background(getActivity());
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("password",old_pass_text);
                        jsonObject.put("new_password",new_pass_text);
                        jsonObject.put("email",shared_email);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    change_pass.execute( new JSONObject[]{jsonObject});
                    old_pass.setText(null);
                    new_pass.setText(null);
                    re_new_pass.setText(null);
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content,new setting())
                            .commit();

                }


            }
        });
    }
    private void getSharedPassword()
    {
        shared_preference sp = new shared_preference(getActivity());
        shared_password=sp.getPassword();
        shared_email = sp.getUsername();
    }
    private boolean check_credentials()
    {
        old_pass_text=old_pass.getText().toString();
        new_pass_text=new_pass.getText().toString();
        re_new_pass_text=re_new_pass.getText().toString();
        if (old_pass_text.isEmpty())
        {
            Toast.makeText(getActivity(),"Enter the old password",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(new_pass_text.isEmpty()||new_pass_text.length()<8)
        {
            Toast.makeText(getActivity(),"password is too short",Toast.LENGTH_SHORT).show();
            new_pass.setText(null);
            re_new_pass.setText(null);
            return false;
        }
        else if(re_new_pass_text.isEmpty()||!re_new_pass_text.contentEquals(new_pass_text))
        {
            Toast.makeText(getActivity(),"password do not match",Toast.LENGTH_SHORT).show();
            re_new_pass.setText(null);
            return false;
        }
        else if(!shared_password.contentEquals(old_pass_text))
        {
            Toast.makeText(getActivity(),"check your old password",Toast.LENGTH_SHORT).show();
            old_pass.setText(null);
            return false;
        }
        else return true;


    }


}
