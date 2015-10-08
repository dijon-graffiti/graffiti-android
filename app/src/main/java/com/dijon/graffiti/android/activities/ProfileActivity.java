package com.dijon.graffiti.android.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.dijon.graffiti.R;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView mProfielPic;
    FloatingActionButton fab;
    RecyclerView mRecyclerView;
    ImageView mProfilePicture;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("First Name");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mProfielPic = (ImageView) findViewById(R.id.profile_picture);
        Picasso.with(this).load(R.drawable.background1).into(mProfielPic);

        fab = (FloatingActionButton) findViewById(R.id.fab_capture);
        fab.setOnClickListener(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mProfilePicture = (ImageView) findViewById(R.id.profile_picture);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fab_capture:
                startActivity(new Intent(this, CameraActivity.class));
                break;
        }
    }
}
