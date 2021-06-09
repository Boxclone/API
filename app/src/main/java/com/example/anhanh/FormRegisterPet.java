package com.example.anhanh;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class FormRegisterPet extends AppCompatActivity {

    EditText edt_name;

    Button btnDangKy, btnUpdate;

    ImageView btnPrevious;

    String url = "https://60b6e30617d1dc0017b887cc.mockapi.io/Pet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_register_pet);

        mappingId();

        Intent intent = getIntent();
        int idPet = intent.getIntExtra("id", 0);
        String namePet = intent.getStringExtra("name");

        if (namePet != null) {
            btnDangKy.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.VISIBLE);

            edt_name.setText(namePet);
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putDataToJsonAPI(url, idPet);
                Intent intent = new Intent(FormRegisterPet.this, PetDirectActivity.class);
                startActivity(intent);
            }
        });

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FormRegisterPet.this, PetDirectActivity.class);
                startActivity(intent);
            }
        });

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postDataToJsonAPI(url);
            }
        });
    }

    private void putDataToJsonAPI(String url, int idPet) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.PUT, url + '/' + idPet, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(FormRegisterPet.this, "Update Successfully!", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FormRegisterPet.this, "Error by Post data!", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("id", idPet + "");
                params.put("name", edt_name.getText().toString() + "");

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void postDataToJsonAPI(String url) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(FormRegisterPet.this, "Thêm dữ liệu thành công", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(FormRegisterPet.this, "Lỗi khi thêm dữ liệu!", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Random random = new Random();
                int id = random.nextInt(1000);
                HashMap<String, String> params = new HashMap<>();
                params.put("id", id + "");
                params.put("name", edt_name.getText().toString() + "");

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void mappingId() {

        edt_name = findViewById(R.id.edt_name);

        btnDangKy = findViewById(R.id.btnDangKy);
        btnPrevious = findViewById(R.id.btnPrevious);
        btnUpdate = findViewById(R.id.btnUpdate);
    }
}