package com.example.pj_quanlisinhvien_nhom9;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class ThongKeActivity extends AppCompatActivity {
    TextView tvHK1, tvHK2, tvHK3, tvTongSV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);
        setControl();
        setEvent();
    }

    private void setControl() {
        tvHK1 = findViewById(R.id.tvHK1);
        tvHK2 = findViewById(R.id.tvHK2);
        tvHK3 = findViewById(R.id.tvHK3);
        tvTongSV = findViewById(R.id.tvTongSV);
    }

    private void setEvent() {
        //set back drawer
        // Sử dụng layout tùy chỉnh làm tiêu đề cho ActionBar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_menu_drawer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        tvHK1.setText(intent.getStringExtra("hk1"));
        tvHK2.setText(intent.getStringExtra("hk2"));
        tvHK3.setText(intent.getStringExtra("hk3"));
        tvTongSV.setText(intent.getStringExtra("tongSV"));
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(ThongKeActivity.this, QuanLiDiem_Activity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
}