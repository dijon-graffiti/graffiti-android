package com.dijon.graffiti.android.fragments;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.dijon.graffiti.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FinalizePostFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FinalizePostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
public class FinalizePostFragment extends Fragment {
    private static final String ARG_IMAGE = "arg_image";
    private byte[] mImageByteArray;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param imageBytes Image to display.
     * @return A new instance of fragment FinalizePostFragment.
     */
    public static FinalizePostFragment newInstance(byte[] imageBytes) {
        FinalizePostFragment fragment = new FinalizePostFragment();
        Bundle args = new Bundle();
        args.putByteArray(ARG_IMAGE, imageBytes);
        fragment.setArguments(args);
        return fragment;
    }

    public FinalizePostFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mImageByteArray = getArguments().getByteArray(ARG_IMAGE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_finalize_post, container, false);
        ImageView imgPost = (ImageView) view.findViewById(R.id.image_post);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_finalize);
        final EditText title = (EditText) view.findViewById(R.id.text_title);
        final EditText caption = (EditText) view.findViewById(R.id.text_caption);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onFinalizeButtonClicked(title.getText().toString(), caption.getText().toString());
            }
        });
        setupToolbar(toolbar);
        Bitmap bmp = BitmapFactory.decodeByteArray(mImageByteArray, 0, mImageByteArray.length);
        imgPost.setImageBitmap(bmp);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void setupToolbar(Toolbar toolbar) {
        if (toolbar != null) {
            AppCompatActivity activity = ((AppCompatActivity) getActivity());
            activity.setSupportActionBar(toolbar);
            if (activity.getSupportActionBar() != null) {
                activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                activity.getSupportActionBar().setTitle("");
            }
        }
    }

    public interface OnFragmentInteractionListener {
        void onFinalizeButtonClicked(String title, String caption);
    }

}
