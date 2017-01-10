package com.taeven.anew.background;

import android.content.Context;
import android.content.Intent;
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
 * Created by vaibhav on 17/12/16.
 */
public class register_json extends AsyncTask<JSONObject,Void,String> {
    private String url;
    private String result;
    Context context;
    public register_json(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(JSONObject... jsonObjects) {

        try {
            url ="http://trippyentrepreneur.com/android/register.php";

            JSONObject jsonObject =jsonObjects[0];
            HttpClient client = new DefaultHttpClient();
            HttpPost post =new HttpPost(url);

            StringEntity entity = null;
//            post.setHeader("Accept", "application/json");
//            post.setHeader("Content-type", "application/json");
            entity = new StringEntity("json="+jsonObject.toString());

            post.setEntity(entity);
           post.setHeader( "content-type", "application/x-www-form-urlencoded");



            if(check_connection()) {

                HttpResponse response = client.execute(post);
//             result = org.apache.http.util.EntityUtils.toString(response.getEntity());
                result = EntityUtils.toString(response.getEntity());

            }
            else
            {
                return "No Connection !!";
            }
            Log.d("after bacj","fasdfasd");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
/*
HttpURLConnection httpURLConnection =(HttpURLConnection)url.openConnection();
httpURLConnection.setDoOutput(true);
httpURLConnection.setDoInput(true);
httpURLConnection.setRequestMethod("POST");
httpURLConnection.setConnectTimeout(10000);
httpURLConnection.setReadTimeout(10000);
httpURLConnection.connect();
OutputStreamWriter writer=new OutputStreamWriter(httpURLConnection.getOutputStream());
String data = "json="+jsonObject.toString();
data = URLEncoder.encode(data,"utf-8");
writer.write(data);
InputStreamReader reader = new InputStreamReader(httpURLConnection.getInputStream());
String line = reader.rea
*/

        return result;

    }

    private boolean check_connection()
    {
        ConnectivityManager connectivityManager =(ConnectivityManager)context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo =connectivityManager.getActiveNetworkInfo();
        return networkInfo!=null && networkInfo.isConnected();
    }
    @Override
    protected void onPostExecute(String result) {

        Toast.makeText(context,result,Toast.LENGTH_SHORT).show();
        if(result.contentEquals("register success")) {
            Intent i = new Intent("com.taeven.anew.login");
            context.startActivity(i);

        }
    }
}
