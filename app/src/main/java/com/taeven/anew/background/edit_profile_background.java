package com.taeven.anew.background;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.taeven.anew.R;

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
 * Created by vaibhav on 21/12/16.
 */
public class edit_profile_background extends AsyncTask<JSONObject,Void,JSONObject> {
    private View view;
    private EditText college,education,experience_proj,branch_spec,experience,spec_offer,interest,about,
            language,price,session_time;
    private String college_text,education_text,experience_proj_text,branch_spec_text,experience_text,spec_offer_text,
            interest_text,result,about_text,language_text,price_text,session_text,url;
    private ProgressBar progressBar;
    private FloatingActionButton send_button;
    private Context context;
    private JSONObject all_data;

    public edit_profile_background(View view, Context context)
    {
        this.context = context;
        this.view = view;
    }
    @Override
    protected JSONObject doInBackground(JSONObject... jsonObjects) {
        JSONObject credentials =jsonObjects[0];
        try {

            url ="http://trippyentrepreneur.com/android/retrieve_data.php";

            HttpClient client = new DefaultHttpClient();
            HttpPost post =new HttpPost(url);

            StringEntity entity = null;
            entity = new StringEntity("json="+credentials.toString());

            post.setEntity(entity);
            post.setHeader( "content-type", "application/x-www-form-urlencoded");



            if(check_connection()) {

                HttpResponse response = client.execute(post);
                result = EntityUtils.toString(response.getEntity());
                JSONArray array = new JSONArray(result);
                all_data = array.getJSONObject(0);
                return all_data;

            }

            Log.d("after bacj","fasdfasd");
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {

        Log.d("this",all_data.toString());
        setViews();
        set_text();
        set_edit_text();

        progressBar.setVisibility(View.INVISIBLE);
    }

    public void setViews()
    {
        college = (EditText)view.findViewById(R.id.college_edit);
        education = (EditText)view.findViewById(R.id.education_edit);
        experience = (EditText)view.findViewById(R.id.experience_field_edit);
        experience_proj = (EditText)view.findViewById(R.id.proj_inter_edit);
        branch_spec = (EditText)view.findViewById(R.id.branch_spec_edit);
        spec_offer = (EditText)view.findViewById(R.id.spec_offer_edit);
        interest = (EditText)view.findViewById(R.id.interest_edit);
        about = (EditText)view.findViewById(R.id.about_edit);
        language = (EditText) view.findViewById(R.id.language_edit);
        price = (EditText)view.findViewById(R.id.price_edit);
        session_time = (EditText)view.findViewById(R.id.session_time);
        progressBar = (ProgressBar)view.findViewById(R.id.retrieve_data_progressprogreebar);
        send_button = (FloatingActionButton)view.findViewById(R.id.send_butt_edit);
        setListener();

    }

    private void set_text()
    {
        try {
            college_text = all_data.getString("college");
            education_text = all_data.getString("education");
            experience_proj_text = all_data.getString("exp_project");
            branch_spec_text = all_data.getString("branch_specialization");
            experience_text = all_data.getString("experience_field");
            spec_offer_text = all_data.getString("specialization_offer");
            interest_text = all_data.getString("interest");
            about_text = all_data.getString("about");
            language_text = all_data.getString("language");
            price_text = all_data.getString("price");
            session_text = all_data.getString("session_time");

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    private void get_text_fromEditText()
    {
        college_text = college.getText().toString();
        education_text = education.getText().toString();
        experience_proj_text = experience_proj.getText().toString();
        branch_spec_text = branch_spec.getText().toString();
        experience_text = experience.getText().toString();
        spec_offer_text = spec_offer.getText().toString();
        interest_text = interest.getText().toString();
        about_text = about.getText().toString();
        language_text = language.getText().toString();
        price_text = price.getText().toString();
        session_text = session_time.getText().toString();

    }
    private void putJsonObjectValues(JSONObject jsonObject)
    {
        try {
            jsonObject.put("email",all_data.getString("email"));
            jsonObject.put("password",all_data.getString("password"));
            jsonObject.put("college",college_text);
            jsonObject.put("education",education_text);
            jsonObject.put("experience_proj",experience_proj_text);
            jsonObject.put("branch",branch_spec_text);
            jsonObject.put("experience",experience_text);
            jsonObject.put("spec_offer",spec_offer_text);
            jsonObject.put("interest",interest_text);
            jsonObject.put("about",about_text);
            jsonObject.put("language",language_text);
            if(price_text.contentEquals("null"))
            jsonObject.put("price",0);
            else
                jsonObject.put("price",Integer.parseInt(price_text));

            jsonObject.put("session_time",session_text);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void setListener()
    {
        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject details = new JSONObject();
                get_text_fromEditText();
                putJsonObjectValues(details);
                save_profile save_it = new save_profile(context);
                save_it.execute(new JSONObject[]{details});

            }
        });
    }
    private void set_edit_text()
    {
        college.setText(college_text);
        education.setText(education_text);
        experience_proj.setText(experience_proj_text);
        branch_spec.setText(branch_spec_text);
        experience.setText(experience_text);
        spec_offer.setText(spec_offer_text);
        interest.setText(interest_text);
        about.setText(about_text);
        language.setText(language_text);
        price.setText(price_text);
        session_time.setText(session_text);
    }
    private boolean check_connection()
    {
        ConnectivityManager connectivityManager =(ConnectivityManager)context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo =connectivityManager.getActiveNetworkInfo();
        return networkInfo!=null && networkInfo.isConnected();
    }
}
