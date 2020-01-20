package com.example.s1_20200119_vesion1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.OnLifecycleEvent;

import android.content.Intent;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class Main3Activity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtDep;
    private TextView txtSN;
    private TextView txtAssetName;
    private TextView txtCancel;
    private TextView txtSave;
    private TextView txtBack;
    private TextView txtHead;
    private TextView txtnewsn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Intent intent = getIntent();
        String data = intent.getStringExtra("data");

        txtAssetName = findViewById(R.id.txt3AstName);
        txtSN = findViewById(R.id.txt3sn);
        txtDep = findViewById(R.id.txt3CurrentDep);
        txtBack = findViewById(R.id.heae_lblBack);
        txtHead = findViewById(R.id.head_lblTitle);
        txtHead.setText("Asset Transfer");
        txtCancel = findViewById(R.id.txt3Cancel);
        txtSave = findViewById(R.id.txt3Save);

        txtnewsn = findViewById(R.id.txt3newsn);
        String format = String.format("%05s", 9, 8);
        txtnewsn.setText(format);


        myJosnReader(data);
        action();
    }

    private void action() {
        txtSave.setOnClickListener(this);
        txtBack.setOnClickListener(this);
        txtCancel.setOnClickListener(this);

    }

    private void myJosnReader(String jj) {
        try {
            JSONObject data = new JSONObject(jj);
            String assetName = data.getString("assetName");
            String depName = data.getString("depName");
            String sn = data.getString("sn");
            txtDep.setText(depName);
            txtAssetName.setText(assetName);
            txtSN.setText(sn);
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
