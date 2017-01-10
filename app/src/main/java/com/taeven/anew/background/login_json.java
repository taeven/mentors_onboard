package com.taeven.anew.background;

/**
 * Created by vaibhav on 18/12/16.
 */

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.taeven.anew.background.getImageBackground;
import com.taeven.anew.shared_preference;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
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
 * Created by vaibhav on 17/12/16.
 */
public class login_json extends AsyncTask<JSONObject,Void,String> {
    private String url;
    private JSONObject jsonObject;
    private String result;
    Context context;
    public login_json(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(JSONObject... jsonObjects) {

        try {
            url ="http://trippyentrepreneur.com/android/login.php";

             jsonObject =jsonObjects[0];
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


        if(result.contains("login success"))
        {
            Intent i = new Intent("com.taeven.anew.after_login");
            shared_preference sp =new shared_preference(context);
            try {
                sp.savePreference(jsonObject.getString("username"),jsonObject.getString("password"),result.substring(13));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            getImageBackground getImage = new getImageBackground(context);
            getImage.execute(new JSONObject[]{jsonObject});
            context.startActivity(i);
        }
    }

}
