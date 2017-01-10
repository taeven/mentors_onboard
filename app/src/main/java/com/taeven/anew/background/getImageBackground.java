package com.taeven.anew.background;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Base64;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by vaibhav on 23/12/16.
 */
public class getImageBackground extends AsyncTask<JSONObject,Void,String> {
    private JSONObject jsonObject;
    private String result;
    private Context context;

    getImageBackground(Context context)
    {
        this.context = context;
    }
    @Override
    protected String doInBackground(JSONObject... jsonObjects) {

        try {
            String url ="http://trippyentrepreneur.com/android/get_image.php";

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

        return null;

    }

    private boolean check_connection()
    {
        ConnectivityManager connectivityManager =(ConnectivityManager)context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo =connectivityManager.getActiveNetworkInfo();
        return networkInfo!=null && networkInfo.isConnected();
    }
    @Override
    protected void onPostExecute(String s) {

        byte[] bitmap = Base64.decodeBase64(result.getBytes());
        Bitmap bitmap1 = BitmapFactory.decodeByteArray(bitmap, 0, bitmap.length);
        saveImage(context,bitmap1);



//        Toast.makeText(context,result,Toast.LENGTH_SHORT).show();

    }

    public void saveImage(Context context, Bitmap b) {
        String name = "profile.jpg";
        FileOutputStream out;
        try {
            out = context.openFileOutput(name, Context.MODE_PRIVATE);

            b.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
