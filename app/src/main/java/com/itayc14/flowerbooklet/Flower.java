package com.itayc14.flowerbooklet;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by itaycohen on 18.6.2017.
 */
public class Flower implements Serializable {

    private String name;
    private String bestSeason;
    private String imageLink;

    public Flower(String name, String bestSeason, String imageLink){
        this.name = name;
        this.bestSeason = bestSeason;
        this.imageLink = imageLink;
    }

    public Flower(JSONObject jsonObject) throws JSONException {
        this.name = jsonObject.getString("name");
        this.bestSeason = jsonObject.getString("best season");
        this.imageLink = jsonObject.getString("image link");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBestSeason() {
        return bestSeason;
    }

    public void setBestSeason(String bestSeason) {
        this.bestSeason = bestSeason;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
}
