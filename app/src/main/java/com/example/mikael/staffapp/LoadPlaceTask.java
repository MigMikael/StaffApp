package com.example.mikael.staffapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Mig on 02-Aug-15.
 */
public class LoadPlaceTask extends AsyncTask<String, Void, String> implements View.OnClickListener {

    private Context mContext;
    String[] roomName=null;
    private String selectRoom = "1";
    Button chooseRoom;

    public String getSelectRoom() {
        return selectRoom;
    }

    public LoadPlaceTask(Context context){
        mContext = context;
    }

    @Override
    protected String doInBackground(String... urls) {
        return loadData(urls[0]);
    }

    protected void onPostExecute(String result){
        TextView textView = (TextView)((Activity)mContext).findViewById(R.id.sentstatus_textview);
        textView.setText(result);
        try {
            JSONObject json = new JSONObject(result);
            JSONArray places = json.getJSONArray("places");
            roomName = new String[places.length()];
            for (int i=0 ; i<places.length();i++){
                JSONObject room = places.getJSONObject(i);
                String name = room.getString("name");
                roomName[i] = name;
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        chooseRoom = (Button)((Activity)mContext).findViewById(R.id.choose_room_button);
        chooseRoom.setOnClickListener(this);
    }

    private String loadData(String strUrl){
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
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Select your room");
        builder.setSingleChoiceItems(roomName, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //selectRoom = roomName[i];
                selectRoom = Integer.toString(i+1);
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (roomName.length > 1) {
                    String selectRoomName = roomName[Integer.parseInt(selectRoom) - 1];
                    chooseRoom.setText(selectRoomName);
                }
                dialog.dismiss();
            }
        });
        builder.create();
        builder.show();
    }
}
