package com.example.mikael.staffapp;

/**
 * Created by Mig on 31-Jul-15.
 */
public class CheckPoint {
    private String user_id;
    private String place_id;

    public CheckPoint(String user_id, String place_id) {
        this.user_id = user_id;
        this.place_id = place_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }
}
