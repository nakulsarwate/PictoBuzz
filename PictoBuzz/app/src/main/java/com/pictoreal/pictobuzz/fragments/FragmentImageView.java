package com.pictoreal.pictobuzz.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pictoreal.pictobuzz.R;

/**
 * Created by row_hammer on 19/3/17.
 */

public class FragmentImageView extends Fragment {
    View V;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        V=inflater.inflate(R.layout.fragment_contactus,container,false);

        return V;
    }
}
