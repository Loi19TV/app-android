package com.example.pj_quanlisinhvien_nhom9;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class Adapter_Khoa extends ArrayAdapter {
    Context context;
    int resource;
    List<String> data;

    public Adapter_Khoa(@NonNull Context context, int resource, List<String> data) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);
        TextView tvTenKhoa = convertView.findViewById(R.id.tvTenKhoa);
        String tenKhoa = data.get(position);
        tvTenKhoa.setText(tenKhoa);
        return convertView;
    }
}
