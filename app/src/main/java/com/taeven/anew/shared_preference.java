package com.taeven.anew;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by vaibhav on 20/12/16.
 */
public class shared_preference {
    private String PREFERENCE;
    private String username;
    private String name;
    private String password;
    private SharedPreferences sharedPreference;
    public shared_preference(Context context)
    {
        PREFERENCE = "shared_preference";
        sharedPreference = context.getSharedPreferences(PREFERENCE,context.MODE_PRIVATE);
        password = getPassword();
        username = getUsername();

        name = getName();

    }

    public String getUsername()
    {
        return sharedPreference.getString("username",null);
    }

    public String getPassword()
    {
        return sharedPreference.getString("password",null);
    }

    public String getName()
    {
        return sharedPreference.getString("name",null);
    }
    public boolean checkExist()
    {
        if(username==null || password== null)
            return false;
        else
            return true;

    }
    public void savePreference(String username,String password,String name)
    {
        this.username = username;
        this.password = password;
        this.name = name;
        SharedPreferences.Editor editor =sharedPreference.edit();
        editor.putString("username",username);
        editor.putString("password",password);
        editor.putString("name",name);
        editor.commit();
    }
    public void erase_preference()
    {
        sharedPreference.edit().clear().commit();
    }

}
