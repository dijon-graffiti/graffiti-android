package com.dijon.graffiti.android.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.dijon.graffiti.R;
import com.dijon.graffiti.android.fragments.HomeFragment;
import com.dijon.graffiti.android.fragments.ProfileFragment;

public class HomeActivity extends AppCompatActivity {

    private Fragment currentFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        changeFragmentMain(Fragment.instantiate(this, HomeFragment.class.getName()), "home",R.anim.no_animation,R.anim.no_animation);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showProfile(View view) {
        //TODO: "profile" will be changed to the users name
//        changeFragmentMain(ProfileFragment.newInstance(), "profile",
//                R.anim.slide_in_up,
//                R.anim.no_animation);
        startActivity(new Intent(this, ProfileActivity.class));
    }
    public void hideProfile(View view) {
//        changeFragmentMain(Fragment.instantiate(this, HomeFragment.class.getName()),
//                "home", R.anim.no_animation, R.anim.slide_out_down);
    }

    public void changeFragmentMain(Fragment fragment, String fragmentName , int enter, int exit) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        currentFrag = fragment;
        fragmentManager.executePendingTransactions();
        invalidateOptionsMenu();

        fragmentManager.beginTransaction()
                .setCustomAnimations(enter, exit)
                .replace(R.id.main_content, fragment)
                .commitAllowingStateLoss();

    }

    @Override
    public void onBackPressed(){
        if(currentFrag.getClass().equals(ProfileFragment.class)){
            hideProfile(new View(this));
        }
        else{
            super.onBackPressed();
        }
    }

}
