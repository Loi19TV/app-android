package com.example.pj_quanlisinhvien_nhom9;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class QuanLiSinhVienActivity extends AppCompatActivity {
    EditText edtNhapMSSVCanTim;
    EditText edtNhapMSSV;
    EditText edtNhapHoTen;
    EditText edtNhapLop;
    TextView tvThongTinSinhVien;
    Button btnTimTTSV;
    Button btnThem;
    Button btnXoa;
    Button btnSua;
    Button btnDSSV;
    static int demSLSV = 0;
    DrawerLayout drawerLayout;
    NavigationView navView;
    ActionBarDrawerToggle drawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_li_sinh_vien);
        setControl();
        setEvent();
    }
    private void setControl() {
        edtNhapMSSVCanTim = findViewById(R.id.edtNhapMSSVCanTim);
        edtNhapMSSV = findViewById(R.id.edtNhapMSSV);
        edtNhapHoTen = findViewById(R.id.edtNhapHoTen);
        edtNhapLop = findViewById(R.id.edtNhapLop);
        tvThongTinSinhVien = findViewById(R.id.tvThongTinSinhVien);
        btnThem = findViewById(R.id.btnThem);
        btnXoa = findViewById(R.id.btnXoa);
        btnSua = findViewById(R.id.btnSua);
        btnDSSV = findViewById(R.id.btnDSSV);
        btnTimTTSV = findViewById(R.id.btnTimTTSV);
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
                    Intent intent = new Intent(QuanLiSinhVienActivity.this, StartActivity.class);
                    startActivity(intent);
                    Toast.makeText(QuanLiSinhVienActivity.this, "Đã chọn màn hình chính", Toast.LENGTH_LONG).show();
                }
                if (item.getItemId() == R.id.mnKhoa){
                    Intent intent = new Intent(QuanLiSinhVienActivity.this, ChoseDepartmentActivity.class);
                    startActivity(intent);
                    Toast.makeText(QuanLiSinhVienActivity.this, "Đã chọn màn hình khoa", Toast.LENGTH_LONG).show();
                }
                if (item.getItemId() == R.id.mnSinhVien){
                    Toast.makeText(QuanLiSinhVienActivity.this, "Đã chọn màn hình vinh viên", Toast.LENGTH_LONG).show();
                }
                if (item.getItemId() == R.id.mnDiem){
                    Intent intent = new Intent(QuanLiSinhVienActivity.this, QuanLiDiem_Activity.class);
                    startActivity(intent);
                    Toast.makeText(QuanLiSinhVienActivity.this, "Đã chọn màn hình điểm", Toast.LENGTH_LONG).show();
                }
                if (item.getItemId() == R.id.mnCaiDat){
                    Toast.makeText(QuanLiSinhVienActivity.this, "Đã chọn màn hình cài đặt", Toast.LENGTH_LONG).show();
                }
                drawerLayout.closeDrawers();
                return false;
            }
        });
        //>>

        DB_SinhVien_Diem dbSinhVienDiem = new DB_SinhVien_Diem(this);
        demSLSV = dbSinhVienDiem.DocDanhSachSinhVien().size();

        //Tìm để xem thông tin sinh viên
        btnTimTTSV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtNhapMSSVCanTim.getText().toString()))
                {
                    Toast.makeText(QuanLiSinhVienActivity.this, "Vui lòng nhập mã sinh viên để tìm!", Toast.LENGTH_LONG).show();
                }
                else
                {
                    for (SinhVien item : dbSinhVienDiem.DocDanhSachSinhVien()) {
                        if (edtNhapMSSVCanTim.getText().toString().equals(item.getMaSV())) {
                            tvThongTinSinhVien.setText(item.XuatSinhVien());
                            return;
                        }
                    }
                    Toast.makeText(QuanLiSinhVienActivity.this, "Mã sinh viên không tồn tại!", Toast.LENGTH_LONG).show();
                }
            }
        });

        //Click Button Thêm để thêm sinh vien
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtNhapMSSV.getText().toString()) || TextUtils.isEmpty(edtNhapHoTen.getText().toString()) || TextUtils.isEmpty(edtNhapLop.getText().toString()))
                {
                    Toast.makeText(QuanLiSinhVienActivity.this, "Vui lòng nhập đầy đủ dữ liệu!", Toast.LENGTH_LONG).show();
                }
                else
                {
                    List<MonHoc> danhSachMonHoc = new ArrayList<>();
                    SinhVien sinhVien = new SinhVien(edtNhapMSSV.getText().toString(), edtNhapHoTen.getText().toString(), edtNhapLop.getText().toString(), danhSachMonHoc);
                    dbSinhVienDiem.ThemSinhVien(sinhVien);
                    tvThongTinSinhVien.setText(sinhVien.XuatSinhVien());
                    demSLSV++;
                    Toast.makeText(QuanLiSinhVienActivity.this, "Thêm sinh viên thành công!", Toast.LENGTH_LONG).show();
                }
            }
        });

        //Click Button Xóa để xóa sinh viên
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtNhapMSSVCanTim.getText().toString()))
                {
                    Toast.makeText(QuanLiSinhVienActivity.this, "Vui lòng nhập mã sinh viên để tìm và xóa!", Toast.LENGTH_LONG).show();
                }
                else
                {
                    for (SinhVien item : dbSinhVienDiem.DocDanhSachSinhVien()) {
                        if (edtNhapMSSVCanTim.getText().toString().equals(item.getMaSV())) {
                            dbSinhVienDiem.XoaSinhVienBangMa(item.getMaSV());
                            tvThongTinSinhVien.setText("");
                            demSLSV--;
                            Toast.makeText(QuanLiSinhVienActivity.this, "Xóa sinh viên thành công!", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                    Toast.makeText(QuanLiSinhVienActivity.this, "Mã sinh viên không tồn tại!", Toast.LENGTH_LONG).show();
                }
            }
        });

        //Click Button sửa để sửa sinh viên
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtNhapMSSVCanTim.getText().toString()))
                {
                    Toast.makeText(QuanLiSinhVienActivity.this, "Vui lòng nhập mã sinh viên để tìm và sửa!", Toast.LENGTH_LONG).show();
                }
                else if (TextUtils.isEmpty(edtNhapMSSV.getText().toString()) || TextUtils.isEmpty(edtNhapHoTen.getText().toString()) || TextUtils.isEmpty(edtNhapLop.getText().toString()))
                {
                    Toast.makeText(QuanLiSinhVienActivity.this, "Vui lòng nhập đầy đủ dữ liệu để sửa!", Toast.LENGTH_LONG).show();
                }
                else
                {
                    for (SinhVien item : dbSinhVienDiem.DocDanhSachSinhVien()) {
                        if (edtNhapMSSVCanTim.getText().toString().equals(item.getMaSV())) {
                            List<MonHoc> danhSachMonHoc = new ArrayList<>();
                            SinhVien sinhVienMoi = new SinhVien(edtNhapMSSV.getText().toString(), edtNhapHoTen.getText().toString(), edtNhapLop.getText().toString(), danhSachMonHoc);
                            dbSinhVienDiem.SuaSinhVien(sinhVienMoi, item);
                            tvThongTinSinhVien.setText(sinhVienMoi.XuatSinhVien());
                            Toast.makeText(QuanLiSinhVienActivity.this, "Sửa sinh viên thành công!", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                    Toast.makeText(QuanLiSinhVienActivity.this, "Mã sinh viên không tồn tại!", Toast.LENGTH_LONG).show();
                }
            }
        });

        //Click Button DSSV để sang màn hình danh sách sinh viên
        btnDSSV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuanLiSinhVienActivity.this, DSSVActivity.class);
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