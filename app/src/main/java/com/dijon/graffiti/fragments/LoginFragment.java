package com.dijon.graffiti.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dijon.graffiti.R;
import com.dijon.graffiti.activities.LoginActivity;
import com.google.android.gms.common.SignInButton;
import com.squareup.picasso.Picasso;

import java.util.Random;

public class LoginFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private ImageView mImgBackground;
    private SignInButton mGoogleLoginButton;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    public LoginFragment() {
    }

    // <editor-fold desc="Lifecycle">
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        mImgBackground = (ImageView) view.findViewById(R.id.image_background);
        mGoogleLoginButton = (SignInButton) view.findViewById(R.id.sign_in_with_google);
        init();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity;
        if (context instanceof Activity) {
            activity = (Activity) context;
            try {
                mListener = (OnFragmentInteractionListener) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString()
                        + " must implement OnFragmentInteractionListener");
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    // </editor-fold>

    // <editor-fold desc="Helpers">
    private void init() {
        setBackground();
        setupListeners();
    }

    private void setBackground() {
        Random random = new Random();
        String name = "background" + ((Math.abs(random.nextInt()) % 5) + 1);
        Resources resources = getActivity().getResources();
        int resourceId = resources.getIdentifier(name, "drawable", getActivity().getPackageName());
        Log.e(LoginActivity.TAG, "Drawable Error: " + name);
        Picasso.with(getActivity()).load(resourceId).into(mImgBackground);
    }

    private void setupListeners() {
        mGoogleLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onGoogleButtonClicked();
            }
        });
    }
    // </editor-fold>

    public interface OnFragmentInteractionListener {
        void onGoogleButtonClicked();
    }
}