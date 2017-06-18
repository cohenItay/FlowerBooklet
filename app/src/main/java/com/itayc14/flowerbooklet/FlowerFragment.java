package com.itayc14.flowerbooklet;

import android.graphics.Matrix;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by itaycohen on 18.6.2017.
 */

public class FlowerFragment extends Fragment {

    private ArrayList<Flower> flowersList;
    private int position;
    private Matrix matrix = new Matrix();
    private float scale = 1.0f;
    private ImageView flowerImageView;
    ScaleGestureDetector scaleGestureDetector;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_flower, container, false);
        flowersList = (ArrayList)getArguments().getSerializable("arr");
        position = getArguments().getInt("pos");
        TextView header = (TextView)rootView.findViewById(R.id.fragment_flower_title);
        flowerImageView = (ImageView)rootView.findViewById(R.id.fragment_image);
        TextView season = (TextView)rootView.findViewById(R.id.fragment_season);
        ProgressBar pb = (ProgressBar)rootView.findViewById(R.id.fragment_progressbar);
        Flower flower = flowersList.get(position);
        header.setText(flower.getName());
        season.setText(flower.getBestSeason());
        if(flower.getImageLink() != null && !flower.getImageLink().isEmpty()) {
            pb.setVisibility(View.GONE);
            ImageView failedImg = new ImageView(getContext());
            failedImg.setBackgroundResource(R.drawable.no_image_exist);
            Glide.with(getActivity()).load(flower.getImageLink()).into(flowerImageView);
            if(flowerImageView.getDrawable() == null)
                flowerImageView.setImageResource(R.drawable.no_image_exist);
            //Log.d("tag", "onCreateView: "+flower.getImageLink());
        }
        scaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleListener());
        flowerImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                scaleGestureDetector.onTouchEvent(event);
                return true;
            }
        });
        return rootView;
    }

    public class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scale = scale * detector.getScaleFactor();
            scale = Math.max(0.1f, Math.min(scale, 5f));
            matrix.setScale(scale, scale);
            flowerImageView.setImageMatrix(matrix);
            return true;
        }
    }
}
