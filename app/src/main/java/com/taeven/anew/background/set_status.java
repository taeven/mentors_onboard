package com.taeven.anew.background;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import com.taeven.anew.fragment.status;
import com.taeven.anew.shared_preference;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by vaibhav on 14/1/17.
 */
public class set_status extends AsyncTask<Void,Void,String>{
    private Context context;
    private String result;
    public set_status(Context context)
    {
        this.context = context;
    }
    @Override
    protected String doInBackground(Void... voids) {
        shared_preference pref = new shared_preference(context);
        JSONObject user_info = new JSONObject();
        try {
            user_info.put("username",pref.getUsername());
            user_info.put("password",pref.getPassword());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String urlString ="http://trippyentrepreneur.com/android/get_profile_status.php";
        String data = "json="+user_info.toString();
        HttpClient client = new DefaultHttpClient();
        HttpPost post =new HttpPost(urlString);

        post.setHeader( "content-type", "application/x-www-form-urlencoded");

        try {
            post.setEntity(new StringEntity(data));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Log.d(null,"mentors start");
        if(check_connection()) {

            HttpResponse response = null;
            try {
                response = client.execute(post);
                result = EntityUtils.toString(response.getEntity());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }
        else
        {
            return "error conn";
        }
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d("data ",s);
        if (s.contains("error conn"))
            Toast.makeText(context,"check your internet connection!!",Toast.LENGTH_SHORT).show();
        else if (s.contains("error"))
            Log.d("error","error from php");
        else
        {

            try {
                JSONObject jsonObject = new JSONObject(s);
                float percentage = (float)jsonObject.getDouble("per");
                String submitted = jsonObject.getString("submit");
                status.percentage.setText("("+percentage+"%)");
                if(percentage == 100.00)
                {
                    status.profile_coomplete.toggle();
                    if(submitted.contentEquals("1")) {
                        status.profile_submittted.toggle();
                        status.profile_evaluation.toggle();
                        status.result.setText(jsonObject.getString("result"));
                    }
                }


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
