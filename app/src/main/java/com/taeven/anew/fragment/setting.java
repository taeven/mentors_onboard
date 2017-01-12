package com.taeven.anew.fragment;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.taeven.anew.R;
import com.taeven.anew.background.upload_image_background;
import com.taeven.anew.shared_preference;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class setting extends Fragment {
    private TextView change_pass,change_pic,logout;
    private ImageView profile_pic;
    private int PICK_IMAGE_REQUEST =1 ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_setting, container, false);
        setviews(view);
        setListeners();
        getActivity().setTitle("Settings");
        setProfilePicture();
        return view;
    }
    private void setviews(View view)
    {
        change_pass = (TextView) view.findViewById(R.id.pass_change);
        change_pic = (TextView) view.findViewById(R.id.profile_change);
        logout = (TextView) view.findViewById(R.id.logout);
        profile_pic = (ImageView)view.findViewById(R.id.profile_pic_setting);
    }

    private void setProfilePicture()
    {
        Bitmap bitmap = getImageBitmap(getActivity());
        if(bitmap!=null)
        profile_pic.setImageBitmap(bitmap);


    }

    private void setListeners()
    {
        change_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent.createChooser(intent,"Select Picture"),PICK_IMAGE_REQUEST);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                shared_preference sp = new shared_preference(getActivity());
                sp.erase_preference();

                File dir = getActivity().getFilesDir();
                File file = new File(dir, "profile.jpg");
                boolean deleted = file.delete();
              if(!deleted)
              {
                  Log.d("delete","notdeleted");

              }
                else
              {
                  Log.d("delete","deleted");
              }

                Intent intent = new Intent("com.taeven.anew.login");
                getActivity().startActivity(intent);
            }
        });
        change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content,new change_password())
                        .commit();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK && data!=null && data.getData()!=null)
        {
            Uri uri = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                ImageView imageView = (ImageView) getView().findViewById(R.id.profile_pic_setting);
                imageView.setImageBitmap(bitmap);



                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                bitmap.compress(Bitmap.CompressFormat.JPEG,60, stream); //compress to which format you want.
                String image_str = Base64.encodeToString(stream.toByteArray(),Base64.DEFAULT);

                JSONObject jsonString=new JSONObject();
                try {
                    jsonString.put("email",new shared_preference(getActivity()).getUsername());
                    jsonString.put("password",new shared_preference(getActivity()).getPassword());
                    jsonString.put("img", image_str);
                    upload_image_background upload = new upload_image_background(getActivity(),bitmap);
                    upload.execute( new JSONObject[]{jsonString});
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
            // Log.d(TAG, String.valueOf(bitmap));


        }
        else
        {
            Log.d("errpor","if notExecuted");
        }
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
