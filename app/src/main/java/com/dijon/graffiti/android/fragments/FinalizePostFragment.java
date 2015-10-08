package com.dijon.graffiti.android.fragments;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dijon.graffiti.R;

import java.nio.ByteBuffer;

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
     * @param image Image to display.
     * @return A new instance of fragment FinalizePostFragment.
     */
    public static FinalizePostFragment newInstance(Image image) {
        FinalizePostFragment fragment = new FinalizePostFragment();
        Bundle args = new Bundle();
        ByteBuffer buffer = image.getPlanes()[0].getBuffer();
        args.putByteArray(ARG_IMAGE, new byte[buffer.remaining()]);
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

    public interface OnFragmentInteractionListener {
    }

}
