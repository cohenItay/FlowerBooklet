package com.itayc14.flowerbooklet;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public interface DownloadJSONCompleted {
        public void onDownloadFinished(ArrayList<Flower> moviesList);
    }


    private ViewPager pager;
    private PagerAdapter adapter;
    private ArrayList<Flower> flowersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startActivity(new Intent(this, Splash.class));

        pager = (ViewPager)findViewById(R.id.pager);
        final Handler handler = new Handler();
        DownloadJSONCompleted completed = new DownloadJSONCompleted() {
            @Override
            public void onDownloadFinished(ArrayList<Flower> flowersList) {
                MainActivity.this.flowersList = flowersList;
                adapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        pager.setAdapter(adapter);

                    }
                });
            }
        };
        new DownloadThread(DownloadThread.GET_FLOWERS, completed).start();
        DownloadJSONCompleted completed2 = new DownloadJSONCompleted() {
            @Override
            public void onDownloadFinished(ArrayList<Flower> moviesList) {

            }
        };
        new DownloadThread(DownloadThread.GET_TRANSLATE, completed2).start();

    }

    @Override
    public void onBackPressed() {
        if (pager.getCurrentItem() == 0)
            super.onBackPressed();
        else
            pager.setCurrentItem(pager.getCurrentItem() -1);
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            FlowerFragment flowerFrag = new FlowerFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("arr", flowersList);
            bundle.putInt("pos", position);
            flowerFrag.setArguments(bundle);
            return flowerFrag;
        }

        @Override
        public int getCount() {
            return flowersList.size();
        }
    }


}
