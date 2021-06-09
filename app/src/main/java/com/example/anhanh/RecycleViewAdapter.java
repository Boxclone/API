package com.example.anhanh;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.LinkedList;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.RecycleViewHolder> {

    private LinkedList<Pet> linkedList;
    private LayoutInflater inflater;
    private Context context;

    public RecycleViewAdapter(LinkedList<Pet> linkedList, Context context) {
        inflater = LayoutInflater.from(context);
        this.linkedList = linkedList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recycle_view, parent, false);
        return new RecycleViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewHolder holder, int position) {
        Pet pet = linkedList.get(position);
        holder.tvName.setText(pet.getNamePet());
        holder.imgPet.setImageResource(R.drawable.ic_pet);
    }

    @Override
    public int getItemCount() {
        return linkedList.size();
    }

    public void filterList(LinkedList<Pet> list) {
        linkedList = list;
        notifyDataSetChanged();
    }

    public class RecycleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private RecycleViewAdapter adapter;
        public TextView tvName;
        public ImageView imgPet;

        public RecycleViewHolder(View view, RecycleViewAdapter adapter) {
            super(view);

            tvName = view.findViewById(R.id.tvName);
            imgPet = view.findViewById(R.id.imgPet);

            this.adapter = adapter;

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            Pet element = linkedList.get(position);
            ImageView btnMinus = v.findViewById(R.id.btnMinus);
            ImageView btnEdit = v.findViewById(R.id.btnEdit);
            String url = "https://60b6457e17d1dc0017b87669.mockapi.io/Pet";

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, FormRegisterPet.class);

                    intent.putExtra("id", element.getIdPet());
                    intent.putExtra("name", element.getNamePet());

                    context.startActivity(intent);
                }
            });

            btnMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DeleteDataToJsonAPI(url, element.getIdPet());
                }
            });
            adapter.notifyDataSetChanged();
        }
        
        private void DeleteDataToJsonAPI(String url, int id) {
            StringRequest stringRequest = new StringRequest(
                    Request.Method.DELETE, url + '/' + id, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    linkedList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);
        }
    }
}
