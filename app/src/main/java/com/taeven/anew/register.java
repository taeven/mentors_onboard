package com.taeven.anew;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.taeven.anew.background.register_json;

import org.json.JSONObject;

public class register extends AppCompatActivity {
   private EditText name,email,mobile,password,repass,sex,address;
    private TextView day,month,year;
    private View.OnClickListener fordate;
    private FloatingActionButton submit,reset;
//    private String[] string_month = {"JAN","FAB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
//    private String[] string_sex = {"Male","Female","Other"};
    private profile_data person;
    private ScrollView scrollView;
    private DatePicker datePicker;
    private Button done_calender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Register");
        set_views();
        onclick();

    }

    private  void onclick()
    {
        fordate = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("datepicer","visible");
                datePicker.setVisibility(View.VISIBLE);
                scrollView.setVisibility(View.GONE);
                submit.setVisibility(View.GONE);
                reset.setVisibility(View.GONE);
                done_calender.setVisibility(View.VISIBLE);

            }
        };

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check_credential();
            }
        });
       day.setOnClickListener(fordate);
        month.setOnClickListener(fordate);
        year.setOnClickListener(fordate);
        done_calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int Day_text = datePicker.getDayOfMonth();
                int month_text = datePicker.getMonth()+1;
                int year_text = datePicker.getYear();
                Log.d("day", Integer.toString(Day_text));
                Log.d("month",Integer.toString(month_text));
                Log.d("year",Integer.toString(year_text));
                datePicker.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
                submit.setVisibility(View.VISIBLE);
                reset.setVisibility(View.VISIBLE);
                done_calender.setVisibility(View.GONE);
                try {
                    day.setText(Integer.toString(Day_text));
                    month.setText(Integer.toString(month_text));
                    year.setText(Integer.toString(year_text));
//                    month.setText(month_text);
//                    year.setText(year_text);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset_data();

            }
        });
    }
    private void set_views()
    {
        name=(EditText)findViewById(R.id.name_reg);
        email=(EditText)findViewById(R.id.email_reg);
        mobile=(EditText)findViewById(R.id.mobile_reg);
        password=(EditText)findViewById(R.id.passwd_reg);
        repass=(EditText)findViewById(R.id.repass_reg);
        sex=(EditText)findViewById(R.id.sex_reg);
        day=(TextView) findViewById(R.id.date_reg);
        month=(TextView) findViewById(R.id.month_reg);
        year=(TextView) findViewById(R.id.year_reg);
        address=(EditText)findViewById(R.id.address_reg);
        submit=(FloatingActionButton) findViewById(R.id.submit_reg);
        reset=(FloatingActionButton) findViewById(R.id.reset_reg);
        scrollView=(ScrollView) findViewById(R.id.scroll_reg);
        datePicker=(DatePicker) findViewById(R.id.date_picker_reg);
        done_calender=(Button) findViewById(R.id.done_cal_reg);
    }

    private void reset_data()
    {
        name.setText(null);
        email.setText(null);
        mobile.setText(null);
        password.setText(null);
        repass.setText(null);
        sex.setText(null);
        day.setText(null);
        month.setText(null);
        year.setText(null);
        address.setText(null);


    }
    private void check_credential()
    {
        JSONObject jsonObject = new JSONObject();
        person = new profile_data();
        person.setName(name.getText().toString());

        person.setEmail(email.getText().toString());
        String repass_string= repass.getText().toString();
        person.setPassword(password.getText().toString());
        person.setSex(sex.getText().toString());
        person.setDay(day.getText().toString());
        person.setMonth(month.getText().toString());
        person.setYear(year.getText().toString());
        person.setAddress(address.getText().toString());

        if(person.getName().isEmpty())
        {
            Toast.makeText(this,R.string.empty_name,Toast.LENGTH_SHORT).show();
        }
        else  if(person.getEmail().isEmpty())
        {
            Toast.makeText(this,getString(R.string.email_empty),Toast.LENGTH_SHORT).show();
        }
       else  if(person.getPassword().isEmpty() || person.getPassword().length()<8)
        {
            Toast.makeText(this,getString(R.string.short_paswd),Toast.LENGTH_SHORT).show();
        }
        else  if(!person.getPassword().contentEquals(repass_string))
        {
            Toast.makeText(this,getString(R.string.paswd_match_error),Toast.LENGTH_SHORT).show();
        }
        else  if(person.getSex().isEmpty())
        {
            Toast.makeText(this,"Enter sex!!",Toast.LENGTH_SHORT).show();
        }
        else  if(person.getDay().isEmpty() || person.getMonth().isEmpty() || person.getYear().isEmpty())
        {
            Toast.makeText(this,"Invalid date of birth!!",Toast.LENGTH_SHORT).show();
        }
        else  if(person.getAddress().isEmpty())
        {
            Toast.makeText(this,getString(R.string.empty_address),Toast.LENGTH_SHORT).show();
        }
        else

        {
            try {
                jsonObject.put("name", person.getName());
                jsonObject.put("email",person.getEmail());
                jsonObject.put("mobile",person.getMobile());
                jsonObject.put("password",person.getPassword());
                jsonObject.put("sex",person.getSex());
                jsonObject.put("date",person.getDate());
                jsonObject.put("address",person.getAddress());

                register_json jsonasync = new register_json(this);
                jsonasync.execute(new JSONObject[]{jsonObject});
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }


    }

    public void submit_request(String url,profile_data person)
    {
//
//        try{
//            HttpClient httpClient =new DefaultHttpClient();
//
//        }
    }


}
