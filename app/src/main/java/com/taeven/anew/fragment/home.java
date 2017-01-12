package com.taeven.anew.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.taeven.anew.R;
import com.taeven.anew.adapter.mentor_list_adapter;
import com.taeven.anew.after_login;
import com.taeven.anew.background.get_mentors_data;
import com.taeven.anew.manage_temp_storage;
import com.taeven.anew.mentor;

import java.util.ArrayList;


public class home extends Fragment {

    private int count=0;
    private ListView listView;
    private mentor_list_adapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        getActivity().setTitle("Home");
        setviews(view);
        setAdapter();

        return view;
    }
    public void setviews(View view)
    {
        listView = (ListView) view.findViewById(R.id.mentors_details);
    }
    public void setAdapter()
    {
        final ArrayList<mentor> list = new ArrayList<mentor>();
        adapter = new mentor_list_adapter(getActivity(),list);
        listView.setAdapter(adapter);
        final Button button=new Button(getActivity());
        button.setText("Load more ...");
        button.setBackgroundColor(getResources().getColor(R.color.primary_text_material_light));
        if(!after_login.progress)
        listView.addFooterView(button );
        else
        listView.addFooterView(new ProgressBar(getActivity()));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listView.removeFooterView(button);
                listView.addFooterView(new ProgressBar(getActivity()));

                get_mentors_data getMentorsData = new get_mentors_data(getActivity());
                getMentorsData.execute(5*after_login.count);
                after_login.count++;

            }
        });
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.profile);

//        add_mentor(new mentor("vaibhav",bitmap));
//        add_mentor( new mentor("monty",bitmap));
        get_details_from_file();
    }
    public void add_mentor(mentor person)
    {
        adapter.add(person);
    }

    public void get_details_from_file()
    {
        manage_temp_storage manager = new manage_temp_storage(getActivity());
        mentor[] details;
        details=manager.get_data();
        if (details!=null) {
            for (int i = 0; i < details.length; i++) {
                if(details[i]==null)
                    break;
                add_mentor(details[i]);
            }
        }



    }


}
