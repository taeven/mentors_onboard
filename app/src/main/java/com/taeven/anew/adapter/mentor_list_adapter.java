package com.taeven.anew.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.taeven.anew.R;
import com.taeven.anew.mentor;

import org.apache.commons.codec.binary.Base64;

import java.util.ArrayList;

/**
 * Created by vaibhav on 2/1/17.
 */
public class mentor_list_adapter extends ArrayAdapter<mentor> {
    public mentor_list_adapter(Context context, ArrayList<mentor> mentors) {
        super(context, 0,mentors);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        mentor person = getItem(position);
        if(convertView == null)
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.mentors_details_single,parent,false);
        TextView name = (TextView) convertView.findViewById(R.id.name_mentors);
        ImageView img = (ImageView) convertView.findViewById(R.id.profile_pic_mentor);
        name.setText(person.getName());
////
//        byte[] b= Base64.decodeBase64(person.getImage().getBytes());
//                    Bitmap decodeByteArray = BitmapFactory.decodeByteArray(b,0,b.length);
        if(person.getImage()!=null)
        img.setImageBitmap(person.getImage());
        else
        img.setImageResource(R.drawable.default_dp);

        return convertView;

    }
}
