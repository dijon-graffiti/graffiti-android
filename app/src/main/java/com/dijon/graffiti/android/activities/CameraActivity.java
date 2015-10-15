package com.dijon.graffiti.android.activities;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;

import com.dijon.graffiti.R;
import com.dijon.graffiti.android.fragments.CameraFragment;
import com.dijon.graffiti.android.fragments.FinalizePostFragment;
import com.dijon.graffiti.android.util.SharedPreferenceHelper;
import com.dijon.graffiti.network.GraffitiClient;
import com.dijon.graffiti.network.models.Post;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class CameraActivity extends AppCompatActivity implements CameraFragment.OnFragmentInteractionListener, FinalizePostFragment.OnFragmentInteractionListener {

    public static final String TAG = CameraActivity.class.getSimpleName();

    private byte[] imageInBytes;

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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
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

    @Override
    public void onImageCaptured(byte[] imageBytes) {
        imageInBytes = imageBytes;
        showFragment(FinalizePostFragment.newInstance(imageBytes), false);
    }

    @Override
    public void onFinalizeButtonClicked(String title, String caption) {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();

        Post post = new Post();
        post.setCaption(caption);
        post.setImage(Base64.encodeToString(imageInBytes, Base64.DEFAULT));
        post.setLattitude(latitude);
        post.setLongitude(longitude);
        post.setTitle(title);
        post.setUserId(SharedPreferenceHelper.getUserId(getApplicationContext()));
        GraffitiClient.uploadPhoto(post, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    Log.i(TAG, "Data could not be saved. " + firebaseError.getMessage());
                } else {
                    Log.i(TAG, "Data saved successfully.");
                    finish();
                }
            }
        });
    }
}
