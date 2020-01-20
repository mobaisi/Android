package com.example.s1_20200119_vesion1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String IP = "http://192.168.43.193/";
    private Spinner spDep;
    private Spinner spAssetGroup;
    private ImageView imgAdd;
    private ImageView imgDate1;
    private ImageView imgDate2;
    private SearchView txtFinder;
    private ListView lst_1;
    private TextView txtCount;
    private TextView txtDate2;
    private TextView txtDate1;
    private String[] deps;
    private String[] groups;
    private ArrayAdapter<String> groupAdapter;
    private ArrayAdapter<String> depAdapter;
    private MyAdapter myAdapter;
    private JSONArray datas;
    private SimpleDateFormat simpleDateFormat;
    private TextView lblClear;
    private ConstraintLayout layout1;
    private ConstraintLayout layout0;
    private ConstraintLayout layout2;
    private ListView lst_2;
    private int orientation = 1;
    private MyAdapter myAdapter1;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        orientation = newConfig.orientation;

        // 1竖屏 2 横屏
        /*        if (orientation==newConfig.ORIENTATION_PORTRAIT)*/
        if (orientation == 2) {
            layout0.setVisibility(View.GONE);
            layout1.setVisibility(View.GONE);
            layout2.setVisibility(View.VISIBLE);
        } else {
            layout2.setVisibility(View.GONE);
            layout0.setVisibility(View.VISIBLE);
            layout1.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spDep = findViewById(R.id.sp3DepName);
        spAssetGroup = findViewById(R.id.spAssetGroup);
        imgAdd = findViewById(R.id.imgAdd);
        imgDate1 = findViewById(R.id.imgDate1);
        imgDate2 = findViewById(R.id.imgDate2);
        txtFinder = findViewById(R.id.txtFinder1);
        lst_1 = findViewById(R.id.lst_F1);
        txtDate1 = findViewById(R.id.txtDate1);
        txtDate2 = findViewById(R.id.txtDate2);
        txtCount = findViewById(R.id.txtCount);
        lblClear = findViewById(R.id.lblClear);
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        layout2 = findViewById(R.id.layout2);
        layout0 = findViewById(R.id.layout0);
        layout1 = findViewById(R.id.layout_1);
        lst_2 = findViewById(R.id.lst_F1_2);
        InitString();
        adapter();
        action();
    }

    private void action() {
        imgAdd.setOnClickListener(this);
        imgDate1.setOnClickListener(this);
        imgDate2.setOnClickListener(this);
        spAssetGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                finder();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        txtFinder.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                finder();
                return false;
            }
        });
        spDep.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                finder();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        lblClear.setOnClickListener(this);
    }

    private void adapter() {
        depAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, deps);
        groupAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, groups);
        spDep.setAdapter(depAdapter);
        spAssetGroup.setAdapter(groupAdapter);
        datas = new JSONArray();

        myAdapter = new MyAdapter(datas, MainActivity.this) {


            private TextView txt3;
            private TextView txt2;
            private TextView txt1;
            private ImageView img3;
            private ImageView img2;
            private ImageView img1;
            private ImageView imgIcon;

            @Override
            public void onClick(View view) {
                super.onClick(view);
                switch (view.getId()) {
                    case R.id.lst_img1:

                        break;
                    case R.id.lst_img2:

                        Intent intent = new Intent(MainActivity.this, Main3Activity.class);
                        try {
                            String s = datas.getJSONObject((int) view.getTag()).toString();
                            intent.putExtra("data", s);
                           // Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        startActivity(intent);
                        break;
                    case R.id.lst_img3:

                        break;

                }
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                VH vh = null;
                if (view == null && orientation == 1) {
                    view = LayoutInflater.from(MainActivity.this).inflate(R.layout.lst_layout1, null, false);
                    vh = new VH(view);
                    view.setTag(vh);
                } else if (view != null && orientation == 1) {
                    vh = (VH) view.getTag();
                }

                if (view == null && orientation == 2) {
                    view = LayoutInflater.from(MainActivity.this).inflate(R.layout.lst_layout2, null, false);
                    vh = new VH(view);
                    view.setTag(vh);
                } else if (view != null && orientation == 2) {
                    vh = (VH) view.getTag();
                }

                if (orientation == 1) {
                    imgIcon = vh.getView(R.id.lst_img_icon);
                    img1 = vh.getView(R.id.lst_img1);
                    img2 = vh.getView(R.id.lst_img2);
                    img3 = vh.getView(R.id.lst_img3);
                    txt1 = vh.getView(R.id.lst_txt1);
                    txt2 = vh.getView(R.id.lst_txt2);
                    txt3 = vh.getView(R.id.lst_txt3);
                    img1.setTag(i);
                    img2.setTag(i);
                    img3.setTag(i);

                    img2.setOnClickListener(this);
                    try {
                        JSONObject data = datas.getJSONObject(i);
                        txt1.setText(data.getString("assetName"));
                        txt2.setText(data.getString("depName"));
                        txt3.setText(data.getString("sn"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                if (orientation == 2) {
                     imgIcon = vh.getView(R.id.lst2_imgIcon);
                     img1 = vh.getView(R.id.lst2_img1);
                     txt1 = vh.getView(R.id.lst2_txt1);
                     txt2 = vh.getView(R.id.lst2_txt2);
                    img1.setTag(i);
                    try {
                        JSONObject data = datas.getJSONObject(i);
                        txt1.setText(data.getString("assetName"));
                        txt2.setText(data.getString("sn"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                ;

                return view;
            }
        };

        lst_1.setAdapter(myAdapter);
        lst_2.setAdapter(myAdapter);


    }


    /* else if (view == null && orientation == 2) {
                    view = LayoutInflater.from(MainActivity.this).inflate(R.layout.lst_layout2, null, false);
                    vh = new VH(view);
                    ImageView imgIcon = vh.getView(R.id.lst_img_icon);
                    ImageView img1 = vh.getView(R.id.lst2_img1);
                    TextView txt1 = vh.getView(R.id.lst2_txt1);
                    TextView txt2 = vh.getView(R.id.lst2_txt2);

                    try {
                        JSONObject data = datas.getJSONObject(i);
                        txt1.setText(data.getString("assetName"));
                        txt2.setText(data.getString("sn"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    view.setTag(vh);
                }*/


    /*   myAdapter1 = new MyAdapter(datas, MainActivity.this) {
         @Override
         public View getView(int i, View view, ViewGroup viewGroup) {
             VH vh;
             if (view == null) {
                 view = LayoutInflater.from(MainActivity.this).inflate(R.layout.lst_layout2, null, false);
                 vh = new VH(view);
                 view.setTag(vh);
             } else {
                 vh = (VH) view.getTag();
             }

             return view;
         }
     };*/
    private void InitString() {
        deps = new String[]{"Select Department", "Exploration"
                , "Production"
                , "Transportation"
                , "R&D"
                , "Distribution"
                , "QHSE"};
        groups = new String[]{"Select AssetGroup", "Hydraulic"
                , "Electrical"
                , "Mechanical "};

    }

    private void getDate(final TextView txt) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this);
        datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                Calendar instance = Calendar.getInstance();
                instance.set(i, i1, i2);
                txt.setText(simpleDateFormat.format(instance.getTime()));
            }
        });

        datePickerDialog.show();
    }

    ;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgDate1:
                getDate(txtDate1);
                break;
            case R.id.imgDate2:
                getDate(txtDate2);
                break;
            case R.id.lblClear:
                txtDate2.setText("End Date");
                txtDate1.setText("Start Date");
                spDep.setSelection(0);
                spAssetGroup.setSelection(0);
                break;
            case R.id.imgAdd:
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
        }
    }

    public void JooReader(String str) {
        try {
            JSONObject jsonObject = new JSONObject(str);
            datas = null;
            datas = jsonObject.optJSONArray("data");
            txtCount.setText(datas.length() + " assets from " + jsonObject.optString("count"));
            myAdapter.setDatas(datas);
            myAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void finder() {
        HashMap<String, Object> post = new HashMap<>();

        post.put("depid", spDep.getSelectedItemPosition());
        post.put("assetGroupId", spAssetGroup.getSelectedItemPosition());
        post.put("date2", txtDate2.getText().toString());
        //post.;
        MyTask myTask = new MyTask(IP + "api/Values/PostList") {
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JooReader(s);
                Log.d("ContentValues:", "onPostExecute: " + s);
            }
        };
        myTask.Post(post);

    }
}
