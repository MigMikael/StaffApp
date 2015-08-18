package com.example.mikael.staffapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

    private TextView contentTxt;

    private String scanContent = "";
    private String scanFormat = "";

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

        contentTxt = (TextView)findViewById(R.id.scan_content);

        loadPoll = new LoadPollTask(PollActivity.this);
        loadPoll.execute("http://scienceweek58.herokuapp.com/api/poll_votes");

        q1PollVote = new PollVote("53","1");
        //q1PollVote = new PollVote(scanContent,"1");
        choice1 = (RadioButton) findViewById(R.id.q1_choice1);
        choice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                q1PollVote.setChoice("1");
                Toast.makeText(PollActivity.this,q1PollVote.getChoice(),Toast.LENGTH_SHORT).show();
            }
        });
        choice2 = (RadioButton) findViewById(R.id.q1_choice2);
        choice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                q1PollVote.setChoice("2");
                Toast.makeText(PollActivity.this,q1PollVote.getChoice(),Toast.LENGTH_SHORT).show();
            }
        });
        choice3 = (RadioButton) findViewById(R.id.q1_choice3);
        choice3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                q1PollVote.setChoice("3");
                Toast.makeText(PollActivity.this,q1PollVote.getChoice(),Toast.LENGTH_SHORT).show();
            }
        });

        q2PollVote = new PollVote("53", "2");
        //q2PollVote = new PollVote(scanContent, "2");
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean b) {
                int rateInt = (int)rating;
                q2PollVote.setChoice(String.valueOf(rateInt));
            }
        });

        sendButton = (FloatingActionButton) findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //PostPollTask taskOne = new PostPollTask(PollActivity.this, q1PollVote);
                //taskOne.execute("http://scienceweek58.herokuapp.com/api/poll_tables");
                //taskOne.execute("http://posttestserver.com/post.php");

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

    public void scanQR(View view){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt(" ");
        integrator.setResultDisplayDuration(0);
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.initiateScan();
    }
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if (scanningResult != null) {

            scanContent = scanningResult.getContents();
            scanFormat = scanningResult.getFormatName();

            contentTxt.setText("CONTENT: " + scanContent);

        }else{
            Toast toast = Toast.makeText(getApplicationContext(),"No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}


/*
json pol::      http://scienceweek58.herokuapp.com/api/poll_votes

json choice::   http://scienceweek58.herokuapp.com/api/choice_votes

                http://scienceweek58.herokuapp.com/api/poll_tables

user_id    choice_vote_id   poll_vote_id
 */
