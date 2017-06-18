package com.itayc14.flowerbooklet;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by itaycohen on 18.6.2017.
 */

public class DownloadThread extends Thread {

    protected static final String GET_FLOWERS = "http://52.51.81.191:85/getFlowers";
    protected static final String GET_TRANSLATE = "http://52.51.81.191:85/getTranslate";
    private static final String TAG = "tag";
    private String link;
    private ArrayList<Flower> flowersList;
    private MainActivity.DownloadJSONCompleted downloadFinishedListener;


    public DownloadThread(String whichData, MainActivity.DownloadJSONCompleted downloadFinishedListener) {
        link = whichData;
        this.downloadFinishedListener = downloadFinishedListener;
    }

    @Override
    public void run() {
        URL url = null;
        flowersList = new ArrayList<Flower>();
        try {
            url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;

            while((line=reader.readLine()) != null) {
                if (link.contentEquals(GET_FLOWERS)) {
                    JSONObject searchResultJSONComplex = new JSONObject(line);
                    JSONArray resultArray = searchResultJSONComplex.getJSONArray("data");
                    for (int i = 0; i < resultArray.length(); i++)
                        flowersList.add(new Flower(resultArray.getJSONObject(i)));
                } else {
                    Log.d(TAG, "run: "+line);
                }
            }
            downloadFinishedListener.onDownloadFinished(flowersList);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
