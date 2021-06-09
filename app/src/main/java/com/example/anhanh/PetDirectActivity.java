package com.example.anhanh;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

public class PetDirectActivity extends AppCompatActivity {

    LinkedList<Pet> linkedList = new LinkedList<>();
    RecycleViewAdapter adapter;
    RecyclerView recyclerView;
    ImageView btnAdd;

    String url = "https://60b6457e17d1dc0017b87669.mockapi.io/Pet";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_direct);

        mappingId();

        getDataFromJsonAPI();
        
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PetDirectActivity.this, FormRegisterPet.class);
                startActivity(intent);
            }
        });
    }

    private void getDataFromJsonAPI() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = (JSONObject) response.get(i);
                        int idPet = object.getInt("id");
                        String namePet = object.getString("name");
                        linkedList.add(new Pet(idPet, namePet));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapter = new RecycleViewAdapter(linkedList, PetDirectActivity.this);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new GridLayoutManager(PetDirectActivity.this, 2));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PetDirectActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void mappingId() {

        recyclerView = findViewById(R.id.recycleView);

        btnAdd = findViewById(R.id.btnAdd);
    }
}