package com.example.s1_20200119_vesion1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Main3Activity extends AppCompatActivity implements View.OnClickListener {
    private String IP = "http://192.168.1.7/";
    private TextView txtDep;
    private TextView txtSN;
    private TextView txtAssetName;
    private TextView txtCancel;
    private TextView txtSave;
    private TextView txtBack;
    private TextView txtHead;
    private TextView txtnewsn;
    String dd = "";
    String gg = "";
    String nnnn = "";
    private Spinner spDepName;
    private Spinner spDepLocation;
    private JSONArray depLocations;
    private JSONArray depNames;
    private ArrayList<String> list;
    private ArrayAdapter<String> strAdapter;
    private ArrayAdapter<String> strAdapter1;
    private ArrayList<String> list1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Intent intent = getIntent();
        String joo = intent.getStringExtra("data");

        init();




//        openFileInput("oue");

        txtnewsn = findViewById(R.id.txt3newsn);

       // txtnewsn.setText(dd + gg + nnnn);
        JSONObject data = myJosnReader(joo);
        action();
        list = new ArrayList<>();
        list1 = new ArrayList<>();
        depNames = new JSONArray();
        depLocations = new JSONArray();
        MyTask myTask = new MyTask(IP + "api/Values/getCmb") {
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                MyJsonReader(s);
                strAdapter.notifyDataSetChanged();
                // strAdapter.setNotifyOnChange(true);
            }
        };

        HashMap<String, Object> map = new HashMap<>();
        map.put("status", 1);
        myTask.Post(map);
        adapter();
    }

    private void adapter() {
        strAdapter = new ArrayAdapter<>(Main3Activity.this, R.layout.support_simple_spinner_dropdown_item, list);
        strAdapter1 = new ArrayAdapter<>(Main3Activity.this, R.layout.support_simple_spinner_dropdown_item, list1);
        spDepName.setAdapter(strAdapter);
        spDepLocation.setAdapter(strAdapter1);
    }

    private void init() {
        spDepName = findViewById(R.id.sp3DepName);
        spDepLocation = findViewById(R.id.sp3DepLocation);
        txtAssetName = findViewById(R.id.txt3AstName);
        txtSN = findViewById(R.id.txt3sn);
        txtDep = findViewById(R.id.txt3CurrentDep);
        txtBack = findViewById(R.id.heae_lblBack);
        txtHead = findViewById(R.id.head_lblTitle);
        txtHead.setText("Asset Transfer");
        txtCancel = findViewById(R.id.txt3Cancel);
        txtSave = findViewById(R.id.txt3Save);
        TextView txt3Date = findViewById(R.id.txt3Date);
        txt3Date.setText(String.format("年-月-日 HH:MM:SS 格式：%tF %<tT",new Date()));
    }

    private void MyJsonReader(String s) {
        try {
            JSONObject object = new JSONObject(s);
            depNames = object.getJSONArray("data");
            for (int i = 0; i < depNames.length(); i++) {
                list.add(depNames.optJSONObject(i).optString("name"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void MyJsonReader1(String s) {
        try {
            JSONObject object = new JSONObject(s);
            depLocations = object.getJSONArray("data");
            list1.clear();
            for (int i = 0; i < depLocations.length(); i++) {
                list1.add(depLocations.optJSONObject(i).optString("name"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void action() {
        txtSave.setOnClickListener(this);
        txtBack.setOnClickListener(this);
        txtCancel.setOnClickListener(this);

        spDepName.setOnTouchListener((v, g) -> {
            Toast.makeText(Main3Activity.this, "99", Toast.LENGTH_SHORT).show();
            if (g.getAction() == MotionEvent.ACTION_DOWN) {
                //    showDialog();
            }
            return false;

        });
        spDepName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    JSONObject jsonObject = depNames.getJSONObject(i);
                    MyTask myTask1 = new MyTask(IP + "api/Values/getCmb") {
                        @Override
                        protected void onPostExecute(String s) {
                            super.onPostExecute(s);
                            MyJsonReader1(s);
                            strAdapter1.notifyDataSetChanged();
                            // strAdapter.setNotifyOnChange(true);
                        }
                    };
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("status", 2);
                    map.put("id", jsonObject.optString("id"));
                    myTask1.Post(map);


                    String sn = txtSN.getText().toString().substring(2);
                    Toast.makeText(Main3Activity.this, jsonObject.optString("id") + sn, Toast.LENGTH_SHORT).show();
                    int id = jsonObject.optInt("id");
                    String format = String.format("%02d", id);
                    txtnewsn.setText(format + sn);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spDepLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Main3Activity.this);
        builder.setTitle("Select Your Need !");
        builder.setMessage("OpenFileInPut Stream !");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }

    private JSONObject myJosnReader(String jj) {
        try {
            JSONObject data = new JSONObject(jj);
            String assetName = data.getString("assetName");
            String depName = data.getString("depName");
            String sn = data.getString("sn");
            String id = data.getString("id");
            id = dd;
            txtDep.setText(depName);
            txtAssetName.setText(assetName);
            txtSN.setText(sn);

            return data;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.heae_lblBack:
                finish();
                break;
            case R.id.txt3Cancel:
                finish();
                break;
            case R.id.txt3Save:
                save();
                break;
        }
    }

    private void save() {
        Toast.makeText(this, "Save Complete", Toast.LENGTH_SHORT).show();
        finish();
    }
}
