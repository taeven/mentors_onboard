package com.taeven.anew.background;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.taeven.anew.shared_preference;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by vaibhav on 22/12/16.
 */
public class save_profile extends AsyncTask<JSONObject,Void,String> {
    private String url,result;
    private Context context;
    private JSONObject jsonObject;

    save_profile(Context context)
    {
        this.context = context;
    }
    @Override
    protected String doInBackground(JSONObject... jsonObjects) {

        try {
            url ="http://www.trippyentrepreneur.com/android/update_profile.php";

            jsonObject =jsonObjects[0];
            HttpClient client = new DefaultHttpClient();
            HttpPost post =new HttpPost(url);

            StringEntity entity = null;
            entity = new StringEntity("json="+jsonObject.toString());

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
        Log.d("sent",jsonObject.toString());

        if(s.contentEquals("update success"))
        {
            Toast.makeText(context,"profile updated successfully",Toast.LENGTH_SHORT).show();


        }
        else
        {
            Log.d("result = ",s);
            Toast.makeText(context,"profile is not changed",Toast.LENGTH_SHORT).show();
        }
    }
    private boolean check_connection()
    {
        ConnectivityManager connectivityManager =(ConnectivityManager)context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo =connectivityManager.getActiveNetworkInfo();
        return networkInfo!=null && networkInfo.isConnected();
    }
}
