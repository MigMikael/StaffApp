package com.example.mikael.staffapp;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Mig on 16-Aug-15.
 */
public class PostPollTask extends AsyncTask<String, Void, String> {
    private Context mContext;
    private PollVote pollVote;


    public PostPollTask(Context context, PollVote mPollVote){
        mContext = context;
        pollVote = mPollVote;
    }

    @Override
    protected String doInBackground(String... urls) {
        return postdata(urls[0],pollVote);
    }

    @Override
    protected void onPostExecute(String result){
        TextView textView = (TextView)((Activity)mContext).findViewById(R.id.sentstatus_textview);
        textView.setText(result);

        char confirm = result.charAt(result.length()-2);
        String sendStatus = "";
        if(confirm == '1'){
            sendStatus = "Send Complete";
        }else{
            sendStatus = "Try Again";
        }
        Toast toast = Toast.makeText(mContext,sendStatus, Toast.LENGTH_SHORT);
        toast.show();
    }

    private String postdata(String strUrl,PollVote pollVote){
        InputStream inputStream = null;
        String result = "";
        try {
            HttpClient httpclient = new DefaultHttpClient();

            HttpPost httpPost = new HttpPost(strUrl);

            String json = "";

            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("user_id", pollVote.getUser_id());
            jsonObject.accumulate("poll_id", pollVote.getPoll_id());
            jsonObject.accumulate("choice", pollVote.getChoice());

            json = jsonObject.toString();

            StringEntity se = new StringEntity(json);

            httpPost.setEntity(se);

            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            HttpResponse httpResponse = httpclient.execute(httpPost);

            inputStream = httpResponse.getEntity().getContent();

            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }
}