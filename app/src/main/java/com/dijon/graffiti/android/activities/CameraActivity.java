package com.dijon.graffiti.android.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dijon.graffiti.R;
import com.dijon.graffiti.android.fragments.CameraFragment;
import com.dijon.graffiti.android.fragments.FinalizePostFragment;

public class CameraActivity extends AppCompatActivity implements CameraFragment.OnFragmentInteractionListener, FinalizePostFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, CameraFragment.newInstance())
                    .commit();
        }
    }

    @Override
    public void onImageCaptured(Image image) {
        showFragment(FinalizePostFragment.newInstance(image), false);
    }

    private void showFragment(Fragment newFragment, boolean addToBackStack) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, newFragment);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }
}
