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

public class Adapter_Mon extends ArrayAdapter {
    Context context;
    int resource;
    List<MonHoc> data;
    public Adapter_Mon(@NonNull Context context, int resource, List<MonHoc> data) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);
        TextView tvTenMon = convertView.findViewById(R.id.tvTenMon);
        TextView tvSoTinChiMon = convertView.findViewById(R.id.tvSoTinChiMon);
        MonHoc mh = data.get(position);
        tvTenMon.setText(mh.getTenMon());
        tvSoTinChiMon.setText("Số tín chỉ: " + mh.getSoTinChi());
        return convertView;
    }
}
