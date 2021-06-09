package com.example.anhanh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    Button btnRegister, btnSignIn;
    EditText edt_username, edt_password, edt_repass;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        mappingId();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DangKy();
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
    }

    private void DangKy() {
        String username = edt_username.getText().toString();
        String password = edt_password.getText().toString();
        String repass = edt_repass.getText().toString();

        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password quá ngắn, tối thiểu phải 6 ký tự!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!repass.equals(password)){
            Toast.makeText(getApplicationContext(), "Mật khẩu không trùng khớp!", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(username, repass).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this,"Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }else {
                    Toast.makeText(MainActivity.this,"Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void mappingId() {
        btnRegister = findViewById(R.id.btnRegister);
        btnSignIn = findViewById(R.id.btnSignIn);

        edt_password = findViewById(R.id.edt_password);
        edt_repass = findViewById(R.id.edt_repassword);
        edt_username = findViewById(R.id.edt_username);


    }
}