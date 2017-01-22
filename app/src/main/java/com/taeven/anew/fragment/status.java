package com.taeven.anew.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.taeven.anew.R;
import com.taeven.anew.background.set_status;

public class status extends Fragment {
    public static RadioButton profile_coomplete,profile_submittted,profile_evaluation;
    public static TextView result,percentage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_status, container, false);
        getActivity().setTitle("Status");

        set_status stat = new set_status(getActivity());
        setViews(view);
        stat.execute();
        return view;
    }

    public void setViews(View view)
    {
        profile_coomplete=(RadioButton)view.findViewById(R.id.profile_completed_radio);
        profile_submittted=(RadioButton)view.findViewById(R.id.profile_submitted_radio);
        profile_evaluation=(RadioButton)view.findViewById(R.id.evaluation_progress_radio);
        result=(TextView)view.findViewById(R.id.result_status);
        percentage=(TextView)view.findViewById(R.id.percentage_status);
    }

}
