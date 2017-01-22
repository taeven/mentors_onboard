package com.taeven.anew.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.taeven.anew.R;
import com.taeven.anew.background.submit_profile_background;
import com.taeven.anew.shared_preference;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;

public class profile_menu extends Fragment {
    private TextView edit_profile,submit_profile,contact_us,name;
    private ImageView profile_pic;
    private ProgressBar progressImg;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile_menu, container, false);
        setviews(view);
        setListeners();
        getActivity().setTitle("My Profile");
        setProfilePicture();
        return view;
    }
    private void setviews(View view)
    {
        edit_profile = (TextView) view.findViewById(R.id.edit_profile);
        submit_profile = (TextView) view.findViewById(R.id.submit_profile);
        contact_us = (TextView) view.findViewById(R.id.contact_us);
        name = (TextView) view.findViewById(R.id.name_prof_menu);
        profile_pic = (ImageView) view.findViewById(R.id.profile_pic);
        progressImg = (ProgressBar) view.findViewById(R.id.progress_img);

        setname();
    }
    private void setname()
    {
        name.setText(new shared_preference(getActivity()).getName());
    }

    private void setListeners()
    {
        final View.OnClickListener positive_response = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject jsonObject = new JSONObject();
                shared_preference sp =new shared_preference(getActivity());
                try {
                    jsonObject.put("email",sp.getUsername());
                    jsonObject.put("password",sp.getPassword());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                submit_profile_background submit =new submit_profile_background(getActivity());
                submit.execute( new JSONObject[]{jsonObject});
            }
        };




        progressImg.setVisibility(View.INVISIBLE);
        submit_profile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final AlertDialog dialog = new AlertDialog.Builder(getActivity())
                        .setTitle("Warning!!")
                        .setMessage("Once you submit profile for evaluation, you can not edit it later.")
                        .setPositiveButton("Submit",null)
                        .setNegativeButton("Cancel",null)
                        .create();
                dialog.show();
                dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(positive_response);
                dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });



            }
        });
        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content,new edit_profile())
                        .commit();
            }
        });

    }

    private void setProfilePicture()
    {
        Bitmap bitmap = getImageBitmap(getActivity());
        if(bitmap!=null)
            profile_pic.setImageBitmap(bitmap);


    }



    public Bitmap getImageBitmap(Context context){
        String name="profile.jpg";
        try{
            FileInputStream fis = context.openFileInput(name);
            Bitmap b = BitmapFactory.decodeStream(fis);
            fis.close();
            return b;
        }
        catch(Exception e){

        }
        return null;
    }


}
