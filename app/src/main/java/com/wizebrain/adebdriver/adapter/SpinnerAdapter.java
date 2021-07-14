package com.wizebrain.adebdriver.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.wizebrain.adebdriver.R;

import java.util.List;

public class SpinnerAdapter extends BaseAdapter {
    Context context;

  //  List<String> list;
String [] list;
    LayoutInflater inflter;
    public static String siid;

    public SpinnerAdapter(Context context, String[] list) {
        this.context = context;
        this.list = list;
        inflter = (LayoutInflater.from(context));
    }
    @Override
    public int getCount() {
        return list.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.spinner_row, null);
        final TextView status = (TextView) view.findViewById(R.id.cart_cat_name);

        status.setText(list[i]);

        return view;
    }
}
