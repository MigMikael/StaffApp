package com.example.mikael.staffapp;

/**
 * Created by Mig on 16-Aug-15.
 */
public class PollVote {
    private String user_id;
    private String poll_id;
    private String choice;

    public PollVote(String user_id, String poll_id) {
        this.user_id = user_id;
        this.poll_id = poll_id;
    }

    public PollVote(String user_id, String poll_id, String choice) {
        this.user_id = user_id;
        this.poll_id = poll_id;
        this.choice = choice;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getPoll_id() {
        return poll_id;
    }

    public String getChoice() {
        return choice;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setPoll_id(String poll_id) {
        this.poll_id = poll_id;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }
}
