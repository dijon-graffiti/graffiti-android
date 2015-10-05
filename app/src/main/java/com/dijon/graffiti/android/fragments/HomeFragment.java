package com.dijon.graffiti.android.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dijon.graffiti.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

        }
    }


}
