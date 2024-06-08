package com.example.pj_quanlisinhvien_nhom9;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class QuanLiDiem_Activity extends AppCompatActivity {
    EditText edtNhapMSSVCanTim;
    EditText edtNhapTenMon;
    EditText edtNhapDiem;
    Spinner spDanhSachMaSV;
    ArrayAdapter adapter_DSP;
    Spinner spDanhSachHocKy;
    ArrayAdapter adapter_DSPHK;
    Button btnThem;
    Button btnXoa;
    Button btnSua;
    Button btnThongKe;
    ListView lvDanhSachDiemCacMon;
    ArrayAdapter adapter_DSD;
    int index = -1;
    DrawerLayout drawerLayout;
    NavigationView navView;
    ActionBarDrawerToggle drawerToggle;
    static String maSVSPDuocChon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_li_diem);
        setControl();
        setEvent();
    }
    private void setControl() {
        edtNhapMSSVCanTim = findViewById(R.id.edtNhapMSSVCanTim);
        edtNhapTenMon = findViewById(R.id.edtNhapTenMon);
        edtNhapDiem = findViewById(R.id.edtNhapDiem);
        spDanhSachMaSV = findViewById(R.id.spDanhSachMaSV);
        spDanhSachHocKy = findViewById(R.id.spDanhSachHocKy);
        btnThem = findViewById(R.id.btnThem);
        btnXoa = findViewById(R.id.btnXoa);
        btnSua = findViewById(R.id.btnSua);
        btnThongKe = findViewById(R.id.btnThongKe);
        lvDanhSachDiemCacMon = findViewById(R.id.lvDanhSachDiemCacMon);
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
                    Intent intent = new Intent(QuanLiDiem_Activity.this, StartActivity.class);
                    startActivity(intent);
                    Toast.makeText(QuanLiDiem_Activity.this, "Đã chọn màn hình chính", Toast.LENGTH_LONG).show();
                }
                if (item.getItemId() == R.id.mnKhoa){
                    Intent intent = new Intent(QuanLiDiem_Activity.this, ChoseDepartmentActivity.class);
                    startActivity(intent);
                    Toast.makeText(QuanLiDiem_Activity.this, "Đã chọn màn hình khoa", Toast.LENGTH_LONG).show();
                }
                if (item.getItemId() == R.id.mnSinhVien){
                    Intent intent = new Intent(QuanLiDiem_Activity.this, QuanLiSinhVienActivity.class);
                    startActivity(intent);
                    Toast.makeText(QuanLiDiem_Activity.this, "Đã chọn màn hình vinh viên", Toast.LENGTH_LONG).show();
                }
                if (item.getItemId() == R.id.mnDiem){
                    Toast.makeText(QuanLiDiem_Activity.this, "Đã chọn màn hình điểm", Toast.LENGTH_LONG).show();
                }
                if (item.getItemId() == R.id.mnCaiDat){
                    Toast.makeText(QuanLiDiem_Activity.this, "Đã chọn màn hình cài đặt", Toast.LENGTH_LONG).show();
                }
                drawerLayout.closeDrawers();
                return false;
            }
        });
        //>>
        DB_SinhVien_Diem dbSinhVienDiem = new DB_SinhVien_Diem(QuanLiDiem_Activity.this);
        List<String> dsMaSV = new ArrayList<>();
        for (SinhVien sv : dbSinhVienDiem.DocDanhSachSinhVien())
        {
            dsMaSV.add(sv.getMaSV());
        }

        //set spinner mã sinh viên
        adapter_DSP = new ArrayAdapter(QuanLiDiem_Activity.this, android.R.layout.simple_list_item_1, dsMaSV);
        spDanhSachMaSV.setAdapter(adapter_DSP);

        spDanhSachMaSV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maSVSPDuocChon = dsMaSV.get(position).toString();
                spDanhSachHocKy.setSelection(0);
                List<String> dsDiemMonHoc = new ArrayList<>();
                for (MonHocSV mhSV : dbSinhVienDiem.taoBangDiemChoSinhVien(maSVSPDuocChon, "HK1"))
                {
                    dsDiemMonHoc.add(mhSV.XuatMonHoc());
                }
                adapter_DSD = new ArrayAdapter(QuanLiDiem_Activity.this, android.R.layout.simple_list_item_1, dsDiemMonHoc);
                lvDanhSachDiemCacMon.setAdapter(adapter_DSD);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //set spinner học kỳ
        String[] dsHocKy = {"HK1", "HK2", "HK3"};
        adapter_DSPHK = new ArrayAdapter(QuanLiDiem_Activity.this, android.R.layout.simple_list_item_1, dsHocKy);
        spDanhSachHocKy.setAdapter(adapter_DSPHK);

        spDanhSachHocKy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String hocKyDuocChon = (String) parent.getItemAtPosition(position);
                List<String> dsDiemMonHoc = new ArrayList<>();
                for (MonHocSV mhSV : dbSinhVienDiem.taoBangDiemChoSinhVien(maSVSPDuocChon, hocKyDuocChon))
                {
                    dsDiemMonHoc.add(mhSV.XuatMonHoc());
                }
                adapter_DSD.clear();
                adapter_DSD.addAll(dsDiemMonHoc);
                adapter_DSD.notifyDataSetChanged();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //Click Button Thêm để điểm sinh vien
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtNhapTenMon.getText().toString()) || TextUtils.isEmpty(edtNhapDiem.getText().toString()))
                {
                    Toast.makeText(QuanLiDiem_Activity.this, "Vui lòng nhập đầy đủ dữ liệu!", Toast.LENGTH_LONG).show();
                }
                else
                {
                    dbSinhVienDiem.ThemMonDiem(new MonHocSV(edtNhapTenMon.getText().toString(), Integer.parseInt(edtNhapDiem.getText().toString())));
                    adapter_DSD.clear();
                    List<String> dsDiemMonHoc = new ArrayList<>();
                    for (MonHocSV mhSV : dbSinhVienDiem.DocDanhSachDiem())
                    {
                        dsDiemMonHoc.add(mhSV.XuatMonHoc());
                    }
                    adapter_DSD.addAll(dsDiemMonHoc);
                    adapter_DSD.notifyDataSetChanged();
                    Toast.makeText(QuanLiDiem_Activity.this, "Thêm điểm thành công!", Toast.LENGTH_LONG).show();
                }
            }
        });

        //Set click item lisview lấy position
        lvDanhSachDiemCacMon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                index = position;
            }
        });

        //Xóa điểm của sinh viên
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index == -1)
                {
                    Toast.makeText(QuanLiDiem_Activity.this, "Bạn cần chọn!", Toast.LENGTH_LONG).show();
                    return;
                }
                for (MonHocSV mh : dbSinhVienDiem.DocDanhSachDiem())
                {

                }
                dbSinhVienDiem.XoaMonDiem(dbSinhVienDiem.DocDanhSachDiem().get(index).getTenMonHoc());
                adapter_DSD.clear();
                List<String> dsDiemMonHoc = new ArrayList<>();
                for (MonHocSV mhSV : dbSinhVienDiem.DocDanhSachDiem())
                {
                    dsDiemMonHoc.add(mhSV.XuatMonHoc());
                }
                adapter_DSD.addAll(dsDiemMonHoc);
                adapter_DSD.notifyDataSetChanged();
                Toast.makeText(QuanLiDiem_Activity.this, "Xóa điểm thành công!", Toast.LENGTH_LONG).show();
                index = -1;
                return;
            }
        });

        //Sửa điêm sinh viên
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index == -1)
                {
                    Toast.makeText(QuanLiDiem_Activity.this, "Bạn cần chọn!", Toast.LENGTH_LONG).show();
                    return;
                }
                else if (TextUtils.isEmpty(edtNhapTenMon.getText().toString()) || TextUtils.isEmpty(edtNhapDiem.getText().toString()))
                {
                    Toast.makeText(QuanLiDiem_Activity.this, "Vui lòng nhập đầy đủ dữ liệu để sửa điểm!", Toast.LENGTH_LONG).show();
                }
                else
                {
                    MonHocSV mhSV = new MonHocSV();
                    mhSV.setTenMonHoc(edtNhapTenMon.getText().toString());
                    mhSV.setDiemMonHoc(Integer.parseInt(edtNhapDiem.getText().toString()));
                    dbSinhVienDiem.SuaMonDiem(mhSV, dbSinhVienDiem.DocDanhSachDiem().get(index).getTenMonHoc());
                    adapter_DSD.clear();
                    List<String> dsDiemMonHoc = new ArrayList<>();
                    for (MonHocSV mhSV1 : dbSinhVienDiem.DocDanhSachDiem())
                    {
                        dsDiemMonHoc.add(mhSV1.XuatMonHoc());
                    }
                    adapter_DSD.addAll(dsDiemMonHoc);
                    adapter_DSD.notifyDataSetChanged();
                    Toast.makeText(QuanLiDiem_Activity.this, "Sửa điểm thành công!", Toast.LENGTH_LONG).show();
                    index = -1;
                    return;
                }
            }
        });

        // Thống kê
        btnThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int demSLSVGHK1 = 0, demSLSVGHK2 = 0, demSLSVGHK3 = 0;

                for (String maSV : dsMaSV) {
                    int demHK1 = 0, demHK2 = 0, demHK3 = 0;
                    float tongDiemSVHK1 = 0, tongDiemSVHK2 = 0, tongDiemSVHK3 = 0;

                    for (MonHocSV mhSV : dbSinhVienDiem.taoBangDiemChoSinhVien(maSV, "HK1")) {
                        demHK1++;
                        tongDiemSVHK1 += mhSV.getDiemMonHoc();
                    }

                    for (MonHocSV mhSV : dbSinhVienDiem.taoBangDiemChoSinhVien(maSV, "HK2")) {
                        demHK2++;
                        tongDiemSVHK2 += mhSV.getDiemMonHoc();
                    }

                    for (MonHocSV mhSV : dbSinhVienDiem.taoBangDiemChoSinhVien(maSV, "HK3")) {
                        demHK3++;
                        tongDiemSVHK3 += mhSV.getDiemMonHoc();
                    }

                    float diemTrungBinhHK1 = demHK1 > 0 ? tongDiemSVHK1 / demHK1 : 0;
                    float diemTrungBinhHK2 = demHK2 > 0 ? tongDiemSVHK2 / demHK2 : 0;
                    float diemTrungBinhHK3 = demHK3 > 0 ? tongDiemSVHK3 / demHK3 : 0;

                    if (diemTrungBinhHK1 >= 8) {
                        demSLSVGHK1++;
                    }

                    if (diemTrungBinhHK2 >= 8) {
                        demSLSVGHK2++;
                    }

                    if (diemTrungBinhHK3 >= 8) {
                        demSLSVGHK3++;
                    }
                }

                Intent intent = new Intent(QuanLiDiem_Activity.this, ThongKeActivity.class);
                intent.putExtra("hk1", String.valueOf(demSLSVGHK1));
                intent.putExtra("hk2", String.valueOf(demSLSVGHK2));
                intent.putExtra("hk3", String.valueOf(demSLSVGHK3));
                intent.putExtra("tongSV", String.valueOf(dsMaSV.size()));
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