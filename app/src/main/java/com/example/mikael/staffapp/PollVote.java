package com.example.mikael.staffapp;

/**
 * Created by Mig on 16-Aug-15.
 */
public class PollVote {

    private String user_id;
    private String poll_vote_id;
    private String choice_vote_id;

    public PollVote(String user_id, String poll_id) {
        this.user_id = user_id;
        this.poll_vote_id = poll_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getPoll_id() {
        return poll_vote_id;
    }

    public String getChoice() {
        return choice_vote_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setPoll_id(String poll_id) {
        this.poll_vote_id = poll_id;
    }

    public void setChoice(String choice) {
        this.choice_vote_id = choice;
    }
}

/*
user_id   choice_vote_id   poll_vote_id
*/