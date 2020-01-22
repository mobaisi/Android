package com.example.s1_20200119_vesion1;

import android.content.Context;
import android.util.JsonReader;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.AbstractList;
import java.util.HashMap;

public class MyAdapter extends BaseAdapter implements View.OnClickListener {
    JSONArray datas;
    Context context;

    public MyAdapter(JSONArray jsonArray, Context context) {
        this.datas = jsonArray;
        this.context = context;
    }

    public JSONArray getDatas() {
        return datas;
    }

    public void setDatas(JSONArray datas) {
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.length();
    }

    @Override
    public JSONObject getItem(int i) {
        try {
            return datas.getJSONObject(i);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }

    @Override
    public void onClick(View view) {

    }

    class VH{
        private HashMap<Integer, View> views;
        private View parent;

        VH(View parent) {
            this.parent = parent;
            views = new HashMap<>();
        }
        public <T extends View>T getView(int id) {
            View view = views.get(id);
            if (view == null) {
                view = parent.findViewById(id);
                views.put(id, view);
            }
            return (T) view;
        }
    }
}
