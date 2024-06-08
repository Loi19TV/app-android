package com.example.pj_quanlisinhvien_nhom9;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class DSSVActivity extends AppCompatActivity {
    ListView lvDSSV;
    ArrayAdapter adapter_DSSV;
    Button btnSapXep;
    TextView tvTongSoSinhVien;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dssvactivity);
        setControl();
        setEvent();
    }

    private void setControl() {
        lvDSSV = findViewById(R.id.lvDSSV);
        btnSapXep = findViewById(R.id.btnSapXep);
        tvTongSoSinhVien = findViewById(R.id.tvTongSoSinhVien);
    }

    private void setEvent() {
        //set back drawer
        // Sử dụng layout tùy chỉnh làm tiêu đề cho ActionBar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_menu_drawer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DB_SinhVien_Diem dbSinhVienDiem = new DB_SinhVien_Diem(this);
        //Tạo List chứa thông tin được xuất ra từ mỗi sinh viên
        List<String> danhSachThongTinSinhVien = new ArrayList<>();
        for (SinhVien sv : dbSinhVienDiem.DocDanhSachSinhVien()) {
            danhSachThongTinSinhVien.add(sv.XuatSinhVien());
        }
        //set adapter và ListView
        adapter_DSSV = new ArrayAdapter(this, android.R.layout.simple_list_item_1, danhSachThongTinSinhVien);
        lvDSSV.setAdapter(adapter_DSSV);

        //Tổng số sinh viên
        tvTongSoSinhVien.setText("Tổng số sinh viên: " + QuanLiSinhVienActivity.demSLSV);

        //Click button sắp xếp để sắp xếp sinh viên theo tên A - Z
//        btnSapXep.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Collections.sort(QuanLiSinhVienActivity.danhSachSinhVien, new Comparator<SinhVien>() {
//                    @Override
//                    public int compare(SinhVien sv1, SinhVien sv2) {
//                        return sv1.getHoTenSV().compareTo(sv2.getHoTenSV());
//                    }
//                });
//                //Tạo List chứa thông tin được xuất ra từ mỗi sinh viên
//                List<String> danhSachThongTinSinhVien2 = new ArrayList<>();
//                for (SinhVien sv : QuanLiSinhVienActivity.danhSachSinhVien) {
//                    danhSachThongTinSinhVien2.add(sv.XuatSinhVien());
//                }
//                //set adapter và ListView
//                ArrayAdapter adapter_DSSV2;
//                adapter_DSSV2 = new ArrayAdapter(DSSVActivity.this, android.R.layout.simple_list_item_1, danhSachThongTinSinhVien2);
//                lvDSSV.setAdapter(adapter_DSSV2);
//            }
//        });

        //Click để quay về màn hình quản lí sinh viên
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(DSSVActivity.this, QuanLiSinhVienActivity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
}