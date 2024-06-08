package com.example.pj_quanlisinhvien_nhom9;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText edtInputName;
    EditText edtInputPass;
    Button btnLogin;
    Button btnSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setControl();
        setEvent();
    }
    private void setControl() {
        btnLogin = findViewById(R.id.btnLogin);
        edtInputName = findViewById(R.id.edtInputName);
        edtInputPass = findViewById(R.id.edtInputPass);
        btnSignUp = findViewById(R.id.btnSignUp);
    }
    private void setEvent() {
        //set back drawer
        // Sử dụng layout tùy chỉnh làm tiêu đề cho ActionBar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_menu_drawer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Click button Login để sang màn hình bắt đầu (StartActivity)
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtInputName.getText().toString()) || TextUtils.isEmpty(edtInputPass.getText().toString()))
                {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập name và password!", Toast.LENGTH_LONG).show();
                    return;
                }
                if (edtInputName.getText().toString().equals("admin") && edtInputPass.getText().toString().equals("123"))
                {
                    Intent intent = new Intent(MainActivity.this, StartActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Sai name và password!", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });

        //Click button SignUp để sang màn hình đăng ký (SignUpActivity)
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}