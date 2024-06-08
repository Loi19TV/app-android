package com.example.pj_quanlisinhvien_nhom9;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class StartActivity extends AppCompatActivity {
    Button btnKhoa;
    Button btnDiem;
    Button btnTTSV;
    Button btnWeb;
    DrawerLayout drawerLayout;
    NavigationView navView;
    ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        setControl();
        setEvent();
    }
    private void setControl() {
        btnKhoa = findViewById(R.id.btnKhoa);
        btnDiem = findViewById(R.id.btnDiem);
        btnTTSV = findViewById(R.id.btnTTSV);
        drawerLayout = findViewById(R.id.drawerLayout);
        navView = findViewById(R.id.navView);
    }

    private void setEvent() {
        //<< set drawer
        // Sử dụng layout tùy chỉnh làm tiêu đề cho ActionBar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_menu_drawer);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.mnStartScreen){
                    Toast.makeText(StartActivity.this, "Đã chọn màn hình chính", Toast.LENGTH_LONG).show();
                }
                if (item.getItemId() == R.id.mnKhoa){
                    Intent intent = new Intent(StartActivity.this, ChoseDepartmentActivity.class);
                    startActivity(intent);
                    Toast.makeText(StartActivity.this, "Đã chọn màn hình khoa", Toast.LENGTH_LONG).show();
                }
                if (item.getItemId() == R.id.mnSinhVien){
                    Intent intent = new Intent(StartActivity.this, QuanLiSinhVienActivity.class);
                    startActivity(intent);
                    Toast.makeText(StartActivity.this, "Đã chọn màn hình vinh viên", Toast.LENGTH_LONG).show();
                }
                if (item.getItemId() == R.id.mnDiem){
                    Intent intent = new Intent(StartActivity.this, QuanLiDiem_Activity.class);
                    startActivity(intent);
                    Toast.makeText(StartActivity.this, "Đã chọn màn hình điểm", Toast.LENGTH_LONG).show();
                }
                if (item.getItemId() == R.id.mnCaiDat){
                    Toast.makeText(StartActivity.this, "Đã chọn màn hình cài đặt", Toast.LENGTH_LONG).show();
                }
                drawerLayout.closeDrawers();
                return false;
            }
        });
        //>>

        //click Button Khoa để sang màn hình chọn khoa, danh sách khoa (ChoseDepartmentActivity)
        btnKhoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, ChoseDepartmentActivity.class);
                startActivity(intent);
            }
        });
        //click Button Điểm để sang màn hình xem điểm sinh viên (QuanLiDiemActivity)
        btnDiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, QuanLiDiem_Activity.class);
                startActivity(intent);
            }
        });


        //click Button TTSV để sang màn hình thông tin sinh viên (QuanLiDiemActivity)
        btnTTSV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, QuanLiSinhVienActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}