package com.taeven.anew;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import com.taeven.anew.background.get_mentors_data;
import com.taeven.anew.fragment.home;
import com.taeven.anew.fragment.profile_menu;
import com.taeven.anew.fragment.refer;
import com.taeven.anew.fragment.setting;
import com.taeven.anew.fragment.status;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class after_login extends AppCompatActivity {
    public static ObjectOutputStream outputStream;
    public static int count = 1;
    public static boolean progress = true;

    public static ImageView home_butt,profile_butt,status_butt,refer_butt,setting_butt;
    int active_now;

    @Override
    protected void onStart() {
        File file = new File(getFilesDir(),"temp_storage.data");
        file.delete();

        try {

            FileOutputStream fileOutputStream=openFileOutput("temp_storage.data",MODE_APPEND);
            outputStream = new ObjectOutputStream(fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onStart();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);
        setTitle("Company name");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Toolbar toolbar_bottom = (Toolbar) findViewById(R.id.toolbar_down);
       // toolbar_bottom.inflateMenu(R.menu.down_toolbar_menu);
        active_now = R.id.home_img_view;
        setViews();
        Log.d("view set","done");
        setListeners();
        Log.d("listener set","done");
        home_butt.performClick();
        get_mentors_data mentors_data = new get_mentors_data(this);
        mentors_data.execute(0);

    }

    private void setViews()
    {
        home_butt = (ImageView)findViewById(R.id.home_img_view);
        profile_butt = (ImageView)findViewById(R.id.profile_img_view);
        status_butt = (ImageView)findViewById(R.id.stat_img_view);
        refer_butt = (ImageView)findViewById(R.id.refer_img_view);
        setting_butt = (ImageView)findViewById(R.id.setting_img_view);

    }



    private void reset_others()
    {

        switch (active_now)
        {
            case R.id.home_img_view:
                home_butt.setImageResource(R.drawable.home_unselected);


                break;
            case R.id.profile_img_view:
                profile_butt.setImageResource(R.drawable.profile_unselected);

                break;
            case R.id.stat_img_view:
                status_butt.setImageResource(R.drawable.status_unselected);

                break;
            case R.id.refer_img_view:
                refer_butt.setImageResource(R.drawable.refer_unselected);

                break;
            case R.id.setting_img_view:
                setting_butt.setImageResource(R.drawable.setting_unselected);

                break;

        }

    }
    private void setListeners()
    {
        home_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset_others();
                active_now = R.id.home_img_view;
                home_butt.setImageResource(R.drawable.home_selected);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content,new home())
                        .commit();

            }
        });
        Log.d(" set","done");
        profile_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset_others();
                active_now = R.id.profile_img_view;
                profile_butt.setImageResource(R.drawable.profile_selected);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content,new profile_menu())
                        .commit();

            }
        });
        Log.d(" set","done");
        status_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset_others();
                active_now = R.id.stat_img_view;
                status_butt.setImageResource(R.drawable.status_selected);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content,new status())
                        .commit();

            }
        });
        refer_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset_others();
                active_now = R.id.refer_img_view;
                refer_butt.setImageResource(R.drawable.refer_selected);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content,new refer())
                        .commit();

            }
        });
        Log.d(" set","done");
        setting_butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset_others();
                active_now = R.id.setting_img_view;
                setting_butt.setImageResource(R.drawable.setting_selected);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content,new setting())
                        .commit();

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.trple_dot,menu);

        return true;
    }


}
