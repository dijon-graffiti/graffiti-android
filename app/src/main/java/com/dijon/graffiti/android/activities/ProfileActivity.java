package com.dijon.graffiti.android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

    Toolbar mToolbar;
    FloatingActionButton fab;
    RecyclerView mRecyclerView;
    ImageView mProfilePicture;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setupToolbar("First Name");

        fab = (FloatingActionButton) findViewById(R.id.fab_capture);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mProfilePicture = (ImageView) findViewById(R.id.profile_picture);

        fab.setOnClickListener(this);
        Picasso.with(this).load(R.drawable.background1).into(mProfilePicture);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_capture:
                startActivity(new Intent(this, CameraActivity.class));
                break;
        }
    }

    private void setupToolbar(String title) {
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setTitle(title);
            }
        }
    }
}
