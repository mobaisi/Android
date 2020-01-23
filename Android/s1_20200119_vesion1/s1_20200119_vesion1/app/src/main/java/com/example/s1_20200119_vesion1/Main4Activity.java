package com.example.s1_20200119_vesion1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Main4Activity extends AppCompatActivity {
    private String IP = "http://192.168.1.7/";
    private JSONArray datas;
    private MyAdapter myAdapter;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        Intent intent = getIntent();
        try {
            JSONObject data = new JSONObject(intent.getStringExtra("data"));
            id = data.optString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        datas = new JSONArray();
        MyTask myTask = new MyTask(IP + "api/Values/History") {
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    datas = jsonObject.optJSONArray("data");
                    if (datas == null || datas.length() == 0) {
                        Toast.makeText(Main4Activity.this, "null History", Toast.LENGTH_SHORT).show();
                    }
                    Log.d("ContentValues:", "onPostExecute: " + datas.toString());
                    myAdapter.setDatas(datas);
                    myAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", id);
        myTask.Post(hashMap);
        myAdapter = new MyAdapter(datas, Main4Activity.this) {

            private VH vh;

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                if (view == null) {
                    view = LayoutInflater.from(Main4Activity.this).inflate(R.layout.lst_history, null, false);
                    vh = new VH(view);
                    view.setTag(vh);
                } else {
                    vh = (VH) view.getTag();
                }
                // TextView txt2 = vh.getView(R.id.lst3_txt3);
                TextView txt3 = vh.getView(R.id.lst3_txt3);
                TextView txt4 = vh.getView(R.id.lst3_txt4);
                TextView txt5 = vh.getView(R.id.lst3_txt5);
                TextView txt6 = vh.getView(R.id.lst3_txt6);
                TextView txt7 = vh.getView(R.id.lst3_txt7);

                try {
                    JSONObject jsonObject = datas.getJSONObject(i);
                    txt3.setText(jsonObject.optString("date"));
                    txt4.setText(jsonObject.optString("from1"));
                    txt5.setText(jsonObject.optString("from2"));
                    txt6.setText(jsonObject.optString("to1"));
                    txt7.setText(jsonObject.optString("to2"));
                    // txt3.setText(jsonObject.optString("date"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return view;
            }
        };
        ListView lst_ = findViewById(R.id.lst3_root);
        lst_.setAdapter(myAdapter);

    }
}
