package com.example.mikael.staffapp;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Mig on 10-Aug-15.
 */
public class LoadPollTask extends AsyncTask<String, Void, String> implements View.OnClickListener {

    private Context mContext;

    public LoadPollTask(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected String doInBackground(String... urls) {
        return loadData(urls[0]);
    }

    protected void onPostExecute(String result) {
        //TextView textView = (TextView) ((Activity) mContext).findViewById(R.id.show_load_content);
        //textView.setText(result);

        TextView questionOneTV = (TextView)((Activity)mContext).findViewById(R.id.q1_question);
        TextView questionTwoTV = (TextView)((Activity)mContext).findViewById(R.id.q2_question);

        try {
            JSONObject json = new JSONObject(result);
            JSONArray listPollVote = json.getJSONArray("list_poll_votes");

            JSONObject q1 = listPollVote.getJSONObject(0);
            String question1 = q1.getString("name");
            questionOneTV.setText(question1);

            JSONObject q2 = listPollVote.getJSONObject(1);
            String question2 = q2.getString("name");
            questionTwoTV.setText(question2);

        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    private String loadData(String strUrl) {
        String strResult = "";
        try {
            URL url = new URL(strUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            strResult = readStream(con.getInputStream());
        }catch(Exception e){
            e.printStackTrace();
        }
        return strResult;
    }

    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try{
            reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while((line = reader.readLine()) != null){
                sb.append(line);
            }
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            if (reader != null){
                try {
                    reader.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    @Override
    public void onClick(View view) {

    }
}
