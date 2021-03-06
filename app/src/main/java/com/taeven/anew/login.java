package com.taeven.anew;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.taeven.anew.background.login_json;

import org.json.JSONException;
import org.json.JSONObject;

public class login extends AppCompatActivity {

    public TextView register;
    public Button login_button;
    public EditText username;
    public ImageView show_hide;
    public String username_text;
    public String password_text;
    public EditText password;
    private Boolean visibility=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("login");

        username = (EditText)findViewById(R.id.username_login);
        password = (EditText)findViewById(R.id.password_login);
        create_new();
//        check_conn();
        login();
    }

    public void login()
    {
        show_hide=(ImageView)findViewById(R.id.show_pwd_login);
        show_hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(visibility)
                {
                    visibility=false;
                    show_hide.setImageResource(R.drawable.visible);
                    password.setTransformationMethod(new PasswordTransformationMethod());
                }
                else
                {
                    visibility=true;
                    show_hide.setImageResource(R.drawable.blind);
                    password.setTransformationMethod(null);
                }
            }
        });

        login_button=(Button)findViewById(R.id.but_login);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check_credentials())
                {
                    ///to be done;
//                    Intent i = new Intent("com.taeven.anew.after_login");
//                    startActivity(i);
                }

            }
        });



    }
    public void create_new()
    {
        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent("com.taeven.anew.register");
                startActivity(i);
            }
        });
    }

    public Boolean check_credentials()
    {
        JSONObject jsonObject = new JSONObject();

        username_text=username.getText().toString();
        password_text=password.getText().toString();
        if(username_text.isEmpty())
        {
            Toast.makeText(this,getString(R.string.empty_user),Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (password_text.isEmpty())
        {
            Toast.makeText(this,getString(R.string.empty_pswd),Toast.LENGTH_SHORT).show();
            return false;
        }
        else
        {
            try {
                jsonObject.put("username",username_text);
                jsonObject.put("password",password_text);
                login_json send_request = new login_json(this);
                send_request.execute(new JSONObject[]{jsonObject});
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return true;
        }

    }

}
