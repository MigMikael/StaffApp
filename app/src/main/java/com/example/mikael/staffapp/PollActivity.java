package com.example.mikael.staffapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

/**
 * Created by Mig on 07-Aug-15.
 */
public class PollActivity extends ActionBarActivity {

    private EditText contentText;

    private String scanContent = "";

    LoadPollTask loadPoll;

    RadioButton choice1,choice2,choice3;
    RatingBar ratingBar;

    PollVote q1PollVote;
    PollVote q2PollVote;

    FloatingActionButton sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll);

        contentText = (EditText)findViewById(R.id.scan_content2);

        Bundle bundle = getIntent().getExtras();
        scanContent = bundle.getString("id");
        contentText.setText(scanContent);

        loadPoll = new LoadPollTask(PollActivity.this);
        loadPoll.execute("http://scienceweek58.herokuapp.com/api/poll_votes");

        q1PollVote = new PollVote("1");
        choice1 = (RadioButton) findViewById(R.id.q1_choice1);
        choice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                q1PollVote.setChoice("12");
                Toast.makeText(PollActivity.this,q1PollVote.getChoice(),Toast.LENGTH_SHORT).show();
            }
        });
        choice2 = (RadioButton) findViewById(R.id.q1_choice2);
        choice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                q1PollVote.setChoice("13");
                Toast.makeText(PollActivity.this,q1PollVote.getChoice(),Toast.LENGTH_SHORT).show();
            }
        });
        choice3 = (RadioButton) findViewById(R.id.q1_choice3);
        choice3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                q1PollVote.setChoice("14");
                Toast.makeText(PollActivity.this,q1PollVote.getChoice(),Toast.LENGTH_SHORT).show();
            }
        });

        q2PollVote = new PollVote("2");
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean b) {
                int rateInt = (int)rating;
                rateInt+=14;
                q2PollVote.setChoice(String.valueOf(rateInt));
            }
        });

        sendButton = (FloatingActionButton) findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                q1PollVote.setUser_id(contentText.getText().toString());
                PostPollTask taskOne = new PostPollTask(PollActivity.this, q1PollVote);
                taskOne.execute("http://scienceweek58.herokuapp.com/api/poll_tables");
                //taskOne.execute("http://posttestserver.com/post.php");

                q2PollVote.setUser_id(contentText.getText().toString());
                PostPollTask taskTwo = new PostPollTask(PollActivity.this, q2PollVote);
                taskTwo.execute("http://scienceweek58.herokuapp.com/api/poll_tables");
                //taskTwo.execute("http://posttestserver.com/post.php");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}


/*
json pol::      http://scienceweek58.herokuapp.com/api/poll_votes

json choice::   http://scienceweek58.herokuapp.com/api/choice_votes

                http://scienceweek58.herokuapp.com/api/poll_tables

user_id    choice_vote_id   poll_vote_id
 */
