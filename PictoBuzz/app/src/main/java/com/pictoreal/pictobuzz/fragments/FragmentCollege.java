package com.pictoreal.pictobuzz.fragments;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.pictoreal.pictobuzz.R;

import static com.pictoreal.pictobuzz.activities.MainActivity.intentType;
import static com.pictoreal.pictobuzz.activities.MainActivity.intentViewComp;
import static com.pictoreal.pictobuzz.activities.MainActivity.intentViewEntc;
import static com.pictoreal.pictobuzz.activities.MainActivity.intentViewFE;
import static com.pictoreal.pictobuzz.activities.MainActivity.intentViewIt;

/**
 * Created by shivani on 8/1/17.
 *
 * 13/2/2017
 * AKshay PAtel
 * add: Check if Internet is available or not
 */

public class FragmentCollege extends Fragment implements View.OnClickListener{
    private View V;
    private ImageButton I1,I2,I3,I4;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        V=inflater.inflate(R.layout.fragment_college,container,false);
        //Set reference to RecyclerView
        I1 = (ImageButton)V.findViewById(R.id.image_button_fe);
        I2 = (ImageButton)V.findViewById(R.id.image_button_comp);
        I3 = (ImageButton)V.findViewById(R.id.image_button_entc);
        I4 = (ImageButton)V.findViewById(R.id.image_button_it);

        //I1.setImageResource(R.drawable.comp_image);
        I1.setOnClickListener(this);
        I2.setOnClickListener(this);
        I3.setOnClickListener(this);
       I4.setOnClickListener(this);
        return V;
    }//end of onCreateView

    @Override
    public void onClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId())
        {
            case R.id.image_button_fe:
                bundle.putString(intentType,intentViewFE);
                Log.i("===FragmentCollege","FE");

                break;
            case R.id.image_button_comp:
                bundle.putString(intentType,intentViewComp);
                break;
            case R.id.image_button_entc:
                bundle.putString(intentType,intentViewEntc);
                break;
            case R.id.image_button_it:
                bundle.putString(intentType,intentViewIt);
                break;

        }
    //    FragmentTransaction transaction = getFragmentManager().beginTransaction();
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        FragmentCardView fragmentCardView=new FragmentCardView();
        fragmentCardView.setArguments(bundle);
        ft.replace(this.getId(),fragmentCardView);

        ft.addToBackStack("tag");  //to swtich between fragments 8/1/2017
        ft.commit();
    }//[End ONClick]
}//[END Fragment College]
//8/1/2017