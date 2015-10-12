package com.dijon.graffiti.android.fragments;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dijon.graffiti.R;
import com.dijon.graffiti.android.activities.CameraActivity;
import com.dijon.graffiti.android.activities.ProfileActivity;

/**
 * A placeholder fragment containing a simple view.
 */

public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    // List:
    FloatingActionButton fab;
    SwipeRefreshLayout mSwipeLayout;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        fab = (FloatingActionButton) view.findViewById(R.id.fab_capture);
        mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        ImageView profpic = (ImageView) view.findViewById(R.id.profile_picture);
        profpic.setOnClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        mSwipeLayout.setColorSchemeResources(R.color.graf_primary, R.color.graf_primary_dark);
        mSwipeLayout.setOnRefreshListener(this);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // TODO: Set Adapter
        // mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        // mRecyclerView.addOnScrollListener(scrollListener);
        view.findViewById(R.id.fab_capture).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_capture: {
                startActivity(new Intent(getContext(), CameraActivity.class));
                break;
            }
            case R.id.profile_picture:
                Intent i = new Intent(getActivity(), ProfileActivity.class);
                ActivityOptionsCompat o = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                        Pair.create(view, "profile_picture"), Pair.create((View) fab, "fab"),
                        Pair.create(getActivity().findViewById(R.id.toolbar), "toolbar"));
                getActivity().startActivity(i, o.toBundle());

                break;
        }
    }

    @Override
    public void onRefresh() {
        // Get list of expenses.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
            }
        }.execute();

    }


}
