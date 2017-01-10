package com.taeven.anew.background;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by vaibhav on 21/12/16.
 */
public class submit_profile_background extends AsyncTask<JSONObject,Void,String> {
    private String result;
    private Context context;
    public submit_profile_background(Context context)
    {
        this.context = context;
    }

    @Override
    protected String doInBackground(JSONObject... jsonObjects) {

        try {
            String url = "http://trippyentrepreneur.com/android/submit.php";

            JSONObject jsonObject = jsonObjects[0];
            HttpClient client = new DefaultHttpClient();
            HttpPost post =new HttpPost(url);

            StringEntity entity = null;
           entity = new StringEntity("json="+ jsonObject.toString());

            post.setEntity(entity);
            post.setHeader( "content-type", "application/x-www-form-urlencoded");



            if(check_connection()) {

                HttpResponse response = client.execute(post);
                result = EntityUtils.toString(response.getEntity());
            }
            else
            {
                return "No connection Available";
            }
            Log.d("after bacj","fasdfasd");

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

        if(s.contentEquals("submission success"))
        {
            Toast.makeText(context,"profile submitted successfully",Toast.LENGTH_SHORT).show();

        }
        else
        {
            Log.d("result = ",s);
            Toast.makeText(context,"profile submission error",Toast.LENGTH_SHORT).show();
        }
    }


    private boolean check_connection()
    {
        ConnectivityManager connectivityManager =(ConnectivityManager)context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo =connectivityManager.getActiveNetworkInfo();
        return networkInfo!=null && networkInfo.isConnected();
    }
}
