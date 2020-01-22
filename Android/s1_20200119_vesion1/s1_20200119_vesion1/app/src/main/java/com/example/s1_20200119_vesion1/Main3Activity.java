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
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Intent intent = getIntent();
        String joo = intent.getStringExtra("data");


        txtAssetName = findViewById(R.id.txt3AstName);
        txtSN = findViewById(R.id.txt3sn);
        txtDep = findViewById(R.id.txt3CurrentDep);
        txtBack = findViewById(R.id.heae_lblBack);
        txtHead = findViewById(R.id.head_lblTitle);
        txtHead.setText("Asset Transfer");
        txtCancel = findViewById(R.id.txt3Cancel);
        txtSave = findViewById(R.id.txt3Save);

        spDepName = findViewById(R.id.sp3DepName);
        spDepLocation = findViewById(R.id.sp3DepLocation);


//        openFileInput("oue");

        txtnewsn = findViewById(R.id.txt3newsn);

        txtnewsn.setText(dd + gg + nnnn);
        JSONObject data = myJosnReader(joo);
        action();
        list = new ArrayList<>();
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


        strAdapter = new ArrayAdapter<>(Main3Activity.this, R.layout.support_simple_spinner_dropdown_item, list);
      //  locationAdapter = new MyAdapter(depLocations, Main3Activity.this);
        Spinner sp = findViewById(R.id.spinner);
        sp.setAdapter(new ArrayAdapter<String>(Main3Activity.this,R.layout.support_simple_spinner_dropdown_item,new String[]{"1","2","3"}));

        spDepName.setAdapter(strAdapter);
        spDepLocation.setAdapter(strAdapter);
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

    private void action() {
        txtSave.setOnClickListener(this);
        txtBack.setOnClickListener(this);
        txtCancel.setOnClickListener(this);

        spDepName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Toast.makeText(Main3Activity.this, "99", Toast.LENGTH_SHORT).show();
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    //    showDialog();
                }
                return false;
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
