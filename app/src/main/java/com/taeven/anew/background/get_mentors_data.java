package com.taeven.anew.background;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.taeven.anew.after_login;
import com.taeven.anew.manage_temp_storage;
import com.taeven.anew.testing;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by vaibhav on 4/1/17.
 */
public class get_mentors_data  extends AsyncTask<Integer, Void,String> {
    private String urlString;
    private JSONObject jsonObject;
    private String result;
    private Context context;
    public get_mentors_data(Context context)
    {
        this.context = context;
    }


    @Override
    protected String doInBackground(Integer... integers) {

        try {


            urlString ="http://trippyentrepreneur.com/android/get_mentors.php?skip="+integers[0];


            HttpClient client = new DefaultHttpClient();
            HttpPost post =new HttpPost(urlString);



            Log.d(null,"mentors start");
            if(check_connection()) {

                HttpResponse response = client.execute(post);
                result = EntityUtils.toString(response.getEntity());
            }
            else
            {
                return "No connection Available";
            }
            Log.d("after bacj","get mentor");

        } catch (UnsupportedEncodingException e) {
            Log.d("1st","");
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            Log.d("2nd","");

            e.printStackTrace();
        } catch (IOException e) {
            Log.d("3rd","");
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String s) {

        if(s.contentEquals("update success"))
        {
            Toast.makeText(context,"password changed successfully",Toast.LENGTH_SHORT).show();


        }
        else
        {
            Log.d("result = ",s);

            try {
                JSONArray obj = new JSONArray(s);
                int i=0;
                while (!obj.isNull(i))
                {
                    JSONObject tmp = obj.getJSONObject(i);
                    manage_temp_storage manager = new manage_temp_storage(context);
                    Log.i("manager get_mentor","gvdfgsfd");
                    testing temp = new testing();
                    temp.name=tmp.getString("name");
                    temp.image=tmp.getString("image");
                    if(temp.name!="null")
                    manager.save_data(temp);
                    Log.i("after save","adsfsdsad");
                    i++;


                }
                after_login.progress=false;
                after_login.home_butt.performClick();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
    private boolean check_connection()
    {
        ConnectivityManager connectivityManager =(ConnectivityManager)context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo =connectivityManager.getActiveNetworkInfo();
        return networkInfo!=null && networkInfo.isConnected();
    }
}