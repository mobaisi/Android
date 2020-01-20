package com.example.s1_20200119_vesion1;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import static android.content.ContentValues.TAG;

public class MyTask extends AsyncTask<HashMap<String,Object>,Integer,String> {
    private String Method = "POST";
    URL url;
    HttpURLConnection conn;


    MyTask(String url) {
        try {
            this.url = new URL(url);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }



    public void Post(){
        execute();

    }

    public void Post(HashMap hashMap){
        execute(hashMap);
    }
    @Override
    protected String doInBackground(HashMap<String, Object>... hashMaps) {

        StringBuilder sb = new StringBuilder();
        String postbody = "";
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(Method);
            conn.setRequestProperty("Content-Type","application/json");
            conn.setReadTimeout(6000);
            conn.setConnectTimeout(6000);
            conn.setUseCaches(false);

            if (hashMaps.length > 0) {
            /*    String s =
                postbody = s;*/
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
                String s = new JSONObject(hashMaps[0]).toString();
                Log.d(TAG, "doInBackground: "+s);
                bufferedWriter.write(s);
                bufferedWriter.close();
            }


            int responseCode = conn.getResponseCode();
            Log.d(TAG, "doInBackground: "+responseCode + postbody);

            if (HttpURLConnection.HTTP_OK == responseCode) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                while (null != (line = bufferedReader.readLine())) {
                    sb.append(line);
                }
                bufferedReader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "Result: "+ sb.toString());
        return  sb.toString();

    }
}
