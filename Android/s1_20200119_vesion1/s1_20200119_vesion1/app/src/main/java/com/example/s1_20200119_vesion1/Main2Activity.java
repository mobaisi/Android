package com.example.s1_20200119_vesion1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.sql.BatchUpdateException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.SimpleFormatter;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {
    private String IP = "http://192.168.1.7/";
    private Spinner spAssetGroup;
    private Spinner spUnKnow;
    private TextView lblCancel;
    private TextView lblSave;
    private TextView lblBack;
    private TextView lblTitle;
    private ImageView imgDate;
    private TextView txtDate;
    private TextView txtSN;
    private EditText edt2AssetName;
    private EditText edt2Description;
    private Button btn1;
    private Button btn2;
    private ArrayList<String> lst3;
    private ArrayList<String> lst4;
    private JSONArray depNames;
    private ArrayList<String> lst1;
    private ArrayList<String> lst2;
    private Spinner spDepName;
    private Spinner spDepLocation;
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> adapter1;
    private ArrayAdapter<String> adapter2;
    private ArrayAdapter<String> adapter4;
    private JSONArray depLocation;
    private JSONArray assetGroups;
    private JSONArray unKnows;
    private SimpleDateFormat simpleFormatter;
    private String dd = "1";
    private int mm = 0;
    private String gg = "1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
      /*  dd = "1";
        gg = "1";
        mm = "1";*/
        init();
        Adepater();
        action();
    }

    private void action() {
        //imgDate.setOnClickListener(this);
        spDepName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                MyTask myTask1 = new MyTask(IP + "api/Values/getCmb") {
                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                        MyJsonReader(s);

                    }
                };
                HashMap<String, Object> map1 = new HashMap<>();
                map1.put("status", 2);
                map1.put("id", depNames.optJSONObject(i).optInt("id"));
                myTask1.Post(map1);

                dd = String.format("%02d", depNames.optJSONObject(i).optInt("id"));
                getSN();
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
        spAssetGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                gg = String.format("%02d", assetGroups.optJSONObject(i).optInt("id"));
                getSN();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void getSN() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("status", 4);
        map.put("name", dd + "/" + gg + "/");
        new MyTask(IP + "api/Values/getCmb") {
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                MyJsonReader(s);

                txtSN.setText(dd + "/" + gg + "/" + String.format("%04d", mm));
            }
        }.Post(map);

    }

    private void init() {
        simpleFormatter = new SimpleDateFormat("yyyy-MM-dd");

        spDepName = findViewById(R.id.sp2DepName);
        spDepLocation = findViewById(R.id.sp2DepLocation);
        spAssetGroup = findViewById(R.id.sp2AssetGroup);
        spUnKnow = findViewById(R.id.sp2UnKnow);
        lblCancel = findViewById(R.id.lblCancel);
        lblSave = findViewById(R.id.lblSave);
        lblBack = findViewById(R.id.heae_lblBack);
        lblTitle = findViewById(R.id.head_lblTitle);
        lblTitle.setText("Asset Information");
        imgDate = findViewById(R.id.img2Date);
        txtDate = findViewById(R.id.txt2Date);
        txtSN = findViewById(R.id.txt2Sn);
        edt2AssetName = findViewById(R.id.txt2AstName);
        edt2Description = findViewById(R.id.txt2Description);
        btn1 = findViewById(R.id.btnImage1
        );
        btn2 = findViewById(R.id.btnImage2
        );


        imgDate.setOnClickListener(this);
        lblBack.setOnClickListener(this);
        lblCancel.setOnClickListener(this::onClick);
        lblSave.setOnClickListener(this::onClick);


    }

    private void Adepater() {

        lst1 = new ArrayList<>();
        lst2 = new ArrayList<>();
        lst3 = new ArrayList<>();
        lst4 = new ArrayList<>();
        depNames = new JSONArray();
        depLocation = new JSONArray();
        assetGroups = new JSONArray();
        unKnows = new JSONArray();
        adapter = new ArrayAdapter<>(Main2Activity.this, R.layout.support_simple_spinner_dropdown_item, lst1);
        adapter1 = new ArrayAdapter<>(Main2Activity.this, R.layout.support_simple_spinner_dropdown_item, lst2);
        adapter2 = new ArrayAdapter<>(Main2Activity.this, R.layout.support_simple_spinner_dropdown_item, lst3);
        adapter4 = new ArrayAdapter<>(Main2Activity.this, R.layout.support_simple_spinner_dropdown_item, lst4);


        spDepName.setAdapter(adapter);
        spDepLocation.setAdapter(adapter1);
        spAssetGroup.setAdapter(adapter2);
        spUnKnow.setAdapter(adapter4);

        HashMap<String, Object> map = new HashMap<>();
        map.put("status", 1);
        new MyTask(IP + "api/Values/getCmb") {
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                MyJsonReader(s);

            }
        }.Post(map);


        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("status", 3);
        new MyTask(IP + "api/Values/getCmb") {
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                MyJsonReader(s);
            }
        }.Post(map1);


        HashMap map2 = new HashMap();
        map2.put("status", 5);
        new MyTask(IP + "api/Values/getCmb") {
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                MyJsonReader(s);
            }
        }.Post(map2);
    }

    private void MyJsonReader(String s) {
        try {
            JSONObject object = new JSONObject(s);
            if (object.optInt("msg") == 1) {
                depNames = object.getJSONArray("data");
                for (int i = 0; i < depNames.length(); i++) {
                    lst1.add(depNames.optJSONObject(i).optString("name"));
                }
                adapter.notifyDataSetChanged();
            } else if (object.optInt("msg") == 2) {

                depLocation = object.getJSONArray("data");
                lst2.clear();
                for (int i = 0; i < depLocation.length(); i++) {
                    lst2.add(depLocation.optJSONObject(i).optString("name"));
                }
                adapter1.notifyDataSetChanged();
            } else if (object.optInt("msg") == 3) {
                assetGroups = object.getJSONArray("data");
                lst3.clear();
                for (int i = 0; i < assetGroups.length(); i++) {
                    lst3.add(assetGroups.optJSONObject(i).optString("name"));
                }
                adapter2.notifyDataSetChanged();
            } else if (object.optInt("msg") == 4) {
                mm = object.optInt("count") + 1;
                Toast.makeText(Main2Activity.this, String.valueOf(mm), Toast.LENGTH_SHORT).show();
            } else if (object.optInt("msg") == 5) {
                unKnows = object.getJSONArray("data");
                lst4.clear();
                for (int i = 0; i < unKnows.length(); i++) {
                    lst4.add(unKnows.optJSONObject(i).optString("name"));
                }
                adapter4.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img2Date:
                getDate(txtDate);
                break;
            case R.id.heae_lblBack:
                finish();
                break;
            case R.id.lblSave:
                save();
                //     Toast.makeText(this, "Save Complete", Toast.LENGTH_SHORT).show();
                //  finish();
                break;
            case R.id.lblCancel:
                finish();
                break;

        }
    }

    private void save() {
        String assetName = edt2AssetName.getText().toString().trim();
        if (assetName.isEmpty()) {
            Toast.makeText(this, "AssetName is Empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        String description = edt2Description.getText().toString().trim();


        if (description.isEmpty()) {
            Toast.makeText(this, "Description is Empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (spDepLocation.getSelectedItemPosition() < 0) {
            Toast.makeText(this, "DepartmentLocation is not Selected!", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this, assetName, Toast.LENGTH_SHORT).show();

        HashMap<String, Object> map = new HashMap<>();

        try {
            map.put("assetName", assetName);
            map.put("description",description);
            map.put("assetGroup",  assetGroups.getJSONObject(spAssetGroup.getSelectedItemPosition()).optInt("id"));
            map.put("DepId",  depNames.getJSONObject(spDepName.getSelectedItemPosition()).optInt("id"));
            map.put("DepLocationId",  depLocation.getJSONObject(spDepLocation.getSelectedItemPosition()).optInt("id"));
            map.put("employeeId", unKnows.getJSONObject(spUnKnow.getSelectedItemPosition()).optInt("id"));
            map.put("SN", txtSN.getText().toString());
            map.put("date", txtDate.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        new MyTask(IP + "api/Values/postAsset").Post(map);

    }

    private void getDate(final TextView txt) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(Main2Activity.this);
        datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                Calendar instance = Calendar.getInstance();
                instance.set(i, i1, i2);
                txt.setText(simpleFormatter.format(instance.getTime()));
            }
        });

        datePickerDialog.show();
    }

}
