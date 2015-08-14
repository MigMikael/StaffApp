package com.example.mikael.staffapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import org.json.JSONObject;

/**
 * Created by Mig on 30-Jul-15.
 */
public class PostTask extends AsyncTask<String, Void, String>{
    private Context mContext;
    private CheckPoint checkPoint;


    public PostTask(Context context, CheckPoint mCheckPoint){
        mContext = context;
        checkPoint = mCheckPoint;
    }

    @Override
    protected String doInBackground(String... urls) {
        return postdata(urls[0],checkPoint);
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

    private String postdata(String strUrl,CheckPoint checkPoint){
        InputStream inputStream = null;
        String result = "";
        try {
            HttpClient httpclient = new DefaultHttpClient();

            HttpPost httpPost = new HttpPost(strUrl);

            String json = "";

            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("user_id", checkPoint.getUser_id());
            jsonObject.accumulate("place_id", checkPoint.getPlace_id());

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

    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }
}
