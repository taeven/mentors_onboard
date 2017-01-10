package com.taeven.anew.background;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by vaibhav on 22/12/16.
 */
public class upload_image_background extends AsyncTask<JSONObject,Void,String> {

    private JSONObject jsonObject;
    private String result;
    private Context context;
    private Bitmap image;


    public upload_image_background(Context context, Bitmap bitmap)
    {
        this.context = context;
        this.image = bitmap;
    }

    @Override
    protected String doInBackground(JSONObject... jsonObjects) {


        String urlString = "http://trippyentrepreneur.com/android/save_image.php";

            jsonObject = jsonObjects[0];
        if(check_connection()) {
            try {
                URL url = new URL(urlString);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream()));
                String data = URLEncoder.encode("json","UTF-8")+"="+URLEncoder.encode(jsonObject.toString());

                writer.write(data);
                writer.close();

                BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                result = reader.readLine();
                reader.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d("result",s);

        if(s.contentEquals("updated sucessfully"))
        {
            Log.d("result",s);
            Toast.makeText(context,"profile picture uploaded successfully",Toast.LENGTH_SHORT).show();
            saveImage(context,image);

        }
        else
        {
            Log.d("result = ",s);
            Toast.makeText(context,"picture is not uploaded",Toast.LENGTH_SHORT).show();
        }
    }

    public void saveImage(Context context, Bitmap b){
        String name="profile.jpg";
        FileOutputStream out;
        try {
            out = context.openFileOutput(name, Context.MODE_PRIVATE);

            b.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private boolean check_connection()
    {
        ConnectivityManager connectivityManager =(ConnectivityManager)context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo =connectivityManager.getActiveNetworkInfo();
        return networkInfo!=null && networkInfo.isConnected();
    }
}
