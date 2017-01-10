package com.taeven.anew;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vaibhav on 10/12/16.
 */
public class profile_adapter extends ArrayAdapter<profile_single_ele> {


    public profile_adapter(Context context, ArrayList<profile_single_ele> list) {
        super(context,0, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        profile_single_ele ele = getItem(position);
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.double_text_view,parent,false);
        }
        Log.d("initiating...","dsad");


        TextView title_=(TextView)convertView.findViewById(R.id.title_profile);
        TextView value=(TextView)convertView.findViewById(R.id.value_profile);
        title_.setText(ele.Title);
        value.setText(ele.Value);
        Log.d("completed ...","dasfsd");
        return convertView;
    }
}
